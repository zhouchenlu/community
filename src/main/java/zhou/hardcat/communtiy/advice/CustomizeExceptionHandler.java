package zhou.hardcat.communtiy.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import zhou.hardcat.communtiy.exception.CustomizeException;

@ControllerAdvice public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class) ModelAndView handler(Throwable e, Model model) {
        if (e instanceof CustomizeException) {
            model.addAttribute("message", e.getMessage());
        } else {
            model.addAttribute("message", "服务器忙,请稍后重试");
        }
        return new ModelAndView("error");
    }
}
