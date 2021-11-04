package com.shop;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

/*
 * 상품페이지
 * 상품디테일페이지
 * 관리자-상품페이지
 * 상품글쓰기폼
 * 상품디테일페이지
 * 
 */
@WebServlet("/shop/*")
public class ShopServlet extends MyServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		if(uri.indexOf("garment.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("garment-write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("garment-article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("garment-update.do") != -1) {
			update(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
		
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/shop/listpage.jsp";
		
		forward(req, resp, path);
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
