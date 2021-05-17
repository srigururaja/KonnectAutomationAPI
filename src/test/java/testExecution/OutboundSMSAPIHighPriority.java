package testExecution;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.konnect.api.generic.APIResponseValidationLibrary;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;






public class OutboundSMSAPIHighPriority {
	// StatusCode verification Use Cases
		public int ActualResp;
		public int ExpectedResp;
		ArrayList<String> recpList = new ArrayList<String>();

		/*
		 * Passing valid Request
		 */
		@Test(description = "OutBound SMS Success response code..", groups = {"APIKonnectResponseSuccessCode","OutBoundSMS" }, priority = 9)
		public void OutBoundSMSValidData() {
			recpList.add("919886001212");
			recpList.add("919886001211");
			// System.out.println(APIController.sconfig);
			
			RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
			Response resp = given().spec(requestSpec)
					.body(KonnectPayLoad.OutBoundSMSLowPriority_payLoad("send_sms", "OutBound_Suc", "KVGURU", "918882000001", recpList, "false", "46389", "This is my SMS API KV_SMS", "https://www.google.com", KonnectAPIConstant.str_priority_high))
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
		 * Passing Invalid Authentication
		 * Message Invalid Authentication
		 */
		@Test(description = "OutBound SMS InValid Authentication..", groups = {"APIKonnectResponseErrorCode","OutBoundSMS" }, priority = 4)
		public void OutBoundSMSInValidAuthentication() {
			recpList.add("919886001212");
			recpList.add("919886001211");
			// System.out.println(APIController.sconfig);
			
			RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
			Response resp = given().spec(requestSpec)
					.body(KonnectPayLoad.OutBoundSMSLowPriority_payLoad("send_sms", "OutBoundSMS_InvalidAuth", "KVGURU", "918882000001", recpList, "false", "46389", "This is my SMS API KV_SMS", "https://www.google.com", KonnectAPIConstant.str_priority_high))
					.when().contentType(ContentType.JSON)
					.post("Al7nk5vd1rN8FX+yXS7MQA/Messages")
					.then().log().all().extract().response();
			
			System.out.println("Response Code : ------>" +resp.getStatusCode());
			Reporter.log("Response Code : ----->"+resp.getStatusCode(),true);

			// Status code
			APIResponseValidationLibrary.responseStatusCodeValidation(resp, "Unauthorized");

			} // end of invalid authentication API request...
		
		/*
		 * Passing Invalid API Token
		 * Message : Invalid API Token
		 * Eror Code : 401
		 */
		@Test(description = "OutBound SMS InValid API Token..", groups = {"APIKonnectResponseErrorCode","OutBoundSMS" }, priority = 3)
		public void OutBoundSMSInValidAPIToken() {
			recpList.add("919886001212");
			recpList.add("919886001211");
			// System.out.println(APIController.sconfig);
			
			RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS("75uv5FZwjj7dOdSTiJPvXuXEkWQyCWx4BKhm4Mj9npI");
			Response resp = given().spec(requestSpec)
					.body(KonnectPayLoad.OutBoundSMSLowPriority_payLoad("send_sms", "OutBoundSMS_InvalidAPIToken", "KVGURU", "918882000001", recpList, "false", "46389", "This is my SMS API KV_SMS", "https://www.google.com", KonnectAPIConstant.str_priority_high))
					.when().contentType(ContentType.JSON)
					.post(KonnectAPIConstant.str_KA_Account3_ACCOUNT_Id+"/Messages")
					.then().log().all().extract().response();
			
			System.out.println("Response Code : ------>" +resp.getStatusCode());
			Reporter.log("Response Code : ----->"+resp.getStatusCode(),true);

			// Status code
			APIResponseValidationLibrary.responseStatusCodeValidation(resp, "Unauthorized");

			} // end of invalid API Token Error Code 401 API request...

		/*
		 * Passing invalid POST URl
		 * Error Code : 404
		 */
		@Test(description = "OutBound SMS Server Error..", groups = {"APIKonnectResponseErrorCode","OutBoundSMS" }, priority = 7)
		public void OutBoundSMSInvalidURL() {

			Response resp = given().header("Authorization", "75uv5FZwjj7dOdSTiJPvXuXEkWQyCWx4BKhm4Mj9npI=")
					.header("Content-Type", "application/json")
					.body("{    \"cmd\":\"send_sms\"," + " \"id\":\"request_1\"," 
							+ " \"sendermask\":\"KVGURU\"," + " \"from\":\"918882000001\"," + " \"to\":[\"919886001264\"],"
							+ " \"track_url\":\"false\"," + " \"expiry\":\"46389\","
							+ " \"body\":\"This is my SMS API KV_SMS\","
							+ " \"url_to_track\":\"https://qa-moov-tgo-kct.kirusa.com/campaignmanage/sms\" } ")
					.when().contentType(ContentType.JSON)
					.post("http://192.168.230.43:8080/api/v1/Accounts/Al7nk5vd1rN8FX+yXS7MQA==");
			System.out.println("Response from the API : " + resp.getStatusCode());
			ActualResp = resp.getStatusCode();
			ExpectedResp = APIController.convert_str_to_int(APIController.readconfigproperties("not_found"));
			Assert.assertEquals(ActualResp, ExpectedResp);
			ExpectedResp = resp.getStatusCode();
			if (resp.getStatusCode() == ExpectedResp) {
				System.out.println("Api Request sent along with Not found check the valid URL");
				System.out.println(resp.asString());
			} else {
				System.out.println("Api Request not submitted successfully");
			}
		}
		
		/*
		 * Passing missed parameter in URL
		 * ex: /Message tag missed
		 * Error Code : 405 method not found
		 */
		@Test(description = "OutBound SMS Invalid Parameter passed in URL..", groups = {"APIKonnectResponseErrorCode","OutBoundSMS" }, priority = 8)
		public void OutBoundSMSInvalidURLParams() {
			recpList.add("919886001212");
			recpList.add("919886001211");
			// System.out.println(APIController.sconfig);
			
			RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
			Response resp = given().spec(requestSpec)
					.body(KonnectPayLoad.OutBoundSMSLowPriority_payloadparams("send_sms", "OutBoundSMS_invalidparams", "918882000001", recpList, "false", "46389", "This is my SMS API KV_SMS", "https://www.google.com", KonnectAPIConstant.str_priority_high))
					.when().contentType(ContentType.JSON)
					.post(KonnectAPIConstant.str_KA_Account3_ACCOUNT_Id+"/Message")
					.then().log().all().extract().response();
			
			System.out.println("------>" +resp.getStatusCode());
			Reporter.log("----->"+resp.getStatusCode(),true);
		 // Status code
		 	APIResponseValidationLibrary.responseStatusCodeValidation(resp, "method_not_allowed");

			}
		
		/*
		 * passing request with invalid Sender Number
		 * Error code : 3005
		 * Error reason : {"error_reason":"Invalid Sender Number","error_code":"3005","status":"error"}
		 */
		@Test(description = "OutBound SMS Invalid Sender Number Error code..", groups = {"APIKonnectResponseSuccessCode","OutBoundSMS" }, priority = 6)
		public void OutBoundSMSInvalidSenderNum() {
			recpList.add("919886001212");
			recpList.add("919886001211");
			// System.out.println(APIController.sconfig);
			
			RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
			Response resp = given().spec(requestSpec)
					.body(KonnectPayLoad.OutBoundSMSLowPriority_payLoad("send_sms", "OutBound_InvalidSendernum", "KVGURU", "91888200000", recpList, "false", "46389", "This is my SMS API KV_SMS", "https://www.google.com", KonnectAPIConstant.str_priority_high))
					.when().contentType(ContentType.JSON)
					.post(KonnectAPIConstant.str_KA_Account3_ACCOUNT_Id+"/Messages")
					.then().log().all().extract().response();
			
			System.out.println("------>" +resp.getStatusCode());
			Reporter.log("----->"+resp.getStatusCode(),true);
			// Status code
		    Assert.assertEquals(resp.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + resp.getStatusCode());
			
		   
		    // Status
		    String str_status=APIController.getStringValue(APIController.getJsonString(resp.asString()), "status");
		    Assert.assertEquals(str_status, KonnectAPIConstant.str_error_Status,"Failed..!! Status not matching");
		    System.out.println("Status : "+str_status);
		    Reporter.log("Status : "+str_status);
		    
		 // Error_code
		    String str_errorCode=APIController.getStringValue(APIController.getJsonString(resp.asString()), "error_code");
		    System.out.println("Error code --> " + str_errorCode + " : description ==> "+APIErrorCode_Description.errCode_3005_InvalidSenderNumber);
		    Assert.assertEquals(APIController.convert_str_to_int(str_errorCode), APIController.convert_str_to_int(APIController.readconfigproperties("Invalid_Sender_Number")),"Failed..!! expected error code 3005");

		    
		 // Error Reason
		    String str_errorReason=APIController.getStringValue(APIController.getJsonString(resp.asString()), "error_reason");
		    Assert.assertEquals(str_errorReason, "Invalid Sender Number","Failed..!! Error reason not matching");
		    System.out.println("Error Reason : "+str_errorReason);
		      
			}
		
		/*
		 * passing request with in Empty Body Request
		 * Error code : 3010
		 * Error reason : {"error_reason":"Please provide the 'body'","error_code":"3010","status":"error"}
		 */
		@Test(description = "OutBound SMS Success response code..", groups = {"APIKonnectResponseSuccessCode","OutBoundSMS" }, priority = 2)
		public void OutBoundSMSEmptyParam() {
			recpList.add("919886001212");
			recpList.add("919886001211");
			// System.out.println(APIController.sconfig);
			
			RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
			Response resp = given().spec(requestSpec)
					.body(KonnectPayLoad.OutBoundSMSLowPriority_payLoad("send_sms", "OutBound_InvalidSendernum", "KVGURU", "918882000001", recpList, "false", "46389", "", "https://www.google.com", KonnectAPIConstant.str_priority_high))
					.when().contentType(ContentType.JSON)
					.post(KonnectAPIConstant.str_KA_Account3_ACCOUNT_Id+"/Messages")
					.then().log().all().extract().response();
			
			System.out.println("------>" +resp.getStatusCode());
			Reporter.log("----->"+resp.getStatusCode(),true);
			// Status code
		    Assert.assertEquals(resp.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + resp.getStatusCode());
			
		   
		    // Status
		    String str_status=APIController.getStringValue(APIController.getJsonString(resp.asString()), "status");
		    Assert.assertEquals(str_status, KonnectAPIConstant.str_error_Status,"Failed..!! Status not matching");
		    System.out.println("Status : "+str_status);
		    Reporter.log("Status : "+str_status);
		    
		 // Error_code
		    String str_errorCode=APIController.getStringValue(APIController.getJsonString(resp.asString()), "error_code");
		    System.out.println("Error code --> " + str_errorCode + " : description ==> "+APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
		    Assert.assertEquals(APIController.convert_str_to_int(str_errorCode), APIController.convert_str_to_int(APIController.readconfigproperties("Required_Field_Missing")),"Failed..!! expected error code 3010");

		    
		 // Error Reason
		    String str_errorReason=APIController.getStringValue(APIController.getJsonString(resp.asString()), "error_reason");
		    Assert.assertEquals(str_errorReason, "Please provide the 'body'","Failed..!! Error reason not matching");
		    System.out.println("Error Reason : "+str_errorReason);
		    
		  
			}

		
		/*
		 * passing request with Filed Missing Request
		 * Error code : 3011
		 * Error reason : {"error_reason":"Recipient List Empty","error_code":"3011","status":"error"}
		 */
		@Test(description = "OutBound SMS  Recipient List Empty Error code..", groups = {"APIKonnectResponseSuccessCode","OutBoundSMS" }, priority = 1)
		public void OutBoundSMSEmptyRecipient() {
			recpList.add("");
			//recpList.add("919886001211");
			// System.out.println(APIController.sconfig);
			
			RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
			Response resp = given().spec(requestSpec)
					.body(KonnectPayLoad.OutBoundSMSLowPriority_payLoad("send_sms", "OutBound_InvalidSendernum", "KVGURU", "918882000001", recpList, "false", "46389", "ffhsdghsgd", "https://www.google.com", KonnectAPIConstant.str_priority_high))
					.when().contentType(ContentType.JSON)
					.post(KonnectAPIConstant.str_KA_Account3_ACCOUNT_Id+"/Messages")
					.then().log().all().extract().response();
			
			System.out.println("------>" +resp.getStatusCode());
			Reporter.log("----->"+resp.getStatusCode(),true);
			// Status code
		    Assert.assertEquals(resp.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + resp.getStatusCode());
			System.out.println(resp.getStatusCode());
		   
		    // Status
		    String str_status=APIController.getStringValue(APIController.getJsonString(resp.asString()), "status");
		    Assert.assertEquals(str_status, KonnectAPIConstant.str_error_Status,"Failed..!! Status not matching");
		    System.out.println("Status : "+str_status);
		    Reporter.log("Status : "+str_status);
		    
		 // Error_code
		    String str_errorCode=APIController.getStringValue(APIController.getJsonString(resp.asString()), "error_code");
		    System.out.println("Error code --> " + str_errorCode + " : description ==> "+APIErrorCode_Description.errCode_3011_ProvidedRecipentListEmpty);
		    Assert.assertEquals(APIController.convert_str_to_int(str_errorCode), APIController.convert_str_to_int(APIController.readconfigproperties("Recipient_List_Empty")),"Failed..!! expected error code 3011");

		    
		 // Error Reason
		    String str_errorReason=APIController.getStringValue(APIController.getJsonString(resp.asString()), "error_reason");
		    Assert.assertEquals(str_errorReason, "Recipient List Empty","Failed..!! Error reason not matching");
		    System.out.println("Error Reason : "+str_errorReason);
		  
			}

		/*
		 * passing request with invalid JSON
		 * Error code : invalid_json
		 * Error reason : {"error_reason":"Missing value at 11 [character 5 line 2]","error_code":"invalid_json","status":"error"}
		 */
		@Test(description = "OutBound SMS  Invalid JSon Error code..", groups = {"APIKonnectResponseSuccessCode","OutBoundSMS" }, priority = 5)
		public void OutBoundSMSInvalidJSon() {
			recpList.add("");
			//recpList.add("919886001211");
			// System.out.println(APIController.sconfig);
			
			RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_KA_Account3_API_TOKEN_AUTHval);
			Response resp = given().spec(requestSpec)
					.body("{    \"cmd\":\"send_sms\"," + " \"id\":," 
							+ " \"sendermask\":\"KVGURU\"," + " \"from\":\"918882000001\"," + " \"to\":[\"919886001212\"],"
							+ " \"track_url\":\"false\"," + " \"expiry\":\"46389\","
							+ " \"body\":\"Hi user for outbound sms\","
							+ " \"url_to_track\":\"https://qa-moov-tgo-kct.kirusa.com/campaignmanage/sms\" } ")
					.when().contentType(ContentType.JSON)
					.post(KonnectAPIConstant.str_KA_Account3_ACCOUNT_Id+"/Messages")
					.then().log().all().extract().response();
			
			System.out.println("------>" +resp.getStatusCode());
			Reporter.log("----->"+resp.getStatusCode(),true);
			String ExpectedRes = APIController.readconfigproperties("Invalid_Json");
			String response = resp.asString();
			Reporter.log("Error Response : --------->>>>>> "+response,true);
			String Status = JsonPath.read(response, "$.status");
			System.out.println("Status : --------->>>>>> "+Status);
			String ErrorCode = JsonPath.read(response, "$.error_code");
			System.out.println("Error Code : --------->>>>>> "+ErrorCode);
			Reporter.log("Error Code : --------->>>>>> "+ErrorCode,true);
			String ErrorReason = JsonPath.read(response, "$.error_reason");
			System.out.println("Error Reason : --------->>>>>> "+ErrorReason);
			Assert.assertEquals(ErrorCode, ExpectedRes);
			if (ErrorCode.equals(ExpectedRes)) {
				System.out.println("Invalid Json Parsing check the Body part either Json declared correctly or not...");
				System.out.println(resp.asString());
			} else {
				System.out.println("Api Request not submitted successfully");
			}
			}
	

}
