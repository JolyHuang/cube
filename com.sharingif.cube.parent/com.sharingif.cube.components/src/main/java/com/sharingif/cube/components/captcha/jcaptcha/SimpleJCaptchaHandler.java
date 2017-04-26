package com.sharingif.cube.components.captcha.jcaptcha;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.InitializingBean;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.SimpleTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.sharingif.cube.components.captcha.ICaptchaHandler;

/**
 *
 * @Description:  [Simple JCaptch验证码处理程序]
 * @Author:       [Joly]
 * @CreateDate:   [2014年5月29日 下午6:40:40]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年5月29日 下午6:40:40]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SimpleJCaptchaHandler implements ICaptchaHandler, InitializingBean {
	
	private TextPaster textPaster;
	private BackgroundGenerator backgroundGenerator;
	private FontGenerator fontGenerator;
	private WordToImage wordToImage;
	
	
	
	public TextPaster getTextPaster() {
		return textPaster;
	}
	public void setTextPaster(TextPaster textPaster) {
		this.textPaster = textPaster;
	}
	public BackgroundGenerator getBackgroundGenerator() {
		return backgroundGenerator;
	}
	public void setBackgroundGenerator(BackgroundGenerator backgroundGenerator) {
		this.backgroundGenerator = backgroundGenerator;
	}
	public FontGenerator getFontGenerator() {
		return fontGenerator;
	}
	public void setFontGenerator(FontGenerator fontGenerator) {
		this.fontGenerator = fontGenerator;
	}
	public WordToImage getWordToImage() {
		return wordToImage;
	}
	public void setWordToImage(WordToImage wordToImage) {
		this.wordToImage = wordToImage;
	}



	@Override
	public byte[] getCaptchByteStream(String Content) throws IOException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		
		BufferedImage bufferedImage = wordToImage.getImage(Content);
        
		ImageIO.write(bufferedImage, "png", out);
        
		return out.toByteArray();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if(null != wordToImage)
			return;
		
		if(null == fontGenerator)
			fontGenerator = new TwistedAndShearedRandomFontGenerator(new Integer(50), new Integer(70));
		
		if(null == backgroundGenerator)
			backgroundGenerator = new FunkyBackgroundGenerator(new Integer(230), new Integer(100));
		
		if(null == textPaster)
			textPaster = new SimpleTextPaster(new Integer(4), new Integer(4), Color.WHITE);
		
		wordToImage = new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster);
		
		
	}

}
