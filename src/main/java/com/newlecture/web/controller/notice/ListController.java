package com.newlecture.web.controller.notice;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;


// NoticeListController을 사용하기위해 컴파일한테 이름이 /notice/list라고 지정하게 한 것
// /notice/list이 목록페이지를 요청할 때 사용될 수 있는 이름임!

// 컨트롤러는 사용자의 입력과 출력 즉, 사용자와 상호작용하는 것에 포커스를 맞추고 있음
@WebServlet("/notice/list")
public class ListController extends HttpServlet{

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
		List<NoticeView> list = service.getNoticePubList(field,query,page);
		int count = service.getNoticeCount(field,query);
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		//forward는 페이지의 결괏값을 가지고 다른 페이지로 이동
		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(request,response);
		
		
		
	}
	
}
