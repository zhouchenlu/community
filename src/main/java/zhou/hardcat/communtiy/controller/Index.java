package zhou.hardcat.communtiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class Index {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/index")
    public String hello(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie)){
                    String token = cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        return "index";
    }
}
