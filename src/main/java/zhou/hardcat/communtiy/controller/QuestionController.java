package zhou.hardcat.communtiy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhou.hardcat.communtiy.datatransferobject.PageInfoDTO;
import zhou.hardcat.communtiy.datatransferobject.QuestionDTO;
import zhou.hardcat.communtiy.service.QuestionService;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class QuestionController {
    @Resource
    private QuestionService questionService;
    @RequestMapping("/loadquestions")
    public String loadQuestion(Model model,
                               @RequestParam(name = "page",defaultValue ="1")Integer page,
                               @RequestParam(name="pageSize",defaultValue = "5")Integer pageSize){
        PageInfoDTO all = questionService.findAll(page, pageSize);
        model.addAttribute("pageInfoDTO",all);
        return "redirect:/";
    }
    @RequestMapping("/question/{id}")
    public String questionDetail(@PathVariable(name="id") Integer id, Model model) {
        QuestionDTO questionDTO=questionService.findById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
