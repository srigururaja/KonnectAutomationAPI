package testExecution;

public class KonnectInboundPayLoad {

	public static String inbound()
	{
		return "{\r\n" + 
				"  \"id\": \"Anil_inbound\",\r\n" + 
				"  \"phone_number\": \"918884000010\",\r\n" + 
				"  \"callback_url\": \"https://konnectqa1.kirusa.com/admin/callback_url\"\r\n" + 
				"}";
	}
	
	public static String inbound_PnNumRemoved_req()
	{
		return "{\r\n" + 
				"  \"id\": \"Anil_inbound\",\r\n" + 
				"  \r\n" + 
				"  \"callback_url\": \"https://konnectqa1.kirusa.com/admin/callback_url\"\r\n" + 
				"}";
	}
	
	public static String inbound_idFieldMissing_payload()
	{
		return "{\r\n" + 
				"  \"phone_number\": \"918884000010\",\r\n" + 
				"  \"callback_url\": \"https://konnectqa1.kirusa.com/admin/callback_url\"\r\n" + 
				"}";
	}
	
	
	public static String inbound_payload(String strid,String phNum, String callbackUrl)
	{
		return "{\r\n" + 
				"  \"id\": \""+strid+"\",\r\n" + 
				"  \"phone_number\": \""+phNum+"\",\r\n" + 
				"  \"callback_url\": \""+callbackUrl+"\"\r\n" + 
				"}";
	}
}
