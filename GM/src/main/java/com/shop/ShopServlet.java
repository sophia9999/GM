package com.shop;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

/*
 * 상품페이지
 * 상품디테일페이지
 * 관리자-상품페이지
 * 상품글쓰기폼
 * 상품디테일페이지
 * 
 */
@MultipartConfig
@WebServlet("/shop/*")
public class ShopServlet extends MyUploadServlet{

	private static final long serialVersionUID = 1L;

	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		HttpSession session = req.getSession();
		
		// 이미지를 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "photo";
		
		if(uri.indexOf("garment.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("garment-write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("garment-writeSubmit.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("garment-article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("garment-update.do") != -1) {
			update(req, resp);
		} else if (uri.indexOf("garment-updateSubmit.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("garment-detail.do") != -1) {
			addDetail(req, resp);
		} else if (uri.indexOf("garment-sizeList.do") != -1) {
			articleSizeList(req, resp);
		} else if (uri.indexOf("garment-detailwrite.do") != -1) {
			detailWrite(req, resp);
		} else if (uri.indexOf("garment-detailupdate.do") != -1) {
			detailUpdate(req, resp);
		} else if (uri.indexOf("garment-detailupdateSubmit.do") != -1) {
			detailUpdateSubmit(req, resp);
		}
		
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/shop/listpage.jsp";
		ShopDAO dao = new ShopDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info= (SessionInfo)session.getAttribute("member");
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page); 
			}
			
			int dataCount = dao.dataCount(1);			
			// 보여질 데이터 개수 관리자일시 모든 상품
			if(info != null) {
				if(info.getUserId().equals("admin")) {
					dataCount = dao.dataCount();
				} else {
					// 관리자 아닐 시에는 display 에 1로 설정한 것들만 보이도록
					dataCount = dao.dataCount(1);
				}				
			}
			
			// 전체페이지 수
			
			int rows = 9;
			int total_page = util.pageCount(rows, dataCount);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 게시물 가져오기
			
			List<ShopDTO> list = dao.listShop(start, end, 1);
			
			if(info != null ) {
				if(info.getUserId().equals("admin")) {
					list = dao.listShop(start, end);				
				} else { // 관리자 계정이 아닐 시 리스트에서 보이는 항목들
					list = dao.listShop(start, end, 1);
				}				
			}
			
			// 페이징 처리
			String listUrl = cp + "/shop/garment.do";
			String articleUrl = cp + "/shop/garment-article.do?page=" + current_page;
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, path);
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/shop/writeForm.jsp";
		
		HttpSession session = req.getSession();
		SessionInfo info= (SessionInfo)session.getAttribute("member");
		
		req.setAttribute("mode", "write");
		session.setAttribute("member", info);
		
		forward(req, resp, path);
	}
	
	private void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String cp = req.getContextPath();
		ShopDAO dao = new ShopDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info= (SessionInfo)session.getAttribute("member");
		
		// GET방식이면 리스트로
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/shop/garment.do");
			return;
		}
		
		try {
			
			if (info == null) { // 로그인 되지 않은 경우
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			
			ShopDTO dto = new ShopDTO();
			dto.setUserId(info.getUserId());
			
			// TYPE 테이블에 따른 type 설정
			String type = req.getParameter("type");
			int typeNo = 0;
			if( type.equals("top")) {
				typeNo = 1;
			} else if (type.equals("bottom")) {
				typeNo = 2;
			} else if (type.equals("outer")) {
				typeNo = 3;
			} else if (type.equals("etc")) {
				typeNo = 4;
			}
			dto.setTnum(typeNo);
			dto.setClothname(req.getParameter("clothname"));
			dto.setDisplayYN(Integer.parseInt( req.getParameter("displayYN") ) );
			dto.setCcoment(req.getParameter("content"));
			dto.setUnitPrice(Integer.parseInt(req.getParameter("unitprice")));
			dto.setPrice(Integer.parseInt(req.getParameter("price")));
			dto.setDiscount(Integer.parseInt(req.getParameter("discount")));
			
			String [] seasons = req.getParameterValues("season");
			String s = "";
			if(seasons != null) {
				for(String ss : seasons) {
					s += ss + ",";
				}
				s = s.substring(0, s.lastIndexOf(","));
			}
			dto.setSeason(s);
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
			dao.insertGerment(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/shop/garment.do");
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//  게시물 보기
		ShopDAO dao = new ShopDAO();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		int prePage = 1;
		
		try {
			
			if(req.getParameter("prePage") != null) {
				prePage = Integer.parseInt(req.getParameter("prePage"));
			}
			
			int num = Integer.parseInt(req.getParameter("num"));
			
			ShopDTO dto = dao.readDetail(num);
			if(dto==null) {
				resp.sendRedirect(cp + "/shop/garment.do?page="+page);
				return;
			}
			dto.setCcoment(dto.getCcoment().replaceAll("\n", "<br>"));
			
			List<ShopDTO> listFile = dao.listPhotoFile(num);
			List<ShopDTO> colorList = dao.colorList(num);
			
			req.setAttribute("dto", dto);
			req.setAttribute("listFile", listFile);
			req.setAttribute("page", page);
			req.setAttribute("prePage", prePage);
			req.setAttribute("colorList", colorList);
			
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			
			req.setAttribute("price", nf.format(dto.getPrice()));
			
			forward(req, resp, "/WEB-INF/views/shop/detailpage.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/shop/garment.do?page="+page);
	}
	
	// 사이즈 정보보내기 AJAX - HTML
	protected void articleSizeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShopDAO dao = new ShopDAO();
		List<ShopDTO> sizeList = null;

		try {
			int ccnum = Integer.parseInt(req.getParameter("ccnum"));
			sizeList = dao.sizeList(ccnum);
			
			req.setAttribute("sizeList", sizeList);
			forward(req, resp, "/WEB-INF/views/shop/sizeList.jsp");
			return;

		} catch (NumberFormatException e) {
			e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(405);
	}
	
	protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정폼
		ShopDAO dao = new ShopDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");
		
		try {
			int cnum = Integer.parseInt(req.getParameter("num"));
			ShopDTO dto = dao.readDetail(cnum);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/shop/garment.do?page="+page);
				return;
			}
			
			if (!dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/shop/garment.do?page="+page);
				return;
			}
			
			List<ShopDTO> listFile = dao.listPhotoFile(cnum);
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("listFile", listFile);
			
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/shop/writeForm.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/shop/garment.do?page="+page);

	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 업데이트 완료
		ShopDAO dao = new ShopDAO();
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/sphoto/list.do");
			return;
		}
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String page = req.getParameter("page");

		try {
			ShopDTO dto = new ShopDTO();
			dto.setCnum(Integer.parseInt(req.getParameter("num")));

			// TYPE 테이블에 따른 type 설정
			String type = req.getParameter("type");
			int typeNo = 0;
			if( type.equals("top")) {
				typeNo = 1;
			} else if (type.equals("bottom")) {
				typeNo = 2;
			} else if (type.equals("outer")) {
				typeNo = 3;
			} else if (type.equals("etc")) {
				typeNo = 4;
			}
			dto.setTnum(typeNo);
			dto.setClothname(req.getParameter("clothname"));
			dto.setDisplayYN(Integer.parseInt( req.getParameter("displayYN") ) );
			dto.setCcoment(req.getParameter("content"));
			dto.setUnitPrice(Integer.parseInt(req.getParameter("unitprice")));
			dto.setPrice(Integer.parseInt(req.getParameter("price")));
			dto.setDiscount(Integer.parseInt(req.getParameter("discount")));
			dto.setUserId(info.getUserId());
			String [] seasons = req.getParameterValues("season");
			String s = "";
			if(seasons != null) {
				for(String ss : seasons) {
					s += ss + ",";
				}
				s = s.substring(0, s.lastIndexOf(","));
			}
			dto.setSeason(s);
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
			dao.updateGarment(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/shop/garment.do?page="+page);

	}
	
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정에서 파일만 삭제
		ShopDAO dao = new ShopDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");
		
		try {
			int cnum = Integer.parseInt(req.getParameter("num"));
			int fileNum = Integer.parseInt(req.getParameter("fileNum"));
			
			ShopDTO dto = dao.readDetail(cnum);
			
			if (dto == null) {
				resp.sendRedirect(cp + "/shop/garment.do?page=" +page);
				return;
			}
			
			if(!info.getUserId().equals(dto.getUserId())) {
				resp.sendRedirect(cp + "/shop/garment.do?page=" +page);
				return;
			}
			List<ShopDTO> listFile = dao.listPhotoFile(cnum);
			
			for(ShopDTO vo : listFile) {
				if(vo.getFileNum() == fileNum) {
					FileManager.doFiledelete(pathname, vo.getImageFilename());
					dao.deletePhotoFile("one", fileNum);
					listFile.remove(vo);
					break;
				}
			}
			req.setAttribute("dto", dto);
			req.setAttribute("listFile", listFile);
			req.setAttribute("page", page);

			req.setAttribute("mode", "update");
			
			forward(req, resp, "WEB-INF/views/shop/writeForm.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/shop/garment.do?page=" +page);

	}

	// 색상/사이즈 관리 페이지
	private void addDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShopDAO dao = new ShopDAO();
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info =(SessionInfo)session.getAttribute("member");

		String cp = req.getContextPath();
		
		int cnum = Integer.parseInt(req.getParameter("num"));
		
		ShopDTO dto = new ShopDTO();
		dto = dao.readDetail(cnum);
		String page = req.getParameter("page");
		
		int prePage = 1;
		try {
			
			if(req.getParameter("prePage") != null) {
				prePage = Integer.parseInt(req.getParameter("prePage"));
			}
			
			if(info == null) {
			resp.sendRedirect(cp + "/shop/garment.do");
			}
			
			int current_page = 1;
			if(page !=null) {
				current_page = Integer.parseInt(page);
			}
			
			// 해당 옷의 취급 색상 개수 (전체데이터개수)
			int dataCount = dao.colorDataCount(cnum);
			
			// 전체 페이지 수
			int rows = 5;
			int total_page = util.pageCount(rows, dataCount);
			if (current_page > total_page) {
				current_page = total_page;
			}

			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 게시물 가져오기
			List<ShopDTO> colorList = dao.colorList(start, end, cnum);
			
			// 페이징 처리
			String listUrl = cp + "/shop/garment-detail.do?num=" + cnum;
			String paging = util.paging(current_page, total_page, listUrl);
						
			req.setAttribute("page", page);
			req.setAttribute("prePage", prePage);
			req.setAttribute("num", cnum);
			req.setAttribute("dto", dto);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("page", current_page);
			req.setAttribute("paging", paging);
			req.setAttribute("list", colorList);
			req.setAttribute("colorList", colorList);
			req.setAttribute("mode", "write");
					
			forward(req, resp, "/WEB-INF/views/shop/writeColor.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/shop/garment-detail.do?num="+cnum+"&page="+page);
	}
	
	protected void detailWrite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShopDAO dao = new ShopDAO();
		
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String color = req.getParameter("color");
		String size = req.getParameter("size");
		int cnum = Integer.parseInt(req.getParameter("cnum"));
		int stock = Integer.parseInt(req.getParameter("stock"));
		// GET방식이면 리스트로
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/shop/garment.do");
			return;
		}
				
		try {
			
			if (info == null) { // 로그인 되지 않은 경우
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			ShopDTO dto = new ShopDTO();
			dto.setCnum(cnum);
			dto.setColor(color);
			dto.setSize(size);
			dto.setStock(stock);
			
			dao.insertDetail(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/shop/garment-detail.do?num="+cnum);
	}

	protected void detailUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShopDAO dao = new ShopDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		// GET방식이면 리스트로
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/shop/garment.do");
			return;
		}
		int cnum = Integer.parseInt(req.getParameter("cnum"));
						
		try {
			if (info == null) { // 로그인 되지 않은 경우
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			
			int cdnum = Integer.parseInt(req.getParameter("cdnum"));
			
			ShopDTO vo = dao.readColorSize(cdnum);
			
			req.setAttribute("mode", "update");
			req.setAttribute("vo", vo);
			
			
			ShopDTO dto = new ShopDTO();
			dto = dao.readDetail(cnum);
			String page = req.getParameter("page");
			
			int current_page = 1;
			if(page !=null) {
				current_page = Integer.parseInt(page);
			}
			
			// 해당 옷의 취급 색상 개수 (전체데이터개수)
			int dataCount = dao.colorDataCount(cnum);
			
			// 전체 페이지 수
			int rows = 5;
			int total_page = util.pageCount(rows, dataCount);
			if (current_page > total_page) {
				current_page = total_page;
			}

			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 게시물 가져오기
			List<ShopDTO> colorList = dao.colorList(start, end, cnum);
			
			// 페이징 처리
			String listUrl = cp + "/shop/garment-detail.do?num=" + cnum;
			String paging = util.paging(current_page, total_page, listUrl);
						
			req.setAttribute("page", page);
			req.setAttribute("num", cnum);
			req.setAttribute("dto", dto);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("page", current_page);
			req.setAttribute("paging", paging);
			req.setAttribute("list", colorList);
			req.setAttribute("colorList", colorList);
			
			forward(req, resp, "/WEB-INF/views/shop/writeColor.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/shop/garment-detail.do?num="+cnum);
	}
	
	protected void detailUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShopDAO dao = new ShopDAO();
		
		String cp = req.getContextPath();
		int cnum = Integer.parseInt(req.getParameter("cnum"));
		int cdnum = Integer.parseInt(req.getParameter("cdnum"));
		try {
			String size = req.getParameter("size");
			String color = req.getParameter("color");
			int stock = Integer.parseInt(req.getParameter("stock"));
			int ccnum = Integer.parseInt(req.getParameter("ccnum"));
			
			ShopDTO dto = new ShopDTO();
			dto.setCnum(cnum);
			dto.setCdnum(cdnum);
			dto.setCcnum(ccnum);
			dto.setSize(size);
			dto.setStock(stock);
			dto.setColor(color);
			
			dao.updateColor(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/shop/garment-detail.do?num="+cnum);
		
	}
}
