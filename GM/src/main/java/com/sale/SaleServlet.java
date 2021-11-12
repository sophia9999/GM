package com.sale;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

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
		
		if(uri.indexOf("sale.do") != -1) {
			list(req, resp);
		}else if(uri.indexOf("clothes.do") != -1) {
			saleCloth(req, resp);
		}else if(uri.indexOf("salesdetails.do") != -1) {
			salesdetail(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		forward(req, resp, "/WEB-INF/views/sale/list.jsp");
	}
	
	protected void saleCloth(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			
			List<SalesDTO> list = new SalesDAO().readAlllist();
			
			
			JSONObject job = new JSONObject();				
			job.put("data", list);
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	protected void salesdetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String date = req.getParameter("date");
			
			List<SalesDTO> list = new SalesDAO().readdatesale(date);
			
			
			JSONObject job = new JSONObject();				
			job.put("data", list);
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
		
}
