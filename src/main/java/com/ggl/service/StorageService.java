package com.ggl.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Stream;

//import org.springframework.beans.factory.annotation.Value;

@Service("storage")
public class StorageService {

	public static final Logger logger = LoggerFactory.getLogger(StorageService.class);

//	private String files="/var/www/html/booking/";	
	private String files="/var/www/html/assets/Files/";	

	private String imagepath="/home/ec2-user/GGL/PaymentFiles/";
	//private String bookingimagepath="35.162.40.190/booking/";
	private String bookingimagepath="/var/www/html/booking/";

	//private String bookingimagepath="D:/GGL/2019/August/2/ggl-server/src/main/webapp/assets/booking/";
	
	private final Path rootLocation = Paths.get(files);
	private final Path imageRootLocation = Paths.get(imagepath);
	private final Path bookingRootLocation = Paths.get(bookingimagepath);
	
	public String store(MultipartFile file ,String memberID,String uploadStatus) {
		String fileName=null;
		String status = "Fail";
		try {
			if(uploadStatus == "paymentUpload"){
				logger.info("Path ---->"+imageRootLocation);
				logger.info("---------- Upload Payment ---------");
				if(memberID != null) {
					fileName = memberID + ".jpg";
				}
				else {
					fileName = file.getOriginalFilename();
				}
				Files.deleteIfExists(this.imageRootLocation.resolve(fileName));
				Files.copy(file.getInputStream(), this.imageRootLocation.resolve(fileName));
			}else if(uploadStatus == "updateImage"){
				logger.info("Path ---->"+rootLocation);
				logger.info("---------- Upload Image on List of Company ---------");
				if(memberID != null) {
					fileName = memberID + ".jpg";
				}
				else {
					fileName = file.getOriginalFilename();
				}
				Files.deleteIfExists(this.rootLocation.resolve(fileName));
				Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
			}else if(uploadStatus == "bookingImage"){
				logger.info("Path ---->"+bookingRootLocation);
				logger.info("---------- Upload Image on Booking ---------");
				if(memberID != null) {
					fileName = memberID + ".jpg";
				}
				else {
					fileName = file.getOriginalFilename();
				}
				Files.deleteIfExists(this.bookingRootLocation.resolve(fileName));
				Files.copy(file.getInputStream(), this.bookingRootLocation.resolve(fileName));
			}
			status="Success";
		} catch (Exception e) {
			status="failure";
			logger.error("Exception -->"+e.getMessage());
			throw new RuntimeException("FAIL!");
		}
		return status;
	}

	public Stream<Path> loadAll() {
		logger.info("------ Inside LoadAll Method --------");
        try {
            return Files.walk(this.imageRootLocation, 1)
                    .filter(path -> path.equals(this.imageRootLocation))
                    .map(path -> this.imageRootLocation.relativize(path));
        } catch (IOException e) {
			logger.error("Exception -->"+e.getMessage());
            throw new RuntimeException("Failed to read stored files", e);
        }

    }

	public Resource loadFile(String filename) {
		logger.info("------- Inside loadfile Method ---------");
		try {
			logger.info("------- root Path ---------"+imageRootLocation);
			logger.info("------- File Name ---------"+filename);	
			Path file = imageRootLocation.resolve(filename);
			logger.info("------- After Path Called ---------"+file);
			Resource resource = new UrlResource(file.toUri());
			logger.info("------- After Resources ---------"+resource);
			if (resource.exists() || resource.isReadable()) {
				logger.info("------- Resources is exsist or isreadable---------"+resource.exists());
				return resource;
			} else {
				logger.info("------- Resources is not exsist or is not readable---------");
				Path file1 = imageRootLocation.resolve("NoImage.jpg"); 
				Resource status = new UrlResource(file1.toUri());
				return status;
				//throw new RuntimeException("FAIL!"); 
			}
		} catch (MalformedURLException e) {
			logger.error("Exception -->"+e.getMessage());
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(imageRootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(imageRootLocation);
		} catch (IOException e) {
			logger.error("Exception -->"+e.getMessage());
			throw new RuntimeException("Could not initialize storage!");
			
		}
	}

}
