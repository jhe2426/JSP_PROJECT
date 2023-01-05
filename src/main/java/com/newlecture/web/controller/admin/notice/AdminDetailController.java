package com.newlecture.web.controller.admin.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@WebServlet("/admin/board/notice/detail")
public class AdminDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int id = Integer.parseInt(request.getParameter("id"));
		
		NoticeService service = new NoticeService();
		Notice notice = service.getNotice(id);		
		request.setAttribute("n", notice);
		
		//redirect는 페이지 전환
		//forward는 페이지의 결괏값을 가지고 다른 페이지로 이동
		// request를 저장 공간으로 사용하여 이 request를 다른 페이지로 이동시켜
		// request저장 공간을 사용할 수 있게 되는 것임
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/detail.jsp").forward(request,response);
		
	}
	
	
}
