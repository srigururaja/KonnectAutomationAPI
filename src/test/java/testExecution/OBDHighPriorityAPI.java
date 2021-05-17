package testExecution;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.konnect.api.generic.APIResponseValidationLibrary;

import io.restassured.http.ContentType;

/*
 * @Author  : Anantha
 * API      : OBD
 * priority :  High
 *
 * 'Account ID' value of the user is passed along with the URL
 * 'API token' of the user Passed in 'Authorization' section [header]
 * 
 * OBD Low/high [voice -> obd]  [+919221000007][number][caller_id]
 * "caller_id" number[Outbound Dialers] should be available
 * 
 * Request URL  : http://konnectautomation.kirusa.com:8080/api/v1/Accounts/4zXT399xEXfskC6xWOFv2Q==/Calls
 * Request Type : POST
 * Request Body :
 *  {
		"id": "Test",
		"caller_id":"919221000007",
		"recipient":["2348100000001","2348100000002","2348100000003","2348100000004","2348100000005","2348100000006","2348100000007","2348100000008","2348100000009","2348100000010"],
		"media_url":"https://konnectqa1.kirusa.com/uploads/media/2018_11_13/content_700_1542101618842.wav",
		"callback_url":"https://konnectqa1.kirusa.com/admin/callback_url",
		"direction":"outbound",
		"priority":"high"
	}
 * 
 */


public class OBDHighPriorityAPI extends BaseClass {

	
	String mediaURL   = "https://konnectqa1.kirusa.com/uploads/media/2018_11_13/content_700_1542101618842.wav";
	String callBckUrl = "https://konnectqa1.kirusa.com/admin/callback_url";

	/*
	 * Passing valid Request
	 */
	@Test(description = "Passing valid Request", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHigh_validRequest() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
		
	    // Status
        APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_OK_Status,"String","");
	}// end of 'obdHigh_validRequest'
	
	/*
	 * Passing Request with out CallerID filed 
	 * Error code : 3010
	 * Response : {"error_reason":"Field 'Required Field Missing <caller_id>' is missing in the request.","error_code":"3010","status":"error"}
	 */
	@Test(description = "Passing Request with out CallerID filed", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_withOutCallerId() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", null, recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		Reporter.log("Response : " + responseObj.prettyPrint(),true);
		
		
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);		
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String",""); 

		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",APIResponseValidationLibrary.requiredFieldMissing("caller_id") ,"String","");
	    
	    // Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing); 
	    
	}// obdHighTest_withOutCallerId
	
	
	/*
	 * Passing Request with invalid CallerID filed 
	 * Error code : 3007
	 * Response : {"error_reason":"Invalid Caller Id","error_code":"3007","status":"error"}
	 */
	@Test(description = "Passing Request with invalid CallerID filed", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_InvalidCallerId() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", "qwerty", recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		Reporter.log("Response : " + responseObj.prettyPrint(),true);
		
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);	
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
						
	   // Header Validation
	   //APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
	    
	   // Status
	   APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String",""); 
	   
	   // Error Reason
	   APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",KonnectAPIConstant.str_InvalidCallerID,"String","");
	    
	   // Error_code
	   APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Invalid_Caller_Id","number",APIErrorCode_Description.errCode_3007_InvalidCallerId); 
	   
	} // end of 'obdHighTest_InvalidCallerId'
	
	
	/*
	 * Passing Request with empty CallerID filed 
	 * Error code : 3007
	 * Response : {"error_reason":"Field 'Required Field Missing <caller_id>' is missing in the request.","error_code":"3010","status":"error"}
	 */
	@Test(description = "Passing Request with empty CallerID filed ", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_Empty_CallerId() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", "", recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		Reporter.log("Response : " + responseObj.prettyPrint(),true);
		
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	    
	    // Error Reason
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason", KonnectAPIConstant.str_RequiredFiledMissing_CallerID_errorMsg,"String","");
	    
	    // Error_code
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
		
	} // end of 'obdHighTest_InvalidCallerId'
	
	
	
	/*
	 * Passing Request with empty recipient filed 
	 * Error code : 3011
	 * {"error_reason":"Recipient List Empty","error_code":"3011","status":"error"}
	 */
	@Test(description = "Passing Request with empty recipient filed ", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_Empty_recipient() {
		ArrayList<String> recpList1 = new ArrayList<String>();
		recpList1.add("");
		
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", KonnectAPIConstant.str_KA_Account2_2_callerID, recpList1, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		 Reporter.log("Response : " + responseObj.prettyPrint(),true);
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	    
	    // Error Reason
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason", KonnectAPIConstant.str_RecipientListEmpty,"String","");
	    
	    // Error_code
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Recipient_List_Empty","number",APIErrorCode_Description.errCode_3011_ProvidedRecipentListEmpty);
		
	} // end of 'obdHighTest_Empty_recipient'
	
	
	/*
	 * Passing Request with without 'recipient' filed 
	 * Error code : 3011
	 * {"error_reason":"Field 'Required Field Missing <recipient>' is missing in the request.","error_code":"3010","status":"error"}
	 */
	@Test(description = "Passing Request with without 'recipient' filed ", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_withOut_recipient() {
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", KonnectAPIConstant.str_KA_Account2_2_callerID, null, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		Reporter.log("Response : " + responseObj.prettyPrint(),true);
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	    
	    // Error Reason
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason", KonnectAPIConstant.str_RequiredFiledMissing_recipient_errorMsg,"String","");
	    
	    // Error_code
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
		
	} // end of 'obdHighTest_InvalidCallerId'
	
	
	/*
	 * Passing Request with without 'recipient' filed 
	 * Error code : 3011
	 * {"error_reason":"Field 'Required Field Missing <recipient>' is missing in the request.","error_code":"3010","status":"error"}
	 */
	@Test(description = "Passing Request with without 'recipient' filed", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_InvalidRecipientInputs() {
		
		recpList.add("!@#$");
		recpList.add("qwerty");
		recpList.add("123456");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
	    Reporter.log("Response : " + responseObj.prettyPrint(),true);
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_OK_Status,"String","");
		
	} // end of 'obdHighTest_InvalidCallerId'
	
	
	/*
	 * Passing Request with empty media_url filed 
	 * Error code : 3010
	 * Response : {"error_reason":"Please provide the 'media_url'","error_code":"3010","status":"error"}
	 */
	@Test(description = "Passing Request with invalid media_url filed", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_Empty_MediaURL() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, "", callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
	    Reporter.log("Response : " + responseObj.prettyPrint(),true);
			
    	// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
		
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	    
	    // Error Reason
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason", KonnectAPIConstant.str_ProvideMediaURL,"String","");
	    
	    // Error_Code
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
		
	} // end of 'obdHighTest_InvalidCallerId'
	
	
	/*
	 * Passing Request with without media_url filed 
	 * Error code : 3010
	 * Response : {"error_reason":"Field 'Required Field Missing <media_url>' is missing in the request.","error_code":"3010","status":"error"}
	 */
	@Test(description = "Passing Request with without media_url filed", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_withOut_MediaURL() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, null, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		Reporter.log("Response : " + responseObj.prettyPrint(),true);
		
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	    
	    // Error Reason KonnectAPIConstant.str_RequiredFiledMissing_mediaURL_errorMsg
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",KonnectAPIConstant.requiredFieldMissing("media_url"),"String","");
	    
	    // Error_Code
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
		
	} // end of 'obdHighTest_withOut_MediaURL'
	
	
	/*
	 * Passing Request with without media_url filed 
	 */
	@Test(description = "Passing Request with without media_url filed", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_invalid_MediaURL() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, "sdfsdf", callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		Reporter.log("Response : " + responseObj.prettyPrint(),true);     // Header Validation
		
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_OK_Status,"String","");
	} // end of 'obdHighTest_withOut_MediaURL'
	
	
	/*
	 * Passing Request with empty CallerID filed 
	 * Error code : 3010
	 * Response : {"error_reason":"Field 'Required Field Missing <id>' is missing in the request.","error_code":"3010","status":"error"}
	 */
	@Test(description = "Passing Request with empty CallerID filed ", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_Empty_Id() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("", KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		Reporter.log("Response : " + responseObj.prettyPrint(),true);    
		
		// Header Validation    
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
				
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	    
	    // Error Reason
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason", KonnectAPIConstant.requiredFieldMissing("id"),"String","");
	    
	    // Error_code
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
		
	} // end of 'obdHighTest_InvalidCallerId'
	

	/*
	 * Passing Request with empty CallerID filed 
	 * Error code : 3010
	 * Response : {"error_reason":"Field 'Required Field Missing <id>' is missing in the request.","error_code":"3010","status":"error"}
	 */
	@Test(description = "Passing Request with empty CallerID filed ", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_withOutID() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad(null, KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		Reporter.log("Response : " + responseObj.prettyPrint(),true);     
		
		// Header Validation    
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
	    // Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	    
	    // Error Reason
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason", KonnectAPIConstant.requiredFieldMissing("id"),"String","");
	    
	    // Error_code
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
		
	} // end of 'obdHighTest_InvalidCallerId'
	

	
	/*
	 * Passing invalid resource 
	 * Error code : 405
	 */
	@Test(description = "Passing Request with empty CallerID filed ", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_passingInvlaidResourceName() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test",KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/Cal").then().log()
				.all().extract().response();
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "method_not_allowed");
		
	} // end of 'obdHighTest_InvalidCallerId'
	

	/*
	 * Passing invalid Authorization
	 * Error code : 401
	 */
	@Test(description = "Passing invalid Authorization ", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_passingInvalidAuthorization() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10", KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post("_wIWRXrmTHfkzyjSYWG/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "Unauthorized");
		
	} // end of 'obdHighTest_passingInvalidAuthorization'
	
	
	/*
	 * Passing invalid API Token
	 * Error code : 401
	 */
	@Test(description = "Passing invalid Authorization ", groups = { "APIKonnectResponseErrorCode", "OBDHighPriority" }, priority = 1)
	public void obdHighTest_passingInvalidAPIToken() {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		
		 requestSpec = KonnectRequestBuilder.buildReqOBDCalls("gA39fRsQZvacg4+Oh5N_mljKvEENcXO+ye19s");
		
		 responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.OBDLowPriority_payLoad("Test_10",KonnectAPIConstant.str_KA_Account2_2_callerID, recpList, mediaURL, callBckUrl, "outbound", KonnectAPIConstant.str_priority_high))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "Unauthorized");
		
	} // end of 'obdHighTest_passingInvalidAuthorization'
	
}
