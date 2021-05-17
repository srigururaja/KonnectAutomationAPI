package testExecution;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.konnect.api.generic.APIResponseValidationLibrary;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/*
 * API : 2way obd
 *
 * @Author : Anantha 
 *
 * "priority":"low"
 * 
 * 2way OBD :  voice -> obd->  ["caller_id"]
 * 			   voice -> ivr
 *  
 *  pre condition : ivr number should be buyed/available in the account, caller_id should be available
 *  
 *  Request : http://konnectautomation.kirusa.com:8080/api/v1/Accounts/IaGAIqYDNlG5fn8JVirdBg==/Calls
 *  
 *  Request :
 *  {
		"id": "Anil_multi_10",
		"caller_id":"919221000013",
		"recipient":["918881000056"],
		"media_url":"https://konnectqa1.kirusa.com/uploads/media/2018_11_13/content_700_1542101618842.wav",
		"callback_url":"https://konnectqa1.kirusa.com/admin/callback_url",
		"direction":"2way",
		"priority":"low",
		"ivr_number":"919977000078"
	}
 *	
 * Note : once IVR number is attached need to deleted[ivr number] by delete request
 */
public class TwoWayOBDLowPriorityAPI extends BaseClass {

	/*
	 * Passing valid Request and detaching the number
	 */
	 @Test(description = "Passing valid Request", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 1)
	public void TwowayOBD_lowPriority_validRequest() throws InterruptedException {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
		
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, KonnectAPIConstant.str_KA_Account2_callerID, recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
				.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
				.all().extract().response();
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
		
	    // Status
        APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_OK_Status,"String","");
	
        Thread.sleep(5000);
        
        System.out.println("-- Detach number processing --");
        String delPhNumber = KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority;
        String delete_resourceEndPointURL = KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.InboundPhoneNumbersEndPoint+"/"+delPhNumber;
       
        RequestSpecification requestSpec1 = KonnectRequestBuilder.buildReq(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
        
        
        Response responseObj1 = given().spec(requestSpec1).when()
				      .delete(delete_resourceEndPointURL)
				      .then().log().all().extract().response(); 
        
      // Status code
      APIResponseValidationLibrary.responseStatusCodeValidation(responseObj1, "success"); 
     
     // Status
     APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_OK_Status,"String","");
	
     Thread.sleep(8000);
     
	 }// end of 'TwowayOBD_lowPriority_validRequest'
	 
	 
	 /********************
	 /*
	  *    Field : id
	  *    priority :"low"
	  */
	 /*******************
	 
	 /*
	  * Response : {"error_reason":"Field 'Required Field Missing <id>' is missing in the request.","error_code":"3010","status":"error"}
	  */
	 @Test(description = "Passing request with out Id field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_WitOut_id_Request() throws InterruptedException {
			recpList.add("2348100000001");
			recpList.add("2348100000002");
			recpList.add("2348100000003");
			
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(null, KonnectAPIConstant.str_KA_Account2_callerID, recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",APIResponseValidationLibrary.requiredFieldMissing("id") ,"String","");
		
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
			
		// Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
		
	        
	}// end of 'TwowayOBD_lowPriority_WitOut_id_Request'
	 
	 
	 /*
	  * Response : {"error_reason":"Field 'Required Field Missing <id>' is missing in the request.","error_code":"3010","status":"error"}
	  */
	 @Test(description = "Passing empty string for Id field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_Empty_id_Request() throws InterruptedException {
		
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
			
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				     .body(KonnectPayLoad.TwoWay_OBDRequest("", KonnectAPIConstant.str_KA_Account2_callerID, recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",APIResponseValidationLibrary.requiredFieldMissing("id") ,"String","");
			
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
		
		// Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");

	 }// end of 'TwowayOBD_lowPriority_Empty_id_Request'
	 
	 
	 /************************/
	 /*
	  *    Field : callerId
	  *    Priority : 'low'
	  */
	 /***********************/
	 
	 
	 /*
	  * Response : {"error_reason":"Field 'Required Field Missing <caller_id>' is missing in the request.","error_code":"3010","status":"error"}
	  */
	 @Test(description = "Passing request with out 'callerId' field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_WitOut_callerId_Request() throws InterruptedException {
			recpList.add("2348100000001");
			recpList.add("2348100000002");
			recpList.add("2348100000003");
			
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, null, recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",APIResponseValidationLibrary.requiredFieldMissing("caller_id") ,"String","");
			
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
			
		// Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	       
	 }// end of 'TwowayOBD_lowPriority_WitOut_callerId_Request'	
	 
	 
	 /*
	  * Response : {"error_reason":"Field 'Required Field Missing <caller_id>' is missing in the request.","error_code":"3010","status":"error"}
	  */
	 @Test(description = "Passing empty field  in 'callerId' field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_Empty_callerId_Request() throws InterruptedException {
			recpList.add("2348100000001");
			recpList.add("2348100000002");
			recpList.add("2348100000003");
			
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, "", recpList, KonnectAPIConstant.mediaURL,
						KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",APIResponseValidationLibrary.requiredFieldMissing("caller_id") ,"String","");
			
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
			
		// Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	       
	 }// end of 'TwowayOBD_lowPriority_Empty_callerId_Request'	
	 
	 
	 /*
	  * Response : {"error_reason":"Invalid Caller Id","error_code":"3007","status":"error"}
	  */
	 @Test(description = "Passing invalid string in 'callerId' field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_invalid_callerId_Request() throws InterruptedException {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
			
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				      .body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, "qwert", recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					  .when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					  .all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",KonnectAPIConstant.str_InvalidCallerID ,"String","");
			
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Invalid_Caller_Id","number",APIErrorCode_Description.errCode_3007_InvalidCallerId);
			
		// Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	       
	 }// end of 'TwowayOBD_lowPriority_invalid_callerId_Request'	
	 
	 
	 /************************/
	 /*
	  *    Field : recipient
	  *    
	  */
	 /***********************/
	 
	 /*
	  * {"error_reason":"Field 'Required Field Missing <recipient>' is missing in the request.","error_code":"3010","status":"error"}
	  */
	 @Test(description = "Passing empty value under 'recipient' field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_Empty_recipient_Request() throws InterruptedException {
			
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, KonnectAPIConstant.str_KA_Account2_callerID, null,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",APIResponseValidationLibrary.requiredFieldMissing("recipient") ,"String","");
			
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
			
		// Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	       
	 }// end of 'TwowayOBD_lowPriority_Empty_recipient_Request'	
	 
	 /************************/
	 /*
	  *    Field : media_url
	  *    Priority : low
	  */
	 /***********************/

	 /*
	  * {"error_reason":"Please provide the 'media_url'","error_code":"3010","status":"error"}
	  */
	 @Test(description = "Passing request with empty 'MediaURL' field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_Empty_MediaURL_Request() throws InterruptedException {
			
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003"); 
		 
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, KonnectAPIConstant.str_KA_Account2_callerID, recpList, "",
						KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",KonnectAPIConstant.str_ProvideMediaURL ,"String","");
		
		
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
			
		// Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	       
	 }// end of 'TwowayOBD_lowPriority_Empty_MediaURL_Request'		
	 
	 
	 /*
	  * {"error_reason":"Field 'Required Field Missing <media_url>' is missing in the request.","error_code":"3010","status":"error"}
	  */
	 @Test(description = "Passing request without 'MediaURL' field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_withOut_MediaURL_Request() throws InterruptedException {
			
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003"); 
		 
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, KonnectAPIConstant.str_KA_Account2_callerID, recpList,
						null, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",APIResponseValidationLibrary.requiredFieldMissing("media_url") ,"String","");
					
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
					
		// Status
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	       
	 }// end of 'TwowayOBD_lowPriority_withOut_MediaURL_Request'	
	 
	 
	 /************************/
	 /*
	  *    Field : ivr_number
	  *    Priority : low 
	  */
	 /***********************/
	 
	 @Test(description = "Passing request with invalid 'ivr_number' field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_Invalid_IVRNumber_Request() throws InterruptedException {
			
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003"); 
		 
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, KonnectAPIConstant.str_KA_Account2_callerID, recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, "912345"))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",KonnectAPIConstant.str_Invalid_IVR_Number ,"String","");
		
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Invalid_IVR_Number","number",APIErrorCode_Description.errCode_3008_InvalidIVRNumber);
			
		// Status
	    APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	       
	 }// end of 'TwowayOBD_lowPriority_Invalid_IVRNumber_Request'
	 
	 
	 /*
	  * {"error_reason":"Field 'Required Field Missing <ivr_number>' is missing in the request.","error_code":"3010","status":"error"}
	  */
	 @Test(description = "Passing request with empty 'ivr_number' field", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_Empty_IVRNumber_Request() throws InterruptedException {
			
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003"); 
		 
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, KonnectAPIConstant.str_KA_Account2_callerID, recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, null))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
			
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);
			
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",APIResponseValidationLibrary.requiredFieldMissing("ivr_number") ,"String","");
							
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Required_Field_Missing","number",APIErrorCode_Description.errCode_3010_RequiredFieldMissing);
							
		// Status
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
	       
	 }// end of 'TwowayOBD_lowPriority_Empty_IVRNumber_Request'
	 
	 
	 /*
	  * Invalid API Token
	  */
	 @Test(description = "Passing invalid API Token", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_invalidAPIToken_Request() throws InterruptedException {
			
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003"); 
		 
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_API_Invalid_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, KonnectAPIConstant.str_KA_Account2_callerID, recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "Unauthorized");
	 }// end of 'TwowayOBD_lowPriority_invalidAPIToken_Request'
	 
	 
	 /*
	  * Invalid Authorization
	  */
	 @Test(description = "Passing invalid Authorization Token", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 2)
		public void TwowayOBD_lowPriority_invalidAuthorizationToken_Request() throws InterruptedException {
			
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003"); 
		 
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
			
		responseObj = given().spec(requestSpec)
					 .body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, KonnectAPIConstant.str_KA_Account2_callerID, recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_InvalidACCOUNT_Id+"/"+KonnectAPIConstant.CallsEndPoint).then().log()
					.all().extract().response();
			
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "Unauthorized");
	
	 }// end of 'TwowayOBD_lowPriority_invalidAuthorizationToken_Request'
	 
	 /*
	  * Passing invalid resource EndPoint
	  */
	 @Test(description = "Passing invalid resource EndPoint", groups = { "APIKonnectResponseErrorCode", "2WayOBDHighPriority" }, priority = 3)
	 public void TwowayOBD_lowPriority_invalidResourceEndPoint() throws InterruptedException {
		recpList.add("2348100000001");
		recpList.add("2348100000002");
		recpList.add("2348100000003");
				
		requestSpec = KonnectRequestBuilder.buildReqOBDCalls(KonnectAPIConstant.str_KA_Account2_API_TOKEN_AUTHval);
		
		responseObj = given().spec(requestSpec)
					.body(KonnectPayLoad.TwoWay_OBDRequest(KonnectAPIConstant.id, KonnectAPIConstant.str_KA_Account2_callerID, recpList,
						KonnectAPIConstant.mediaURL, KonnectAPIConstant.callBckUrl, KonnectAPIConstant.direction_2Way,
						KonnectAPIConstant.str_priority_low, KonnectAPIConstant.str_KA_Account2_2wayOBD_IVRNumber_low_Priority))
					.when().post(KonnectAPIConstant.str_KA_Account2_ACCOUNT_Id+"/Call").then().log()
					.all().extract().response();
				
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "method_not_allowed");
			
	 }// end of 'TwowayOBD_invalidResourceEndPoint'
		 
	 
}
