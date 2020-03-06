package com.example.demo.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


public class FileUtility {


	 
	 public static String getFileNameCheck(Path filePath, MultipartFile file) {

		 	System.out.println("****************************************************************************************");
		 	//String fileExtension = FileUtility.getFileExtension(file.getOriginalFilename());
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		    String customFileName = fileName;
	
		    int fileCount = 0;
		    while (new File(filePath + File.separator + customFileName).exists()) {
		      fileCount++;
		      System.out.println("File already exists");
		      customFileName = fileName + "_" + fileCount;
		          System.out.println("fileName========="+filePath + File.separator + customFileName);
		    }
		    return customFileName;
	  }
	 
	  public static String getFileExtension(String fullName) {
		    //checkNotNull(fullName);
		    String fileName = new File(fullName).getName();
		    int dotIndex = fileName.lastIndexOf('.');
		    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
		  }
	  
	  public static String generateFileName(MultipartFile multiPart, String email) {
		  	String fileName = StringUtils.cleanPath(multiPart.getOriginalFilename());
		    return  "document/" + email + "/policy/" + new Date().getTime() + "-" + fileName;
		}
	  
	  public static File convertMultiPartToFile(MultipartFile file) throws IOException {
		    File convFile = new File(file.getOriginalFilename());
		    FileOutputStream fos = new FileOutputStream(convFile);
		    fos.write(file.getBytes());
		    fos.close();
		    return convFile;
		}
	
}
