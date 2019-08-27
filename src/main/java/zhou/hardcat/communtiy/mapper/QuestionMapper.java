package zhou.hardcat.communtiy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import zhou.hardcat.communtiy.model.Question;
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified," +
            "creator,comment_count,view_count,like_count,tag)values(#{title}," +
            "#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount}," +
            "#{viewCount},#{likeCount},#{tag}" )
    int insertQuestion(Question question);
}
