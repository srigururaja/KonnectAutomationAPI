package databaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.testng.annotations.Test;

import testExecution.APIController;

public class DataBaseCommonQuery {

	public static ArrayList<String> getChannelPhone_status_activity(String strPhoneNumber) {
		Connection conn = DatabaseConfiguration.get_konnect_DbConnection();

		String str_status = null;
		String str_lastactivity = null;

		System.out.println("PHONE NUMBER : " + strPhoneNumber);

		ArrayList<String> list = new ArrayList<String>();
		try {
			PreparedStatement pstmt = conn
					.prepareStatement(APIController.readDBproperties("table.channel.Phone.status"));

			pstmt.setNString(1, strPhoneNumber);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				str_status = rs.getString("status");
				str_lastactivity = rs.getString("last_activity");

				list.add(str_status);
				list.add(str_lastactivity);
				System.out.println("STATUS : " + str_status);
				System.out.println("LAST ACTIVITY : " + str_lastactivity);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<String> update_Channel_Phone_status_activity(String strStatus, String strlstActivity,
			String strPhnum) {

		Connection conn = DatabaseConfiguration.get_konnect_DbConnection();

		ArrayList<String> list = new ArrayList<String>();
		try {
			PreparedStatement pstmt = conn
					.prepareStatement(APIController.readDBproperties("table.channel.status.update"));

			pstmt.setString(1, strStatus);
			pstmt.setString(2, strlstActivity);
			pstmt.setString(3, strPhnum);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
}
