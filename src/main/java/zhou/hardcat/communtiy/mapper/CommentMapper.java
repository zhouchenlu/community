package zhou.hardcat.communtiy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zhou.hardcat.communtiy.model.Comment;

import java.util.List;

@Mapper public interface CommentMapper {
    @Insert("insert into comment (parent_id, type, commentator, gmt_create, gmt_modified, content) values"
        + "(#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified}, #{content})") int insert(
        Comment comment);

    @Select("select * from comment where parent_id = #{id}") List<Comment> selectById(Integer id);
}
