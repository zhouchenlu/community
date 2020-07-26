package zhou.hardcat.communtiy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import zhou.hardcat.communtiy.datatransferobject.PageInfoDTO;
import zhou.hardcat.communtiy.datatransferobject.QuestionDTO;
import zhou.hardcat.communtiy.mapper.CommentMapper;
import zhou.hardcat.communtiy.mapper.QuestionMapper;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.model.Comment;
import zhou.hardcat.communtiy.model.Question;
import zhou.hardcat.communtiy.model.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service public class QuestionService {
    private static final Integer NUM_1 = 1;
    @Resource private QuestionMapper questionMapper;
    @Resource private UserMapper userMapper;
    @Resource private CommentMapper commentMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);

    public PageInfoDTO findAll(Integer page, Integer pageSize) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        //先查数据总数
        int count = questionMapper.selectCount();
        //封装了一个处理pageInfoDTO对象的方法
        pageInfoDTO.setPageInfo(count, page, pageSize);
        List<Question> questions = questionMapper.selectAll(pageInfoDTO.getPageStart(), pageSize);
        return getPageInfoDTO(pageInfoDTO, questions);
    }

    public PageInfoDTO findAllByUserId(Integer id, Integer page, Integer pageSize) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        int count = questionMapper.selectCountByUserID(id);
        //封装了一个处理pageInfoDTO对象的方法
        pageInfoDTO.setPageInfo(count, page, pageSize);
        List<Question> questions = questionMapper.selectAllByUserId(id, pageInfoDTO.getPageStart(), pageSize);
        return getPageInfoDTO(pageInfoDTO, questions);
    }

    public QuestionDTO findById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectById(id);
        questionMapper.updateByCondition(question.getViewCount() + NUM_1, question.getId());
        User user = userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        List<Comment> comments = commentMapper.selectById(id);
        for (Comment comment : comments) {
            comment.setUser(userMapper.findById(comment.getCommentator()));
        }
        questionDTO.setComment(comments);
        questionDTO.setUser(user);
        return questionDTO;
    }

    private PageInfoDTO getPageInfoDTO(PageInfoDTO pageInfoDTO, List<Question> questions) {
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findById(question.getCreator());
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfoDTO.setQuestions(questionDTOList);
        return pageInfoDTO;
    }

    public void dealComment(Integer id, String content, User user) {
        Comment comment = new Comment();
        comment.setCommentator(user.getId());
        comment.setContent(content);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setParentId(id);
        comment.setGmtModified(System.currentTimeMillis());
        comment.setType(0);
        // 入库comment表
        commentMapper.insert(comment);
        // 更新评论数question信息
        Question question = questionMapper.selectById(id);
        int isSuccess = 0;
        if (question != null) {
            isSuccess = questionMapper.updateCommentCount(question.getCommentCount() + NUM_1, id);
        }
        LOGGER.error("update comment counts isSuccess = {}", isSuccess);
    }
}
