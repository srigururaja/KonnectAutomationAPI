package com.konnect.api.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class TarfileCreate {
	public WebDriver driver;
	public TarfileCreate(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public static void CreateTarGZ()throws FileNotFoundException, IOException{
		    TarArchiveOutputStream tOut = null;
			GzipCompressorOutputStream gzOut = null;
			BufferedOutputStream bOut = null;
			FileOutputStream fOut = null;
			try {
		        System.out.println(new File(".").getAbsolutePath());
		       /* String dirPath = "C:\\Users\\Srigururaja T\\Documents\\ATUReporter";
		        String tarGzPath = "C:\\Users\\Srigururaja T\\Documents\\ATUReporter.tar.gz";*/
		        String dirPath = "/home/sthiyagaraj/eclipse-workspace/KonnectAPI/target/surefire-reports";
		        String tarGzPath = "/home/sthiyagaraj/eclipse-workspace/KonnectAPI/target/APIURL.tar.gz";
		       // String dirPath = "/home/ATUReporter";
		       // String tarGzPath = "/home/ATUReporter.tar.gz";
		        fOut = new FileOutputStream(new File(tarGzPath));
		        bOut = new BufferedOutputStream(fOut);
		        gzOut = new GzipCompressorOutputStream(bOut);
		        tOut = new TarArchiveOutputStream(gzOut);
		        tOut.setLongFileMode(tOut.LONGFILE_POSIX);
		        addFileToTarGz(tOut, dirPath, "");
		    } finally {
		        tOut.finish();
		        tOut.close();
		        gzOut.close();
		        bOut.close();
		        fOut.close();
		    }
		}
	private static void addFileToTarGz(TarArchiveOutputStream tOut, String path, String base)
		    throws IOException
		{
		    File f = new File(path);
		    System.out.println(f.exists());
		    String entryName = base + f.getName();
		    TarArchiveEntry tarEntry = new TarArchiveEntry(f, entryName);
		    tOut.putArchiveEntry(tarEntry);

		    if (f.isFile()) {
		        IOUtils.copy(new FileInputStream(f), tOut);
		        tOut.closeArchiveEntry();
		    } else {
		        tOut.closeArchiveEntry();
		        File[] children = f.listFiles();
		        if (children != null) {
		            for (File child : children) {
		                System.out.println(child.getName());
		                addFileToTarGz(tOut, child.getAbsolutePath(), entryName + "/");
		            }
		        }
		    }
		}
}
