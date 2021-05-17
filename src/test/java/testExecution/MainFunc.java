package testExecution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.konnect.api.utils.ExecuteScript;
import com.konnect.api.utils.Send_Email;
import com.konnect.api.utils.TarfileCreate;

public class MainFunc {
	
	public static WebDriver driver;
	
	/*
	 * Email Send Methods
	 */
	public static void email_send () throws InterruptedException {
		Send_Email email = new Send_Email(driver);
		TimeUnit.SECONDS.sleep(5);
		email.sending_email();
	}
	
	/*
	 * Create API URL Script
	 */
	public static void APIURLScript() throws Exception {
		//TarfileCreate.CreateTarGZ();
		//ExecuteScript.copy_linuxfile();
		//ExecuteScript.untar_file_linux();
		
	}
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//APIURLScript();
		//email_send();
	}

}
