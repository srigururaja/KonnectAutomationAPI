package testExecution;

public class APIErrorCode_Description {
	
	public final static String httpStatusCode_200 = "Request is successful"; 
	public final static String httpStatusCode_201 = "Resource created";
	public final static String httpStatusCode_204 = "Resource deleted";
	
	public final static String httpStatusCode_400 = "Validation failed"; 
	public final static String httpStatusCode_401 = "Credentials not valid";
	public final static String httpStatusCode_404 = "Resource not found";
	public final static String httpStatusCode_405 = "Method not allowed";
	
	
	public final static String errCode_3001_Messageoverflow = "The message rate exceeded, the message needs to be sent again with some gap.";
	public final static String errCode_3002_AccountSuspended = "The account is suspended, please contact Konnect admin.";
	public final static String errCode_3003_LowBalance = "The account does not have sufficient funds to send a Message/Call.";
	public final static String errCode_3004_InvalidMessageLength = "The Message length is invalid - it is either 0 or exceeds the maximum limit for the text message.";
	public final static String errCode_3005_InvalidSenderNumber = "The provided sender phone number is invalid.";
	
	public final static String errCode_3006_InvalidSenderMask = "The provided sender mask is invalid";
	public final static String errCode_3007_InvalidCallerId = "Invalid Caller Id";
	public final static String errCode_3008_InvalidIVRNumber ="The provided IVR number is invalid.";
	public final static String errCode_3009_InvalidTransactionId = "Either the transaction id is invalid or duplicate";
	public final static String errCode_3010_RequiredFieldMissing = "Required Field Missing.";
	
	
	public final static String errCode_3011_ProvidedRecipentListEmpty="The provided Recipient List is Empty";
	public final static String errCode_3014_InvalidRecipentList="The provided Recipient List is Invalid";
	public final static String errCode_3017_InvalidNumber = "Number is not owned by the user or is invalid";
	public final static String errCode_invalid_Json = "Missing value at 11 \"[character 5 line 2]\"";
	 
}
