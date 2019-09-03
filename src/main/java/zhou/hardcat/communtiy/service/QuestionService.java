package zhou.hardcat.communtiy.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import zhou.hardcat.communtiy.datatransferobject.PageInfoDTO;
import zhou.hardcat.communtiy.datatransferobject.QuestionDTO;
import zhou.hardcat.communtiy.mapper.QuestionMapper;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.model.Question;
import zhou.hardcat.communtiy.model.User;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;
    public PageInfoDTO findAll(Integer page,Integer pageSize){
        PageInfoDTO pageInfoDTO=new PageInfoDTO();
        //先查数据总数
        int count=questionMapper.selectCount();
        //封装了一个处理pageInfoDTO对象的方法
        pageInfoDTO.setPageInfo(count,page,pageSize);
        List<Question> questions=questionMapper.selectAll(pageInfoDTO.getPageStart(),pageSize);
        return getPageInfoDTO(pageInfoDTO, questions);
    }

    public PageInfoDTO findAllByUserId(Integer id, Integer page, Integer pageSize) {
        PageInfoDTO pageInfoDTO=new PageInfoDTO();
        int count=questionMapper.selectCountByUserID(id);
        //封装了一个处理pageInfoDTO对象的方法
        pageInfoDTO.setPageInfo(count,page,pageSize);
        List<Question> questions=questionMapper.selectAllByUserId(id,pageInfoDTO.getPageStart(),pageSize);
        return getPageInfoDTO(pageInfoDTO, questions);
    }

    public QuestionDTO findById(Integer id) {
        QuestionDTO questionDTO=new QuestionDTO();
        Question  question=questionMapper.selectById(id);
        User user=userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }


    private PageInfoDTO getPageInfoDTO(PageInfoDTO pageInfoDTO, List<Question> questions) {
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO=new QuestionDTO();
            User user=userMapper.findById(question.getCreator());
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfoDTO.setQuestions(questionDTOList);
        return pageInfoDTO;
    }
}
