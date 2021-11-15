package com.order;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
 * 주문list
 * 주문상세
 * 배송등록
 */

@WebServlet("/order/*")
public class OrderServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인 되지 않은 경우
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		
		if(uri.indexOf("order.do")!=-1) {
			orderlist(req, resp);
		} else if(uri.indexOf("orderdetail.do")!=-1) {
			orderdetail(req, resp);
		} else if(uri.indexOf("delevely.do")!=-1) {
			delevelywrite(req, resp);
		} else if(uri.indexOf("delevely_ok.do")!=-1) {
			delevelysubmit(req, resp);
		} else if(uri.indexOf("delevely_update.do")!=-1) {
			delevelyUpdate(req, resp);
		}
	}
	
	protected void orderlist(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//주문목록
		OrderDAO dao = new OrderDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();

		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null ) {
				current_page = Integer.parseInt(page);
			}
			// 전체 데이터 개수
			int dataCount = dao.dataCount();

			// 전체 페이지 수
			
			int rows = 10;
			int total_page = util.pageCount(rows, dataCount);
			if (current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시물 가져올 시작과 끝위치
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 게시물 가져오기
			List<OrderDTO> list = dao.ordeList(start, end);

			// 페이징 처리
			String listUrl = cp + "/order/order.do";
			String articleUrl = cp + "/order/orderdetail.do?page=" + current_page;
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 orderlist.jsp에 전달할 값
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
			
			
			} catch (Exception e) {
				e.printStackTrace();	
			}
			
		
		forward(req, resp, "/WEB-INF/views/order/orderlist.jsp");
	}
	
	protected void orderdetail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 포토 서블릿 참고
		OrderDAO dao = new OrderDAO();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			int odNum = Integer.parseInt(req.getParameter("odNum"));
			
			OrderDTO dto = dao.readOrder(odNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/order/order.do?page=" + page);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("clothName", URLEncoder.encode(dto.getClothName(), "UTF-8"));
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/order/orderarticle.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/order/order.do?page=" + page);

	}
	
	// 배송 등록 폼
	protected void delevelywrite(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		OrderDAO dao = new OrderDAO();
		
		int odNum = Integer.parseInt(req.getParameter("odNum"));
		String clothName = req.getParameter("clothName");
		
		OrderDTO dto = dao.readOrder(odNum);
		
		clothName = URLDecoder.decode(clothName, "UTF-8");
		
		req.setAttribute("dto", dto);
		req.setAttribute("odNum", odNum);
		req.setAttribute("clothName", clothName);
		forward(req, resp, "/WEB-INF/views/order/delevelyForm.jsp");
	}
	
	// 배송 등록중 
	protected void delevelysubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		OrderDAO dao = new OrderDAO();
		
		
		String cp = req.getContextPath();
		if( req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/order/order.do");
			return;
		}
		
		try {
			OrderDTO dto = new OrderDTO();
						
			dto.setDeNum(req.getParameter("deNum"));
			dto.setState(req.getParameter("state"));
			dto.setSendDate(req.getParameter("senddate"));
			dto.setArriveDate(req.getParameter("arrivedate"));
			dto.setOdNum(req.getParameter("odNum"));
			
			dao.insertDelevery(dto);
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		resp.sendRedirect(cp+"/order/order.do");
	}
	
	protected void delevelyUpdate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		OrderDAO dao = new OrderDAO();
		
		
		String cp = req.getContextPath();
		if( req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/order/order.do");
			return;
		}
		
		try {
			OrderDTO dto = new OrderDTO();
						
			dto.setDeNum(req.getParameter("deNum"));
			dto.setState(req.getParameter("state"));
			dto.setSendDate(req.getParameter("senddate"));
			dto.setArriveDate(req.getParameter("arrivedate"));
			dto.setOdNum(req.getParameter("odNum"));
			
			dao.updateDelevery(dto);
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		resp.sendRedirect(cp+"/order/order.do");
	}
}

