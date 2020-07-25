package zhou.hardcat.communtiy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhou.hardcat.communtiy.datatransferobject.AccessTokenDTO;
import zhou.hardcat.communtiy.datatransferobject.GithubUser;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.model.User;
import zhou.hardcat.communtiy.provider.GithubProvider;
import zhou.hardcat.communtiy.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Resource
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String secret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Resource
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpSession session,
                           Model model) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatar_url());
            int isSuccess = userService.updateOrInsert(user);
            if (isSuccess > 0) {
                //登陆成功，
                response.addCookie(new Cookie("token", token));
                session.setAttribute("user", user);
                model.addAttribute("msg", "登陆成功");
                return "redirect:/";
            }
        }
        //登陆失败，重新登录
        model.addAttribute("msg", "登陆失败.");
        return "publish";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/toLogin")
    public String login() {
        return "login";
    }

    @PostMapping("/lr")
    public String register(HttpServletRequest request, Model model,
                           @RequestParam(value = "username") String username,
                           @RequestParam("password")String password,
                           HttpSession session) {
        List<User> users = userService.selectByUsername(username);
        //登录
        if (users.size() != 0) {
            User user = userService.login(username, password);
            if (user != null) {
                session.setAttribute("user", user);
                model.addAttribute("msg", "登陆成功");
                return "redirect:/";
            }
        }
        // 注册
        User userTemp = new User();
        userTemp.setName(username);
        userTemp.setAccountId(password);
        userTemp.setGmtCreate(System.currentTimeMillis());
        if (userService.register(userTemp) == 0) {
            model.addAttribute("msg", "注册失败！！");
        } else {
            model.addAttribute("msg", "注册成功！！,请登录!");
        }
        return "login";
    }
}
