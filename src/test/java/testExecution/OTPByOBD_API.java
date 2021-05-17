package testExecution;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.specification.RequestSpecification;

/*
 * @Author : Anantha 
 * 
 * 'Account ID' value of the user is passed along with the URL
 * 'API token' of the user Pass in request body 'api_token' 
 * 
 * 
 * "to":"13371234567" [to number will be the reciver]
   "caller_id":"13371234568", [caller_id will be number from the OBD]
 * voice -> obd -> +91 92210 00012 [caller_id] [number][+91 92210 00028]
 * 
 * Request URL  : https://konnectautomation.kirusa.com/admin/api/obd
 * Request type : POST
 * Request Body :
 *  {
	  "id": "ANHD687", 
	  "cmd": "send_otp_by_obd",
	  "api_token":"+XmLr_X7jXxFhRIZqhhCjUdhuwhMiA5KBuhDFzoWNDE=",
	  "to":"919886002121",
	  "caller_id":"919221000007",
	  "otp": "7878" 
	}
 *  
 */

public class OTPByOBD_API {
	
	public final static String str_API_TOKEN_AUTHval = "+XmLr_X7jXxFhRIZqhhCjUdhuwhMiA5KBuhDFzoWNDE=";
	public final static String str_ACCOUNT_Id = "4zXT399xEXfskC6xWOFv2Q==";
	public final static String str_CallerId="919221000007";
	public final static String str_CMD="send_otp_by_obd";

	/*****************OTP By OBD***************************/
	
	/*
	 *  Passing invalid APIToken
	 *  Message : Invalid API Token
	 */
	@Test(description = "invalid APIToken ", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_invalidAPIToken() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		//String delPhNumber = null;
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObd_payload("ANHD687", str_CMD, "RsQZvacg4+Oh5N_mljKvEENcXO+ye19sjummZM=", "918881000038", str_CallerId))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    Assert.assertEquals(str_errReason, "Invalid API Token","Failed..!! Error reason not matching");
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(str_errCode, "invalid_api_token","Failed..!! Error code not matching");
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!!status not matching");
		
	}// end of 'OTPByOBD_API_invalidAPIToken'
	
	
	/*
	 *  Passing invalid Account ID
	 *  "error_code":"invalid_app_token"
	 */
	@Test(description = "invalid AccountID ", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_invalidAccountID() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD("XrmTHfkzyjSYWGDUw==");
		//String delPhNumber = null;
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObd_payload("ANHD687", str_CMD, str_API_TOKEN_AUTHval, "918881000038", str_CallerId))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    Assert.assertEquals(str_errReason, "Invalid Account ID","Failed..!! Error reason not matching");
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(str_errCode, "invalid_app_token","Failed..!! Error code not matching");
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! status not matching");
		
	}// end of 'OTPByOBD_API_invalidAccountID'
	
	/*
	 *  Passing invalid cmd
	 *  "error_code":"invalid_app_token"
	 */
	@Test(description = "invalid cmd ", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_invalidCMD() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		//String delPhNumber = null;
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObd_payload("ANHD687", "send_otp_", "+XmLr_X7jXxFhRIZqhhCjUdhuwhMiA5KBuhDFzoWNDE=", "918881000038", str_CallerId))
				.when().contentType(ContentType.JSON).post("/admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    System.out.println("------"+str_errReason.substring(0, 9));
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    System.out.println("Error code : " +str_errCode);
	    Assert.assertEquals(str_errCode, "1000","Failed..!! Error code not matching, expected 1000");
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    
	    // cmd
	    String str_cmd=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "cmd");
	    Assert.assertEquals(str_cmd, "send_otp_","Failed..!! Error status not matching");
		
	}// end of 'OTPByOBD_API_invalidCMD'
	
	
	
	/*
	 *  removing OTP field and checking response
	 *  
	 */
	@Test(description = "removing OTP field and checking response", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_WithOurOTPfield() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		//String delPhNumber = null;
		
		// KonnectInboundPayLoad.otpByObd_noOTP_payload("ANHD687", str_CMD, str_API_TOKEN_AUTHval, "918881000038", "919221000009")
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687", str_CMD, str_API_TOKEN_AUTHval, "918881000038", "919221000009",null))
				.when().contentType(ContentType.JSON).post("/admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    Assert.assertEquals(str_errReason, "Field 'Required Field Missing <otp>' is missing in the request.","Failed..!! Error reason not matching");
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(APIController.convert_str_to_int(str_errCode), APIController.convert_str_to_int(APIController.readconfigproperties("Required_Field_Missing")),"Failed..!! expected error code 3010");
	    System.out.println("Error code --> " + str_errCode + " : description ==> "+APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    
	    // cmd
	    String str_localcmd=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "cmd");
	    Assert.assertEquals(str_localcmd, str_CMD,"Failed..!! Error status not matching");
		
	}// end of 'OTPByOBD_API_WithOurOTPfield'
	
	
	/*
	 * removing CallerId field and checking response
	 */
	@Test(description = "removing CallerId field and checking response", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_WithOut_CallerId_field() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687", str_CMD, str_API_TOKEN_AUTHval, "918881000038", null,"7878"))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response : ----->" + resp.asString());
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    Assert.assertEquals(str_errReason, "Field 'Required Field Missing <caller_id>' is missing in the request.","Failed..!! Error reason not matching");
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(APIController.convert_str_to_int(str_errCode), APIController.convert_str_to_int(APIController.readconfigproperties("Required_Field_Missing")),"Failed..!! expected error code 3010");
	    System.out.println("Error code --> " + str_errCode + " : description ==> "+APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    
	    // cmd
	    String str_localcmd=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "cmd");
	    Assert.assertEquals(str_localcmd, str_CMD,"Failed..!! Error status not matching");	
	
	}// end of 'OTPByOBD_API_WithOut_CallerId_field'
	
	
	/*
	 *  removing To field and checking response
	 *  
	 */
	@Test(description = "removing To field from request and checking response", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_WithOut_To_field() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		//String delPhNumber = null;
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687", str_CMD, str_API_TOKEN_AUTHval,null,str_CallerId,"7878"))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
	    
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    Assert.assertEquals(str_errReason, "Field 'Required Field Missing <to>' is missing in the request.","Failed..!! Error reason not matching");
	    System.out.println("Error reason : " +str_errReason);
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(APIController.convert_str_to_int(str_errCode), APIController.convert_str_to_int(APIController.readconfigproperties("Required_Field_Missing")),"Failed..!! expected error code 3010");
	    System.out.println("Error code --> " + str_errCode + " : description ==> "+APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    System.out.println("Status : " +str_statuse);
	    
	    // cmd
	    String str_cmd=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "cmd");
	    Assert.assertEquals(str_cmd, str_CMD,"Failed..!! Error status not matching");
	    System.out.println("cmd : " +str_statuse);
	    
	}// end of 'OTPByOBD_API_WithOut_To_field'
	
	
	/*
	 *  removing APIToken field and checking response
	 *  
	 */
	@Test(description = "removing APIToken field from request and checking response", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_WithOut_APIToken_field() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		//String delPhNumber = null;
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687",str_CMD,null,"918881000038",str_CallerId,"7878"))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    Assert.assertEquals(str_errReason, "Field 'Required Field Missing <api_token>' is missing in the request.","Failed..!! Error reason not matching, expected 'Field 'Required Field Missing <api_token>' is missing in the request.' ");
	    System.out.println("Error reason : " +str_errReason);
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(APIController.convert_str_to_int(str_errCode),APIController.convert_str_to_int(APIController.readconfigproperties("Required_Field_Missing"))," Failed..!! Expected 3010 status code but found ");
		System.out.println("Error code --> " + str_errCode + " : description ==> "+APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    System.out.println("Status : " +str_statuse);
	 
	}// end of 'OTPByOBD_API_WithOut_APIToken_field'
	
	
	
	/*
	 *  removing CMD field and checking response
	 */
	@Test(description = "removing OTP field and checking response", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_WithOut_CMD_field() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687",null,str_API_TOKEN_AUTHval,"918881000038",str_CallerId,"7878"))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    System.out.println("Error reason : "+str_errReason);
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(str_errCode, "invalid_json","Failed..!! Error code not matching, expected 'invalid_json' ");
	    System.out.println("Error code : "+str_errCode);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    System.out.println("status : "+str_statuse);
	
	}// end of 'OTPByOBD_API_WithOut_CMD_field'
	
	
	//==================Passing Empty String in the request=========================//
	
	/*
	 * Passing all data empty to request
	 */
	@Test(description = "removing OTP field and checking response", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_allfiledsemptyRequest() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("","","","","",""))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    Assert.assertEquals(str_errReason, "Invalid API Token","Failed..!! Error reason not matching");
	    System.out.println("Error reason : " +str_errReason);
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(str_errCode, "invalid_api_token","Failed..!! Error code not matching, expected 'invalid_api_token' ");
	    System.out.println("Error code : "+str_errCode);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    System.out.println("status : "+str_statuse);
	    
	}// end of 'OTPByOBD_API_allfiledsemptyRequest'
	
	
	/*
	 * Passing only api_token as data rest fields as empty 
	 */
	@Test(description = "removing OTP field and checking response", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_OnlyapitokenData() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("","",str_API_TOKEN_AUTHval,"","",""))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Reason
	    String str_errReason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    System.out.println("Error reason : " +str_errReason);
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(str_errCode, "1000","Failed..!! Error code not matching, expected 1000");
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    System.out.println("status : "+str_statuse);
	    
	}// end of 'OTPByOBD_API_OnlyapitokenData'
	
	
	/*
	 * Passing empty string inside 'cmd'
	 */
	@Test(description = "Passing empty string inside 'cmd'", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_emptyStringCmd() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687","",str_API_TOKEN_AUTHval,"918881000038",str_CallerId,"7878"))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(str_errCode, "1000","Failed..!! Error code not matching, expected 1000");
	    System.out.println("Error code : " +str_errCode);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    System.out.println("status : "+str_statuse);
	    
	}// end of 'OTPByOBD_API_emptyStringCmd'
	
	/*
	 * Passing empty string inside 'To
	 */
	@Test(description = "Passing empty string inside 'To'", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_emptyStringTo() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687","send_otp_by_obd",str_API_TOKEN_AUTHval,"",str_CallerId,"7878"))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
        // Error Reason
	    String str_errRerason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    Assert.assertEquals(str_errRerason, "To number is not valid","Failed..!! Error Reason not matching");
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(str_errCode, "to.number.not.valid","Failed..!! Error code not matching");
	    System.out.println("Error code : " +str_errCode);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    System.out.println("status : "+str_statuse);
	    
	}// end of 'OTPByOBD_API_emptyStringTo'
	
	
	/*
	 * Passing empty string inside 'caller_Id'
	 */
	@Test(description = "Passing empty string inside 'callerId'", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_emptyString_callerId() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687","send_otp_by_obd",str_API_TOKEN_AUTHval,"918881000038","","7878"))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
        // Error Reason
	    String str_errRerason=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_reason");
	    Assert.assertEquals(str_errRerason, "From number invalid. Use your purchased OBD/2-Way Voice number.","Failed..!! Error Reason not matching");
	    System.out.println("Error Reason : " +str_errRerason);
	    
	    // Error Code
	    String str_errCode=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "error_code");
	    Assert.assertEquals(str_errCode, "from.number.not.valid","Failed..!! Error code not matching");
	    System.out.println("Error code : " +str_errCode);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "error","Failed..!! Error status not matching");
	    System.out.println("status : "+str_statuse);
	    
	}// end of 'OTPByOBD_API_emptyString_callerId'
	
	
	/*
	 * Passing empty string inside 'OTP'
	 */
	@Test(description = "Passing empty string inside 'callerId'", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_emptyString_OTP() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687","send_otp_by_obd",str_API_TOKEN_AUTHval,"918881000038",str_CallerId,""))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Cmd
	    String str_cmd=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "cmd");
	    Assert.assertEquals(str_cmd, str_CMD,"Failed..!! cmd not matching");
	    System.out.println("cmd : "+str_cmd);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "ok","Failed..!! Error status not matching");
	    System.out.println("status : "+str_statuse);
	    
	}// end of 'OTPByOBD_API_emptyString_OTP'
	
	
	/*
	 * Passing valid data
	 */
	@Test(description = "Passing valid data", groups = {"APIKonnectResponseErrorCode","OTPByOBD" }, priority = 1)
	public void OTPByOBD_API_validData() {
		
		RequestSpecification reqspec = KonnectRequestBuilder.buildReqOTPByOBD(str_ACCOUNT_Id);
		
		Response responseObj = given().spec(reqspec)
				.body(KonnectPayLoad.otpByObdHashMap("ANHD687","send_otp_by_obd",str_API_TOKEN_AUTHval,"918881000038",str_CallerId,"7878"))
				.when().contentType(ContentType.JSON).post("admin/api/obd").then().log()
				.all().extract().response();
		
			
		Response resp = responseObj.then().log().all().extract().response();
		
		System.out.println("Response Status code : " + responseObj.getStatusCode());
		
		System.out.println("Response : ----->" + resp.asString());
		 
		// Status code
	    Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		
	    // Cmd
	    String str_cmd=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "cmd");
	    Assert.assertEquals(str_cmd, str_CMD,"Failed..!! cmd not matching");
	    System.out.println("cmd : "+str_cmd);
	    
	    // Status
	    String str_statuse=APIController.getStringValue(APIController.getJsonString(responseObj.asString()), "status");
	    Assert.assertEquals(str_statuse, "ok","Failed..!! Error status not matching");
	    System.out.println("status : "+str_statuse);
	    
	}// end of 'OTPByOBD_API_validData'
	
	
	
	
}// end of class
