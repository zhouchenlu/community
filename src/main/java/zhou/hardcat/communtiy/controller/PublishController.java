package zhou.hardcat.communtiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.model.Question;
import zhou.hardcat.communtiy.model.User;
import zhou.hardcat.communtiy.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService QuestionService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String toPublic() {
        return "publish";
    }

    @PostMapping("/publish")
    public String postPublic(String title, String description, String tag,
                             HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        User user;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie)) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        Question question = new Question();
                        question.setCommentCount(0);
                        question.setCreator(user.getId());
                        question.setDescription(description);
                        question.setGmtCreate(System.currentTimeMillis());
                        question.setGmtModified(System.currentTimeMillis());
                        question.setLikeCount(0);
                        question.setTag(tag);
                        question.setTitle(title);
                        question.setViewCount(0);
                        int isSuccess = QuestionService.add(question);
                    }
                    break;
                }
            }
        }else{
         model.addAttribute("error","用户未登录");
        }
        return "publish";
    }
}
