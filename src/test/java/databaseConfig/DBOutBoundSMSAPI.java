package databaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import testExecution.APIController;

public class DBOutBoundSMSAPI {
	
	public static void APIOutBoundSMSDel() {
		Connection conn = DatabaseConfiguration.get_konnect_DbConnection();
		
		try {
			Statement statement = conn.createStatement();
			// Delete the record from log_outbound_sms table on current date 
			String sql= "DELETE from log_outbound_sms where date(creation_time) = CURDATE()";
			statement.execute(sql);
			System.out.println("Deleted the records from log_outbound_sms table");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> APIOutBoundSMSCamp (String camp_id) {
		Connection conn = DatabaseConfiguration.get_konnect_DbConnection();
		ArrayList <String> data_value = new ArrayList<String>();
		
		try {
			PreparedStatement pstmt = conn
					.prepareStatement(APIController.readDBproperties("outboundSMS_camp"));
			pstmt.setNString(1, camp_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
				data_value.add(rs.getString("name"));	
				data_value.add(rs.getString("type"));	
				data_value.add(rs.getString("sub_type"));	
				data_value.add(rs.getString("source"));	
				data_value.add(rs.getString("status"));	
				System.out.println("Details gather from Campaign table of OutBound SMS API");
					System.out.println("Name: '"+data_value.get(0)+"'\n"
							+ "Type: '"+data_value.get(1)+"'"
									+ "sub_type: '"+data_value.get(2)+"' \n"
											+ "source: '"+data_value.get(3)+"' \n"
													+ "status: '"+data_value.get(4)+"'  " );
				
				
			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return(data_value);
	}
	
	public static ArrayList<String> APIOutBoundSMSReport (String req_id, String creation_date) {
		Connection conn = DatabaseConfiguration.get_konnect_DbConnection();
		ArrayList <String> data_value = new ArrayList<String>();
		
		try {
			PreparedStatement pstmt = conn
					.prepareStatement(APIController.readDBproperties("outboundSMS_report"));
			//pstmt.setNString(1, req_id + creation_date);
			pstmt.setString(1, req_id);
			pstmt.setString(2,creation_date);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
				data_value.add(rs.getString("sms_type"));	
				data_value.add(rs.getString("sms_priority"));	
				data_value.add(rs.getString("message_status"));	
				data_value.add(rs.getString("to_number"));	
				data_value.add(rs.getString("original_from_number"));
				data_value.add(rs.getString("sender_mask"));
				data_value.add(rs.getString("sms_content"));
				data_value.add(rs.getString("request_id"));
				data_value.add(rs.getString("campaign_id"));
				data_value.add(rs.getString("campaign_sms_schedule_id"));
				data_value.add(rs.getString("campaign_sms_schedule_run_id"));
				data_value.add(rs.getString("routed_provider_id"));
				data_value.add(rs.getString("price"));
				data_value.add(rs.getString("creation_status"));
				data_value.add(rs.getString("queued_status"));
				data_value.add(rs.getString("sent_status"));
				data_value.add(rs.getString("delivery_status"));
			/*	System.out.println("Details gather from log_outbound_sms table of OutBound SMS API verification report part");
					System.out.println("sms_type: '"+data_value.get(0)+"'\n"
							+ "sms_priority: '"+data_value.get(1)+"'\n"
							+ "message_status: '"+data_value.get(2)+"'\n"
							+ "to_number: '"+data_value.get(3)+"'\n"
							+ "original_from_number: '"+data_value.get(4)+"'\n"
							+ "sender_mask: '"+data_value.get(5)+"'\n"
							+ "sms_content: '"+data_value.get(6)+"'\n"
							+ "request_id: '"+data_value.get(7)+"'\n"
							+ "campaign_id: '"+data_value.get(8)+"'\n"
							+ "campaign_sms_schedule_id: '"+data_value.get(9)+"'\n"
							+ "campaign_sms_schedule_run_id: '"+data_value.get(10)+"'\n"
							+ "routed_provider_id: '"+data_value.get(11)+"'\n"
							+ "price: '"+data_value.get(12)+"'\n"
							+ "creation_status: '"+data_value.get(13)+"'\n"
							+ "queued_status: '"+data_value.get(14)+"'\n"
							+ "sent_status: '"+data_value.get(15)+"'\n"
							+ "delivery_status: '"+data_value.get(16)+"' ");
				*/
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return(data_value);
	}

	public static void OutboundSMS_API_DBPart1(String req_id, String creation_date) {
		//DB Validation
		ArrayList<String> repo = DBOutBoundSMSAPI.APIOutBoundSMSReport(req_id,creation_date);
		System.out.println("Detail gather from log_outbound_sms table : " + repo);
		System.out.println("Creation Status : " + repo.get(13));
		String Creation_status = repo.get(13);
		System.out.println("Queued Status : " + repo.get(14));

		if (Creation_status.equals("accepted")) {
			if (repo.get(14).equalsIgnoreCase("queued") || repo.get(14).equalsIgnoreCase("queue_failed")) {
				System.out.println("The given "+req_id+" is successfully sent and accepted with the creation status : "
						+ repo.get(13) + " and the queued status : " + repo.get(14) + "");
			}
		} else if (Creation_status.equals("invalid")) {
			if (repo.get(14)==null) {
				System.out.println("The given "+req_id+" contains invalid number format. with the creation status : "
						+ repo.get(13) + " and the queued status : " + repo.get(14) + "");
			}
		} else if (Creation_status.equals("duplicate")) {
			if (repo.get(14)==null) {
				System.out.println("The given "+req_id+" contains duplicated value in numbes. with the creation status : "
						+ repo.get(13) + " and the queued status : " + repo.get(14) + " ");
			}
		} else {
			System.err.println("DB Verification error from Creation Status on Konnect Process");
		}

	}
}
