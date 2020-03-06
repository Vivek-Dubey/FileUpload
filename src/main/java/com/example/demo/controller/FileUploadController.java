package com.example.demo.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileService;


@RestController
@RequestMapping("/file")
public class FileUploadController {
	
	@Autowired
	private FileService fileService;
	
	
	@PostMapping(value ="/uploadFile")
	  public String uploadFile(
	      @RequestParam(name = "file") MultipartFile uploadedfile) {

//	    String extension =
//	        com.google.common.io.Files.getFileExtension(uploadedfile.getOriginalFilename());
//	    if (!fileStorageService.isAllowedFormat(extension)) {
//	      LOG.info(FILE_REQD_MSG);
//	      throw new FileValidationException(FILE_REQD_MSG);
//	    }
	
		String response = fileService.uploadFile(uploadedfile);
		
	    return response;
	  }

	
	@PostMapping("/uploadFileToS3")
    public String uploadFileToS3(@RequestParam("file") MultipartFile file) {
		try{
			fileService.uploadFiles(Arrays.asList(file));
		} catch (Exception e) {
			return "Unable to upload document." + e;
		}
		return "Document uploaded successfully!!";
    }

    @PostMapping("/uploadMultipleFilesToS3")
    public String uploadMultipleFilesToS3(@RequestParam("files") MultipartFile[] files) {
    	try{
			fileService.uploadFiles(Arrays.asList(files));
		} catch (Exception e) {
			return "Unable to upload document." + e;
		}
		return "Document uploaded successfully!!";
    }

}
