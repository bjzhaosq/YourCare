package com.lawer.web.action.ycare;

import com.alibaba.fastjson.JSONObject;
import com.lawer.domain.RoomList;
import com.lawer.domain.Site;
import com.lawer.domain.User;
import com.lawer.domain.Video;
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.QueryRoom;
import com.lawer.model.SearchParam;
import com.lawer.model.tree.SiteTree;
import com.lawer.model.tree.Tree;
import com.lawer.service.ArticleService;
import com.lawer.service.RoomListService;
import com.lawer.service.VideoService;
import com.lawer.util.Constant;
import com.lawer.util.HTTPSend;
import com.lawer.util.StringUtils;
import com.lawer.web.action.BaseAction;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Namespace("/my")
@ParentPackage("p2p-default")
public class ZhiboUserAction extends BaseAction{

    private static Logger logger = Logger.getLogger(ZhiboUserAction.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private RoomListService roomListService;

    private List<Tree> child;

    private Video video;

    @Action(value = "/myRoom", results = { @Result(name = "success", type = "ftl", location = "/zhibo/user/usercenter.html") })
    public String myRoom() throws Exception {
        checkMenu();
        //添加分类
        List<Site> sites = articleService.getSiteByPid(0, "2");
        request.setAttribute("sites",sites);

        JSONObject json = new JSONObject();
        int currentPage = paramInt("currentPage");//当前页
        int pernum = paramInt("pernum");//每页个数
        String fenlei = paramString("fenlei");
        String zhuboName = paramString("zhuboName");
        String roomNum = paramString("roomNum");
        String platform = paramString("platform");


        if(pernum == 0){
            pernum = 10;
        }
        if(currentPage == 0){
            currentPage = 1;
        }

        SearchParam param = SearchParam.getInstance();
        param.addPage(currentPage,pernum);

        //条件查询
        if(!StringUtils.isBlank(fenlei)){
            param.addParam("siteOne.siteId",fenlei);
        }
        if(!StringUtils.isBlank(zhuboName)){
            param.addParam("siteTwo.video.name",zhuboName);
        }
        if(!StringUtils.isBlank(roomNum)){
            param.addParam("siteTwo.video.roomNum",roomNum);
        }
        if(!StringUtils.isBlank(platform)){
            param.addParam("siteTwo.video.platform",platform);
        }


        param.addParam("siteTwo.userId",getSUser().getId());
        param.addParam("siteTwo.status",1);

        PageDataList<RoomList> rooms = roomListService.findRoomListByPage(param);
        ArrayList<QueryRoom> qrs = new ArrayList<>();
        if(null != rooms && null != rooms.getList() && rooms.getList().size()>0){
            for (RoomList r : rooms.getList()){
                QueryRoom q = new QueryRoom();
                q.setName(r.getSiteTwo().getName());
                q.setFenlei(r.getSiteOne().getName());
                q.setId(r.getSiteTwo().getSiteId());
                q.setPlatform(r.getSiteTwo().getVideo().getPlatform());
                qrs.add(q);
            }
        }
        request.setAttribute("qrs",qrs);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("pernum",pernum);
        request.setAttribute("fenlei",fenlei);
        request.setAttribute("zhuboName",zhuboName);
        request.setAttribute("platform",platform);
        request.setAttribute("roomNum",roomNum);
        request.setAttribute("page",rooms.getPage());


        return SUCCESS;
    }

    @Action(value = "/test", results = { @Result(name = "success", type = "ftl", location = "/zhibo/user/test.html") })
    public String test() throws Exception {
        checkMenu();
        //添加分类
        List<Site> sites = articleService.getSiteByPid(0, "2");
        request.setAttribute("sites",sites);

        JSONObject json = new JSONObject();
        int currentPage = paramInt("currentPage");//当前页
        int pernum = paramInt("pernum");//每页个数
        String fenlei = paramString("fenlei");
        String zhuboName = paramString("zhuboName");
        String roomNum = paramString("roomNum");
        String platform = paramString("platform");


        if(pernum == 0){
            pernum = 10;
        }
        if(currentPage == 0){
            currentPage = 1;
        }

        SearchParam param = SearchParam.getInstance();
        param.addPage(currentPage,pernum);

        //条件查询
        if(!StringUtils.isBlank(fenlei)){
            param.addParam("siteOne.siteId",fenlei);
        }
        if(!StringUtils.isBlank(zhuboName)){
            param.addParam("siteTwo.video.name",zhuboName);
        }
        if(!StringUtils.isBlank(roomNum)){
            param.addParam("siteTwo.video.roomNum",roomNum);
        }
        if(!StringUtils.isBlank(platform)){
            param.addParam("siteTwo.video.platform",platform);
        }


        param.addParam("siteTwo.userId",getSUser().getId());

        PageDataList<RoomList> rooms = roomListService.findRoomListByPage(param);
        ArrayList<QueryRoom> qrs = new ArrayList<>();
        if(null != rooms && null != rooms.getList() && rooms.getList().size()>0){
            for (RoomList r : rooms.getList()){
                QueryRoom q = new QueryRoom();
                q.setName(r.getSiteTwo().getVideo().getName());
                q.setFenlei(r.getSiteOne().getName());
                q.setId(r.getSiteTwo().getSiteId());
                q.setPlatform(r.getSiteTwo().getVideo().getPlatform());
                qrs.add(q);
            }
        }
        request.setAttribute("qrs",qrs);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("pernum",pernum);
        request.setAttribute("fenlei",fenlei);
        request.setAttribute("zhuboName",zhuboName);
        request.setAttribute("platform",platform);
        request.setAttribute("roomNum",roomNum);
        request.setAttribute("page",rooms.getPage());


        return SUCCESS;
    }

    @Action(value = "/deleteRoom")
    public void deleteRoom(){
        JSONObject json = new JSONObject();
        checkMenu();
        int roomId = paramInt("roomId");
        if(roomId == 0){
            setCode(json,"请求参数失败");
            printJson(json.toString());
            return;
        }

        SiteTree siteTree = articleService.getSiteTree(roomId);
        Site s = (Site) siteTree.getChild().get(0).getModel();
        s.setStatus(0);
        articleService.modifySite(s);

        setCode(json,"");
        json.put("name",s.getName());
        printJson(json.toString());
        return;
    }


    @Action(value = "/my404", results = { @Result(name = "success", type = "ftl", location = "/zhibo/404/404.html") })
    public String my404() throws Exception {
        checkMenu();
        return SUCCESS;
    }

    private void checkMenu(){
        SiteTree siteTree = articleService.getSiteTree(getSUser());
        child = siteTree.getChild();
    }

    public List<Tree> getChild() {
        return child;
    }

    public void setChild(List<Tree> child) {
        this.child = child;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
