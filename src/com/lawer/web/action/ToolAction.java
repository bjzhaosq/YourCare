package com.lawer.web.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.octo.captcha.service.CaptchaServiceException;
import com.lawer.util.jcaptcha.CaptchaServiceSingleton;

/**
 * 工具类Action,验证码、生产图片
 * 
 *
 */
@Namespaces({
	@Namespace("/"),
	@Namespace("/tools"),
	@Namespace("/plugins/ueditor/")
})
@ParentPackage("p2p-default") 
public class ToolAction extends  BaseAction {
	private static Logger logger = Logger.getLogger(ToolAction.class);  
	
	private String type;

	private File upload;
	private String uploadFileName;
	private String sep=File.separator;

	//裁剪后的图像大小
	private double cropX;
	private double cropY;
	private double cropW;
	private double cropH;

	private String plugintype;
	
	/**
	 * 输出验证码
	 * @return
	 * @throws Exception
	 */
	@Action("validimg")
	public String validimg() throws Exception {
		genernateCaptchaImage();
		return null;
	}
	
	/**
	 * 生产校验码
	 * @throws IOException
	 */
	protected void genernateCaptchaImage() throws IOException {  
		response.setHeader("Cache-Control", "no-store");  
        response.setHeader("Pragma", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
        ServletOutputStream out = response.getOutputStream();  
        try {  
            String captchaId = request.getSession(true).getId();  
            BufferedImage challenge = (BufferedImage)  CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId, request.getLocale());  
            ImageIO.write(challenge, "jpg", out);  
            out.flush();  
        } catch (CaptchaServiceException e) {  
        } finally {  
            out.close();  
        }  
    }
	


}
