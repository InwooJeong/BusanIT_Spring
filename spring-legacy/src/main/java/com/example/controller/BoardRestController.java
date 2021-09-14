package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.AttachVO;
import com.example.domain.BoardVO;
import com.example.service.AttachService;
import com.example.service.BoardService;

import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("/api/*")
public class BoardRestController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private AttachService attachService;

	// 년/월/일 형식 폴더명 리턴하는 메소드
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String str = sdf.format(new Date());

		return str;
	}

	// 첨부파일 업로드(썸네일 생성) 후 attachList 리턴
	public List<AttachVO> uploadFilesAndGetAttachList(List<MultipartFile> files, int bno)
			throws IllegalAccessError, IOException {

		List<AttachVO> attachList = new ArrayList<AttachVO>();

		// 생성할 파일정보가 없으면 종료
		if (files == null || files.size() == 0) {
			System.out.println("업로드한 첨부파일 개수 : " + files.size());
			return attachList;
		}

		String uploadFolder = "C:/jiw/upload"; // 업로드 기준경로

		File uploadPath = new File(uploadFolder, getFolder()); // C:/ksw/upload/2021/08/31
		System.out.println("uploadPath : " + uploadPath.getPath());

		if (uploadPath.exists() == false) { // !uploadPath.exists()
			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : files) {
			// 업로드 시 file type의 input 태그가 총 5개 사용되었는데
			// 그중에 3개만 파일을 선택하고 2개는 파일선택안하고 비워두면
			// files.size()는 5가 되므로, 실제 선택한 파일정보만 가져오려면 isEmpty()로 걸러야됨
			if (multipartFile.isEmpty()) {
				continue;
			}

			String originalFilename = multipartFile.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			String uploadFilename = uuid.toString() + "_" + originalFilename;

			File file = new File(uploadPath, uploadFilename); // 생성할 파일이름 정보

			// 파일1개 업로드(파일 생성) 완료
			multipartFile.transferTo(file);
			// ======================================================

			// 현재 업로드한 파일이 이미지 파일이면 썸네일 이미지를 추가로 생성하기
			boolean isImage = checkImageType(file); // 이미지 파일여부 확인

			if (isImage == true) {
				File outFile = new File(uploadPath, "s_" + uploadFilename);

				Thumbnailator.createThumbnail(file, outFile, 100, 100); // 썸네일 이미지 파일 생성하기
			}

			// =======================================================

			AttachVO attachVO = new AttachVO();
			attachVO.setUuid(uuid.toString());
			attachVO.setUploadpath(getFolder());
			attachVO.setFilename(originalFilename);
			attachVO.setFiletype((isImage == true) ? "I" : "O");
			attachVO.setBno(bno);

			attachList.add(attachVO);
		} // for

		return attachList;
	}

	private boolean checkImageType(File file) throws IOException {
		boolean isImage = false;

		String contentType = Files.probeContentType(file.toPath()); // "image/jpg"

		isImage = contentType.startsWith("image");

		return isImage;
	}

	// 첨부파일 업로드와 함께 주글쓰기 처리
	@PostMapping(value = "/boards", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BoardVO> createMain(List<MultipartFile> files, BoardVO boardVO, HttpServletRequest request) throws IOException {

		// insert할 글번호 가져오기
		int num = boardService.nextNum();

		// 첨부파일 업로드(썸네일 생성) 후 attachList 리턴
		List<AttachVO> attachList = uploadFilesAndGetAttachList(files, num);

		// ===== insert할 BoardVO 객체 데이터 설정 ======
		boardVO.setNum(num);
		boardVO.setReadcount(0);
		boardVO.setRegDate(new Date());
		boardVO.setIpaddr(request.getRemoteAddr()); // 사용자 IP 주소 저장
		boardVO.setReRef(num); // 주글일 경우 글그룹번호는 글번호와 동일함
		boardVO.setReLev(0); // 주글일 경우 들여쓰기 레벨은 0
		boardVO.setReSeq(0); // 주글일 경우 글그룹 안에서의 순번은 0

		// boardService.register(boardVO);
		boardService.registerBoardAndAttaches(boardVO, attachList); // 주글 한개와 첨부파일 여러개 등록 - 트랜잭션 처리
		
		//글번호에 해당하는 글 한개와 첨부파일 리스트 가져오기
		BoardVO dbBoardVO = boardService.getBoardAndAttaches(num);
	
		return new ResponseEntity<BoardVO>(dbBoardVO, HttpStatus.OK);
	}

	@PostMapping(value = "/boards/reply")
	public void createReply() {

	}
}
