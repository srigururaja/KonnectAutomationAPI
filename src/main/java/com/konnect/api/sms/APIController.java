package com.konnect.api.sms;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

//import com.jayway.restassured.specification.RequestSpecification;

public class APIController {

	public static String sDirpath = System.getProperty("user.dir");
	public static String sconfig = sDirpath + "/resources/APIResponse.properties";

	// read the data from congigProperties
	public static String readconfigproperties(String skey) {
		String svalue = null;
		Properties properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream(sconfig);
			properties.load(fis);
			svalue = properties.getProperty(skey);

		} catch (Exception e) {

		}
		return svalue;
	}
	
	public static int convert_str_to_int(String resp) {
		int Exp_resp = Integer.parseInt(resp.trim());
		return Exp_resp;
	}
	
	public static RequestSpecification buildReq(String str_Authvalue) {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://konnectqa1.kirusa.com/admin/api/v1/Accounts").addHeader("Content-Type","application/json").addHeader("Authorization",str_Authvalue).build();
		return req;
		
	}

	public static JsonPath getJsonString(String jsonResp) {
		JsonPath jsonPath = new JsonPath(jsonResp);
		return jsonPath;
	}
	
	public static String getStringValue(JsonPath js,String strName) {
		return js.getString(strName).toString();
	}
	
	public static void activemqSMSHigh() throws InterruptedException {
		WebDriver driver = null;
		System.setProperty("webdriver.chrome.driver", "./resources/extensionfiles/chromedriver89new");
		driver = new ChromeDriver();
		String user = "admin";
		String pwd = "Abcd@123";
	      // adding username, password with URL
	      String str = "http://" + user+ ":" + pwd + "@" +
	      "192.168.231.237:8161/admin/queues.jsp";
	      driver.get(str);
	      driver.findElement(By.xpath("//*[@name='QueueFilter']")).sendKeys("kvsms_14_agent_sms_high");
	      driver.findElement(By.xpath("//*[@value='Filter']")).click();
	      String Enqueued_count = driver.findElement(By.xpath("//*[@id='queues']/tbody/tr/td[4]")).getText();
	      System.out.println("The Enqueued Count is : "+Enqueued_count);
	      Thread.sleep(5000);
	      driver.quit();
		
	}
}
