package com.review;

/*
 * 상품리뷰보기
 * 리뷰상세
 */
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

@WebServlet("/review/*")
public class ReviewServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		// uri에 따른 작업 구분
		if (uri.indexOf("review-list.do") != -1) {
			list(req, resp);	//list(req, resp);
	    } else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("reviewarticle.do") != -1) {
			review(req, resp);
		} else if (uri.indexOf("myreviewlist.do") != -1) {
			myreviewlist(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}
	

	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		ReviewDAO dao = new ReviewDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 전체 데이터 개수
			int reviewCount;
			reviewCount = dao.reviewCount();
			
			
			//전체 페이지 수 
			int rows = 5;
			int total_page = util.pageCount(rows, reviewCount);
			if (current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			//게시물 가져오기 
			
			List<ReviewDTO> list = null;
			list = dao.listReview(start, end);
			
			// 리스트 글번호 만들기
			int listNum, n = 0;
			for (ReviewDTO dto : list) {
				listNum = reviewCount - (start + n - 1);
				dto.setListNum(listNum);
				n++;
			}	
			
			// 페이징 처리
			String listUrl = cp + "/review/review-list.do";
			String articleUrl = cp + "/review/reviewarticle.do?page=" + current_page;
				
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("reviewCount", reviewCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/review/reviewlist.jsp");
	}

	private void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		
		int odNum = Integer.parseInt(req.getParameter("odNum"));
		String cdNum = req.getParameter("cdNum");
	
		ReviewDAO dao = new ReviewDAO();
		ReviewDTO dto = dao.readClothes(cdNum);
		
		String clothName = dto.getClothName();
		String color = dto.getColor();       // 가져오기 
		String sizes = dto.getSizes();
		String userId = dto.getUserId();
		String userName = dto.getUserName();
		String r_reg_date = dto.getR_reg_date();
		int listNum = dto.getListNum();
		
		req.setAttribute("mode", "write");
		req.setAttribute("odNum", odNum);    //writeForm에 보내기 
		req.setAttribute("clothName", clothName);
		req.setAttribute("color", color);
		req.setAttribute("sizes", sizes);
		req.setAttribute("userId", userId);
		req.setAttribute("userName", userName);
		req.setAttribute("r_reg_date", r_reg_date);
		req.setAttribute("listNum", listNum);
		
		forward(req, resp, "/WEB-INF/views/review/writeForm.jsp");
	}

	private void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		ReviewDAO dao = new ReviewDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/review/review-list.do");
			return;
		}
		
		try {
			ReviewDTO dto = new ReviewDTO();

			// userId는 세션에 저장된 정보
			dto.setUserId(info.getUserId());
			// 파라미터
			// odNum
			dto.setOdNum(Integer.parseInt(req.getParameter("odNum")));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			dto.setUserId("userId");
			dto.setUserName("userName");
			dto.setR_reg_date("r_reg_date");
			dto.setListNum(Integer.parseInt(req.getParameter("listNum")));
			
			dao.insertReview(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/review/review-list.do");
	}

	private void review(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		ReviewDAO dao = new ReviewDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
	
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
	
		int page = Integer.parseInt(req.getParameter("page"));
		String query = "page=" + page;

		try {
			int rNum = Integer.parseInt(req.getParameter("rNum"));
			
			// 게시물 가져오기
			ReviewDTO dto = dao.readReview(rNum);
			if (dto == null) { // 게시물이 없으면 다시 리스트로
				forward(req, resp, "/WEB-INF/views/member/login.jsp");
				return;
			}
			dto.setContent(util.htmlSymbols(dto.getContent()));

			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			
			// 포워딩
			forward(req, resp, "/WEB-INF/views/review/reviewarticle.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/review/review-list.do?" + query);
	}		
	
	private void myreviewlist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		ReviewDAO dao = new ReviewDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String userId = info.getUserId();
	
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 전체 데이터 개수
			int reviewCount;
			reviewCount = dao.reviewCount(userId);
			
			
			//전체 페이지 수 
			int rows = 5;
			int total_page = util.pageCount(rows, reviewCount);
			if (current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			//게시물 가져오기
			
			
			List<ReviewDTO> list = null;
			list = dao.listReview(start, end, userId);
			
			// 리스트 글번호 만들기
			int listNum, n = 0;
			for (ReviewDTO dto : list) {
				listNum = reviewCount - (start + n - 1);
				dto.setListNum(listNum);
				n++;
			}	
			
			// 페이징 처리
			String listUrl = cp + "/review/myreviewlist.do";
			String articleUrl = cp + "/review/reviewarticle.do?page=" + current_page;
				
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("reviewCount", reviewCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/review/myreviewlist.jsp");
		
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 삭제
		ReviewDAO dao = new ReviewDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			int rNum = Integer.parseInt(req.getParameter("rNum"));
			
			dao.deleteReview(rNum, info.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/review/review-list.do?" + query);
	}
}