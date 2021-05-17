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
 * 'Account ID' value of the user is passed along with the URL
 * 'API token' of the user Passed in 'Authorization' section [header]
 * 
 * Account Name : kirusatest20@gmail.com
 * PWD : Abcd@123
 * 
 */

public class InboundSMSAPI {
	public int actualResp;
	public int expectedResp;
	public final static String str_apiToken_AUTHval = "4s2y+QjdwS_bvsGz1vhYx6TeDNQhBmI_wXHyL7lAL6k=";
	

	@Test(description = "Error response code from the Konnect Server..", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_200StatusCode() {
		String strAccoutId = "kan+LsmmsBumBQtPLEYMMA==";
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(reqspec)
				.body(KonnectInboundPayLoad.inbound_payload("Anil_inbound", "918884000010",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/"+strAccoutId+"/InboundPhoneNumbers").then().log().all().extract().response();

		System.out.println("Status code : " + responseObj.getStatusCode());
		System.out.println("Server name : " + responseObj.getHeader("Server"));

		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("success")),
				" Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
	}

	/*
	 * Send Invalid Auth key
	 */
	@Test(description = "Send Invalid Auth key", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_401_StatusCode() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq("U99W3HvGjUdDbnplGMepnkTbbFIqn6QsIwh1o");

		responseObj = given().spec(reqspec)
				.body(KonnectInboundPayLoad.inbound_payload("Anil_inbound", "+91 918884000010",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers").then().log()
				.all().extract().response();

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("Unauthorized")),
				" Failed..!! Expected 401 status code but found " + responseObj.getStatusCode());
	}

	/*
	 * Pre-request : Number should be attached to any campagin 
	 * id = "" [sending empty string] "error_code":"3005"
	 */
	@Test(description = "Send empty id ", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_emptyId_errorCode3005_() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(reqspec)
				.body(KonnectInboundPayLoad.inbound_payload("", "918884000010",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers");

		Response resp = responseObj.then().log().all().extract().response();
		System.out.println("----->" + resp.asString());

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		JsonPath js = APIController.getJsonString(responseObj.asString());

		String strErrCode = js.getString("error_code").toString();
		System.out.println("Error code : " + strErrCode);

		String strStatus = js.getString("status").toString();
		System.out.println("Status : " + strStatus);

		String strErrorReason = js.getString("error_reason").toString();
		System.out.println("Error reason : " + strErrorReason);

		// Status code
		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("success")),
				" Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		// Error Code
		Assert.assertEquals(APIController.convert_str_to_int(strErrCode),
				APIController.convert_str_to_int(APIController.readconfigproperties("Invalid_Sender_Number")),
				" Failed..!! Expected 3005 status code but found " + responseObj.getStatusCode());
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}// end of

	/*
	 * Pre-request : Number should be attached to campagin
	 * 
	 * phone_number = "" [sending empty Number] "error_code":"3005"
	 */
	@Test(description = "Send empty phoneNumber ", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_emptyPhNum_errorCode3005_() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(reqspec)
				.body(KonnectInboundPayLoad.inbound_payload("TestAPI", "", "https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers");

		Response resp = responseObj.then().log().all().extract().response();
		System.out.println("----->" + resp.asString());

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		JsonPath js = APIController.getJsonString(responseObj.asString());

		String strErrCode = js.getString("error_code").toString();
		System.out.println("Error code : " + strErrCode);

		String strStatus = js.getString("status").toString();
		System.out.println("Status : " + strStatus);

		String strErrorReason = js.getString("error_reason").toString();
		System.out.println("Error reason : " + strErrorReason);

		// Status code
		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("success")),
				" Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		// Error Code
		Assert.assertEquals(APIController.convert_str_to_int(strErrCode),
				APIController.convert_str_to_int(APIController.readconfigproperties("Invalid_Sender_Number")),
				" Failed..!! Expected 3005 status code but found " + responseObj.getStatusCode());
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}

	/*
	 * Pre-request : Number should be attached to campagin
	 * 
	 * 'id' removed from the request "error_code":"3010"
	 */
	@Test(description = "remove the id from request ", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_remove_ID_errorCode3010_() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(reqspec).body(KonnectInboundPayLoad.inbound_idFieldMissing_payload()).when()
				.contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers");

		Response resp = responseObj.then().log().all().extract().response();
		System.out.println("----->" + resp.asString());

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		JsonPath js = APIController.getJsonString(responseObj.asString());

		String strErrCode = js.getString("error_code").toString();
		System.out.println("Error code : " + strErrCode);

		String strStatus = js.getString("status").toString();
		System.out.println("Status : " + strStatus);

		String strErrorReason = js.getString("error_reason").toString();
		System.out.println("Error reason : " + strErrorReason);

		// Status code
		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("success")),
				" Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		// Error Code
		Assert.assertEquals(APIController.convert_str_to_int(strErrCode),
				APIController.convert_str_to_int(APIController.readconfigproperties("Required_Field_Missing")),
				" Failed..!! Expected 3010 status code. ");
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Field 'Required Field Missing <id>' is missing in the request.",
				"Failed..!! Expected error reason as 'Field 'Required Field Missing <id>' is missing in the request.' ");
	}

	/*
	 * Pre-request : Number should be attached to campagin
	 * 
	 * 'phone_number' removed from the request "error_code":"3010"
	 */
	@Test(description = " remove the phone number from request ", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_remove_PhNum_errorCode3010_() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(reqspec).body(KonnectInboundPayLoad.inbound_PnNumRemoved_req()).when()
				.contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers");

		Response resp = responseObj.then().log().all().extract().response();
		System.out.println("----->" + resp.asString());

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		JsonPath js = APIController.getJsonString(responseObj.asString());

		String strErrCode = js.getString("error_code").toString();
		System.out.println("Error code : " + strErrCode);

		String strStatus = js.getString("status").toString();
		System.out.println("Status : " + strStatus);

		String strErrorReason = js.getString("error_reason").toString();
		System.out.println("Error reason : " + strErrorReason);

		// Status code
		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("success")),
				" Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		// Error Code
		Assert.assertEquals(APIController.convert_str_to_int(strErrCode),
				APIController.convert_str_to_int(APIController.readconfigproperties("Required_Field_Missing")),
				" Failed..!! Expected 3010 status code. ");
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Field 'Required Field Missing <phone_number>' is missing in the request.",
				"Failed..!! Expected error reason as 'Field 'Required Field Missing <id>' is missing in the request.' ");
	}

	/*
	 * Passing Only special Char in 'PhNum'
	 */
	@Test(description = "Passing Only special Char in 'PhNum'", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_spclCharInPhNum() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(reqspec)
				.body(KonnectInboundPayLoad.inbound_payload("Anil_inbound", "!@#)(*&^",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers").then().log()
				.all().extract().response();
		;

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		JsonPath js = APIController.getJsonString(responseObj.asString());

		String strErrCode = js.getString("error_code").toString();
		System.out.println("Error code : " + strErrCode);

		String strStatus = js.getString("status").toString();
		System.out.println("Status : " + strStatus);

		String strErrorReason = js.getString("error_reason").toString();
		System.out.println("Error reason : " + strErrorReason);

		// Status code
		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("success")),
				" Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		// Error Code
		Assert.assertEquals(APIController.convert_str_to_int(strErrCode),
				APIController.convert_str_to_int(APIController.readconfigproperties("Invalid_Sender_Number")),
				" Failed..!! Expected 3005 status code but found " + responseObj.getStatusCode());
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}

	/*
	 * Passing Number and special Char in 'PhNum'
	 */
	@Test(description = "Passing Number and special Char in 'PhNum'", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_spclCharWtihPhNum() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(reqspec)
				.body(KonnectInboundPayLoad.inbound_payload("Anil_inbound", "!@918884000010",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers").then().log()
				.all().extract().response();

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		JsonPath js = APIController.getJsonString(responseObj.asString());

		String strErrCode = js.getString("error_code").toString();
		System.out.println("Error code : " + strErrCode);

		String strStatus = js.getString("status").toString();
		System.out.println("Status : " + strStatus);

		String strErrorReason = js.getString("error_reason").toString();
		System.out.println("Error reason : " + strErrorReason);

		// Status code
		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("success")),
				" Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		// Error Code
		Assert.assertEquals(APIController.convert_str_to_int(strErrCode),
				APIController.convert_str_to_int(APIController.readconfigproperties("Invalid_Sender_Number")),
				" Failed..!! Expected 3005 status code but found " + responseObj.getStatusCode());
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}// end of 'inbound_spclCharWtihPhNum'
	
	
	/*
	 * Passing diffrent Number formate in 'PhNum' field
	 */
	@Test(description = "Passing diffrent Number formate in 'PhNum' field", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_diffPhNumformate() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(reqspec)
				.body(KonnectInboundPayLoad.inbound_payload("Anil_inbound", "+91 8884000010",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers").then().log()
				.all().extract().response();

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		JsonPath js = APIController.getJsonString(responseObj.asString());

		String strErrCode = js.getString("error_code").toString();
		System.out.println("Error code : " + strErrCode);

		String strStatus = js.getString("status").toString();
		System.out.println("Status : " + strStatus);

		String strErrorReason = js.getString("error_reason").toString();
		System.out.println("Error reason : " + strErrorReason);

		// Status code
		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("success")),
				" Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
		// Error Code
		Assert.assertEquals(APIController.convert_str_to_int(strErrCode),
				APIController.convert_str_to_int(APIController.readconfigproperties("Invalid_Sender_Number")),
				" Failed..!! Expected 3005 status code but found " + responseObj.getStatusCode());
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}// end of 'inbound_spclCharWtihPhNum'	
	
	
	
	/*
	 * Passing invalid AccoundID value
	 */
	@Test(description = "Passing invalid AccoundID value", groups = {"APIKonnectResponseErrorCode" }, priority = 2)
	public void inbound_invalidAccountId() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(reqspec)
				.body(KonnectInboundPayLoad.inbound_payload("Anil_inbound", "+91 8884000010",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().contentType(ContentType.JSON).post("/EnxFZU4K+nFsdp0Cy/InboundPhoneNumbers").then().log()
				.all().extract().response();

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("Unauthorized"))," Failed..!! Expected 401 status code but found " + responseObj.getStatusCode());		
	}// end of 'inbound_spclCharWtihPhNum'	
	
	
	// End to End Scenrio
	/*
	 * Attaching the Number to campagin and Detaching
	 * Detached number trying to detatch
	 * 
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode" }, priority = 1)
	public void inbound_CreateAndDelete() {
		Response responseObj = null;
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);
		String str_del_PhNum = "918884000014";

		responseObj = given().spec(reqspec)
				.body(KonnectInboundPayLoad.inbound_payload("Anil_inbound", "918884000014","https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers").then().log()
				.all().extract().response();

		System.out.println("Response Status code : " + responseObj.getStatusCode());
		Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());		
		
		JsonPath js = APIController.getJsonString(responseObj.asString());

		
		String strstatus = js.getString("status").toString();
		System.out.println("Attaching to campagin, Status : " + strstatus);	
		Assert.assertEquals(strstatus, "ok","Failed..!! expected status as OK");
		
		// Delete Request 
		System.out.println("-----------------");
		Response delrespObj = given().spec(reqspec).when().delete("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers/918884000014").then().log().all().extract().response();
	
		System.out.println("Response Status code : " + delrespObj.getStatusCode());
		JsonPath js1 = APIController.getJsonString(responseObj.asString());
		String str_delstatus = js1.getString("status").toString();
		System.out.println("Detached, Status : " + str_delstatus);	
		Assert.assertEquals(str_delstatus, "ok","Failed..!! expected status as OK");
		
		/*
		 *  Detached number trying to detach once again
		 *  
		 *  Error code = 3017
		 */
		System.out.println("-----------------");
		Response delResp_Obj1 = given().spec(reqspec).when().delete("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers/918884000014").then().log().all().extract().response();
		
		System.out.println("Response Status code : " + delResp_Obj1.getStatusCode());
		Assert.assertEquals(delResp_Obj1.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());		
		
		String strStatus1=APIController.getStringValue(APIController.getJsonString(delResp_Obj1.asString()), "status");
		System.out.println("Status : " + strStatus1);	
		Assert.assertEquals(strStatus1, "error","Failed..!! expected status 'error'");
		
		String str_errReason=APIController.getStringValue(APIController.getJsonString(delResp_Obj1.asString()), "error_reason");
		System.out.println("error reason : " + str_errReason);
		Assert.assertEquals(str_errReason, "Number is already detached","Failed..!! expected error reson as 'Number is already detached'");
		
		String str_errCode=APIController.getStringValue(APIController.getJsonString(delResp_Obj1.asString()), "error_code");
		System.out.println("-----> error Code : " + str_errCode);
		Assert.assertEquals(APIController.convert_str_to_int(str_errCode), APIController.convert_str_to_int(APIController.readconfigproperties("Invalid_Number")),"Failed..!! expected error code 3017");
		
		// attaching the Number to campagin
		given().spec(reqspec)
		.body(KonnectInboundPayLoad.inbound_payload("Anil_inbound", "918884000014","https://konnectqa1.kirusa.com/admin/callback_url"))
		.when().contentType(ContentType.JSON).post("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers").then().log()
		.all().extract().response();
		
	}// end of 'inbound_spclCharWtihPhNum'	

	
	/*****************DELETE API***************************/
	
	/*
	 *  Empty number sent in request
	 *  Response Status code : 405
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode" }, priority = 1)
	public void inbound_DeleteAPI_emptyRequest() {
		
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);
		//String delPhNumber = null;
		
		Response delrespObj = given().spec(reqspec).when()
				.delete("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers/").then().log().all().extract()
				.response();
		
		/*Response delrespObj = given().spec(reqspec).when()
				.delete("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers/+"+delPhNumber+"+").then().log().all().extract()
				.response();*/
		
		System.out.println("Response Status code : " + delrespObj.getStatusCode());
		Assert.assertEquals(delrespObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("method_not_allowed"))," Failed..!! Expected 405 status code but found " + delrespObj.getStatusCode());
	}
	
	/*
	 *  Invalid number passed in request
	 *  Response Status code : 3017
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode" }, priority = 1)
	public void inbound_DeleteAPI_invalidPhNum() {
		
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);
		String delPhNumber = "91888400";
		
		Response delrespObj = given().spec(reqspec).when()
				.delete("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNumbers/+"+delPhNumber+"+").then().log().all().extract()
				.response();
		
		System.out.println("Response Status code : " + delrespObj.getStatusCode());
		Assert.assertEquals(delrespObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + delrespObj.getStatusCode());
		
		String str_errReason=APIController.getStringValue(APIController.getJsonString(delrespObj.asString()), "error_reason");
		System.out.println("-----> error reason : " + str_errReason);
		Assert.assertEquals(str_errReason, "Invalid Number","Failed..!! expected error reason 'Invalid Number'");	
		
		String strStatus=APIController.getStringValue(APIController.getJsonString(delrespObj.asString()), "status");
		System.out.println("Status : " + strStatus);	
		Assert.assertEquals(strStatus, "error","Failed..!! expected status 'error'");
		
		String str_errCode=APIController.getStringValue(APIController.getJsonString(delrespObj.asString()), "error_code");
		System.out.println("Error code : " + str_errCode);
		Assert.assertEquals(APIController.convert_str_to_int(str_errCode), APIController.convert_str_to_int(APIController.readconfigproperties("Invalid_Number")),"Failed..!! expected error code 3017");
	}
	
	/*
	 *  Invalid EndPoint
	 *  Response Status code : 405
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode" }, priority = 1)
	public void inbound_DeleteAPI_invalidEndPoint() {
		
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);
		
		String delPhNumber = "918884000014";
		
		
		Response delrespObj = given().spec(reqspec).when()
				.delete("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNum/+"+delPhNumber+"+").then().log().all().extract()
				.response();
		
		System.out.println("Response Status code : " + delrespObj.getStatusCode());
		Assert.assertEquals(delrespObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("method_not_allowed"))," Failed..!! Expected 405 status code but found " + delrespObj.getStatusCode());
	}
	
	/*
	 *  Passing Alphabets 
	 *  Response Status code : 405
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode" }, priority = 1)
	public void inbound_DeleteAPI_alphabets() {
		
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);
		
		String delPhNumber = "qwert";
		
		Response delrespObj = given().spec(reqspec).when()
				.delete("/kan+LsmmsBumBQtPLEYMMA==/InboundPhoneNum/+"+delPhNumber+"+").then().log().all().extract()
				.response();
		
		System.out.println("Response Status code : " + delrespObj.getStatusCode());
		Assert.assertEquals(delrespObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("method_not_allowed"))," Failed..!! Expected 405 status code but found " + delrespObj.getStatusCode());
	}
	
	/*
	 * 401 Unauthorized
	 *  Response Status code : 401
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode" }, priority = 1)
	public void inbound_DeleteAPI_Unauthorized() {
		
		RequestSpecification reqspec = APIController.buildReq(str_apiToken_AUTHval);
		
		String delPhNumber = "918884000014";
		
		Response delrespObj = given().spec(reqspec).when()
				.delete("/kan+LsmmsBumBQtPL/InboundPhoneNumbers/+"+delPhNumber+"+").then().log().all().extract()
				.response();
		
		System.out.println("Response Status code : " + delrespObj.getStatusCode());
		Assert.assertEquals(delrespObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("Unauthorized"))," Failed..!! Expected 401 status code but found " + delrespObj.getStatusCode());
	}
	
	
}// end of class
