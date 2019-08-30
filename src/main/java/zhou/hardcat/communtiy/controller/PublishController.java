
package zhou.hardcat.communtiy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhou.hardcat.communtiy.mapper.QuestionMapper;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.model.Question;
import zhou.hardcat.communtiy.model.User;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String toPublic(HttpServletRequest request) {
        getUser(request, request.getCookies());
        return "publish";
    }

    @PostMapping("/publishing")
    public String doPublic(@RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("tag") String tag,
                           HttpServletRequest request,
                           Model model) {
        Cookie[] cookies = request.getCookies();
        User user = getUser(request, cookies);
        if(user==null){
            model.addAttribute("msg","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setGmtCreate(System.currentTimeMillis());
        question.setDescription(description);
        question.setTag(tag);
        question.setTitle(title);
        question.setCreator(user.getId());
        question.setGmtModified(question.getGmtCreate());
        int isInsert = questionMapper.insert(question);
        if(isInsert<1){
            model.addAttribute("msg","系统异常,问题发布失败");
        }
        model.addAttribute("msg","发布成功");
        return "publish";
    }

    private User getUser(HttpServletRequest request, Cookie[] cookies) {
        User user=null;
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        return user;
    }
}