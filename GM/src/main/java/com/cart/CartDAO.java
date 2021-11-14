package com.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class CartDAO {
	private Connection conn = DBConn.getConnection();
	
	// 장바구니 상품 개수
	public int cartCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT SUM(amount) FROM cart WHERE userId = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}
	
	public int disCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT sum(discount*amount)\r\n"
					+ "FROM clothes c, color_detail cd, clothes_detail cld, cart\r\n"
					+ "WHERE c.cNum = cd.cNum AND cd.ccNum = cld.ccNum AND cld.cdNum = cart.cdNum \r\n"
					+ "    AND userId = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}
	
	// 주문상품 개수
	public int orderCount(int[] box) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		
		try {
			sql = "SELECT SUM(amount) FROM (SELECT cart_num,userId, fileName, clothName, amount, price, discount, cart_date, sizes, color\r\n"
				+ "FROM clothes c, color_detail cd, clothes_detail cld, cart, (SELECT fileNum, fileName, cNum FROM (\r\n"
				+ "               SELECT fileNum, fileName, cNum,\r\n"
				+ "               ROW_NUMBER() OVER(PARTITION BY cnum ORDER BY fileNum ASC) rank\r\n"
				+ "           FROM clothes_file\r\n"
				+ "          ) WHERE rank = 1) cf\r\n"
				+ "WHERE c.cNum = cf.cNUm AND c.cNum = cd.cNum AND cd.ccNum = cld.ccNum AND cld.cdNum = cart.cdNum)\r\n"
				+ "WHERE cart_num IN (";
			
			for(int i = 0; i < box.length; i++) {
				sql += "?,";
			}
			
			sql = sql.substring(0, sql.length() - 1) + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < box.length; i++) {
				pstmt.setInt(i + 1, box[i]);
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			} 
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}		
		return result;
	}
	
	public int disCount(int[] box) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		
		try {
			sql = "SELECT SUM(discount*amount) FROM (SELECT cart_num,userId, fileName, clothName, amount, price, discount, cart_date, sizes, color\r\n"
				+ "FROM clothes c, color_detail cd, clothes_detail cld, cart, (SELECT fileNum, fileName, cNum FROM (\r\n"
				+ "               SELECT fileNum, fileName, cNum,\r\n"
				+ "               ROW_NUMBER() OVER(PARTITION BY cnum ORDER BY fileNum ASC) rank\r\n"
				+ "           FROM clothes_file\r\n"
				+ "          ) WHERE rank = 1) cf\r\n"
				+ "WHERE c.cNum = cf.cNUm AND c.cNum = cd.cNum AND cd.ccNum = cld.ccNum AND cld.cdNum = cart.cdNum)\r\n"
				+ "WHERE cart_num IN (";
			
			for(int i = 0; i < box.length; i++) {
				sql += "?,";
			}
			
			sql = sql.substring(0, sql.length() - 1) + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < box.length; i++) {
				pstmt.setInt(i + 1, box[i]);
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			} 
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}		
		return result;
	}
	
	// 장바구니 추가
	public int insertCart(CartDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO cart(cart_num, amount, userId, cdnum)"
				+ " VALUES (cart_SEQ.NEXTVAL,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getAmount());
			pstmt.setString(2, dto.getUserId());
			pstmt.setInt(3, dto.getClothDetailNum());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
		return result;
	}	
	
	// 장바구니 리스트
	public List<CartDTO> cartList(String userId) {
		List<CartDTO> list = new ArrayList<CartDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT cart_num,userId, fileName, clothName, amount, price, discount, cart_date, sizes, color, cld.cdNum\r\n"
				+ "FROM clothes c, color_detail cd, clothes_detail cld, cart,(SELECT fileNum, fileName, cNum FROM (\r\n"
				+ "               SELECT fileNum, fileName, cNum,\r\n"
				+ "               ROW_NUMBER() OVER(PARTITION BY cnum ORDER BY fileNum ASC) rank\r\n"
				+ "           FROM clothes_file\r\n"
				+ "          ) WHERE rank = 1) cf \r\n"
				+ "WHERE c.cNum = cf.cNUm AND c.cNum = cd.cNum AND cd.ccNum = cld.ccNum AND cld.cdNum = cart.cdNum\r\n"
				+ "AND userId = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CartDTO dto = new CartDTO();
				
				dto.setCartNum(rs.getInt("cart_num"));
				dto.setUserId(rs.getString("userId"));
				dto.setFileName(rs.getString("fileName"));
				dto.setClothName(rs.getString("clothName"));
				dto.setAmount(rs.getInt("amount"));
				dto.setPrice(rs.getInt("price"));
				dto.setDiscount(rs.getInt("discount"));
				dto.setCart_date(rs.getString("cart_date"));
				dto.setSize(rs.getString("sizes"));
				dto.setColor(rs.getString("color"));
				dto.setClothDetailNum(rs.getInt("cdNum"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return list;
	}
	
	// 주문리스트
	public List<CartDTO> orderList(int[] box) throws SQLException {
		List<CartDTO> list = new ArrayList<CartDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select * from (SELECT cart_num,userId, fileName, clothName, amount, price, discount, cart_date, sizes, color, cld.cdNum\r\n"
					+ "                FROM clothes c, color_detail cd, clothes_detail cld, cart, (SELECT fileNum, fileName, cNum FROM (\r\n"
					+ "               SELECT fileNum, fileName, cNum,\r\n"
					+ "               ROW_NUMBER() OVER(PARTITION BY cnum ORDER BY fileNum ASC) rank\r\n"
					+ "           FROM clothes_file\r\n"
					+ "          ) WHERE rank = 1) cf\r\n"
					+ "                WHERE c.cNum = cf.cNUm AND c.cNum = cd.cNum AND cd.ccNum = cld.ccNum AND cld.cdNum = cart.cdNum)\r\n"
					+ "WHERE cart_num IN (";
			
			for(int i = 0; i < box.length; i++) {
				sql += "?,";
			}
			
			sql = sql.substring(0, sql.length() - 1) + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < box.length; i++) {
				pstmt.setInt(i + 1, box[i]);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CartDTO dto = new CartDTO();
				
				dto.setCartNum(rs.getInt("cart_num"));
				dto.setUserId(rs.getString("userId"));
				dto.setFileName(rs.getString("fileName"));
				dto.setClothName(rs.getString("clothName"));
				dto.setAmount(rs.getInt("amount"));
				dto.setPrice(rs.getInt("price"));
				dto.setDiscount(rs.getInt("discount"));
				dto.setCart_date(rs.getString("cart_date"));
				dto.setSize(rs.getString("sizes"));
				dto.setColor(rs.getString("color"));
				dto.setClothDetailNum(rs.getInt("cdNum"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return list;
	}
	
	public List<CartDTO> orderList(int cartNum) {
		List<CartDTO> list = new ArrayList<CartDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select * from (SELECT cart_num,userId, fileName, clothName, amount, price, discount, cart_date, sizes, color, cld.cdNum\r\n"
					+ "                FROM clothes c, color_detail cd, clothes_detail cld, cart, (SELECT fileNum, fileName, cNum FROM (\r\n"
					+ "               SELECT fileNum, fileName, cNum,\r\n"
					+ "               ROW_NUMBER() OVER(PARTITION BY cnum ORDER BY fileNum ASC) rank\r\n"
					+ "           FROM clothes_file\r\n"
					+ "          ) WHERE rank = 1) cf\r\n"
					+ "                WHERE c.cNum = cf.cNUm AND c.cNum = cd.cNum AND cd.ccNum = cld.ccNum AND cld.cdNum = cart.cdNum)\r\n"
					+ "WHERE cart_num = ?";			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cartNum);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CartDTO dto = new CartDTO();
				
				dto.setCartNum(rs.getInt("cart_num"));
				dto.setUserId(rs.getString("userId"));
				dto.setFileName(rs.getString("fileName"));
				dto.setClothName(rs.getString("clothName"));
				dto.setAmount(rs.getInt("amount"));
				dto.setPrice(rs.getInt("price"));
				dto.setDiscount(rs.getInt("discount"));
				dto.setCart_date(rs.getString("cart_date"));
				dto.setSize(rs.getString("sizes"));
				dto.setColor(rs.getString("color"));
				dto.setClothDetailNum(rs.getInt("cdNum"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {

				}
			}
		}
		
		return list;
	}
	
	
	// 주문완료
	public int insertOrder(CartDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO orderDelivery (oNum, userId, order_date, total_price, dPrice, phoneNum,request)\r\n"
					+ " VALUES (order_seq.NEXTVAL, ?, SYSDATE, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
						
			pstmt.setString(1, dto.getUserId());
			pstmt.setInt(2, dto.getTotal());
			pstmt.setInt(3, dto.getFee());
			pstmt.setString(4, dto.getTel());
			pstmt.setString(5, dto.getRequest());
			
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
		return result;
	}
	
	public int insertOrderDetail(String cdNum, String amount) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO orderDetail (odNum, oNum, cQty, cdNum)\r\n"
				+ "VALUES (orderDetail_SEQ.NEXTVAL, order_SEQ.CURRVAL, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, amount);
			pstmt.setString(2, cdNum);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
		return result;
	}

	
	// 장바구니 삭제
	public int deleteCart(int cartNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM cart WHERE cart_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cartNum);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	// 선택상품 삭제
	public int deleteCartList(int[] box) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM cart WHERE cart_num IN(";
			
			for(int i = 0; i < box.length; i++) {
				sql += "?,";
			}
			
			sql = sql.substring(0, sql.length() - 1) + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < box.length; i++) {
				pstmt.setInt(i + 1, box[i]);
			}
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		return result;
	}
	
	// 장바구니 통합금액
	public int totalPrice(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT SUM(price*amount)\r\n"
					+ "FROM clothes c, color_detail cd, clothes_detail cld, cart\r\n"
					+ "WHERE c.cNum = cd.cNum AND cd.ccNum = cld.ccNum AND cld.cdNum = cart.cdNum\r\n"
					+ "    AND userId = ?\r\n"
					+ "GROUP BY userId";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}
	
	// 주문 통합금액
	public int orderPrice(int box[]) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT SUM(amount*price) FROM (SELECT cart_num,userId, clothName, amount, price, discount, cart_date, sizes, color\r\n"
				+ "FROM clothes c, color_detail cd, clothes_detail cld, cart\r\n"
					+ "WHERE c.cNum = cd.cNum AND cd.ccNum = cld.ccNum AND cld.cdNum = cart.cdNum)\r\n"
				+ "WHERE cart_num IN (";
			
			for(int i = 0; i < box.length; i++) {
				sql += "?,";
			}
			
			sql = sql.substring(0, sql.length() - 1) + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < box.length; i++) {
				pstmt.setInt(i + 1, box[i]);
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	// 상품 수량 변경
	public int updateAmount(CartDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE cart SET amount = ? WHERE cart_num = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getAmount());
			pstmt.setInt(2, dto.getCartNum());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}		
		return result;
	}
}
