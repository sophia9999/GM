package com.sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class SalesDAO {
	Connection conn = DBConn.getConnection();
	
	public List<SalesDTO> readorderlist(){
		List<SalesDTO> list = new ArrayList<SalesDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT oNum, userId, order_date, total_price, dPrice, phoneNum FROM orderDelivery";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SalesDTO dto = new SalesDTO();
				dto.setoNum(rs.getInt("oNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setTotal_price(rs.getInt("total_price"));
				dto.setdPrice(rs.getInt("dPrice"));
			
				list.add(dto);
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
		
		
		return list;
	}

	
	public List<SalesDTO> readAlllist(){
		List<SalesDTO> list = new ArrayList<SalesDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(" SELECT o.oNum, userId,cl.cNum,clothName,unitPrice,stock,cd.cdNum,discount,");
			sb.append("            TO_CHAR(order_date, 'YYYY-MM-DD') order_date,price,cQty");
			sb.append("            FROM orderDelivery o  ");
			sb.append("            JOIN orderDetail d ON o.oNum = d.oNum");
			sb.append("            JOIN clothes_detail cd ON cd.cdnum = d.cdnum");
			sb.append("            JOIN color_detail cld ON cd.ccnum = cld.ccnum");
			sb.append("            JOIN clothes cl ON cl.cnum = cld.cnum");
			sb.append("            ORDER BY oNum DESC");
			
			
			
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				SalesDTO dto = new SalesDTO();
				
				dto.setoNum(rs.getInt("oNum"));
				dto.setcNum(rs.getInt("cNum"));				
				dto.setOrderNum(rs.getInt("oNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setClothName(rs.getString("clothName"));
				dto.setUnitPrice(rs.getInt("unitPrice"));
				dto.setStock(rs.getInt("stock"));
				dto.setCdNum(rs.getInt("cdNum"));
				dto.setOrder_date(rs.getString("order_date"));				
				dto.setPrice(rs.getInt("price"));
				dto.setcQty(rs.getInt("cqty"));
				dto.setDiscount(rs.getInt("discount"));
				list.add(dto);
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
		
		
		return list;
	}
	
	
	public List<SalesDTO> readdatesale(String date){
		List<SalesDTO> list = new ArrayList<SalesDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		
		
		try {
			if(date.equals("month")) {
				sb.append(" SELECT  TO_CHAR(order_date, 'YYYY-MM') order_date ,  SUM(unitPrice*cQty) unitPrice, SUM(discount) discount , SUM(price*cQty) price ");
			}else if(date.equals("all")) {
				sb.append(" SELECT   SUM(unitPrice*cQty) unitPrice, SUM(discount) discount , SUM(price*cQty) price");
			}else if(date.equals("year")) {
				sb.append(" SELECT  TO_CHAR(order_date, 'YYYY') order_date ,   SUM(unitPrice*cQty) unitPrice, SUM(discount) discount , SUM(price*cQty) price");
			}else if(date.equals("day")) {
				sb.append(" SELECT  TO_CHAR(order_date, 'YYYY-MM-dd') order_date , SUM(unitPrice*cQty) unitPrice, SUM(discount) discount , SUM(price*cQty) price");
			}
			
			sb.append("   FROM orderDelivery o   ");
			sb.append(" JOIN orderDetail d ON o.oNum = d.oNum");
			sb.append("          JOIN clothes_detail cd ON cd.cdnum = d.cdnum");
			sb.append("          JOIN color_detail cld ON cd.ccnum = cld.ccnum");
			sb.append("          JOIN clothes cl ON cl.cnum = cld.cnum");
			if(date.equals("month")) {
				sb.append("    GROUP BY TO_CHAR(order_date, 'YYYY-MM') ");
			}else if(date.equals("year")) {
				sb.append("    GROUP BY TO_CHAR(order_date, 'YYYY') ");
			}else if(date.equals("day")) {
				sb.append("    GROUP BY TO_CHAR(order_date, 'YYYY-MM-dd') ");
			}
			

			
			
			
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				SalesDTO dto = new SalesDTO();
				
				dto.setPrice(rs.getInt("price"));
				dto.setUnitPrice(rs.getInt("unitPrice"));				
				dto.setDiscount(rs.getInt("discount"));
				if(!date.equals("all"))dto.setOrder_date(rs.getString("order_date"));				
				else dto.setOrder_date("전체");
				dto.setRealPrice(dto.getPrice()-dto.getDiscount()-dto.getUnitPrice());
				list.add(dto);
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
		
		
		return list;
	}

}
