package com.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;



import com.util.DBConn;


//// countReview

public class ReviewDAO {
	private Connection conn = DBConn.getConnection();

	// 리뷰 추가
	public int insertReview(ReviewDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
	
		try {
			sql = "INSERT INTO review (rNum, userId, subject, content, odNum, r_reg_date) "
					+ " VALUES (REVIEW_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getOdNum());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
		return result;
	}
	
	// 데이터 개수
	public int reviewCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM review ";
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
	
	// 데이터 개수(userId 조건 포함)
		public int reviewCount(String userId) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = "SELECT NVL(COUNT(*), 0) FROM review WHERE userId = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, userId);
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
	public List<ReviewDTO> listReview(int start, int end) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM ronum, tb.* FROM ( ");
			sb.append(" 		SELECT rNum, subject, userName, c.clothName, content, r.odNum, od.oNum, cd.sizes, cc.color, ");
			sb.append("   			 TO_CHAR(r_reg_date, 'YYYY-MM-DD') r_reg_date ");
			sb.append(" 		FROM review r ");
			sb.append("         JOIN member m ON r.userId = m.userId ");
			sb.append("         JOIN orderDetail od ON r.odNum = od.odNum ");
			sb.append("         JOIN clothes_detail cd ON od.cdNum = cd.cdNum ");
			sb.append("         JOIN color_detail cc ON cc.ccNum = cd.ccNum ");
			sb.append("         JOIN clothes c ON c.cNum = cc.cNum ");
			sb.append("         ORDER BY rNum DESC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE ronum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();

				dto.setrNum(rs.getInt("rNum"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setClothName(rs.getString("clothName"));
				dto.setContent(rs.getString("content"));
				dto.setOdNum(rs.getInt("odNum"));
				dto.setoNum(rs.getInt("oNum"));
				dto.setR_reg_date(rs.getString("r_reg_date"));
				dto.setSizes(rs.getString("sizes"));
				dto.setColor(rs.getString("color"));
				dto.setAllClothesName(dto.getClothName()+" [color:"+ dto.getColor() + "] [size:" + dto.getSizes()+"]");
				
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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}
	
	// 해당 게시물 보기
	public ReviewDTO readReview(int rNum) {
		ReviewDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT rNum, subject, content, o.odNum, r_reg_date, c.clothName, cc.color, cd.sizes, r.userId, userName "
					+ " FROM review r "
					+ " JOIN member m ON r.userId = m.userId "
					+ " JOIN orderDetail o ON r.odNum=o.odNum "
					+ " JOIN clothes_detail cd ON o.cdNum=cd.cdNum "
					+ " JOIN color_detail cc ON cd.ccNum=cc.ccNum "
					+ " JOIN clothes c ON cc.cNum=c.cNum "
					+ " WHERE rNum = ?";
					
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, rNum );

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ReviewDTO();
				
				dto.setrNum(rs.getInt("rNum"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setOdNum(rs.getInt("odNum"));
				dto.setR_reg_date(rs.getString("r_reg_date"));
				dto.setSizes(rs.getString("sizes"));
				dto.setColor(rs.getString("color"));
				dto.setClothName(rs.getString("clothName"));
				dto.setAllClothesName(dto.getClothName()+" [color:"+ dto.getColor() + "] [size:" + dto.getSizes()+"]");
				
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
	
	// 내가 쓸(썼던) 리뷰
		public List<ReviewDTO> listReview(int start, int end, String userId) {
			List<ReviewDTO> list = new ArrayList<ReviewDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT * FROM ( ");
				sb.append("     SELECT ROWNUM ronum, tb.* FROM ( ");
				sb.append(" 		SELECT rNum, subject, c.clothName, content, r.odNum, od.oNum, cd.sizes, cc.color, userId, cd.cdNum, ");
				sb.append("   			 TO_CHAR(r_reg_date, 'YYYY-MM-DD') r_reg_date ");
				sb.append(" 		FROM orderDetail od ");
				sb.append("         LEFT OUTER JOIN review r  ON r.odNum = od.odNum ");
				sb.append("         JOIN clothes_detail cd ON od.cdNum = cd.cdNum ");
				sb.append("         JOIN color_detail cc ON cc.ccNum = cd.ccNum ");
				sb.append("         JOIN clothes c ON c.cNum = cc.cNum ");
				sb.append("         WHERE userId = ? ");
				sb.append("         ORDER BY rNum DESC ");
				sb.append("     ) tb WHERE ROWNUM <= ? ");
				sb.append(" ) WHERE ronum >= ? ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, userId);
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);

				rs = pstmt.executeQuery();
				while (rs.next()) {
					ReviewDTO dto = new ReviewDTO();
					
					dto.setrNum(rs.getInt("rNum"));
					dto.setSubject(rs.getString("subject"));
					dto.setClothName(rs.getString("clothName"));
					dto.setContent(rs.getString("content"));
					dto.setOdNum(rs.getInt("odNum"));
					dto.setOdNum(rs.getInt("oNum"));
					dto.setR_reg_date(rs.getString("r_reg_date"));
					dto.setSizes(rs.getString("sizes"));
					dto.setColor(rs.getString("color"));//베이직 라운드 퀼팅 패딩 [color:white] [size:M]${dto.clothName}&nbsp[color:${dto.color}]&nbsp[size:${dto.sizes}]
					dto.setAllClothesName(dto.getClothName()+" [color:"+ dto.getColor() + "] [size:" + dto.getSizes()+"]");
					dto.setCdNum(rs.getString("cdNum"));		
					
					
						
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
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e2) {
					}
				}
			}

			return list;
		}
		
		// 옷 정보 불러오기 
		public ReviewDTO readClothes(String cdNum) {
			ReviewDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = "SELECT clothName, sizes, color  "
						+ " FROM clothes_detail cd "
						+ " JOIN color_detail cc ON cd.ccNum=cc.ccNum "
						+ " JOIN clothes c ON cc.cNum=c.cNum "
						+ " WHERE cdNum = ?";
						
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, cdNum );

				rs = pstmt.executeQuery();

				if (rs.next()) {
					dto = new ReviewDTO();
					
					dto.setClothName(rs.getString("clothName"));
					dto.setSizes(rs.getString("sizes"));
					dto.setColor(rs.getString("color"));
			
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
		
		// 게시물 삭제
		public int deleteReview(int rNum, String userId) throws SQLException {
			int result = 0;
			PreparedStatement pstmt = null;
			String sql;

			try {
				if (userId.equals("admin")) {
					sql = "DELETE FROM review WHERE rNum=?";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, rNum);
					
					result = pstmt.executeUpdate();
				} else {
					sql = "DELETE FROM review WHERE rNum=? AND userId=?";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, rNum);
					pstmt.setString(2, userId);
					
					result = pstmt.executeUpdate();
				}
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

}