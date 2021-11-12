package com.mymenu;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

/*
 * 주문내역조회
 * 주문상세 보기
 * 리뷰 작성
 */
@WebServlet("/mymenu/*")
public class MymenuServlet extends MyServlet{

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 주문 내역
		 * 주문 내역 상세보기
		 * 리뷰 쓰기 수정? 
		 */
		
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		// uri에 따른 작업 구분
		if(uri.indexOf("myorder.do")!=-1) {
			orderList(req, resp);
		}else if(uri.indexOf("mymenu.do")!=-1) {
			mymenuList(req, resp);
		} else if(uri.indexOf("myorder_detail.do")!=-1) {
			orderDetail(req, resp);
		} else if(uri.indexOf("myorder_update.do")!=-1) {
			updateOrder(req, resp);
		} else if(uri.indexOf("myorder_update_ok.do")!=-1) {
			updateOrderOk(req, resp);
		} else if(uri.indexOf("myorder_delete.do")!=-1) {
			deleteMyorder(req, resp);
		} 
		
	}
	
	private void mymenuList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String path = "/WEB-INF/views/mymenu/mymenu.jsp";

		forward(req, resp, path);
	}
	
	private void orderList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		MyorderDAO dao = new MyorderDAO();
		String cp = req.getContextPath();
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String userId = info.getUserId();
		MyUtil util = new MyUtil();
		
		try {
			String page = req.getParameter("page");
			int c_page = 1;
			if(page != null) {
				c_page = Integer.parseInt(page);
			}
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			if(req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword,"utf-8");
			}
			
			int dataCount;
			if(keyword.length()==0) {
				dataCount = dao.dataCount(userId);
			}else {
				dataCount = dao.datacount(userId, condition, keyword);
			}
			
			int rows = 2;
			int total_page = util.pageCount(rows, dataCount);
			if(c_page>total_page) {
				c_page = total_page;
			}
			
			int start = (c_page-1)*rows + 1;
			int end = c_page * rows;
			
			List<MyorderDTO>list = null;
			if(keyword.length()==0) {
				list = dao.readMyOrderList(userId, start, end);
			}else {
				list = dao.readMyOrderList(userId, start, end, condition, keyword);
			}
			
			int listN,n=0;
			for(MyorderDTO dto : list) {
				listN = dataCount - (start+n-1);
				dto.setListNum(listN);
				n++;
			}
			
			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징 처리
			String listUrl = cp + "/mymenu/myorder.do";
			String articleUrl = cp + "/mymenu/myorder_detail.do?page=" + c_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(c_page, total_page, listUrl);

			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", c_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/mymenu/orderlist.jsp";
		forward(req, resp, path);
	}
	
	private void orderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int odNum = Integer.parseInt(req.getParameter("odNum"));
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		DecimalFormat df = new DecimalFormat("###,###,###");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		
		try {
			MyorderDTO dto = new MyorderDAO().readMyOrder(odNum);
			String cp = req.getContextPath();
		
			String page = req.getParameter("page");
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			
			if(req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword,"utf-8");
			}
			
			String query = "?page="+page;
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			if(dto == null) {
				resp.sendRedirect(cp+ "/mymenu/myorder.do");
			}
			
			String price = df.format(dto.getPrice());
			req.setAttribute("price", price);
			req.setAttribute("query", query);
			req.setAttribute("dto", dto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/mymenu/orderarticle.jsp";
		forward(req, resp, path);
	}
	
	private void updateOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int odNum = Integer.parseInt(req.getParameter("odNum"));
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		DecimalFormat df = new DecimalFormat("###,###,###");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		
		try {
			MyorderDTO dto = new MyorderDAO().readMyOrder(odNum);
			String cp = req.getContextPath();
		
			String page = req.getParameter("page");
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			
			if(req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword,"utf-8");
			}
			
			String query = "?page="+page;
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			if(dto == null) {
				resp.sendRedirect(cp+ "/mymenu/order.do");
			}
		

			
			String price = df.format(dto.getPrice());
			req.setAttribute("price", price);
			req.setAttribute("query", query);
			req.setAttribute("dto", dto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		String path = "/WEB-INF/views/mymenu/orderupdate.jsp";
		forward(req, resp, path);
	}
	
	private void updateOrderOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cp = req.getContextPath();
		
		try {
			MyorderDTO dto = new MyorderDTO();
			dto.setRecipient(req.getParameter("recipient"));
			dto.setdAddress(req.getParameter("dAddress"));
			dto.setdAddress_detail(req.getParameter("dAddress_detail"));
			dto.setdCode(Integer.parseInt(req.getParameter("dCode")));
			dto.setRecPhoneNum(req.getParameter("recPhoneNum"));			
			dto.setoNum(Integer.parseInt(req.getParameter("oNum")));
			
			new MyorderDAO().updatedLocation(dto);
			
			resp.sendRedirect(cp+ "/mymenu/myorder.do");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void deleteMyorder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		String cp = req.getContextPath();
		
		try {
			int odNum = Integer.parseInt(req.getParameter("odNum"));
			MyorderDTO dto = new MyorderDAO().readMyOrder(odNum);
			
		
		
			
				new MyorderDAO().deleteMyorder(dto);
			
			resp.sendRedirect(cp+ "/mymenu/myorder.do");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
