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

public class DynamicOBDAPI  {

	// StatusCode verification Use Cases
		public int ActualResp;
		public int ExpectedResp;
		ArrayList<String> recpList = new ArrayList<String>();
		
		/*
		 * Passing valid Request
		 */
		@Test(description = "Dynamic OBD Success response code..", groups = {"APIKonnectResponseSuccessCode","DynamicOBD" }, priority = 1)
		public void OutBoundSMSValidData() {
			recpList.add("919886001212");
			recpList.add("919886001211");
			// System.out.println(APIController.sconfig);
			
			RequestSpecification requestSpec = KonnectRequestBuilder.buildReqOutBoundSMS(KonnectAPIConstant.str_API_TOKEN_AUTH_Anil);
			Response resp = given().spec(requestSpec)
					.body(KonnectPayLoad.DynamicOBDLowPriBasic("DynamicOBD", "+919221000030", recpList, "http://192.168.230.55:8080/kvsms/test.jsp", "outbound", KonnectAPIConstant.str_priority_low))
					.when().contentType(ContentType.JSON)
					.post(KonnectAPIConstant.str_ACCOUNT_Id_Anil+"/Calls")
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
		

}
