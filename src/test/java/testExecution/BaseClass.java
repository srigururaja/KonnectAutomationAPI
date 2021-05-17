package testExecution;

import java.util.ArrayList;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {
	ArrayList<String> recpList = new ArrayList<String>();
	
	protected RequestSpecification requestSpec;
	
	protected Response responseObj;
	
	
	
}
