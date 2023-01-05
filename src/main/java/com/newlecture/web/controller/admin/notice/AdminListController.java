package com.newlecture.web.controller.admin.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;


// NoticeListController을 사용하기위해 컴파일한테 이름이 /notice/list라고 지정하게 한 것
// /notice/list이 목록페이지를 요청할 때 사용될 수 있는 이름임!

// 컨트롤러는 사용자의 입력과 출력 즉, 사용자와 상호작용하는 것에 포커스를 맞추고 있음
//여기에 있는 경로를 클라이언트에서 입력하면 알아서 톰캣 서버가 찾아서 실행해주게 됩니다.
@WebServlet("/admin/board/notice/list")
public class AdminListController extends HttpServlet{

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// 전달되는 키의 이름은 하나인데 값이 여러개 일 떄 배열로 받고
		// 값이 여러개 이니깐 한개의 값만 받을 수 있는 getParameter가 아닌
		// getParameterValues()로 값들을 받아온다.
		request.setCharacterEncoding("UTF-8");
		String[] openIds = request.getParameterValues("open-id"); // 3, 5, 8
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");
		//System.out.printf("%s \n", cmd);
		String ids_ = request.getParameter("ids");// 한 페이지에 10개의 게시물의 아이디 값(한 문자열로)
		System.out.print("ids_값======"+ids_);
		//trim()는 좌우 문자열의 빈 공백을 제거해주는 기능
		String[] ids = ids_.trim().split(" "); // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
		
		
		NoticeService service = new NoticeService();
		switch(cmd) {
		case "일괄공개":
			for(String openId : openIds)
				System.out.printf("open id : %s\n", openId);
			
			// asList()배열을 컬렉션(List)으로 바꿔주는 메서드
			List<String> oids = Arrays.asList(openIds);
			// 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 - 3, 5, 8
			// 1,2,4,6,7,9,10
			List<String> cids = new ArrayList(Arrays.asList(ids));
			cids.removeAll(oids);
			System.out.println(Arrays.asList(ids));
			System.out.println(oids);
			System.out.println(cids);
			
			// Transaction : 업무적인 단위, 내가 생각하고 있는 수행 단위, 논리적인 단위
			//service.pubNoticeList(opnIds);, service.closeNoticeList(clsIds); 두 개가 오류 없이 한번에 다
			// 실행되어야만 내가 생각하는 수행 단위가 실행 된 것
			// 내가 생각하는 수행 단위가 하나의 명령어 처럼 실행되는 것이 Transaction 처리라고 한다.
			// 그래서 하나의 메서드에 일괄공개와 일괄삭제를 매개변수로 받는 메서드를 생성할 것
			service.pubNoticeAll(oids,cids);
			//service.closeNoticeList(clsIds);
			break;
		case "일괄삭제":
			int[] ids1 = new int[delIds.length];
			for(int i =0; i<delIds.length; i++)
				ids1[i] = Integer.parseInt(delIds[i]);
			int result = service.deleteNoticeAll(ids1);
			break;
		}

		//sendRedirect() 다른 페이지를 요청하는 메서드, 다른 페이지로 이동시키는 메서드.
		//서버쪽에서 그 서버에 있는 다른 페이지를 요청하듯이 list페이지를 요청하는 것
		response.sendRedirect("list");
		
		
		
	}
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// field와 query가 list?f=title&q=a이런식으로 전달 됨
		
		
		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		// 페이지는 숫자 이지만, 옵션 사항(항상 입력되는 값이 아닌 옵션)이므로 선택 되지 않으면 null이 되어야하는데
		// 기본형은 아무 값이 들어가지 않으면 null값이 아닌 0으로 들어가게 되므로
		// String형으로 받는 것
		String page_ = request.getParameter("p");
		// field와 query는 항상 입력되는 값이 아니므로 입력하지 않으면 안에 null값이 들어 갈 수 있게 되므로
		// 기본 값을 설정해두는 것
		String field = "title";
		if(field_ != null && !field_.equals(""))
			field = field_;
		
		String query = "";
		if(query_ != null && !query_.equals(""))
			query = query_;
		
		int page = 1;
		if(page_ != null && !page_.equals(""))
			page = Integer.parseInt(page_);
		
		// Notice의 내용(하나의 게시판)을 목록(전체 의 게시판)으로
		// 출력해야하므로 모든 게시판을 list에 추가하여 게시물을 전부 다 출력할 수 있도록 만들기 위해 
		// list로 만든 것
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field,query,page);
		int count = service.getNoticeCount(field,query);
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		//forward는 페이지의 결괏값을 가지고 다른 페이지로 이동
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(request,response);
		
		
		
	}
	
	
	
}

