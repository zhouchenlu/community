package zhou.hardcat.communtiy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import zhou.hardcat.communtiy.datatransferobject.PageInfoDTO;
import zhou.hardcat.communtiy.datatransferobject.QuestionDTO;
import zhou.hardcat.communtiy.model.User;
import zhou.hardcat.communtiy.service.QuestionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller public class ProfileController {
    @Resource private QuestionService questionService;

    @GetMapping("/profile/{action}") public String profile(@PathVariable(name = "action") String action, Model model,
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("msg", "登录信息过期,请先登录");
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PageInfoDTO allByUserId = questionService.findAllByUserId(user.getId(), page, pageSize);
            model.addAttribute("pageInfoDTO", allByUserId);
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";
    }

}
