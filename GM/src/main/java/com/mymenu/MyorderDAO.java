package com.mymenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MyorderDAO {
	private Connection conn = DBConn.getConnection();
	
	
	public int dataCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = "SELECT NVL(COUNT(*), 0) FROM orderdelivery od "
					+ "JOIN orderdetail o ON od.onum = o.onum "
					+ "WHERE od.userid =?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return result;
	}
	
	
	public int datacount(String userId, String condition,String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			
			sql = "SELECT NVL(COUNT(*), 0) "
					+ " FROM orderDelivery o "
					+" 	JOIN orderDetail d ON o.oNum = d.oNum "
			        +"  JOIN shipping sh ON sh.onum = o.onum "     
			        +"	JOIN clothes_detail cd ON cd.cdnum = d.cdnum "
			        +"  JOIN color_detail cld ON cd.ccnum = cld.ccnum "
			        +"  JOIN clothes cl ON cl.cnum = cld.cnum "
			        +"  WHERE o.userid =? ";
			if (condition.equals("all")) {
				sql += " AND INSTR(clothname, ?) >= 1 OR INSTR(ccoment, ?) >= 1 ";
			} else if (condition.equals("order_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " AND TO_CHAR(order_date, 'YYYYMMDD') = ? ";
			} else {
				sql += " AND INSTR(" + condition + ", ?) >= 1 ";
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, keyword);
			
			if (condition.equals("all")) {
				pstmt.setString(3, keyword);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return result;
	}
	
	public List<MyorderDTO> readMyOrderList(String userId,int start ,int end){
		List<MyorderDTO> list = new ArrayList<MyorderDTO>();
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		ResultSet rs =null;
		
		try {
			sb.append(" SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("         SELECT o.oNum, userId, TO_CHAR(order_date, 'YYYY-MM-DD') order_date , total_price,dprice,phonenum,");
			sb.append("             d.cdNum,d.cQty,clothname,price,dCode,cComent,TO_CHAR(sendDate, 'YYYY-MM-DD') sendDate,TO_CHAR(arriveDate, 'YYYY-MM-DD') arriveDate,state , d.odNum");
			sb.append("         FROM orderDelivery o ");
			sb.append("         JOIN orderDetail d ON o.oNum = d.oNum AND o.userid = ?");
			sb.append("         JOIN shipping sh ON sh.onum = o.onum");
			sb.append("         JOIN dLocation dl ON dl.onum = o.onum");
			sb.append("         JOIN clothes_detail cd ON cd.cdnum = d.cdnum");
			sb.append("         JOIN color_detail cld ON cd.ccnum = cld.ccnum");
			sb.append("         JOIN clothes cl ON cl.cnum = cld.cnum");
			sb.append("         ORDER BY oNum DESC   ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyorderDTO dto = new MyorderDTO();
				dto.setUserId(rs.getString("userid"));
				dto.setoNum(rs.getInt("onum"));
				dto.setOrder_date(rs.getString("order_date"));
				dto.setTotal_price(rs.getInt("total_price"));
				dto.setbPrice(rs.getInt("dprice"));
				dto.setPhoneNum(rs.getString("phonenum"));
				dto.setCdnum(rs.getInt("cdnum"));
				dto.setCqty(rs.getInt("cqty"));
				dto.setClothName(rs.getString("clothname"));
				dto.setPrice(rs.getInt("price"));
				dto.setCcoment(rs.getString("ccoment"));
				dto.setSenddate(rs.getString("senddate"));
				dto.setArrivedate(rs.getString("arrivedate"));
				dto.setState(rs.getString("state"));
				dto.setOdNum(rs.getInt("odNum"));
				dto.setdCode(rs.getInt("dCode"));
				list.add(dto);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
		
		}
		
		
		return list;
	}
	
	public List<MyorderDTO> readMyOrderList(String userId,int start ,int end,String condition, String keyword){
		List<MyorderDTO> list = new ArrayList<MyorderDTO>();
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		ResultSet rs =null;
		
		try {
			sb.append(" SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("         SELECT o.oNum, userId, TO_CHAR(order_date, 'YYYY-MM-DD') order_date , total_price,dprice,phonenum,");
			sb.append("             d.cdNum,d.cQty,clothname,price,dCode,cComent,TO_CHAR(sendDate, 'YYYY-MM-DD') sendDate,TO_CHAR(arriveDate, 'YYYY-MM-DD') arriveDate,state , d.odNum");
			sb.append("         FROM orderDelivery o ");
			sb.append("         JOIN orderDetail d ON o.oNum = d.oNum AND o.userid = ?");
			sb.append("          JOIN shipping sh ON sh.onum = o.onum");
			sb.append("         JOIN dLocation dl ON dl.onum = o.onum");
			sb.append("         JOIN clothes_detail cd ON cd.cdnum = d.cdnum");
			sb.append("         JOIN color_detail cld ON cd.ccnum = cld.ccnum");
			sb.append("         JOIN clothes cl ON cl.cnum = cld.cnum");
			if (condition.equals("all")) {
				sb.append("     WHERE INSTR(clothname, ?) >= 1 OR INSTR(cComent, ?) >= 1 ");
			} else if (condition.equals("order_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("     WHERE TO_CHAR(order_date, 'YYYYMMDD') = ?");
			} else {
				sb.append("     WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append("         ORDER BY oNum DESC   ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);			

			if (condition.equals("all")) {
				pstmt.setString(2, keyword);
				pstmt.setString(3, keyword);
				pstmt.setInt(4, end);
				pstmt.setInt(5, start);
			} else {
				pstmt.setString(2, keyword);
				pstmt.setInt(3, end);
				pstmt.setInt(4, start);
			}

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyorderDTO dto = new MyorderDTO();
				dto.setUserId(rs.getString("userid"));
				dto.setoNum(rs.getInt("onum"));
				dto.setOrder_date(rs.getString("order_date"));
				dto.setTotal_price(rs.getInt("total_price"));
				dto.setbPrice(rs.getInt("dprice"));
				dto.setPhoneNum(rs.getString("phonenum"));
				dto.setCdnum(rs.getInt("cdnum"));
				dto.setCqty(rs.getInt("cqty"));
				dto.setClothName(rs.getString("clothname"));
				dto.setPrice(rs.getInt("price"));
				dto.setCcoment(rs.getString("ccoment"));
				dto.setSenddate(rs.getString("senddate"));
				dto.setArrivedate(rs.getString("arrivedate"));
				dto.setState(rs.getString("state"));
				dto.setOdNum(rs.getInt("odNum"));
				dto.setdCode(rs.getInt("dCode"));
				
				list.add(dto);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
		
		}
		
		
		return list;
	}
	
	
	public MyorderDTO readMyOrder(int odNum) {
		MyorderDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT o.oNum, userId, TO_CHAR(order_date, 'YYYY-MM-DD') order_date , total_price,dprice,phonenum, d.odNum,dAddress_detail,dAddress,recipient,recPhoneNum,dNum, fileName,");
			sb.append("    d.cdNum,d.cQty,clothname,price,cComent,dCode,TO_CHAR(sendDate, 'YYYY-MM-DD') sendDate,TO_CHAR(arriveDate, 'YYYY-MM-DD') arriveDate,state");
			sb.append("         FROM orderDelivery o ");
			sb.append("         JOIN orderDetail d ON o.oNum = d.oNum ");
			sb.append("          JOIN shipping sh ON sh.onum = o.onum");
			sb.append("          JOIN dLocation dl ON dl.onum = o.onum");
			sb.append("         JOIN clothes_detail cd ON cd.cdnum = d.cdnum");
			sb.append("         JOIN color_detail cld ON cd.ccnum = cld.ccnum");
			sb.append("         JOIN clothes cl ON cl.cnum = cld.cnum");
			sb.append("         JOIN clothes_file cf ON cf.cNum = cl.cnum");
			sb.append("       WHERE d.odNum = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, odNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MyorderDTO();
				dto.setUserId(rs.getString("userid"));
				dto.setoNum(rs.getInt("onum"));
				dto.setOrder_date(rs.getString("order_date"));
				dto.setTotal_price(rs.getInt("total_price"));
				dto.setbPrice(rs.getInt("dprice"));
				dto.setPhoneNum(rs.getString("phonenum"));
				dto.setCdnum(rs.getInt("cdnum"));
				dto.setCqty(rs.getInt("cqty"));
				dto.setClothName(rs.getString("clothname"));
				dto.setPrice(rs.getInt("price"));
				dto.setCcoment(rs.getString("ccoment"));
				dto.setSenddate(rs.getString("senddate"));
				dto.setArrivedate(rs.getString("arriveDate"));
				dto.setState(rs.getString("state"));
				dto.setOdNum(rs.getInt("odNum"));
				dto.setdAddress(rs.getString("dAddress"));
				dto.setdAddress_detail(rs.getString("dAddress_detail"));
				dto.setRecipient(rs.getString("recipient"));
				dto.setRecPhoneNum(rs.getString("recPhoneNum"));
				dto.setdNum(rs.getInt("dNum"));
				dto.setFileName(rs.getString("fileName"));
				dto.setdCode(rs.getInt("dCode"));
			
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {					
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {					
				}
			}
		}
		
		return dto;
	}
	
	
	public int updatedLocation(MyorderDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		try {
				sb.append("UPDATE dLocation SET dAddress_detail=?, dCode=?, dAddress=?, ");
				sb.append("recipient = ?, recPhoneNum=? ");
				sb.append(" WHERE oNum=?");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, dto.getdAddress_detail());
				pstmt.setInt(2, dto.getdCode());				
				pstmt.setString(3, dto.getdAddress());
				pstmt.setString(4, dto.getRecipient());
				pstmt.setString(5, dto.getRecPhoneNum());
				pstmt.setInt(6, dto.getoNum());
			
				result = pstmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		
		return result;
	}
	

	
	public int deleteMyorder(MyorderDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
				conn.setAutoCommit(false);
				sql = "DELETE FROM orderDetail WHERE odNum=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, dto.getOdNum());
				
				result = pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;
				
				sql = "UPDATE orderDelivery SET total_price = ? WHERE oNum =?";
				
				int now_price = dto.getTotal_price() - (dto.getPrice()*dto.getCqty());
				
				
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, now_price);
				pstmt.setInt(2, dto.getoNum());
				
				
				result += pstmt.executeUpdate();
				conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		
		return result;
	}
	
}
