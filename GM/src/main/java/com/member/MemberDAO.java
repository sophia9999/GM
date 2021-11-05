package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	public MemberDTO loginMember(String userId, String userPwd) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
	
		String pattern = "yyyy/MM/dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date now = new Date();
		String nowString = sdf.format(now);
		
		try {
			sb.append(" SELECT userId, userName, pwd, reg_date, update_date ");
			sb.append(" FROM member");
			sb.append(" WHERE userId = ? AND pwd = ? AND login = 1");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserPwd(rs.getString("pwd"));
				dto.setUserName(rs.getString("userName"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setUpdate_date(nowString);
			}
			if(dto!=null) {
				pstmt.close();
				
				
				sb.setLength(0);
				sb.append("UPDATE member SET update_date = ?");
				sb.append("WHERE  userId = ? AND pwd = ? AND login = 1");
				
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, dto.getUpdate_date());
				pstmt.setString(2, dto.getUserId());
				pstmt.setString(3, dto.getUserPwd());
				
				pstmt.executeUpdate();
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}	
	
	public int insertMember(MemberDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
	
		
		try {
			conn.setAutoCommit(false);
			

			sb.append(" INSERT INTO MEMBER(userId, userName, pwd, login, reg_date, update_date) ");
			sb.append(" VALUES (?, ?, ?, DEFAULT, SYSDATE,SYSDATE) ");

			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserName());
			pstmt.setString(3, dto.getUserPwd());
			
			result  = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			sb.setLength(0);
			
			sb.append("INSERT INTO MEMBER_DETAIL(userId, birth, email, code, address, address_detail, phone,name)");
			sb.append(" VALUES (?, ?, ?, ?, ?, ?,?,?)");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getBirth());
			pstmt.setString(3, dto.getEmail());
			pstmt.setInt(4, dto.getCode());
			pstmt.setString(5, dto.getAddress());
			pstmt.setString(6, dto.getAddress_detail());
			pstmt.setString(7, dto.getTel());
			pstmt.setString(8, dto.getUserName());
			
			result += pstmt.executeUpdate();
			
			
			conn.commit();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
				
			if(pstmt != null) {
				try {
					conn.setAutoCommit(true);
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}	
	
	public MemberDTO findMember(String userId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT m1.userId, userName, Pwd,");
			sb.append("      login, reg_date, update_date,");
			sb.append("      TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("      email, phone,");
			sb.append("      code, address, address_detail");
			sb.append("  FROM member m1");
			sb.append("  LEFT OUTER JOIN member_detail m2 ON m1.userId=m2.userId ");
			sb.append("  WHERE m1.userId = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
			
				dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserPwd(rs.getString("pwd"));
				dto.setLogin(rs.getInt("login"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setUpdate_date(rs.getString("update_date"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				if(dto.getEmail() != null) {
					String[] ss = dto.getEmail().split("@");
					if(ss.length == 2) {
						dto.setEmail1(ss[0]);
						dto.setEmail2(ss[1]);
					}
				}
				dto.setTel(rs.getString("phone"));
				if(dto.getTel() != null) {
					String[] ss = dto.getTel().split("-");
					if(ss.length == 3) {
						dto.setTel1(ss[0]);
						dto.setTel2(ss[1]);
						dto.setTel3(ss[2]);
					}
				}
				dto.setCode(rs.getInt("code"));
				dto.setAddress(rs.getString("address"));
				dto.setAddress_detail(rs.getString("address_detail"));
			
			}
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}			
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return dto;
	}
	
	public int updateMember(MemberDTO dto)throws SQLException {
		int result = 0;
		PreparedStatement pstmt =null;
		String sql="";
		
		try {
			conn.setAutoCommit(false);
			
			sql ="UPDATE member SET Pwd=?, update_date=SYSDATE  WHERE userId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserPwd());
			pstmt.setString(2, dto.getUserId());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql ="UPDATE member_detail  SET birth=?, email=?, code=?, address=?, address_detail=?, phone=?  WHERE userId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBirth());
			pstmt.setString(2, dto.getEmail());
			pstmt.setInt(3, dto.getCode());
			pstmt.setString(4, dto.getAddress());
			pstmt.setString(5, dto.getAddress_detail());
			pstmt.setString(6, dto.getTel());
			pstmt.setString(7, dto.getUserId());
			
			result += pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt !=null) {
				try {
					conn.setAutoCommit(true);
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return result;
	}
 
}
