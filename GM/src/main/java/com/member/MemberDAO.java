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
}
