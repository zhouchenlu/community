package zhou.hardcat.communtiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhou.hardcat.communtiy.mapper.QuestionMapper;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.model.Question;
import zhou.hardcat.communtiy.model.User;
import zhou.hardcat.communtiy.service.PublishService;

import javax.servlet.http.HttpServletRequest;

@Controller public class PublishController {
    @Autowired private QuestionMapper questionMapper;
    @Autowired private UserMapper userMapper;
    @Autowired private PublishService publishService;

    @GetMapping("/publish/{id}") public String modifed(@PathVariable(name = "id") Integer id, Model model) {
        Question question = questionMapper.selectById(id);
        model.addAttribute("description", question.getDescription());
        model.addAttribute("title", question.getTitle());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("questionId", question.getId());
        return "publish";
    }

    @GetMapping("/publish") public String toPublic(HttpServletRequest request) {
        return "publish";
    }

    @PostMapping("/publish") public String doPublic(@RequestParam(value = "title", required = false) String title,
        @RequestParam("description") String description, @RequestParam("tag") String tag,
        @RequestParam("questionId") Integer id, HttpServletRequest request, Model model) {

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("msg", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setId(id);
        question.setDescription(description);
        question.setTag(tag);
        question.setTitle(title);
        question.setCreator(user.getId());
        int isSuccess = publishService.modifiedOrCreate(question);
        if (isSuccess < 1) {
            model.addAttribute("msg", "系统异常,问题发布失败");
            return "publish";
        }
        model.addAttribute("msg", "发布成功");
        return "redirect:/";
    }

}