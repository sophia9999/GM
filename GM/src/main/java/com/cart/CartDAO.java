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
	
	// 상품 개수
	public int dataCount(String userId) {
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
	public List<CartDTO> listCart(String userId) {
		List<CartDTO> list = new ArrayList<CartDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT cart_num,userId, fileName, clothName, amount, price, discount, cart_date, sizes, color\r\n"
				+ "FROM clothes c, color_detail cd, clothes_detail cld, cart, clothes_file cf\r\n"
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
	
	// 통합금액
	public int total_price(String userId) {
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
	
	// 해당상품 재고
	/*
	public int stock(int clothDetailNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT stock FROM cart, clothes_detail cd\r\n"
			+ "WHERE cart.cdNum = cd.cdNum AND cart.cdnum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, clothDetailNum);

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
	*/
}
