package testExecution;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.konnect.api.generic.APIResponseValidationLibrary;

import io.restassured.specification.RequestSpecification;

/*
 * @Author : Anantha 
 * 
 * 'Account ID' value of the user is passed along with the URL
 * 'API token' of the user Passed in 'Authorization' section [header]
 * 
 * Request URL  : http://konnectautomation.kirusa.com:8080/api/v1/Accounts/IaGAIqYDNlG5fn8JVirdBg==/InboundPhoneNumbers/919977000077 
 * Request Type : DELETE
 * Body 		: No 
 * Note 		: Pass the Number in the Request URL   
 *  
 */

public class InboundSMS_DeleteAPI extends BaseClass {
	public int actualResp;
	public int expectedResp;
	public final static String str_apiToken_AUTHval = "Oeh2h7xTXJoNws8F3kQqV4PnbWgH0mLn4v6DqZz861g=";
	

	/*****************DELETE API***************************/
	
	/*
	 *  Empty number sent in request
	 *  Response Status code : 405 Method not allowed
	 */
	@Test(description = "Empty number passed in request", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 1)
	public void inbound_DeleteAPI_emptyRequest() {
		
		requestSpec = KonnectRequestBuilder.buildReqInboundDelete(str_apiToken_AUTHval);
				
		responseObj = given().spec(requestSpec).when()
				.delete("/TMYw2tCcWK_kn7sw4x64DQ==/"+KonnectAPIConstant.InboundPhoneNumbersEndPoint).then().log().all().extract()
				.response();
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "method_not_allowed");
		Reporter.log("Response Status code --> " + responseObj.getStatusCode() + " : description ==> "+APIErrorCode_Description.httpStatusCode_405,true);
		
	}// end of 'inbound_DeleteAPI_emptyRequest'
	

	
	/*
	 *  Invalid number passed in request
	 *  
	 *  Response : {"error_reason":"Invalid Number","error_code":"3017","status":"error"}
	 */
	@Test(description = "Invalid number passed in request", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 1)
	public void inbound_DeleteAPI_invalidPhNum() {
		
		requestSpec = KonnectRequestBuilder.buildReqInboundDelete(str_apiToken_AUTHval);
		String delPhNumber = "91888";
		
		responseObj = given().spec(requestSpec).when()
				.delete("/TMYw2tCcWK_kn7sw4x64DQ==/"+KonnectAPIConstant.InboundPhoneNumbersEndPoint+"/"+delPhNumber+"+").then().log().all().extract()
				.response();
		
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);	
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",KonnectAPIConstant.str_InvalidNumber,"String","");
		
		 // Status
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
		
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Invalid_Number","number",APIErrorCode_Description.errCode_3017_InvalidNumber);
			
	} // end of 'inbound_DeleteAPI_invalidPhNum'
	
	
	/*
	 *  Invalid EndPoint
	 *  Response Status code : 405
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode" }, priority = 1)
	public void inbound_DeleteAPI_invalidEndPoint() {
		
		requestSpec = KonnectRequestBuilder.buildReqInboundDelete(str_apiToken_AUTHval);
		
		String delPhNumber = "918884000014";
				
		responseObj = given().spec(requestSpec).when()
				     .delete("/TMYw2tCcWK_kn7sw4x64DQ==/InboundPhoneNum/+"+delPhNumber+"+")
				     .then().log().all().extract().response();
		
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "method_not_allowed");
		Reporter.log("Response Status code --> " + responseObj.getStatusCode() + " : description ==> "+APIErrorCode_Description.httpStatusCode_405,true);
	}// end of 'inbound_DeleteAPI_invalidEndPoint'
	
	
	/*
	 *  Passing Alphabets
	 *   
	 *  Response : {"error_reason":"Invalid Number","error_code":"3017","status":"error"}
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode" }, priority = 1)
	public void inbound_DeleteAPI_alphabets() {
		
		requestSpec = KonnectRequestBuilder.buildReqInboundDelete(str_apiToken_AUTHval);
		
		String delPhNumber = "qwert";
		
		responseObj = given().spec(requestSpec).when()
				.delete("/TMYw2tCcWK_kn7sw4x64DQ==/"+KonnectAPIConstant.InboundPhoneNumbersEndPoint+"/"+delPhNumber+"+").then().log().all().extract()
				.response(); 
		
		// Header Validation
		//APIResponseValidationLibrary.responseHeaderValidation_ContentEncoding(responseObj);	
		
		// Status code
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "success");
		
		// Error Reason
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_reason",KonnectAPIConstant.str_InvalidNumber,"String","");
		
		 // Status
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "status", KonnectAPIConstant.str_error_Status,"String","");
		
		// Error_code
		APIResponseValidationLibrary.generic_responseFieldValidation(responseObj, "error_code", "Invalid_Number","number",APIErrorCode_Description.errCode_3017_InvalidNumber);

	}// end of 'inbound_DeleteAPI_alphabets'
	
	
	/*
	 * 401 Unauthorized
	 *  Response Status code : 401
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode" }, priority = 1)
	public void inbound_DeleteAPI_Unauthorized() {
		
		requestSpec = KonnectRequestBuilder.buildReqInboundDelete(str_apiToken_AUTHval);
		
		String delPhNumber = "918884000014";
		
		responseObj = given().spec(requestSpec).when()
				.delete("/kan+LsmmsBumBQtPL/"+KonnectAPIConstant.InboundPhoneNumbersEndPoint+"/+"+delPhNumber+"+").then().log().all().extract()
				.response();
		
		APIResponseValidationLibrary.responseStatusCodeValidation(responseObj, "Unauthorized");
		Reporter.log("Response Status code --> " + responseObj.getStatusCode() + " : description ==> "+APIErrorCode_Description.httpStatusCode_405,true);
	
	}// end of 'inbound_DeleteAPI_Unauthorized'
	
	
}// end of class
