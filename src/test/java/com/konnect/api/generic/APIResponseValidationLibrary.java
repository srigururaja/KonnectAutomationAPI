package com.konnect.api.generic;

import org.testng.Assert;
import org.testng.Reporter;

import io.restassured.response.Response;
import testExecution.APIController;

public class APIResponseValidationLibrary {

	
	// Response Status Code
	 public static void responseStatusCodeValidation(Response responseObj, String strResponse) {
		Assert.assertEquals(responseObj.getStatusCode(),APIController.convert_str_to_int(APIController.readconfigproperties(strResponse))," Failed..!! Response status code not matching ");
		Reporter.log("Response status code : "+responseObj.statusCode(),true);
	 }
	 
	// Validating String Filed inside the response
	public static void generic_responseFieldValidation(Response responseObj, String fieldName,String fieldValueExpected,String strType, String strErrorDescription) {
		
		String str_Actual = APIController.getStringValue(APIController.getJsonString(responseObj.asString()),fieldName);

		if (strType.equalsIgnoreCase("String")) {
			Assert.assertEquals(str_Actual, fieldValueExpected, "Failed..!! " + fieldName + " not matching");
			Reporter.log(fieldName + " : "+ str_Actual, true );
		} else if (strType.equalsIgnoreCase("number")) {
			Assert.assertEquals(APIController.convert_str_to_int(str_Actual),APIController.convert_str_to_int(APIController.readconfigproperties(fieldValueExpected)),"Failed..!! expected error code not matching");
			//System.out.println("Error code --> " + str_Actual + " : description ==> " + strErrorDescription);
			Reporter.log("Error code --> " + str_Actual + " : description ==> " + strErrorDescription, true );
		}
		//System.out.println(fieldName + " : " + str_Actual);
		//Reporter.log(fieldName + " : " + str_Actual,true);
	}
	 
	 
	 /*
	  *  Content-Encoding : gzip
	  *  
	  */
	public static void responseHeaderValidation_ContentEncoding(Response responseObj) {
		//Content-Encoding 
		Assert.assertEquals(responseObj.getHeader("Content-Encoding").trim(), "gzip","Failed..!! Content-Encoding value not matching");
		Reporter.log("Content-Encoding : "+responseObj.getHeader("Content-Encoding"),true);
	}
	 
	/*
	 */
	public static String requiredFieldMissing(String strMissingFieldName){
		String strMissing = "Field 'Required Field Missing <"+strMissingFieldName+">' is missing in the request.";
		return strMissing;
	}
	
}
