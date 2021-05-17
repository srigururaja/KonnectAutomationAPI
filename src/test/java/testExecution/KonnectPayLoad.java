package testExecution;

import java.util.ArrayList;
import java.util.HashMap;

import io.restassured.path.json.JsonPath;

public class KonnectPayLoad {

	/***************** INBOUND Request **********************/
	
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
				"  \"phone_number\": \"918883000001\",\r\n" + 
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
	
	/***************** OTPByOBD Request **********************/
	public static String otpByObd_payload(String strId,String strCmd, String strApiToken,String str_ToPhNum,String strCallerId)
	{
		return "{\r\n" + 
				"    \"id\": \""+strId+"\",    \r\n" + 
				"    \"cmd\": \""+strCmd+"\",\r\n" + 
				"    \"api_token\":\""+strApiToken+"\",\r\n" + 
				"    \"to\":\""+str_ToPhNum+"\",\r\n" + 
				"    \"caller_id\":\""+strCallerId+"\",\r\n" + 
				"    \"otp\": \"7878\"   \r\n" + 
				"}";
	}
	
	public static String otpByObd_noOTP_payload(String strId,String strCmd, String strApiToken,String str_ToPhNum,String strCallerId)
	{
		return "{\r\n" + 
				"    \"id\": \""+strId+"\",    \r\n" + 
				"    \"cmd\": \""+strCmd+"\",\r\n" + 
				"    \"api_token\":\""+strApiToken+"\",\r\n" + 
				"    \"to\":\""+str_ToPhNum+"\",\r\n" + 
				"    \"caller_id\":\""+strCallerId+"\",\r\n" + 
				"}";
	}
	
	public static String otpByObd_noCMDfield_payload(String strId,String strApiToken,String str_ToPhNum,String strCallerId)
	{
		return "{\r\n" + 
				"    \"id\": \""+strId+"\",    \r\n" + 
				"    \"api_token\":\""+strApiToken+"\",\r\n" + 
				"    \"to\":\""+str_ToPhNum+"\",\r\n" + 
				"    \"caller_id\":\""+strCallerId+"\",\r\n" + 
				"    \"otp\": \"7878\"   \r\n" + 
				"}";
	}
	
	public static String otpByObd_noAPIToken_payload(String strId,String strCmd,String str_ToPhNum,String strCallerId)
	{
		return "{\r\n" + 
				"    \"id\": \""+strId+"\",    \r\n" + 
				"    \"cmd\": \""+strCmd+"\",\r\n" + 
				"    \"to\":\""+str_ToPhNum+"\",\r\n" + 
				"    \"caller_id\":\""+strCallerId+"\",\r\n" + 
				"    \"otp\": \"7878\"   \r\n" + 
				"}";
	}
	
	
	public static String otpByObd_noToNumber_payload(String strId,String strCmd, String strApiToken,String strCallerId)
	{
		return "{\r\n" + 
				"    \"id\": \""+strId+"\",    \r\n" + 
				"    \"cmd\": \""+strCmd+"\",\r\n" + 
				"    \"api_token\":\""+strApiToken+"\",\r\n"  + 
				"    \"caller_id\":\""+strCallerId+"\",\r\n" + 
				"    \"otp\": \"7878\"   \r\n" + 
				"}";
	}
	
	public static HashMap<String, Object> otpByObdHashMap(String strId,String strCmd, String strApiToken,String str_ToPhNum,String strCallerId,String strotp)
	{
		HashMap<String, Object> map = new HashMap<>();
		
		if ((null != strId)) {
			map.put("id", strId);
		}
		if ((null != strCmd)) {
			map.put("cmd", strCmd);
		}
		if ((null != strApiToken)) {
			map.put("api_token", strApiToken);
		}
		if ((null != str_ToPhNum)) {
			map.put("to", str_ToPhNum);
		}

		if ((null != strCallerId)) {
			map.put("caller_id", strCallerId);
		}
		if ((null != strotp)) {
			map.put("otp", strotp);
		}

		return map;
	}
	
	/***************** OBD with Low Priority **********************/
	
	
	
	public static HashMap<String, Object> OBDLowPriority_payLoad(String strId,String strCallerId, ArrayList<String> strRecipient,String strMediaUrl, String strCallbackUrl,String strDirection, String strPriority)
	{
		
		HashMap<String, Object> map = new HashMap<>();	
		
		if ((null != strId)) {
			map.put("id", strId);
		}
		
		if ((null != strCallerId)) {
			map.put("caller_id", strCallerId);
		}
		
		if ((null != strRecipient)) {
			map.put("recipient", strRecipient);
		}
		
		if ((null != strMediaUrl)) {
			map.put("media_url", strMediaUrl);
		}
		
		if ((null != strCallbackUrl)) {
			map.put("callback_url", strCallbackUrl);
		}
		
		if ((null != strDirection)) {
			map.put("direction", strDirection);
		}
		
		if ((null != strPriority)) {
			map.put("priority", strPriority);
		}
		
		
		return map;
	}
	
	/*
	 * **************** OBD with High Priority *********************
	 */
	
	public static HashMap<String, Object> OBDHighPriority_payLoad(String strId, String strCallerId, ArrayList<String> strRecipient, String strMediaUrl, String strCallbackUrl, String strDirection,String strPriority) 
	{
		HashMap<String, Object> map = new HashMap<>();
		if ((null != strId)) {
			map.put("id", strId);
		}
		if ((null != strCallerId)) {
			map.put("caller_id", strCallerId);
		}
		if ((null != strRecipient)) {
			map.put("recipient", strRecipient);
		}
		if ((null != strMediaUrl)) {
			map.put("media_url", strMediaUrl);
		}
		
		if ((null != strCallbackUrl)) {
			map.put("callback_url", strCallbackUrl);
		}
		if ((null != strDirection)) {
			map.put("direction", strDirection);
		}
		
		if ((null != strPriority)) {
			map.put("priority", strPriority);
		}
		return map;
	}// end of OBDHighPriority_payLoad
	
	/*
	 * 2 Way OBD API Request
	 * @Author : Anantha
	 */
	public static HashMap<String, Object> TwoWay_OBDRequest(String strId, String strCallerId,ArrayList<String> strRecipient, String strMediaUrl, String strCallbackUrl, String strDirection,
			String strPriority, String strIVRNumber) {
		HashMap<String, Object> map = commonRequestParameter(strId, strCallerId, strRecipient, strMediaUrl, strCallbackUrl, strDirection, strPriority);
			if ((null != strIVRNumber)) {
				map.put("ivr_number", strIVRNumber);
				}
			return map;
			}// OBDLowPriority_payLoad
	
	/*
	 * Common Request from 2 Way OBD API request
	 * @Author : Anantha
	 */
	public static HashMap<String, Object> commonRequestParameter(String strId, String strCallerId,ArrayList<String> strRecipient, String strMediaUrl, String strCallbackUrl, String strDirection,
			String strPriority) {
					HashMap<String, Object> map = new HashMap<>();
			 if ((null != strId)) {
				 map.put("id", strId);
			}
			 if ((null != strCallerId)) {
				 map.put("caller_id", strCallerId);
			}
			 if ((null != strRecipient)) {
				 map.put("recipient", strRecipient);
			}
			 if ((null != strMediaUrl)) {
				 map.put("media_url", strMediaUrl);
			}
			 if ((null != strCallbackUrl)) {
				 map.put("callback_url", strCallbackUrl);
			}
			 if ((null != strDirection)) {
				 map.put("direction", strDirection);
			}
			 if ((null != strPriority)) {
				 map.put("priority", strPriority);
			}
			 return map;
			 }	
	/*
	 * OutBound SMS API Low Priority
	 * @Author : Guru
	 */
	public static HashMap<String, Object> OutBoundSMSLowPriority_payLoad(String strCmd, String strId,String strSenderMask, String strFrom, ArrayList<String> strTo,String strTrackUrl, String strExpiry,String strBody, String strUrlTrack, String strPriority)
	{
		
		HashMap<String, Object> map = new HashMap<>();	
		
		if ((null != strCmd)) {
			map.put("id", strCmd);
		}
		if ((null != strId)) {
			map.put("id", strId);
		}
		
		if ((null != strSenderMask)) {
			map.put("sendermask", strSenderMask);
		}
		if ((null != strFrom)) {
			map.put("from", strFrom);
		}		
		if ((null != strTo)) {
			map.put("to", strTo);
		}
		
		if ((null != strTrackUrl)) {
			map.put("track_url", strTrackUrl);
		}
		
		if ((null != strExpiry)) {
			map.put("expiry", strExpiry);
		}
		
		if ((null != strBody)) {
			map.put("body", strBody);
		}
		if ((null != strUrlTrack)) {
			map.put("url_to_track", strUrlTrack);
		}		
		if ((null != strPriority)) {
			map.put("priority", strPriority);
		}
		
		
		return map;
	}
	
	/*
	 * OutBound SMS API Low Priority paramater invalid 
	 * @Author : Guru
	 */
	public static HashMap<String, Object> OutBoundSMSLowPriority_payloadparams(String strCmd, String strId, String strFrom, ArrayList<String> strTo,String strTrackUrl, String strExpiry,String strBody, String strUrlTrack, String strPriority)
	{
		
		HashMap<String, Object> map = new HashMap<>();	
		
		if ((null != strCmd)) {
			map.put("id", strCmd);
		}
		if ((null != strId)) {
			map.put("id", strId);
		}
		
		if ((null != strFrom)) {
			map.put("from", strFrom);
		}		
		if ((null != strTo)) {
			map.put("to", strTo);
		}
		
		if ((null != strTrackUrl)) {
			map.put("track_url", strTrackUrl);
		}
		
		if ((null != strExpiry)) {
			map.put("expiry", strExpiry);
		}
		
		if ((null != strBody)) {
			map.put("body", strBody);
		}
		if ((null != strUrlTrack)) {
			map.put("url_to_track", strUrlTrack);
		}		
		if ((null != strPriority)) {
			map.put("priority", strPriority);
		}
		
		return map;
	}
	
	/*
	 * Dynamic OBD API Low Priority paramater
	 * @Author : Guru
	 */
	public static HashMap<String, Object> DynamicOBDLowPriBasic( String strId, String strcallerId, ArrayList<String> strrecipient,String strdocUrl, String strDirection, String strPriority)
	{
		
		HashMap<String, Object> map = new HashMap<>();	
		
		
		if ((null != strId)) {
			map.put("id", strId);
		}
		
		if ((null != strcallerId)) {
			map.put("from", strcallerId);
		}		
		if ((null != strrecipient)) {
			map.put("to", strrecipient);
		}
		
		if ((null != strdocUrl)) {
			map.put("track_url", strdocUrl);
		}
	
		if ((null != strDirection)) {
			map.put("url_to_track", strDirection);
		}		
		if ((null != strPriority)) {
			map.put("priority", strPriority);
		}
		
		return map;
	}
	
	/*
	 * @Author : Anantha
	 * OBD_Inbound API
	 */
	
	public static HashMap<String, Object> obdInboundPayLoad(String strId, String strMediaURL, String strCallBackURL,
			String strIVRNumber, String strDirection) {

		HashMap<String, Object> map = new HashMap<>();

		if ((null != strId)) {
			map.put("id", strId);
		}

		if ((null != strMediaURL)) {
			map.put("media_url", strMediaURL);
		}
		
		if ((null != strCallBackURL)) {
			map.put("callback_url", strCallBackURL);
		}

		if ((null != strIVRNumber)) {
			map.put("ivr_number", strIVRNumber);
		}

		if ((null != strDirection)) {
			map.put("direction", strDirection);
		}

		return map;
	}
			
}
