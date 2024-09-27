package com.project.blogging.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.blogging.services.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		// file Name
		String name = file.getOriginalFilename();

		// Generating random name of file
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

		// full path
		String filePath = path +"/"+ fileName1;

		// create folder of not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return fileName1;
	}

	@Override
	public InputStream getResorce(String path, String fileName) throws FileNotFoundException {

		String fullPath = path +"/"+ fileName;

		InputStream is =new FileInputStream(fullPath);
		// db logic to return inputStream
		return is;
	}
	
	
	

}
