package com.example.wechat;

import com.alibaba.fastjson.JSON;
import com.example.wechat.service.QrCodeService;
import com.example.wechat.util.Config;
import com.example.wechat.util.DownLoadImage;
import com.example.wechat.util.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatApplicationTests {

	@Test
	public void createQrcode() throws IOException {
		// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET

//		String result = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token","grant_type=client_credential&appid=wxdd95228d72f2bcac&secret=9387de14770c7f92cfdb319dcb6a9ff0");
//		System.out.println(result);

		String accessToken = "15_h8WGWzbdXwN2Gc_ZJCsPCRzxvxvpz9Eaf4ms7rpP1ChCo1t262UKcVTNH3VamF-jTBjVT8bbctLn5D9aWLBiVg8LouO0AZNfctgUTE-9E_SEfJowGqCnl0e6xG3PTTeaH1E-KwMmLY4S67M7BDZfACAUWS";
		String sceneStr = "o1BWk0utKViGg5DEPeMgk-OrFrOA";

//		String baseUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken;
//		String param = "{\"expire_seconds\":604800,\"action_name\":\"QR_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\" "+sceneStr+" \"}}}";
//		String result = HttpRequest.sendPost(baseUrl,param);
//		System.out.println(result);
//		Map maps = (Map) JSON.parse(result);
//		String ticket = (String) maps.get("ticket");
//		System.out.println(ticket);
		QrCodeService.sendQrcodeToUser(accessToken, sceneStr);
	}

	@Test
	public void getQrCode(){
		byte[] result = HttpRequest.getImage("https://mp.weixin.qq.com/cgi-bin/showqrcode","ticket=gQF67zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyNVRnZGNwa2xlQWkxWGhPRzFyY00AAgRReOFbAwSAOgkA");
		System.out.println(result.length);

	}

	@Test
	public void downloadImage() throws Exception {
		DownLoadImage.download("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQF67zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyNVRnZGNwa2xlQWkxWGhPRzFyY00AAgRReOFbAwSAOgkA","erweima");
	}

	@Test
	public void sendImage(){
		File file = new File("D:\\xiaoxingWork\\spring-boot-learning\\wechat\\erweima.png");
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=15_ntlKN5caKUb5QA0-Mzw8x7nfLavVw5rymTPMyi0NSwojUhJ3Pc89YvKDMVqfyKTjgomY-taM8nzFoiaRuHGsGNhE56qWsv_VG1beapU8cOJavibKZxttlBdIWiFzmcSW9U_i3rGlGm6znMt1BCGbAHAKLP&type=image";
		try {
			String s= HttpRequest.postImage(url,file);
			System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void getNickName(){
		//https://api.weixin.qq.com/cgi-bin/user/info?access_token=".$access_token."&openid=".$fromUsername."&lang=zh_CN
		String openid = "o1BWk0utKViGg5DEPeMgk-OrFrOA";
		String url ="https://api.weixin.qq.com/cgi-bin/user/info";
		String accesstoken = "15_OIi6JJ6Tdt31fjM4dzkADZDA2HhBQBJpzDE_yMduIrd0S2tF33btSgOmQWexdKKtyYb4dBVbW-G9IUlLVBVAJJq70bR3TIoCR6QFAgyxjmkE_ggCqgmdv0zsX7iDBg9iSzr-V2sKX6n9ZTYzSEUbAIAOAQ";
		String param = "access_token="+accesstoken+"&openid="+openid+"&lang=zh_CN";
		String result = HttpRequest.sendGet(url,param);
		System.out.println(result);

	}


}
