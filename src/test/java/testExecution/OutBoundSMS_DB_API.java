package testExecution;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import databaseConfig.DBOutBoundSMSAPI;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OutBoundSMS_DB_API {
	
	public void OutBoundSMSValidData(String req_id) {
		ArrayList<String> recpList = new ArrayList<String>();
		recpList.add(APIController.readDBproperties("outboundSMS_to_number_success"));
		
		RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
		Response resp = given().spec(requestSpec)
				.body(KonnectPayLoad.OutBoundSMSLowPriority_payLoad("send_sms", req_id, "KVGURU", "918882000001", recpList, "false", "46389", "This is my SMS API KV_SMS", "https://www.google.com", KonnectAPIConstant.str_priority_high))
				.when().contentType(ContentType.JSON)
				.post(KonnectAPIConstant.str_KA_Account3_ACCOUNT_Id+"/Messages")
				.then().log().all().extract().response();
		
		System.out.println("------>" +resp.getStatusCode());
		Reporter.log("----->"+resp.getStatusCode(),true);
		// Status code
	    Assert.assertEquals(resp.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + resp.getStatusCode());
		
	    // Status
	    String str_status=APIController.getStringValue(APIController.getJsonString(resp.asString()), "status");
	    Assert.assertEquals(str_status, "ok","Failed..!! Status not matching");
	    System.out.println("Status : "+str_status);
	    Reporter.log("Status : "+str_status);

		}
	public void OutBoundSMSDupData(String req_id) {
		ArrayList<String> recpList = new ArrayList<String>();
		String dup =(APIController.readDBproperties("outboundSMS_to_number_duplicate"));
		String[] arrdup = dup.split(",");
		for(int i=0; i<arrdup.length;i++) {
			recpList.add(arrdup[i]);
		}
		RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
		Response resp = given().spec(requestSpec)
				.body(KonnectPayLoad.OutBoundSMSLowPriority_payLoad("send_sms", req_id, "KVGURU", "918882000001", recpList, "false", "46389", "This is my SMS API KV_SMS", "https://www.google.com", KonnectAPIConstant.str_priority_high))
				.when().contentType(ContentType.JSON)
				.post(KonnectAPIConstant.str_KA_Account3_ACCOUNT_Id+"/Messages")
				.then().log().all().extract().response();
		
		System.out.println("------>" +resp.getStatusCode());
		Reporter.log("----->"+resp.getStatusCode(),true);
		// Status code
	    Assert.assertEquals(resp.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + resp.getStatusCode());
		
	    // Status
	    String str_status=APIController.getStringValue(APIController.getJsonString(resp.asString()), "status");
	    Assert.assertEquals(str_status, "ok","Failed..!! Status not matching");
	    System.out.println("Status : "+str_status);
	    Reporter.log("Status : "+str_status);

		}
	public void OutBoundSMSInvalidData(String req_id) {
		ArrayList<String> recpList = new ArrayList<String>();
		recpList.add(APIController.readDBproperties("outboundSMS_to_number_invalid"));
		
		RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
		Response resp = given().spec(requestSpec)
				.body(KonnectPayLoad.OutBoundSMSLowPriority_payLoad("send_sms", req_id, "KVGURU", "918882000001", recpList, "false", "46389", "This is my SMS API KV_SMS", "https://www.google.com", KonnectAPIConstant.str_priority_high))
				.when().contentType(ContentType.JSON)
				.post(KonnectAPIConstant.str_KA_Account3_ACCOUNT_Id+"/Messages")
				.then().log().all().extract().response();
		
		System.out.println("------>" +resp.getStatusCode());
		Reporter.log("----->"+resp.getStatusCode(),true);
		// Status code
	    Assert.assertEquals(resp.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + resp.getStatusCode());
		
	    // Status
	    String str_status=APIController.getStringValue(APIController.getJsonString(resp.asString()), "status");
	    Assert.assertEquals(str_status, "ok","Failed..!! Status not matching");
	    System.out.println("Status : "+str_status);
	    Reporter.log("Status : "+str_status);

		}
	
	/*
	 * API Request from Test Scenerio
	 */
	
	@Test(description = "Initiate API request for Success, Duplciated and Invalid for DB Verificaation Part 1 ...", groups = {"APIReq" }, priority = 1)
	public void OutboundSMS_API_Req_cases() throws InterruptedException {
		// Delete the table records in log_outbound_sms courrent date values.
		DBOutBoundSMSAPI.APIOutBoundSMSDel();
		
		//API Request Success 
		OutBoundSMSValidData(APIController.readDBproperties("outboundSMS_success_req_id"));
		Thread.sleep(2000);
		
		//API Request Dulpicate
		OutBoundSMSDupData(APIController.readDBproperties("outboundSMS_duplicate_req_id"));
		Thread.sleep(2000);
		
		//API Request Dulpicate
		OutBoundSMSInvalidData(APIController.readDBproperties("outboundSMS_invalid_req_id"));
		Thread.sleep(20000);

	}
	
	/*
	 * Author: Guru
	 * DB Verification of Out bound SMS API PART 1 Konnect side the request is submitted successfully and pushed into the Gateway
	 */
	@Test(description = "To check DB Validation for Success request from Konnect Side process...", groups = {"DB_Part1" }, priority = 2)
	public void DBPart1_validation_success() {
		DBOutBoundSMSAPI.OutboundSMS_API_DBPart1(APIController.readDBproperties("outboundSMS_success_req_id"),APIController.readDBproperties("outboundSMS_creation_date"));
	}
	
	@Test(description = "To check DB Validation for Duplicate request from Konnect Side process...", groups = {"DB_Part1" }, priority = 3)
	public void DBPart1_validation_duplicate() {
		DBOutBoundSMSAPI.OutboundSMS_API_DBPart1(APIController.readDBproperties("outboundSMS_duplicate_req_id"),APIController.readDBproperties("outboundSMS_creation_date"));
	}
	
	@Test(description = "To check DB Validation for Invalid request from Konnect Side process...", groups = {"DB_Part1" }, priority = 4)
	public void DBPart1_validation_invalid() {
		DBOutBoundSMSAPI.OutboundSMS_API_DBPart1(APIController.readDBproperties("outboundSMS_invalid_req_id"),APIController.readDBproperties("outboundSMS_creation_date"));
	}
	

}
