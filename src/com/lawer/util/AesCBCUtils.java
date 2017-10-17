package com.lawer.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**AES 是一种可逆加密算法，对用户的敏感信息加密处理
 * 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class AesCBCUtils {
    /*
    * 加密用的Key 可以用26个字母和数字组成
    * 此处使用AES-128-CBC加密模式，key需要为16位。
    */
    private static String sKey="zsqaddresslistwx";
    private static String ivParameter="zsqaddresslistiv";
    private static AesCBCUtils instance=null;
    private AesCBCUtils(){

    }
    public static AesCBCUtils getInstance(){
        if (instance==null)
            instance= new AesCBCUtils();
        return instance;
    }
    // 加密
    public String encrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
    }

    // 解密
    public String decrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,encodingFormat);
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 检验手机号码格式
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles){
        Pattern p = Pattern.compile("^1[3|4|7|5|8][0-9]{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static void main(String[] args) throws Exception {
        // 需要加密的字串
//        String cSrc = "123456";
//        System.out.println(cSrc);
//        // 加密
//        long lStart = System.currentTimeMillis();
//        String enString = AesCBCUtils.getInstance().encrypt(cSrc,"utf-8",sKey,ivParameter);
//        System.out.println("加密后的字串是："+ enString);
//
//        long lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("加密耗时：" + lUseTime + "毫秒");
//        // 解密
//        lStart = System.currentTimeMillis();
//        String DeString = AesCBCUtils.getInstance().decrypt(enString,"utf-8",sKey,ivParameter);
//        System.out.println("解密后的字串是：" + DeString);
//        lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("解密耗时：" + lUseTime + "毫秒");
//
//        //"^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"
//        System.out.println(AesCBCUtils.isMobile(DeString));

        String suibian = AesCBCUtils.getInstance().decrypt("RXjgFy+2z3yZwtL7UP6oKjut7oOq4tLjZTURs1L2LWFZf61b1U93WAcMpLGsiF/w","utf-8",sKey,ivParameter);
        JSONObject json = (JSONObject) JSON.parse(suibian);
        System.out.println(json.get("username"));


        System.out.println("解密后的字串是：" + suibian);
    }
}