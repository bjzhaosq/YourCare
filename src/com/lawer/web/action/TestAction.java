package com.lawer.web.action;

import com.lawer.util.HTTPSend;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Namespace("/test/todo")
@ParentPackage("p2p-default")
public class TestAction extends BaseAction{

    private static Logger logger = Logger.getLogger(TestAction.class);


    public static void main(String[] args) throws Exception {
//        String url = "https://www.douyu.com/directory/game/jdqs";
//        String content = HTTPSend.sendGet(url, "");
////        System.out.println(content);
//        Document parse = Jsoup.parse(content);
//        Elements allElements = parse.getElementById("live-list-contentbox").getAllElements();
//
//        Elements li = parse.getElementById("live-list-contentbox").getElementsByTag("li");
//
//
//        for(Element e : li){
//            Document liDoc = Jsoup.parse(e.toString());
//            String zhiboImage = liDoc.getElementsByTag("img").attr("data-original");
//            String roomId = liDoc.getElementsByTag("a").attr("data-rid");
//            String title = liDoc.getElementsByTag("h3").text();
//            Elements span = liDoc.getElementsByTag("span");
//            String yxName = span.get(2).text();
//            String zbName = span.get(3).text();
//            String num = span.get(4).text();
//
//            System.out.println(yxName+":"+zbName+":"+num+":"+roomId+";"+title+":"+zhiboImage);
//
//        }
//        TestAction.douyucaidan();
        String url = "/directory/game/music";
        String[] split = url.split("/");
        String s = split[split.length - 1];
        System.out.println(s);
    }

    /**
     * 从字符串文本中获得数字
     * @param text
     * @return
     */
    public static String getDigit(String text) {
        List<Long> digitList = new ArrayList<Long>();
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(text);
        while (m.find()) {
            String find = m.group(1).toString();
            digitList.add(Long.valueOf(find));
        }
        String s = digitList.toString();

        return digitList.get(digitList.size()-1).toString();
    }

    public static void douyucaidan(){
        String url = "https://www.douyu.com/directory";
        String content = HTTPSend.sendGet(url, "");
//        System.out.println(content);
        Document parse = Jsoup.parse(content);
        Elements allElements = parse.getElementById("live-list-contentbox").getAllElements();

        Elements li = parse.getElementById("live-list-contentbox").getElementsByTag("li");
        for(int i =0 ;i<li.size();i++){
            Elements a = li.get(i).getElementsByTag("a");
            Node node = a.get(0).childNodes().get(1);
            String src = node.attr("data-original");

            String href = a.attr("href");
            System.out.println(a.text()+":"+href+":"+src);
        }
    }

}
