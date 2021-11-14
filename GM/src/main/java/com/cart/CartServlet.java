package com.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.MemberDAO;
import com.member.MemberDTO;
import com.member.SessionInfo;
import com.shop.ShopDAO;
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
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		if(uri.indexOf("cart.do")!=-1) {
			cartList(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			deleteCart(req, resp);
		} else if (uri.indexOf("cartChange.do") != -1) {
			cartAmountChange(req, resp);	
		} else if (uri.indexOf("deleteCartList.do") != -1) {
			deleteCartList(req, resp);	
		} else if (uri.indexOf("orderList.do") != -1) {
			orderList(req, resp);		
		} else if (uri.indexOf("order_ok.do") != -1) {
			orderSubmit(req, resp);
		} else if (uri.indexOf("buyNow.do") != -1) {
			buyNow(req, resp);
		} /*
		else if (uri.indexOf("order.do") != -1) {
			order(req, resp);
		}*/
	}
	
	private void cartList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		CartDAO dao = new CartDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");		
		
		try {
			// String userId = "aaa"; // 임시아이디
			String userId = info.getUserId();			
			
			int cartCount;
			int total_price;
			int fee;
			int disCount;
			
			cartCount = dao.cartCount(userId);
			total_price = dao.totalPrice(userId);
			disCount = dao.disCount(userId);
			fee = total_price >= 70000 ? 0 : 2500;
			
			List<CartDTO> cartList = null;
			cartList = dao.cartList(userId);
				
			req.setAttribute("fee", fee);
			req.setAttribute("cartCount", cartCount);
			req.setAttribute("total_price", total_price);
			req.setAttribute("disCount", disCount);
			req.setAttribute("cartList", cartList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/cart/cart.jsp";
		forward(req, resp, path);
	}
	
	// 주문
	private void orderList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String userId = info.getUserId();
		MemberDTO dto = new MemberDAO().findMember(userId);
		CartDAO dao = new CartDAO();	
		
		try {
			
			int orderCount;
			int total_price;
			int fee;
			int disCount;
			
			String[] nn = req.getParameterValues("box");
			int box[] = null;
			box = new int[nn.length];
			for(int i = 0; i < nn.length; i++) {
				box[i] = Integer.parseInt(nn[i]);
			}
			
			List<CartDTO> orderList = null;
			orderList = dao.orderList(box);
			
			orderCount = dao.orderCount(box);
			total_price = dao.orderPrice(box);
			disCount = dao.disCount(box);
			fee = total_price >= 70000 ? 0 : 2500;
						
			if (orderCount == 0) {
				forward(req, resp, "/WEB-INF/views/cart.jsp");
				return;
			}
			
			req.setAttribute("fee", fee);
			req.setAttribute("orderCount", orderCount);
			req.setAttribute("total_price", total_price);
			req.setAttribute("orderList", orderList);
			req.setAttribute("disCount", disCount);
			req.setAttribute("dto", dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/cart/order.jsp";
		forward(req, resp, path);
	}
	
	private void buyNow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		ShopDAO dao = new ShopDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info= (SessionInfo)session.getAttribute("member");		
		session.setAttribute("member", info);
		
		String cp = req.getContextPath();
				
		try {
			if ( info == null ) {
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			
			int amount = Integer.parseInt(req.getParameter("amount"));
			String userId = info.getUserId();
			int cdnum = Integer.parseInt(req.getParameter("cdnum"));
			
			dao.addCart(cdnum, amount, userId);
			
			MemberDTO dto = new MemberDAO().findMember(userId);
			
			
			req.setAttribute("dto", dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		resp.sendRedirect(cp + "/cart/cart.do");
	}
	/*
	private void order(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String userId = info.getUserId();
		MemberDTO dto = new MemberDAO().findMember(userId);
		CartDAO dao = new CartDAO();
		
		try {			
			int orderCount;
			int total_price;
			int fee;

			int cartNum = Integer.parseInt(req.getParameter("cartNum"));
			
			dao.orderList(cartNum);
			
			List<CartDTO> orderList = null;
			
			fee = total_price >= 70000 ? 0 : 2500;
						
			if (orderCount == 0) {
				forward(req, resp, "/WEB-INF/views/cart.jsp");
				return;
			}
			
			req.setAttribute("fee", fee);
			req.setAttribute("orderCount", orderCount);
			req.setAttribute("total_price", total_price);
			req.setAttribute("orderList", orderList);
			req.setAttribute("dto", dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/cart/order.jsp";
		forward(req, resp, path);
	}
	*/
	private void orderSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String userId = info.getUserId();
		CartDAO dao = new CartDAO();		
				
		try {
			CartDTO dto = new CartDTO();			
			
			dto.setUserId(userId);
			dto.setTotal(Integer.parseInt(req.getParameter("total")));
			dto.setFee(Integer.parseInt(req.getParameter("fee")));
			dto.setRequest(req.getParameter("request"));
			dto.setTel(req.getParameter("tel1")+"-"+req.getParameter("tel2")+"-"+req.getParameter("tel3"));
			dto.setCartNum(Integer.parseInt(req.getParameter("cartNum")));
			
			dao.insertOrder(dto);
			
			String[] cdNum = req.getParameterValues("cdNum");
			String[] amount = req.getParameterValues("amount");
			
			
			for(int i=0; i < cdNum.length; i++) {
				dao.insertOrderDetail(cdNum[i], amount[i]);
			}
			
			String[] nn = req.getParameterValues("cartNum");
			int box[] = null;
			box = new int[nn.length];
			for(int i = 0; i < nn.length; i++) {
				box[i] = Integer.parseInt(nn[i]);
			}
			
			dao.deleteCartList(box);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		String path = "/WEB-INF/views/cart/orderComplete.jsp";
		forward(req, resp, path);
	}
	
	// 삭제
	private void deleteCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartDAO dao = new CartDAO();
		
		String cp = req.getContextPath();
		try {
						
			int cartNum = Integer.parseInt(req.getParameter("cartNum"));
			dao.deleteCart(cartNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/cart/cart.do");
	}
	
	private void deleteCartList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		try {
			String[] nn = req.getParameterValues("box");
			int box[] = null;
			box = new int[nn.length];
			for(int i = 0; i < nn.length; i++) {
				box[i] = Integer.parseInt(nn[i]);
			}
			
			CartDAO dao = new CartDAO();
			
			dao.deleteCartList(box);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/cart/cart.do");
	}
	
	// 수량 변경
	private void cartAmountChange(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartDAO dao = new CartDAO();
		
		String cp = req.getContextPath();
		
		try {
			CartDTO dto = new CartDTO();
			
			dto.setCartNum(Integer.parseInt(req.getParameter("cartNum")));
			dto.setAmount(Integer.parseInt(req.getParameter("amount")));
			
			dao.updateAmount(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/cart/cart.do");
	}
}
