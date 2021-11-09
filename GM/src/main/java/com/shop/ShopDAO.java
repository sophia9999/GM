package com.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ShopDAO {
	private Connection conn = DBConn.getConnection();
	
	// 사용자용 displayYN에서 1로 설정한 것들만 (사용자에게 보이도록 설정한 상품만)
	public int dataCount(int displayYN) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM clothes WHERE displayYN	= 1 ";
			pstmt = conn.prepareStatement(sql);
			
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
	
	// 관리자용 모든 데이터 리스트
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM clothes ";
			pstmt = conn.prepareStatement(sql);
			
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
	
	// 사용자용 displayYN에서 1로 설정한 것들만 (사용자에게 보이도록 설정한 상품만)
	public List<ShopDTO> listShop(int start, int end, int displayYN) {
		List<ShopDTO> list = new ArrayList<ShopDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			
			sb.append("SELECT * FROM (");
			sb.append("   SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("        SELECT c.cNum, registrant, tNum2, tname, clothName, displayYN,");
			sb.append("                cComent, price, unitprice, discount, season, fileNum, fileName");
			sb.append("        FROM clothes c");
			sb.append("		   JOIN type t ON t.tnum = c.tnum2 ");
			sb.append("        LEFT OUTER JOIN (");
			sb.append("            SELECT fileNum, fileName, cNum FROM (");
			sb.append("                SELECT fileNum, fileName, cNum,");
			sb.append("                ROW_NUMBER() OVER(PARTITION BY cnum ORDER BY fileNum ASC) rank");
			sb.append("            FROM clothes_file");
			sb.append("            ) WHERE rank = 1");
			sb.append("        ) cf ON c.cnum = cf.cnum");
			sb.append("        WHERE displayYN = 1");
			sb.append("        ORDER BY cnum DESC");
			sb.append("    ) tb WHERE ROWNUM <= ?");
			sb.append(") WHERE rnum >= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ShopDTO dto = new ShopDTO();
				dto.setCnum(rs.getInt("cNum"));
				dto.setUserId(rs.getString("registrant"));
				dto.setTnum(rs.getInt("tNum2"));
				dto.setTname(rs.getString("tname"));
				dto.setClothname(rs.getString("clothName"));
				dto.setDisplayYN(rs.getInt("displayYN"));
				dto.setCcoment(rs.getString("cComent"));
				dto.setPrice(rs.getInt("price"));
				dto.setUnitPrice(rs.getInt("unitprice"));
				dto.setDiscount(rs.getInt("discount"));
				dto.setSeason(rs.getString("season"));
				dto.setFileNum(rs.getInt("fileNum"));
				dto.setImageFilename(rs.getString("fileName"));
				
				list.add(dto);
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
		
		return list;
	}
	
	// 관리자용 모든 게시글 리스트
	public List<ShopDTO> listShop(int start, int end) {
		List<ShopDTO> list = new ArrayList<ShopDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("   SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("        SELECT c.cNum, registrant, tNum2, tname, clothName, displayYN,");
			sb.append("                cComent, price, unitprice, discount, season, fileNum, fileName");
			sb.append("        FROM clothes c");
			sb.append("        JOIN type t ON t.tnum = c.tnum2");
			sb.append("        LEFT OUTER JOIN (");
			sb.append("            SELECT fileNum, fileName, cNum FROM (");
			sb.append("                SELECT fileNum, fileName, cNum,");
			sb.append("                ROW_NUMBER() OVER(PARTITION BY cnum ORDER BY fileNum ASC) rank");
			sb.append("            FROM clothes_file");
			sb.append("            ) WHERE rank = 1");
			sb.append("        ) cf ON c.cnum = cf.cnum");
			sb.append("        ORDER BY cnum DESC");
			sb.append("    ) tb WHERE ROWNUM <= ?");
			sb.append(") WHERE rnum >= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ShopDTO dto = new ShopDTO();
				dto.setCnum(rs.getInt("cNum"));
				dto.setUserId(rs.getString("registrant"));
				dto.setTnum(rs.getInt("tNum2"));
				dto.setTname(rs.getString("tname"));
				dto.setClothname(rs.getString("clothName"));
				dto.setDisplayYN(rs.getInt("displayYN"));
				dto.setCcoment(rs.getString("cComent"));
				dto.setPrice(rs.getInt("price"));
				dto.setUnitPrice(rs.getInt("unitprice"));
				dto.setDiscount(rs.getInt("discount"));
				dto.setSeason(rs.getString("season"));
				dto.setFileNum(rs.getInt("fileNum"));
				dto.setImageFilename(rs.getString("fileName"));
				
				list.add(dto);
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
		return list;
	}
	
	public int insertGerment(ShopDTO dto) throws Exception {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int seq;
		
		try {
			sql = "SELECT clothes_SEQ.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if (rs.next()) {
				seq = rs.getInt(1);
			}
			dto.setCnum(seq); 
			// 두 테이블에 넣을 때 CURRVAL은 위험할 수 있으므로
			// 받아와서 할 것!
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = "INSERT INTO clothes(cNum, "
					+ " registrant, tNum2, clothName, displayYN, "
					+ " cComent, price, discount, unitprice, season) "
					+ " VALUES(?, "
					+ " ?, ?, ?, ?, ?, ?, ?, ?, ?) ";	
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getCnum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setInt(3, dto.getTnum());
			pstmt.setString(4, dto.getClothname());
			pstmt.setInt(5, dto.getDisplayYN());
			pstmt.setString(6, dto.getCcoment());
			pstmt.setInt(7, dto.getPrice());
			pstmt.setInt(8, dto.getDiscount());
			pstmt.setInt(9, dto.getUnitPrice());
			pstmt.setString(10, dto.getSeason());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO clothes_file(cnum, filenum, filename) "
						+ " VALUES(?, clothes_file_SEQ.NEXTVAL, ?)";	
				pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setInt(1, dto.getCnum());
					pstmt.setString(2, dto.getImageFiles()[i]);
					pstmt.executeUpdate();
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	// 상품 상세 페이지를 위해 읽어오기
	public ShopDTO readDetail(int num) {
		ShopDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT c.cNum, registrant, tNum2, clothName, displayYN, ");
			sb.append("		 cComent, price, unitprice, discount, season, filename, fileNum");
			sb.append(" FROM clothes c");
			sb.append(" LEFT OUTER JOIN (");
			sb.append(" SELECT fileNum, fileName, cNum FROM (");
			sb.append("		SELECT fileNum, fileName, cNum,");
			sb.append("			 ROW_NUMBER() OVER(PARTITION BY cnum ORDER BY fileNum ASC) rank");
			sb.append("		FROM clothes_file) WHERE rank = 1");
			sb.append(" ) cf ON c.cnum = cf.cnum");
			sb.append(" WHERE c.cnum = ?");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ShopDTO();
				dto.setCnum(rs.getInt("cnum"));
				dto.setUserId(rs.getString("registrant"));
				dto.setTnum(rs.getInt("tnum2"));
				dto.setDisplayYN(rs.getInt("displayYN"));
				dto.setCcoment(rs.getString("ccoment"));
				dto.setPrice(rs.getInt("price"));
				dto.setDiscount(rs.getInt("discount"));
				dto.setUnitPrice(rs.getInt("unitprice"));
				dto.setSeason(rs.getString("season"));
				dto.setClothname(rs.getString("clothname"));
				dto.setImageFilename(rs.getString("filename"));
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
	
	// 상세 페이지에 올라가 있는 이미지 파일들 불러오기
	public List<ShopDTO> listPhotoFile(int num) {
		List<ShopDTO> list = new ArrayList<ShopDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT c.cnum, filename, filenum"
					+ "	FROM clothes c"
					+ " LEFT OUTER JOIN clothes_file cf ON c.cnum = cf.cnum"
					+ " WHERE c.cnum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShopDTO dto = new ShopDTO();
				
				dto.setFileNum(rs.getInt("fileNum"));
				dto.setCnum(rs.getInt("cNum"));
				dto.setImageFilename(rs.getString("fileName"));
				list.add(dto);
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
		return list;
	}
	
	public int deletePhotoFile(String mode, int cnum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if (mode.equals("all")) {
				sql = "DELETE FROM clothes_file WHERE cnum = ?";
			} else {
				sql = "DELETE FROM clothes_file WHERE fileNum = ?";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cnum);

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
	
	public int updateGarment(ShopDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE clothes SET "
					+ " registrant=?, tNum2=?, clothName=?, displayYN=?, "
					+ " cComent=?, price=?, discount=?, unitprice=?, season=? "
					+ " WHERE cnum = ? ";	
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setInt(2, dto.getTnum());
			pstmt.setString(3, dto.getClothname());
			pstmt.setInt(4, dto.getDisplayYN());
			pstmt.setString(5, dto.getCcoment());
			pstmt.setInt(6, dto.getPrice());
			pstmt.setInt(7, dto.getDiscount());
			pstmt.setInt(8, dto.getUnitPrice());
			pstmt.setString(9, dto.getSeason());
			pstmt.setInt(10, dto.getCnum());
			result = pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if (dto.getImageFiles() != null) {
				sql = "UPDATE clothes_file SET filenum =clothes_file_SEQ.NEXTVAL, filename=? "
						+ " WHERE cnum=?";	
				pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setString(1, dto.getImageFiles()[i]);
					pstmt.setInt(2, dto.getCnum());
					result = pstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	// 색상/사이즈관리에서 나오는 색상데이터
	public int colorDataCount(int cnum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM color_detail WHERE cnum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cnum);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	// 관리자 색상/사이즈 관리에서 사용하는 list
	public List<ShopDTO> colorList(int start, int end, int cnum) {
		List<ShopDTO> colorList = new ArrayList<ShopDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("        SELECT c.ccnum, c.cnum, color, sizes FROM color_detail c");
			sb.append("        JOIN clothes_detail cd ON c.ccnum = cd.ccnum");
			sb.append("        WHERE cnum = ?");
			sb.append("        ) tb WHERE ROWNUM <= ?");
			sb.append("    ) WHERE rnum >= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, cnum);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShopDTO dto = new ShopDTO();
				dto.setCcnum(rs.getInt("ccnum"));
				dto.setCnum(rs.getInt("cnum"));
				dto.setColor(rs.getString("color"));
				dto.setSize(rs.getString("sizes"));
				colorList.add(dto);
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
		
		return colorList;
	}
	
	// 상품상세에서 color
	public List<ShopDTO> colorList(int cnum) {
		List<ShopDTO> colorList = new ArrayList<ShopDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			// sb.append("SELECT cd.cdnum, cnum, sizes, color FROM color_detail c");
			// sb.append("		JOIN clothes_detail cd ON c.ccnum = cd.ccnum");
			// sb.append(" 	WHERE c.cnum = ?");
			
			sb.append("SELECT c.cnum, ccnum, color FROM color_detail cd");
			sb.append("		JOIN clothes c ON c.cnum = cd.cnum");
			sb.append("		WHERE c.cnum = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, cnum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShopDTO dto = new ShopDTO();
				dto.setCcnum(rs.getInt("ccnum"));
				dto.setColor(rs.getString("color"));
				dto.setCdnum(rs.getInt("cnum"));
				colorList.add(dto);
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
		
		return colorList;
	}
	
	// 상품 상세에서 컬러 누르면 나오는 사이즈들
	public List<ShopDTO> sizeList(int ccnum) {
		List<ShopDTO> sizeList = new ArrayList<ShopDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			
			sb.append("SELECT c.ccnum, cdnum, cnum, color, cdnum, sizes ");
			sb.append(" 	FROM color_detail c");
			sb.append("		JOIN clothes_detail cd ON c.ccnum = cd.ccnum ");
			sb.append("		WHERE c.ccnum = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, ccnum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShopDTO dto = new ShopDTO();
				dto.setCcnum(rs.getInt("ccnum"));
				dto.setColor(rs.getString("color"));
				dto.setCdnum(rs.getInt("cnum"));
				dto.setCcnum(rs.getInt("ccnum"));
				dto.setSize(rs.getString("sizes"));
				dto.setCdnum(rs.getInt("cdnum"));
				sizeList.add(dto);
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
		
		return sizeList;
	}
	
}
