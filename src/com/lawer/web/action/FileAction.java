package com.lawer.web.action;

import java.io.File;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.lawer.context.Constant;
import com.lawer.domain.User;
import com.lawer.service.UserService;
import com.lawer.util.StringUtils;
import com.opensymphony.xwork2.ActionContext;


@Namespace("/file")
@ParentPackage("p2p-default")
public class FileAction extends BaseAction{
	private static Logger logger = Logger.getLogger(FileAction.class);
	
    private File image; //上传的文件
    private String imageFileName; //文件名称
    private String imageContentType; //文件类型
    
    private File[] file; //上传的文件
    private String[] fileFileName; //文件名称
    private String[] fileContentType; //文件类型

	
    
	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String[] getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String[] fileContentType) {
		this.fileContentType = fileContentType;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	@Action(value="imageTest",
			results={@Result(name="imageTest", type="ftl",location="/fileTest.html")
	})
	public String imageTest(){
		return "imageTest";
	}
	
	@Action(value="saveImage",
			results={@Result(name="imageTest", type="ftl",location="/fileTest.html")
	})
	public String saveImage() {
		System.out.println(image);
		System.out.println(imageFileName);
		System.out.println(imageContentType);
		System.out.println(file);
		System.out.println(fileFileName);
		System.out.println(fileContentType);
		return "saveImage";
	}

	
}
