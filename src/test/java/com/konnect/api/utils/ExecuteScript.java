package com.konnect.api.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ExecuteScript {
	public static WebElement element = null;
	// every class this method should be added... based on the class name...

	public static WebElement execute_Jmeter_script(WebDriver driver)
			throws IOException, InterruptedException {
		Process p = Runtime.getRuntime()
				.exec("cmd /c start C:\\apache-jmeter-2.6\\bin\\SnapCall-Konnect\\StartVoteCall.bat");
		p.waitFor();
		Thread.sleep(2000);
		return element;
	}
	
	public static WebElement execute_Jmeter_script_SMSTagging(WebDriver driver)
			throws IOException, InterruptedException {
		Process p = Runtime.getRuntime()
				.exec("cmd /c start C:\\Anantha\\jmeter\\jmeter2.6\\apache-jmeter-2.6\\bin\\AVSEarlymedia\\AVSEarlymedia.bat");
		p.waitFor();
		Thread.sleep(2000);
		return element;
	}

	public static WebElement execute_Jmeter_script_linux(WebDriver driver) throws Exception {
		String host = "192.168.231.44";
		String user = "root";
		String password = "xpsr350";
		String command0 = "/home/jm/jakarta-jm-2.3RC3/bin/SnapCall-Konnect/delete.sh &";
		String command1 = "/home/jm/jakarta-jm-2.3RC3/bin/SnapCall-Konnect/numberchange.sh &";
		String command2 = "/home/jm/jakarta-jm-2.3RC3/bin/SnapCall-Konnect/run_loadtest_in_iterations.sh &";
		Channel channel = null;
		Session session = null;
		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("nohup sh -c " + command0);
			channel.setInputStream(null);
			InputStream in = channel.getInputStream();
			channel.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line1;
			while ((line1 = reader.readLine()) != null) {
				System.out.println(line1);
			}
			System.out.println("ExistCode:" + channel.getExitStatus());
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeUnit.SECONDS.sleep(3);
		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("nohup sh -c " + command1);
			channel.setInputStream(null);
			InputStream in = channel.getInputStream();
			channel.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line1;
			while ((line1 = reader.readLine()) != null) {
				System.out.println(line1);
			}
			System.out.println("ExistCode:" + channel.getExitStatus());
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeUnit.SECONDS.sleep(3);
		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");

			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("nohup sh -c " + command2);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();
			channel.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("ExistCode:" + channel.getExitStatus());
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
		return element;
	}
	
	/*
	 * copy the file from windows to linux server
	 */
	
	public static WebElement copy_file(WebDriver driver)
			throws IOException, InterruptedException {
		Process p = Runtime.getRuntime()
				.exec("cmd /c start C:\\Users\\Kirusa\\Workspacenew\\Konnect_Automation\\logs\\filecopy.bat");
		p.waitFor();
		Thread.sleep(2000);
		return element;
	}
	
	/*
	 * @SMS Taging
	 * 
	 */
	public static WebElement execute_Jmeter_scriptSMSTagging_linux(WebDriver driver) throws Exception {
		String host = "192.168.231.44";
		String user = "root";
		String password = "xpsr350";
		String command2 = "/home/jm/jakarta-jm-2.3RC3/bin/AVSEarlymedia/run_loadtest_in_iterations.sh &";
		Channel channel = null;
		Session session = null;
		
		TimeUnit.SECONDS.sleep(10);
		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");

			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("nohup sh -c " + command2);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();
			channel.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("ExistCode:" + channel.getExitStatus());
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
		System.out.println("Test");
		return element;
	}

	
	/*
	 * unzip the file in linux
	 */
		
	public static WebElement untar_file_linux() throws Exception {
		String host = "192.168.231.237";
		String user = "root";
		String password = "xpsr@350";
		String command = "/var/www/html/snapcall/apiunzip.sh &";
		Channel channel = null;
		Session session = null;
	
		TimeUnit.SECONDS.sleep(3);
		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");

			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("nohup sh -c " + command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();
			channel.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("ExistCode:" + channel.getExitStatus());
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
		return element;
	}
	
	
	
	/*
	 * copy the file from windows to linux server
	 */
	
	public static WebElement copy_file()
			throws IOException, InterruptedException {
		Process p = Runtime.getRuntime()
				.exec("cmd /c start C:\\Users\\\"Srigururaja T\"\\Workspaces\\MyEclipse\\Konnect_Automation\\resources\\textfile\\filecopy.bat");
		p.waitFor();
		Thread.sleep(2000);
		return element;
	}
	
	public static WebElement copy_tarfile()
			throws IOException, InterruptedException {
		Process p = Runtime.getRuntime()
				.exec("cmd /c start C:\\Users\\\"Srigururaja T\"\\Workspaces\\MyEclipse\\Konnect_Automation\\resources\\textfile\\zipfile.bat");
		p.waitFor();
		Thread.sleep(2000);
		return element;
	}
	
	
	/*
	 * FTP File Transfer code
	 */
	
	
	public static void tranferFile() {

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("192.168.231.237", 80);
            ftpClient.login("root", "xpsr@350");
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            File sourceFile = new File("/home/sthiyagaraj/eclipse-workspace/KonnectAPI/target/APIURL.tar.gz");
            InputStream inputStream = new FileInputStream(sourceFile);

            boolean done = ftpClient.storeFile("filename which receiver get", inputStream);
            inputStream.close();
            if (done) {
                System.out.println("file is uploaded successfully..............");
            }

        } catch (IOException e) {
           System.err.println("Exception occured while ftp : "+e);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                System.err.println("Exception occured while ftp logout/disconnect : "+e);
            }
        }

    }
	
	
	/*
	 * Copy File from local to linux server using shell script
 	 */
	
	public static WebElement copy_linuxfile()
			throws IOException, InterruptedException {
		Process p = Runtime.getRuntime()
				.exec("sh -x /home/sthiyagaraj/copy.sh ");
		p.waitFor();
		Thread.sleep(2000);
		BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(p.getInputStream()));
			// Read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(s);
			}

		return element;
	}
	
	
	
	
}
