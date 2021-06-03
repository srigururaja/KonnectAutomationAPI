package testExecution;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.konnect.api.utils.Send_Email;

import databaseConfig.DBOutBoundSMSAPI;

public class MainFunc {
	
	public static WebDriver driver;
	
	public static void test() {
		//ArrayList<String> aaar = DBOutBoundSMSAPI.APIOutBoundSMSCamp("12442");
				//System.out.println("gettting return value from method : "+aaar);
				
				ArrayList<String> respList = new ArrayList<String>();
				String dup = APIController.readDBproperties("outboundSMS_to_number_invalid");
				String[] aaar = dup.split(",");
				for(int i=0; i<aaar.length;i++){
					respList.add(aaar[i]);
				}
				System.out.println(respList);
				//System.out.println(respList.get(0));
				//System.out.println(respList.get(1));
				//OutBoundSMSValidData(listss,"dup_req1");
				
				ArrayList<String> repo = DBOutBoundSMSAPI.APIOutBoundSMSReport("suc_obsms","2021-06-03");
				System.out.println("gettting return value from method : " + repo);
				System.out.println("Creation Status : " + repo.get(13));
				String Creation_status = repo.get(13);
				System.out.println("Queued Status : " + repo.get(14));

				if (Creation_status.equals("accepted")) {
					if (repo.get(14).equalsIgnoreCase("queued") || repo.get(14).equalsIgnoreCase("queue_failed")) {
						System.out.println("The given request is successfully sent and accepted with the creation status : "
								+ repo.get(13) + " and the queued status : " + repo.get(14) + "");
					}
				} else if (Creation_status.equals("invalid")) {
					if (repo.get(14)==null) {
						System.out.println("The given request contains invalid number format. with the creation status : "
								+ repo.get(13) + " and the queued status : " + repo.get(14) + "");
					}
				} else if (Creation_status.equals("duplicate")) {
					if (repo.get(14)==null) {
						System.out.println("The given request contains duplicated value in numbes. with the creation status : "
								+ repo.get(13) + " and the queued status : " + repo.get(14) + " ");
					}
				} else {
					System.err.println("DB error");
				}
				
	}
	
	public static void email_send () throws InterruptedException {
		Send_Email email = new Send_Email(driver);
		TimeUnit.SECONDS.sleep(5);
		email.sending_email();
	}
	
	public static void APIURLScript() throws Exception {
		//TarfileCreate.CreateTarGZ();
		//ExecuteScript.copy_linuxfile();
		//ExecuteScript.untar_file_linux();
		
	}
	
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//APIURLScript();
		//email_send();
		test();
		
	}


}
