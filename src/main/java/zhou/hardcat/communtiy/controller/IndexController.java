package zhou.hardcat.communtiy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhou.hardcat.communtiy.datatransferobject.PageInfoDTO;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.service.QuestionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller public class IndexController {
    @Resource private QuestionService questionService;

    @GetMapping("/") public String hello(HttpServletRequest request, Model model,
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "pageSize", defaultValue = "4") Integer pageSize) {
        PageInfoDTO all = questionService.findAll(page, pageSize);
        model.addAttribute("pageInfoDTO", all);
        return "index";
    }
}