package zhou.hardcat.communtiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhou.hardcat.communtiy.mapper.QuestionMapper;
import zhou.hardcat.communtiy.model.Question;
@Transactional
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    public int add(Question question) {
        return questionMapper.insertQuestion(question);
    }
}
