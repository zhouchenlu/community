package zhou.hardcat.communtiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhou.hardcat.communtiy.datatransferobject.AccessTokenDTO;
import zhou.hardcat.communtiy.datatransferobject.GithubUser;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.model.User;
import zhou.hardcat.communtiy.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private  String clientId;
    @Value("${github.client.secret}")
    private  String secret;
    @Value("${github.redirect.uri}")
    private  String redirectUri;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            User user=new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccount(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtCreate(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            //登陆成功，写session
            request.getSession().setAttribute("user",user);
            return "publish";
        }else{
            //登陆失败，重新登录
            return "redirect:/";
        }
    }

}
