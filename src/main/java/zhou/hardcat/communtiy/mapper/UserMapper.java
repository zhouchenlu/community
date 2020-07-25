package zhou.hardcat.communtiy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zhou.hardcat.communtiy.model.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url)" +
            "values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    int insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(String token);
    @Select("select * from user where id=#{id}")
    User findById(Integer id);
    @Select("select * from user where account_id=#{accountId}")
    User findByAccontId(String accountId);
    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} " +
            "where account_id=#{accountId}")
    int updateByAccountId(User user);
    @Select("select * from user where name = #{username}")
    List<User> findByName(String username);
    @Select("select * from user where name = #{username} and account_id = #{password}")
    User findByNameAndAccountId(String username, String password);
}
