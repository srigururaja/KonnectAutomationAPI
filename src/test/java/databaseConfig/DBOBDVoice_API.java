package databaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;


import com.konnect.api.poj.Campaginpoj;
import com.konnect.api.poj.Campaign_obd_schedule_run_poj;
import com.konnect.api.poj.Campaign_obd_schedule_set_poj;

import io.restassured.path.json.JsonPath;
import testExecution.APIController;



public class DBOBDVoice_API {

	/*
	 * Get Campaign table details 
	 * 
	 * @Author : Anantha
	 */
	public static Campaginpoj  getCampaginDetails(int userIdCopy) {
		Connection conn = DatabaseConfiguration.get_konnect_DbConnection();
		Campaginpoj campagindata = null;
		String str = APIController.readDBproperties("table.campagin");
		System.out.println("Query" +str);
		try {
			PreparedStatement pstmt = conn.prepareStatement(str);
			pstmt.setInt(1, userIdCopy);// (1, Integer.parseInt(primaryno));
			ResultSet rs = pstmt.executeQuery();
			campagindata = new Campaginpoj();
			
			
			while (rs.next()) {
				campagindata.setCampaign_id(rs.getInt("campaign_id"));
				campagindata.setName(rs.getString("name"));
				campagindata.setType(rs.getString("type"));
				campagindata.setSub_type(rs.getString("sub_type"));
				campagindata.setStatus(rs.getString("status"));
				campagindata.setSource(rs.getString("source"));
				campagindata.setRequest_id(rs.getString("request_id"));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return campagindata;
	} // end of getCampaginDetails
	
	
	
	/*
	 * Get campaign_obd_schedule_set details
	 */
	public static Campaign_obd_schedule_set_poj  getCampaginOBDSchedule_setDetails(int campaignID, int str_campaign_obd_schedule_run_id) {
		
		Connection conn = DatabaseConfiguration.get_konnect_DbConnection();
		
		Campaign_obd_schedule_set_poj campagin_obdschedule_setdata = null;
		
		try {
			// select * from  campaign_obd_schedule_set where campaign_id=?
			PreparedStatement pstmt = conn.prepareStatement("select * from  campaign_obd_schedule_set where campaign_id=? and campaign_obd_schedule_run_id=?");
			pstmt.setInt(1, campaignID);// (1, Integer.parseInt(primaryno));
			pstmt.setInt(2, str_campaign_obd_schedule_run_id);
			ResultSet rs = pstmt.executeQuery();
			campagin_obdschedule_setdata = new Campaign_obd_schedule_set_poj();
			
			
			while (rs.next()) {
				campagin_obdschedule_setdata.setCampaign_obd_schedule_set_id(rs.getInt("campaign_obd_schedule_set_id"));
				campagin_obdschedule_setdata.setCampaign_obd_schedule_run_id(rs.getInt("campaign_obd_schedule_run_id"));
				campagin_obdschedule_setdata.setCampaign_obd_schedule_id(rs.getInt("campaign_obd_schedule_id"));
				campagin_obdschedule_setdata.setRequest_id(rs.getString("request_id"));
				campagin_obdschedule_setdata.setCreated_by(rs.getInt("created_by"));
				campagin_obdschedule_setdata.setUpdated_by(rs.getInt("updated_by"));
				campagin_obdschedule_setdata.setCampaign_schedule_set_req(rs.getString("campaign_schedule_set_req"));
				campagin_obdschedule_setdata.setCampaign_schedule_set_res(rs.getString("campaign_schedule_set_res"));
			}
			
		}catch (SQLException e) {
				e.printStackTrace();
			}
		
		return campagin_obdschedule_setdata;
		
	}
	
	
	/*
	 * Get campaign_obd_schedule_run details
	 */
	
	public static Campaign_obd_schedule_run_poj  getCampaginOBDSchedule_runDetails(int campaignID) {
		
		Connection conn = DatabaseConfiguration.get_konnect_DbConnection();
		
		Campaign_obd_schedule_run_poj campagin_obdschedule_rundata = null;
		System.out.println("Campagin ID : " +campaignID);
		//String str = APIController.readDBproperties("table.campagin");
		//System.out.println("Query : " +str);
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from  campaign_obd_schedule_run where campaign_id=? order by campaign_obd_schedule_run_id desc limit 1");
			pstmt.setInt(1, campaignID);// (1, Integer.parseInt(primaryno));
			ResultSet rs = pstmt.executeQuery();
			campagin_obdschedule_rundata = new Campaign_obd_schedule_run_poj();
			
			
			while (rs.next()) {
				campagin_obdschedule_rundata.setCampaign_obd_schedule_run_id(rs.getInt("campaign_obd_schedule_run_id"));
				campagin_obdschedule_rundata.setCampaign_obd_schedule_id(rs.getInt("campaign_obd_schedule_id"));
				campagin_obdschedule_rundata.setCampaign_id(rs.getInt("campaign_id"));
				campagin_obdschedule_rundata.setApi_type(rs.getString("api_type"));
				campagin_obdschedule_rundata.setPriority(rs.getString("priority"));
				campagin_obdschedule_rundata.setRequest_id(rs.getString("request_id"));
				campagin_obdschedule_rundata.setCampaign_schedule_run_req(rs.getString("campaign_schedule_run_req"));//json 
				campagin_obdschedule_rundata.setAudience_count(rs.getInt("audience_count"));
				campagin_obdschedule_rundata.setAudio_file(rs.getString("audio_file"));
				campagin_obdschedule_rundata.setCallback_url_api(rs.getString("callback_url_api"));//json
				campagin_obdschedule_rundata.setCreated_by(rs.getInt("created_by"));
			}
			
		}catch (SQLException e) {
				e.printStackTrace();
			}
		
		return campagin_obdschedule_rundata;
		
	}
	
	
	
	
	@Test
	public void sample()
	{
		//DBOBDVoice_API.campaginDetails1("834");
		//Campaginpoj cmp = DBOBDVoice_API.getCampaginDetails(834);
		
		/*System.out.println("Name : "+cmp.getName());
		System.out.println("Type : "+cmp.getType());*/
		
		
		//=====================set=======================
		
		
		
		//=========================run===================
		List<Integer> runList = DBOBDVoice_API.getOBDschedule_RUN_details("outbound", "high");
		System.out.println("--->"+runList.get(2));
		
		
	}
	
	
	public static String campaginDetails1 (String camp_id) {
		Connection conn = DatabaseConfiguration.get_konnect_DbConnection();
		String obd_type = null;
		String obd_sub_type = null;
		String obd_status = null;
		String obd_name = null;
		String obd_source = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement("select name,type,sub_type,source,status from campaign where user_id_copy =?");
			pstmt.setNString(1, camp_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				obd_name = rs.getString("name");
				obd_type = rs.getString("type");
				obd_sub_type = rs.getString("sub_type");
				obd_source =rs.getString("source");
				obd_status= rs.getString("status");
				
				System.out.println("Name of the campaign in OutBound SMS API is : "+obd_name);
				System.out.println("Type of the campaign in OutBound SMS API is : "+obd_type);
				System.out.println("Sub_Type of the campaign in OutBound SMS API is : "+obd_sub_type);
				System.out.println("Source of the campaign in OutBound SMS API is : "+obd_source);
				System.out.println("Status of the campaign in OutBound SMS API is : "+obd_status);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//===========validation Methods===============
	public static List<String> getobdschedule_SET_details()
	{
		Campaign_obd_schedule_set_poj get_setDetails = DBOBDVoice_API.getCampaginOBDSchedule_setDetails(12439,190);
		System.out.println("obd_schedule_set_id : "+get_setDetails.getCampaign_obd_schedule_set_id());
		System.out.println("obd_schedule_run_id : "+get_setDetails.getCampaign_obd_schedule_run_id());

		// Request
		System.out.println("campaign_schedule_set_req : "+get_setDetails.getCampaign_schedule_set_req());
		List<String> list  =APIController.getListValue(get_setDetails.getCampaign_schedule_set_req(), "phone_number_list");
		System.out.println("SIZE : "+list.size());
		for (String string : list) {
			System.out.println("--NUMBER LIST--");
			System.out.println(""+string); 
		}
		
		// Response
		System.out.println("campaign_schedule_set_res : "+get_setDetails.getCampaign_schedule_set_res());
		JsonPath js = new JsonPath(get_setDetails.getCampaign_schedule_set_res());
		System.out.println(APIController.getStringValue(js, "status"));
		
		return list;
	}
	
	
	public static List<Integer> getOBDschedule_RUN_details(String strapi_type, String strpriority)
	{
		Campaign_obd_schedule_run_poj rundetils = DBOBDVoice_API.getCampaginOBDSchedule_runDetails(12439);
		
		List<Integer> list = new ArrayList<>();
		
		System.out.println("campaign_obd_schedule_run_id : " + rundetils.getCampaign_obd_schedule_run_id());
		list.add(rundetils.getCampaign_obd_schedule_run_id());
		
		System.out.println("campaign_obd_schedule_id : " + rundetils.getCampaign_obd_schedule_id());
		list.add(rundetils.getCampaign_obd_schedule_id());
		
		System.out.println("campaign_id : " + rundetils.getCampaign_id());
		
		// API type outbound/inbound
		System.out.println("api_type : " + rundetils.getApi_type());
		Assert.assertEquals(rundetils.getApi_type(), strapi_type);
		
		// Priority High/low
		System.out.println("priority : " + rundetils.getPriority());
		Assert.assertEquals(rundetils.getPriority(), strpriority);
		
		
		System.out.println("request_id : " + rundetils.getRequest_id());		
		System.out.println("campaign_schedule_run_req : " + rundetils.getCampaign_schedule_run_req());
		System.out.println("audience_count : " + rundetils.getAudience_count());	
		list.add(rundetils.getAudience_count());
		
		System.out.println("audio_file : " + rundetils.getAudio_file());
		System.out.println("callback_url_api : " + rundetils.getCallback_url_api());	
		System.out.println("callback_url_api : " + rundetils.getCreated_by());
		
		System.out.println(list); 
		
		return list;
		
	}
}
