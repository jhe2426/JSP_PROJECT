package com.newlecture.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@MultipartConfig(
		// 파일을 저장하는 위치(디스크를 사용하는 것)
		// fileSizeThreshold =1024*1024 전송하는 데이터가 1메가바이트가 넘어가면 디스크를 쓰자 메모리를 사용하지 말고
		// location = "/tmp", 
		fileSizeThreshold =1024*1024, // 1메가바이트
		// 사용자가 서버에 보낼 수 있는 파일의 사이지를 제한하는 것
		maxFileSize = 1024*1024*50, //하나의 파일의 최대 사이즈임(50메가바이트)
		maxRequestSize = 1024*1024*50*5 // 한 게시물에서의 파일이 여러개 일 경우 전체 파일의 최대 사이즈임(250메가바이트)
)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp").forward(request,response);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		System.out.print("title");
		System.out.print(title);
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");
		
		Collection<Part>parts = request.getParts();
		StringBuilder builder = new StringBuilder();
		for(Part p : parts) {
			if(!p.getName().equals("file")) continue; // contnue는 이 반복문을 빠져 나갈 수 있도록 하는 예약어임
			if(p.getSize() == 0) continue;
			Part filePart = p;
			//getSubmittedFileName()파일명을 알 수 있게 해주는 메서드(파일명을 가지고있는)
			String fileName = filePart.getSubmittedFileName();
			builder.append(fileName);
			builder.append(",");
			InputStream fis = filePart.getInputStream();
			
			// location에는 절대 경로 밖에 사용을 하지 못하므로
			// 운영체제에 따라 적용이 안 될 수있으므로
			/* /upload/ -> c:\\temp\\uploads 이런식으로 물리 경로로 */
			String realPath = request.getServletContext().getRealPath("/upload");
			System.out.println(realPath);
			// file 객체에 경로를 담는 이유는 파일을 저장하는 폴더가 존재하는지를 알아 보기위해
			// File 객체의 exists()메서드를 사용하여 확인 후 존재하지 않으면 폴더를 생성시켜주기위해 사용한 것
			File path = new File(realPath); 
			if(!path.exists())
				path.mkdirs(); // mkdir()는 맨 마지막 하위 폴더만 만들어 주는데
								// mkdirs()는 상위 폴더, 하위 폴더 전부 다를 만들어줌
			//File.separator 현재 시스템의 경로 지정 방법, 경로 구분 방법을 string으로 제공하고 있는 거
			String filePath = realPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath);
			
			// InputStream객체의 read()메서드는 1바이트만 읽고 더 이상 읽을 데이터가 없으면 -1을 반환함 
			// read(btye)는 내가 지정한 btye단위로 읽는 거 읽을 게 있으면 리턴값이 읽어온 바이트의 크기를 리턴
			// 읽을 게 없으면 -1을 리턴
			// 그래서 size에는 읽어온 바이트의 크기나 -1의 값을 가지게 될 것
			byte[] buf = new byte[1024]; // 1키로바이트
			int size = 0;
			while((size = fis.read(buf)) != -1)
				//write(byte[] b)는 버퍼에 있는 배열 만큼 다 써야하지만
				//write(byte[]b, int off, int len)을 사용하면 off에서 len까지만 사용하도록 범위를 지정할 수 있음
				fos.write(buf,0,size);
			fos.close();
			fis.close();
		}
		// 파일 이름의 마지막 부분에 ,를 빼주는 작업
		builder.delete(builder.length()-1, builder.length());
		
		
		boolean pub = false;
		if(isOpen != null)
			pub = true;
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("newlec");
		notice.setFiles(builder.toString());
		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice);
		
		// 경로를 지정하지 않으면 reg컨트롤이 가지고있는 /admin/board/notice/reg인데
		// /admin/board/notice/가 자동적으로 붙어
		///admin/board/notice/list로 이동하게 되어있음
		response.sendRedirect("list");
		
		
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		out.printf("title : %s<br>", title);
//		out.printf("content : %s<br>", content);
//		out.printf("open : %s<br>", isOpen);
	}
}
