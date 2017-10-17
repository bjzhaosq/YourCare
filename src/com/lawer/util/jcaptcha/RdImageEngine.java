package com.lawer.util.jcaptcha;

import java.awt.Color;
import java.awt.Font;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.lawer.context.Global;
import com.lawer.util.Constant;

public class RdImageEngine extends ListImageCaptchaEngine {
	
	@Override  
    protected void buildInitialFactories() {  
		String validimgType = Global.getValue("validimg_type").toLowerCase();//类型
		int num = Global.getInt("validimg_num");//个数
		num = num==0?5:num;//默认为5个
    	WordGenerator wgen =  new RandomWordGenerator("abcdefghijklmnopqrstuvwxyz123456789");
		if(validimgType.equals(Constant.VALIDIMG_ENGLISH)){
			wgen = new RandomWordGenerator("abcdefghijklmnopqrstuvwxyz");
		}else if(validimgType.equals(Constant.VALIDIMG_NUMBER)){
			wgen = new RandomWordGenerator("0123456789");
		}
    	RandomRangeColorGenerator cgen = new RandomRangeColorGenerator(
    	new int[] { 0, 100 }, new int[] { 0, 100 },
    	new int[] { 0, 100 });
    	TextPaster textPaster = new RandomTextPaster(new Integer(num),new Integer(num), cgen, true);
    	BackgroundGenerator backgroundGenerator = new UniColorBackgroundGenerator(new Integer(80), new Integer(37),Color.WHITE);
    	Font[] fontsList = new Font[] { new Font("Arial", 0, 12),
    			new Font("Tahoma", 0, 12), new Font("Verdana", 0, 12), };
    	FontGenerator fontGenerator = new RandomFontGenerator(new Integer(26),
    			new Integer(26), fontsList);

    	WordToImage wordToImage = new ComposedWordToImage(fontGenerator,backgroundGenerator, textPaster);
    	this.addFactory(new GimpyFactory(wgen, wordToImage));
    } 

}
