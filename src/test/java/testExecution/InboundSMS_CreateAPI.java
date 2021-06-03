package testExecution;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import databaseConfig.DataBaseCommonQuery;
import io.restassured.specification.RequestSpecification;

/*
 * @Author : Anantha
 *  
 * 'Account ID' value of the user is passed along with the URL
 * 'API token' of the user Passed in 'Authorization' section [header]
 * 
 * Request URL  : http://konnectautomation.kirusa.com:8080/api/v1/Accounts/TMYw2tCcWK_kn7sw4x64DQ==/InboundPhoneNumbers
 * Method Type  : POST
 * Body :
    {
	  "id": "Test",
	  "phone_number": "918883000001",
	  "callback_url": "https://konnectqa1.kirusa.com/admin/callback_url"
	}
 * 
 * Note : Once number is attached delete it by delete Request
 * 
 */

public class InboundSMS_CreateAPI extends BaseClass{
	public int actualResp;
	public int expectedResp;
	public final static String str_apiToken_AUTHval = "Oeh2h7xTXJoNws8F3kQqV4PnbWgH0mLn4v6DqZz861g=";
	

	@BeforeClass
	public void preCondition() {

		/*requestSpec = KonnectRequestBuilder.buildReqInboundDelete(str_apiToken_AUTHval);
		
		responseObj = given().spec(requestSpec).when()
				.delete("/TMYw2tCcWK_kn7sw4x64DQ==/InboundPhoneNumbers/918883000001")
				.then().log().all().extract().response();*/
		if (DataBaseCommonQuery.getChannelPhone_status_activity(KonnectAPIConstant.str_KA_Account3_InboundSMS).get(0).equals("i")) {
			System.out.println("Number is inactive");
		} else {
			System.out.println("Update the DB with status and Activity");
			DataBaseCommonQuery.update_Channel_Phone_status_activity("i", "detached", KonnectAPIConstant.str_KA_Account3_InboundSMS);
		}

		
		
		//System.out.println("---- Before Class ---->" + responseObj.statusCode());

	}
	
	/*
	 * Comment reason : End to End Scenrio 200 status is verified
	 */
	//@Test(description = "Error response code from the Konnect Server..", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 2)
	public void inbound_200StatusCode() {
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		Response responseObj = null;
		requestSpec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.inbound_payload("Anil_inbound",KonnectAPIConstant.str_KA_Account3_InboundSMS,"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/"+strAccoutId+"/InboundPhoneNumbers").then().log().all().extract().response();

		System.out.println("Status code : " + responseObj.getStatusCode());
		System.out.println("Server name : " + responseObj.getHeader("Server"));

		Assert.assertEquals(responseObj.getStatusCode(),
				APIController.convert_str_to_int(APIController.readconfigproperties("success")),
				" Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());
	}

	/*
	 * Passing Invalid authorization key in header
	 */
	@Test(description = "Passing Invalid authorization key", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 2)
	public void inbound_invalidAuthorization_401_StatusCode() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
		requestSpec = APIController.buildReq("U99W3HvGjUdDbnplGMepnkTbbFIqn6QsIwh1o");

		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.inbound_payload("Anil_inbound",KonnectAPIConstant.str_KA_Account3_InboundSMS,"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/"+strAccoutId+"/InboundPhoneNumbers").then().log()
				.all().extract().response();

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("Unauthorized"))," Failed..!! Expected 401 status code but found " + responseObj.getStatusCode());
		System.out.println("Status code --> " + responseObj.getStatusCode() + " : description ==> "+APIErrorCode_Description.httpStatusCode_401);
	}

	/*
	 * **Pre-request : Number should be attached to any campagin 
	 * id = "" [sending empty string] "error_code":"3005"
	 * "id": ""
	 */
	//@Test(description = "Send empty id ", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 2)
	public void inbound_emptyId_errorCode3005_() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
	   requestSpec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(requestSpec)
		.body(KonnectPayLoad.inbound_payload("",KonnectAPIConstant.str_KA_Account3_InboundSMS,"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/"+strAccoutId+"/InboundPhoneNumbers");

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
		System.out.println("Error code : " + strErrCode + " : description ==> "+APIErrorCode_Description.errCode_3005_InvalidSenderNumber );
		
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}// end of

	/*
	 * **Pre-request : Number should be attached to campagin
	 * 
	 * phone_number = "" [sending empty Number] "error_code":"3005"
	 * "phone_number": "",
	 */
	@Test(description = "Pass empty phoneNumber ", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 2)
	public void inbound_emptyPhNum_errorCode3005_() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
		requestSpec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.inbound_payload("TestAPI", "", "https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/"+strAccoutId+"/InboundPhoneNumbers");

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
		System.out.println("Error code : " + strErrCode + " : description ==> "+APIErrorCode_Description.errCode_3005_InvalidSenderNumber );
		
		// Status
		Assert.assertEquals(strStatus, "error", "Failed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}

	/*
	 * Pre-request : Number should be attached to campagin
	 * 
	 * 'id' removed from the request "error_code":"3010"
	 */
	@Test(description = "remove the id from request ", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 2)
	public void inbound_remove_ID_errorCode3010_() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
		requestSpec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(requestSpec).body(KonnectPayLoad.inbound_idFieldMissing_payload()).when()
				.post("/"+strAccoutId+"/InboundPhoneNumbers");

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
		System.out.println("Error code : " + strErrCode + " : description ==> "+APIErrorCode_Description.errCode_3010_RequiredFieldMissing );
		
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
	@Test(description = " remove the phone number from request ", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 2)
	public void inbound_remove_PhNum_errorCode3010_() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
		requestSpec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(requestSpec).body(KonnectPayLoad.inbound_PnNumRemoved_req()).when()
				.post("/"+strAccoutId+"/InboundPhoneNumbers");

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
		
		System.out.println("Error code : " + strErrCode + " : description ==> "+APIErrorCode_Description.errCode_3010_RequiredFieldMissing );
		
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Field 'Required Field Missing <phone_number>' is missing in the request.",
				"Failed..!! Expected error reason as 'Field 'Required Field Missing <id>' is missing in the request.' ");
	}

	/*
	 * Passing Only special Char in 'PhNum'
	 */
	@Test(description = "Passing Only special Char in 'PhNum'", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 2)
	public void inbound_spclCharInPhNum() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
		requestSpec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.inbound_payload("Anil_inbound", "!@#)(*&^",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/"+strAccoutId+"/InboundPhoneNumbers").then().log()
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
		System.out.println("Error code : " + strErrCode + " : description ==> "+APIErrorCode_Description.errCode_3005_InvalidSenderNumber );
		
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}

	/*
	 * Passing Number and special Char in 'PhNum'
	 */
	@Test(description = "Passing Number and special Char in 'PhNum'", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 2)
	public void inbound_spclCharWtihPhNum() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
		requestSpec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.inbound_payload("Anil_inbound", "!@918884000010",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/"+strAccoutId+"/InboundPhoneNumbers").then().log()
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
		System.out.println("Error code : " + strErrCode + " : description ==> "+APIErrorCode_Description.errCode_3005_InvalidSenderNumber );
		
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}// end of 'inbound_spclCharWtihPhNum'
	
	
	/*
	 * Passing diffrent Number formate in 'PhNum' field
	 */
	@Test(description = "Passing diffrent Number formate in 'PhNum' field", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 2)
	public void inbound_diffPhNumformate() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
		requestSpec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.inbound_payload("Anil_inbound", "+91 8884000010",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/"+strAccoutId+"/InboundPhoneNumbers").then().log()
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
		System.out.println("Error code : " + strErrCode + " : description ==> "+APIErrorCode_Description.errCode_3005_InvalidSenderNumber );
		
		// Status
		Assert.assertEquals(strStatus, "error", "ailed..!! Expected status as 'error' ");
		// Error Reason
		Assert.assertEquals(strErrorReason, "Invalid SMS Number",
				"ailed..!! Expected error reason as 'Invalid SMS Number' ");
	}// end of 'inbound_diffPhNumformate'	
	
	
	
	/*
	 * Passing invalid AccoundID value
	 * Status code : 401
	 */
	@Test(description = "Passing invalid AccoundID value", groups = {"APIKonnectResponseErrorCode","InboundSMS"}, priority = 2)
	public void inbound_invalidAccountId() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
		requestSpec = APIController.buildReq(str_apiToken_AUTHval);

		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.inbound_payload("Anil_inbound", "+91 8884000010",
						"https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/EnxFZU4K+nFsdp0Cy/InboundPhoneNumbers").then().log()
				.all().extract().response();

		System.out.println("Response Status code : " + responseObj.getStatusCode());

		Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("Unauthorized"))," Failed..!! Expected 401 status code but found " + responseObj.getStatusCode());
		System.out.println("Status code --> " + responseObj.getStatusCode() + " : description ==> "+APIErrorCode_Description.httpStatusCode_401);
	
	}// end of 'inbound_spclCharWtihPhNum'	
	
	
	// End to End Scenrio
	/*
	 * Attaching the Number to campagin and Detaching
	 * Detached number trying to detatch
	 * 
	 */
	@Test(description = "End to End Scenrio Attaching and detaching Inbound number", groups = {"APIKonnectResponseErrorCode","InboundSMS" }, priority = 1)
	public void inbound_CreateAndDelete() {
		Response responseObj = null;
		String strAccoutId = "TMYw2tCcWK_kn7sw4x64DQ==";
		
		requestSpec = APIController.buildReq(str_apiToken_AUTHval);
		//String str_del_PhNum = "918884000014";

		responseObj = given().spec(requestSpec)
				.body(KonnectPayLoad.inbound_payload("Anil_inbound", "918883000001","https://konnectqa1.kirusa.com/admin/callback_url"))
				.when().post("/"+strAccoutId+"/InboundPhoneNumbers").then().log()
				.all().extract().response();

		System.out.println("Response Status code : " + responseObj.getStatusCode());
		Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties("success"))," Failed..!! Expected 200 status code but found " + responseObj.getStatusCode());		
		
		JsonPath js = APIController.getJsonString(responseObj.asString());

		
		String strstatus = js.getString("status").toString();
		System.out.println("Attaching to campagin, Status : " + strstatus);	
		Assert.assertEquals(strstatus, "ok","Failed..!! expected status as OK");
		
		// Delete Request 
		System.out.println("-----------------");
		Response delrespObj = given().spec(requestSpec).when().delete("/TMYw2tCcWK_kn7sw4x64DQ==/InboundPhoneNumbers/918883000001").then().log().all().extract().response();
	
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
		Response delResp_Obj1 = given().spec(requestSpec).when().delete("/TMYw2tCcWK_kn7sw4x64DQ==/InboundPhoneNumbers/918883000001").then().log().all().extract().response();
		
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
		System.out.println("Status code --> " + responseObj.getStatusCode() + " : description ==> "+APIErrorCode_Description.errCode_3017_InvalidNumber);
		
		
		// attaching the Number to campagin
		given().spec(requestSpec)
		.body(KonnectPayLoad.inbound_payload("Anil_inbound", "918883000001","https://konnectqa1.kirusa.com/admin/callback_url"))
		.when().post("/"+strAccoutId+"/InboundPhoneNumbers").then().log()
		.all().extract().response();
		
	}// end of 'inbound_CreateAndDelete'	

	
	
	
	
	
	
}// end of class
