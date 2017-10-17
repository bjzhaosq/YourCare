package com.lawer.web.action.ycare;

import com.alibaba.fastjson.JSONObject;
import com.lawer.domain.RoomList;
import com.lawer.domain.Site;
import com.lawer.domain.User;
import com.lawer.domain.Video;
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

import java.util.Date;
import java.util.List;

@Namespace("/zhibo")
@ParentPackage("p2p-default")
public class ZhiboAction extends BaseAction{

    private static Logger logger = Logger.getLogger(ZhiboAction.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private RoomListService roomListService;

    private List<Tree> child;

    private Video video;

    @Action(value = "/index", results = { @Result(name = "success", type = "ftl", location = "/zhibo/index.html") })
    public String zhiboIndex() throws Exception {
        checkMenu();
        return SUCCESS;
    }

    @Action(value = "/room", results = { @Result(name = "success", type = "ftl", location = "/zhibo/zhibo.html") })
    public String zhiboRoom() throws Exception {

        checkMenu();
        int videoId = paramInt("video");
        if(videoId != 0){
            Video vdo = videoService.findVideoById(videoId);
            request.setAttribute("vdo",vdo);
        }

        return SUCCESS;
    }

    @Action(value = "/addRoom", results = { @Result(name = "success", type = "ftl", location = "/zhibo/addRoom.html") })
    public String addRoom() throws Exception {

        checkMenu();
        //添加分类
        List<Site> sites = articleService.getSiteByPid(0, "2");
        request.setAttribute("sites",sites);

        return SUCCESS;
    }

    @Action(value = "/saveRoom")
    public void saveRoom() throws Exception {
        JSONObject json = new JSONObject();
        User user = getSUser();
        String platform = paramString("platform");
        String roomId = paramString("roomId");
        int width = paramInt("width");
        int height = paramInt("height");
        int siteId = paramInt("fenlei");
        String remark = paramString("remark");

        String zhuboName = "";
        String url = "";
        Video v = null;

        if(StringUtils.isBlank(platform) || StringUtils.isBlank(roomId) || siteId == 0){
            setCode(json, "请求参数错误");
            printJson(json.toString());
            return;
        }

        Site s = articleService.getSiteById(siteId);

        if(width == 0 || height == 0){
            width = 1200;
            height = 750;
        }

        if("1".equals(platform)){
            //斗鱼
            logger.info("斗鱼房间号："+roomId);
            String douyuUrl = Constant.DOUYU_URL + roomId;
            String content = HTTPSend.sendGet(douyuUrl, "");
            Document parse = Jsoup.parse(content);
            String title = parse.title();

            String[] split = title.split("_");
            zhuboName = split[0];//主播名称
            if(!articleService.checkSiteCode(zhuboName)){
                setCode(json, "主播 \""+zhuboName+"\" 已添加");
                printJson(json.toString());
                return;
            }
            roomId = StringUtils.getDigit(title);//主播房间号
            url = "https://staticlive.douyucdn.cn/common/share/play.swf?room_id="+roomId;
            platform = "斗鱼";
            v = saveVideo(zhuboName,url,width,height,platform,roomId,s);
        }else if("2".equals(platform)){
            //虎牙
            logger.info("虎牙房间号："+roomId);
            String huyaUrl = Constant.HUYA_URL + roomId;
            String content = HTTPSend.sendGet(huyaUrl, "");
            Document parse = Jsoup.parse(content);
            zhuboName = parse.select("h3").first().attr("title");
            if(!articleService.checkSiteCode(zhuboName)){
                setCode(json, "主播 \""+zhuboName+"\" 已添加");
                printJson(json.toString());
                return;
            }

            url = "http://liveshare.huya.com/"+roomId+"/huyacoop.swf";
            platform = "虎牙";
            v = saveVideo(zhuboName,url,width,height,platform,roomId,s);
        }

        //
        Site site = new Site();
        site.setCode(zhuboName);
        if(StringUtils.isBlank(remark)){
            site.setName(zhuboName);
        }else {
            site.setName(remark);
        }
        site.setNid(zhuboName);
        site.setPid(siteId);
        site.setSort(100);
        site.setStatus(1);
        site.setStatus(1);
        site.setAddtime(new Date());
        site.setVideo(v);
        site.setUserId(user.getId());
        articleService.addSite(site);

        //
        RoomList roomList = new RoomList();
        roomList.setSiteOne(s);
        roomList.setSiteTwo(site);
        roomList.setStatus("1");
        roomList.setAddtime(new Date());
        roomListService.saveRoomList(roomList);

        setCode(json, "");
        json.put("video",v.getId());
        json.put("name",zhuboName);
        printJson(json.toString());
        return;
    }

    private Video saveVideo(String zhuboName,String url,int width,int height,String platform,String num,Site s){
        Video video = new Video();
        video.setName(zhuboName);
        video.setNid(zhuboName);
        video.setContent(url);
        video.setWidth(width);
        video.setHeight(height);
        video.setPlatform(platform);
        video.setAddTime(new Date());
        video.setStatus("1");
        video.setSite(s);
        video.setRoomNum(num);
        //保存
        videoService.saveVideo(video);
        return video;
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
