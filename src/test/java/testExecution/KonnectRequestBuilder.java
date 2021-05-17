package testExecution;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;



public class KonnectRequestBuilder {
	
	public static String qa1_baseURI  = KonnectAPIConstant.Konnect_baseURI;
	public static String automationsbaseURI  = KonnectAPIConstant.Konnect_automation_baseURI;
	public static final String otpByobdBaseURI="https://konnectautomation.kirusa.com/";
	
	public final static String baseURI  = automationsbaseURI;
	
	/*
	 * @Author : Anantha
	 */
	public static RequestSpecification buildReq(String str_Authvalue) {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri(KonnectAPIConstant.Konnect_baseURI+"/api/v1/Accounts").addHeader("Content-Type","application/json").addHeader("Authorization",str_Authvalue).build();
		return req;
		
	}
	
	/*
	 * Request for API --> OTPByOBD
	 * 
	 * @Author : Anantha
	 */
	public static RequestSpecification buildReqOTPByOBD(String str_AccountID) {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri(otpByobdBaseURI).addHeader("Content-Type","application/json").addHeader("Authorization",str_AccountID).build();
		return req;
		
	}
	
	/*
	 * Request for API --> OBD [low and High]
	 * 
	 * @Author : Anantha
	 */
	public static RequestSpecification buildReqOBDCalls(String str_Authvalue) {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseURI+"/api/v1/Accounts/").addHeader("Content-Type","application/json").addHeader("Authorization",str_Authvalue).build();
		return req;
		
	}
	
	/*
	 * @Author : Guru
	 */
	public static RequestSpecification buildReqOutBoundSMS(String str_Authvalue) {
		/*RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://konnectqa1.kirusa.com/api/v1/Accounts/")
				.addHeader("Content-Type", "application/json").addHeader("Authorization", str_Authvalue).build();*/
		
		// Latest
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://konnectautomation.kirusa.com:8080/api/v1/Accounts/")
                .addHeader("Content-Type", "application/json").addHeader("Authorization", str_Authvalue).build();
		return req;

	}
	
	/*
	 * @Author : Guru
	 */
    public static RequestSpecification buildReqOutBoundSMSError(String str_Authvalue) {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://92.168.231.44:8080/api/v1/Accounts/").addHeader("Content-Type","application/json").addHeader("Authorization",str_Authvalue).build();
        return req;
       
    }
	
	
    /*
	 * Request for API --> Inbound Delete
	 * 
	 * @Author : Anantha
	 */
	public static RequestSpecification buildReqInboundDelete(String str_Authvalue) {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri(automationsbaseURI+"/api/v1/Accounts").addHeader("Content-Type","application/json").addHeader("Authorization",str_Authvalue).build();
		return req;
		
	}
	
	
	
}
