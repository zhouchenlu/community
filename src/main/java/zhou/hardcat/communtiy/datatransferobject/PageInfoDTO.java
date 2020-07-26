package zhou.hardcat.communtiy.datatransferobject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data public class PageInfoDTO {
    private Integer page;//当前页码
    private Integer pageSize;//每页数量
    private Integer total;//总页数
    private List<QuestionDTO> questions;
    private Boolean hasPrevious;//上一页
    private Boolean hasNext;//下一页
    private Boolean hasEnd;//最后一页
    private Boolean hasFirst;//首页
    private Integer pageStart;//页面起始
    private List<Integer> pages = new ArrayList<Integer>();

    public void setPageInfo(Integer count, Integer page, Integer pageSize) {
        total = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (page < 1) {
            page = 1;
        }
        if (page > total) {
            page = total;
        }
        this.page = page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= total) {
                pages.add(page + i);
            }
        }
        //page为当前页码
        pageStart = total != 0 ? pageSize * (page - 1) : 0;
        //以下逻辑简化为:
        hasPrevious = page != 1;
        hasNext = !page.equals(total);
        hasFirst = !pages.contains(1);
        hasEnd = !pages.contains(total);
       /* //页数为1,上一页为false,
        if(page==1){
            hasPrevious=false;
        }else{
            hasPrevious=true;
        }
        //页面为最后一页,下一页不展示
        if(page.equals(total)){
            hasNext=false;
        }else{
            hasNext=true;
        }
        if(pages.contains(1)){
            hasFirst=false;
        }else{
            hasFirst=true;
        }
        if(pages.contains(total)){
            hasEnd=false;
        }else{
            hasEnd=true;
        }*/

    }
}
