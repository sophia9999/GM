package com.sale;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

/*
 * 판매내역
 * 
 */

@WebServlet("/sale/*")
public class SaleServlet extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		} else if(! info.getUserId().equals("admin")) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		forward(req, resp, "/WEB-INF/views/sale/list.jsp");
	}
	
		
}
