package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@GetMapping(value= {"/","index"})
	public String home() {
		
		//GET 요청
		//http://localhost:8090/
		System.out.println("index~");
		
		return "index"; //실행할 jsp뷰 이름 리턴
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getImageFile(String fileName) throws IOException{
		System.out.println("get /display fileName : " + fileName);
		
		File file = new File("C:/jiw/upload", fileName);
		System.out.println("get /display 실제 이미지 파일 경로 : " + file.getPath());
		
		HttpHeaders headers = new HttpHeaders();
		
		String contentType = Files.probeContentType(file.toPath());
		headers.add("Content-Type", contentType); //"image/jpeg" "image/png"
		
		byte[] imageData = FileCopyUtils.copyToByteArray(file);
		
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(imageData,headers,HttpStatus.OK);
				
		return responseEntity;		
	}
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName) throws UnsupportedEncodingException {
		System.out.println("get /download fileName : " + fileName);
		
		File file = new File("C:/jiw/upload", fileName);
		
		Resource resource = new FileSystemResource(file);
		System.out.println("resource : " + resource);
		
		if(!resource.exists()) { //다운로드 할 파일이 존재하지 않으면
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); //자원 없음 응답코드로 응답하고 종료
		}
		
		//다운로드 할 파일이 존재하면
		String resourceName = resource.getFilename();
		System.out.println("다운로드 할 파일 : " + resourceName);
		
		int beginIndex = resourceName.indexOf("_") + 1;
		String originFilename = resourceName.substring(beginIndex); //순수 파일명 구하기
		
		String downloadName = new String(originFilename.getBytes("UTF-8"), "ISO-8859-1"); //다운로드 파일명으로 변환
		
		HttpHeaders headers = new HttpHeaders();
		//headers.add("content-Type", "application/octet-stream");
		headers.add("Content-Disposition", "attachment; filename="+downloadName); //다운로드 파일명 헤더에 설정
		
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
		
	}
	
}
