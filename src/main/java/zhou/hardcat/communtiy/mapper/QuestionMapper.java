package zhou.hardcat.communtiy.mapper;

import org.apache.ibatis.annotations.*;
import zhou.hardcat.communtiy.model.Question;

import java.util.List;

@Mapper public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,"
        + "creator,tag)values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator}," + "#{tag})") int insert(
        Question question);

    @Select("select *from question  order by gmt_create desc limit #{pageStart},#{pageSize} ") List<Question> selectAll(
        Integer pageStart, Integer pageSize);

    @Select("select count(1) from question") Integer selectCount();

    @Select("select count(1) from question where creator=#{creator}") int selectCountByUserID(Integer creator);

    @Select("select *from question where creator=#{creator} order by gmt_create desc limit #{pageStart},#{pageSize} ")
    List<Question> selectAllByUserId(Integer creator, Integer pageStart, Integer pageSize);

    @Select("select *from question where id=#{id}") Question selectById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},tag=#{tag},gmt_modified=#{gmtModified} "
        + "where id=#{id}") int updateById(Question dbQuestion);

    @Update("update question set view_count = view_count + 1 where id = #{id}") int updateByCondition(Integer viewCount,
        Integer id);

    @Update("update question set comment_count = comment_count + 1 where id = #{id}") int updateCommentCount(
        Integer commentCount, Integer id);
}
