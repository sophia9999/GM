package com.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class OrderDAO {

	private Connection conn = DBConn.getConnection();

	public int insertDelevery(OrderDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = "INSERT INTO shipping(deNum, state, sendDate, arriveDate, odNum) "
					+ "  VALUES (?, ?, ?, ?, ?)";
						
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getDeNum());
			pstmt.setString(2, dto.getState());
			pstmt.setString(3, dto.getSendDate());
			pstmt.setString(4, dto.getArriveDate());
			pstmt.setString(5, dto.getOdNum());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
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
	
	public OrderDTO shippingRead(String deNum) {
		OrderDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		// 게시판 dao readboard
		
		
		
		try {
			sql = "SELECT deNum, state, senddate, arrivedate, odNum"
					+ " FROM shipping s"
					+ " WHERE deNum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, deNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new OrderDTO();
				
				dto.setDeNum(rs.getString("deNum"));
				dto.setState(rs.getString("state"));
				dto.setSendDate(rs.getString("senddate"));
				dto.setArriveDate(rs.getString("arrivedate"));
				dto.setOdNum(rs.getString("odNum"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if( rs != null) {
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
		return dto;
	}
	
	
	public int updateDelevery(OrderDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		// 게시판update  deNum state senddate arrivedate
		
		try {
			sql = "UPDATE shipping SET state=?, senddate=?, arrivedate=? WHERE deNum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, dto.getState());
			pstmt.setString(2, dto.getSendDate());
			pstmt.setString(3, dto.getArriveDate());
			
			pstmt.setString(4, dto.getDeNum());
			
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
	
	
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM ORDERDELIVERY";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);

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

	// 게시물 리스트
	public List<OrderDTO> ordeList(int start, int end) {
		List<OrderDTO> list = new ArrayList<OrderDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("   		SELECT o.oNum, od.odNum, clothName, m.userId, userName, nvl(state,'준비중') state, denum, ");
			sb.append("       		  TO_CHAR(order_date, 'YYYY-MM-DD') order_date, ");
			sb.append("       		  TO_CHAR(arriveDate, 'YYYY-MM-DD') arriveDate ");
			sb.append(" 		FROM ORDERDELIVERY o ");
			sb.append(" 		JOIN member m ON o.userId = m.userId ");
			sb.append(" 		JOIN orderdetail od ON o.oNum  = od.oNum ");
			sb.append(" 		JOIN clothes_detail cd ON cd.cdNum  = od.cdNum ");
			sb.append(" 		JOIN color_detail co ON co.ccNum = cd.ccNum ");
			sb.append(" 		JOIN clothes c ON c.cNum= co.cNum");
			sb.append(" 		LEFT OUTER JOIN shipping s ON s.odNum = od.odNum ");
			sb.append(" 		ORDER BY o.oNum DESC ");
			sb.append(" 	)  tb WHERE ROWNUM <= ? ");
			sb.append(" )WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderDTO dto = new OrderDTO();

				dto.setoNum(rs.getInt("oNum"));
				dto.setOdNum(rs.getString("oDNum"));
				dto.setClothName(rs.getString("clothName"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserId(rs.getString("userId"));
				dto.setState(rs.getString("state"));
				dto.setDeNum(rs.getString("deNum"));
				
				dto.setOrder_date(rs.getString("order_date"));
				dto.setArriveDate(rs.getString("arriveDate"));

				list.add(dto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}

		}

		return list;
	}

	public OrderDTO readOrder(int odNum) {
		OrderDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		// 제품odnum,사진, 가격price, 수취인recipient, 연락처phoneNum, 배송지daddress_detail,
		// 배송현황state, 배송예정일arriveDate
		try {
			sql = "SELECT o.oNum, od.odNum, c.price, clothName, dL.recipient, dL.recPhoneNum, denum, dL.daddress_detail, s.state, TO_CHAR(s.sendDate, 'YYYY-MM-DD') sendDate, TO_CHAR(s.arriveDate, 'YYYY-MM-DD') arriveDate, fileName "
					+ " FROM orderdelivery o "
					+ " JOIN member m ON o.userId = m.userId "
					+ " JOIN orderdetail od ON o.oNum = od.oNum "
					+ " JOIN clothes_detail cd ON cd.cdNum  = od.cdNum "
					+ " JOIN color_detail co ON co.ccNum = cd.ccNum"
					+ " JOIN clothes c ON c.cNum= co.cNum"
					+ " LEFT OUTER JOIN dLocation dL ON o.oNum = dL.oNum "
					+ " LEFT OUTER JOIN shipping s ON od.odNum = s.odNum "
					+ " LEFT OUTER JOIN ( "
					+ "   SELECT fileNum, cNum, fileName FROM ( "
					+ "      SELECT fileNum, cNum, fileName, "
					+ "            ROW_NUMBER() OVER(PARTITION BY cNum ORDER BY fileNum ASC) rank "
					+ "      FROM clothes_file"
					+ " ) WHERE rank = 1 "
					+ " ) i ON c.cNum = i.cNum "
					+ " WHERE od.odNum = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, odNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new OrderDTO();

				dto.setoNum(rs.getInt("oNum"));
				dto.setOdNum(rs.getString("odNum"));
				dto.setDeNum(rs.getString("deNum"));
				// dto.setUserId(rs.getString("userId")); // 사진???
				dto.setPrice(rs.getInt("Price"));
				dto.setRecipient(rs.getString("recipient"));
				dto.setRecPhoneNum(rs.getString("recPhoneNum"));
				dto.setDaddress_detail(rs.getString("daddress_detail"));
				dto.setState(rs.getString("state"));
				dto.setSendDate(rs.getString("sendDate"));
				dto.setArriveDate(rs.getString("arriveDate"));
				dto.setFilename(rs.getString("filename"));
				dto.setClothName(rs.getString("clothName"));
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

		return dto;
	}
	
}
