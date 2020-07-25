package zhou.hardcat.communtiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhou.hardcat.communtiy.mapper.UserMapper;
import zhou.hardcat.communtiy.model.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public int updateOrInsert(User user) {
        User dbUser=userMapper.findByAccontId(user.getAccountId());
        if(dbUser!=null){
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            return userMapper.updateByAccountId(dbUser);
        }
        user.setGmtCreate(System.currentTimeMillis());
        return userMapper.insert(user);
    }

    public List<User> selectByUsername(String username) {
        return userMapper.findByName(username);
    }

    public User login(String username, String password) {
        return userMapper.findByNameAndAccountId(username,password);
    }

    public int register(User user) {
      return userMapper.insert(user);
    }
}
