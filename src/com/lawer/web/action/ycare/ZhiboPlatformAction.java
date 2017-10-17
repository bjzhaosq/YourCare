package com.lawer.web.action.ycare;

import com.alibaba.fastjson.JSONObject;
import com.lawer.domain.Site;
import com.lawer.domain.Video;
import com.lawer.model.DouyuMulu;
import com.lawer.model.DouyuRoom;
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
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Namespace("/platform")
@ParentPackage("p2p-default")
public class ZhiboPlatformAction extends BaseAction {

    private static Logger logger = Logger.getLogger(ZhiboPlatformAction.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private RoomListService roomListService;

    private List<Tree> child;

    private Video video;

    @Action(value = "/douyu", results = {@Result(name = "success", type = "ftl", location = "/zhibo/platform/index_douyu.html")})
    public String douyu() throws Exception {

        request.setAttribute("dlist", douyumulu());

        return SUCCESS;
    }

    private List<DouyuMulu> douyumulu(){
        List<DouyuMulu> list = new ArrayList<>();
        String url = "https://www.douyu.com/directory";
        String content = HTTPSend.sendGet(url, "");
        Document parse = Jsoup.parse(content);
        Elements allElements = parse.getElementById("live-list-contentbox").getAllElements();

        Elements li = parse.getElementById("live-list-contentbox").getElementsByTag("li");
        for (int i = 0; i < li.size(); i++) {
            Elements a = li.get(i).getElementsByTag("a");
            Node node = a.get(0).childNodes().get(1);
            String src = node.attr("data-original");

            String href = a.attr("href");
            DouyuMulu d = new DouyuMulu();
            d.setName(a.text());
            d.setUrl(href);
            d.setImage(src);
            String[] split = href.split("/");
            d.setNid(split[split.length - 1]);
            list.add(d);
        }
        return list;
    }

    @Action(value = "/rooms", results = {@Result(name = "success", type = "ftl", location = "/zhibo/platform/blank.html")})
    public String rooms() throws Exception {
        String src = paramString("src");
        List<DouyuRoom> list = new ArrayList<DouyuRoom>();
        if (!StringUtils.isBlank(src)) {
            String url = "https://www.douyu.com"+src;
            String content = HTTPSend.sendGet(url, "");
            Document parse = Jsoup.parse(content);
            Elements allElements = parse.getElementById("live-list-contentbox").getAllElements();

            Elements li = parse.getElementById("live-list-contentbox").getElementsByTag("li");


            for (Element e : li) {
                Document liDoc = Jsoup.parse(e.toString());
                String zhiboImage = liDoc.getElementsByTag("img").attr("data-original");
                String roomId = liDoc.getElementsByTag("a").attr("data-rid");
                String title = liDoc.getElementsByTag("h3").text();
                Elements span = liDoc.getElementsByTag("span");
                String yxName = span.get(2).text();
                String zbName = span.get(3).text();
                String num = span.get(4).text();

//                System.out.println(yxName + ":" + zbName + ":" + num + ":" + roomId + ";" + title + ":" + zhiboImage);
                DouyuRoom dr = new DouyuRoom();
                dr.setYxName(yxName);
                dr.setZbName(zbName);
                dr.setNum(num);
                dr.setRoomId(roomId);
                dr.setTitle(title);
                dr.setZbImage(zhiboImage);
                list.add(dr);
            }
        }
        request.setAttribute("dlist", douyumulu());
        request.setAttribute("lr", list);
        request.setAttribute("nid",src);
        String[] split = src.split("/");
        request.setAttribute("biaozhi",split[split.length-1]);
        return SUCCESS;
    }

    @Action(value = "/lookRoom", results = {@Result(name = "success", type = "ftl", location = "/zhibo/platform/viewRoom.html")})
    public String lookRoom() {
        request.setAttribute("dlist", douyumulu());
        int roomId = paramInt("roomId");
        String yxName = paramString("yxName");
        String src = paramString("src");
        String zhuboName = "";
        String url = "";
        String roomNum = "";
        String biaoti = "";
        if(roomId != 0){
            String douyuUrl = Constant.DOUYU_URL + roomId;
            String content = HTTPSend.sendGet(douyuUrl, "");
            Document parse = Jsoup.parse(content);
            biaoti = parse.getElementsByTag("h1").get(0).text();
            String title = parse.title();

            String[] split = title.split("_");
            zhuboName = split[0];//主播名称
            roomNum = StringUtils.getDigit(title);//主播房间号
            url = "https://staticlive.douyucdn.cn/common/share/play.swf?room_id="+roomNum;
        }

        request.setAttribute("zbName",zhuboName);
        request.setAttribute("url",url);
        request.setAttribute("roomNum",roomNum);
        request.setAttribute("biaoti",biaoti);
        request.setAttribute("yxName",yxName);
        request.setAttribute("src",src);
        String[] split = src.split("/");
        request.setAttribute("biaozhi",split[split.length-1]);
        return SUCCESS;
    }


    @Action(value = "/my404", results = {@Result(name = "success", type = "ftl", location = "/zhibo/404/404.html")})
    public String my404() throws Exception {
        checkMenu();
        return SUCCESS;
    }

    private void checkMenu() {
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
