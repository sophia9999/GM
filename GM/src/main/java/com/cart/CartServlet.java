package com.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

/*
 * 장바구니
 * 주문하기
 */

@WebServlet("/cart/*")
public class CartServlet extends MyServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("cart.do")!=-1) {
			cart(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			deleteCart(req, resp);
		} else if (uri.indexOf("change.do") != -1) {
			amountChange(req, resp);
		}
		
	}
	
	private void cart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		CartDAO dao = new CartDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");		
		
		try {
			// String userId = "aaa"; // 임시아이디
			String userId = info.getUserId();			
			
			int dataCount;
			int total_price;
			int fee;
			
			dataCount = dao.dataCount(userId);
			total_price = dao.total_price(userId);
			fee = total_price >= 50000 ? 0 : 2500;
			
			List<CartDTO> list = null;
			list = dao.listCart(userId);
				
			req.setAttribute("fee", fee);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_price", total_price);
			req.setAttribute("list", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/cart/cart.jsp";
		forward(req, resp, path);
	}
	
	// 삭제
	private void deleteCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartDAO dao = new CartDAO();
		
		String cp = req.getContextPath();
		try {
			
//			String[] cartNum = req.getParameterValues("box");
//			
//			for(int i=0; i<cartNum.length; i++) {
//				dao.deleteCart(cartNum[i]);
//			}
			
			int cartNum = Integer.parseInt(req.getParameter("cartNum"));
			dao.deleteCart(cartNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/cart/cart.do");
	}
	
	// 수량 변경
	private void amountChange(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartDAO dao = new CartDAO();
		
		String cp = req.getContextPath();
		
		try {
			CartDTO dto = new CartDTO();
			
			dto.setAmount(Integer.parseInt(req.getParameter("amount")));
			dto.setCartNum(Integer.parseInt(req.getParameter("cartNum")));
			
			dao.updateAmount(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/cart/cart.do");
	}
	
}
