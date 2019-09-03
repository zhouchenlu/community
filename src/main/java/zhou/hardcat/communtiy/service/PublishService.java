package zhou.hardcat.communtiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhou.hardcat.communtiy.exception.CustomizeException;
import zhou.hardcat.communtiy.mapper.QuestionMapper;
import zhou.hardcat.communtiy.model.Question;
@Service
public class PublishService {
    @Autowired
    private QuestionMapper questionMapper;
    public int modifiedOrCreate(Question question) {
        if(question.getId()==null){
            throw new CustomizeException("当前问题不存在,请核对!!");
        }
        Question dbQuestion  = questionMapper.selectById(question.getId());
        if(dbQuestion!=null){
            dbQuestion.setTitle(question.getTitle());
            dbQuestion.setTag(question.getTag());
            dbQuestion.setDescription(question.getDescription());
            dbQuestion.setGmtModified(System.currentTimeMillis());
           return questionMapper.updateById(dbQuestion);
        }
        question.setGmtCreate(System.currentTimeMillis());
        return questionMapper.insert(question);
    }
}
