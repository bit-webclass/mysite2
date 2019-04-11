package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	public List<BoardVo> getList(){
		List<BoardVo> bList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";		
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		// 3. SQL문 준비 / 바인딩 / 실행
			String query =" SELECT BO.no " 
						 +"      , BO.title "
						 +"      , BO.content "
						 +"      , US.name "
						 +"      , BO.hit "
						 +"      , TO_CHAR(BO.reg_date, 'YYYY-MM-DD HH:MI') regDate "
						 +"		 , US.no userNo"
						 +" FROM board BO, users US "
						 +" WHERE  BO.user_no = US.no ";
						 
			pstmt = conn.prepareStatement(query);
			
		// 4.결과처리
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setUserName(name);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setUserNo(userNo);
				
				bList.add(vo);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
			try {
				if (rs != null) { 
						rs.close();
					}
				
				if (pstmt != null) {
						pstmt.close();
					}
				
				if (conn != null) {
						conn.close();
					}
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
		return bList;
	}
	
	
	public void write(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";		//프로젝트별 수정 필요
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		// 3. SQL문 준비 / 바인딩 / 실행
			String query = " INSERT INTO board " 
						 + " VALUES (seq_board_no.NEXTVAL, ?, ?, 0, SYSDATE, ?) ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUserNo());
			
		// 4.결과처리
			int result = pstmt.executeUpdate();
			System.out.println(result + "건 작성완료");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
			try {
				if (rs != null) {
						rs.close();
					}
				if (pstmt != null) {
						pstmt.close();
					}
				if (conn != null) {
						conn.close();
					}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
	}

	public BoardVo select(int no) {
		BoardVo boardVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";		//프로젝트별 수정 필요
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		// 3. SQL문 준비 / 바인딩 / 실행
			String query = " SELECT BO.no " 
						 + "      , BO.title "
						 + "      , BO.content "
						 + "      , US.name "
						 + "      , BO.hit "
						 + "      , TO_CHAR(BO.reg_date, 'YYYY-MM-DD HH:MI') regDate "
						 + "      , US.no userNo"
						 + " FROM board BO, users US "
						 + " WHERE  BO.user_no = US.no "
						 +"  AND	BO.no = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
		// 4.결과처리
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int tno = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");
				
				boardVo = new BoardVo();
				boardVo.setNo(no);
				boardVo.setTitle(title);
				boardVo.setContent(content);
				boardVo.setUserName(name);
				boardVo.setHit(hit);
				boardVo.setRegDate(regDate);
				boardVo.setUserNo(userNo);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
			try {
				if (rs != null) {
						rs.close();
					}
				if (pstmt != null) {
						pstmt.close();
					}
				if (conn != null) {
						conn.close();
					}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		return boardVo;
	}

	public void upCount(int no) {
		BoardVo boardVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";		//프로젝트별 수정 필요
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		// 3. SQL문 준비 / 바인딩 / 실행
			String query = " UPDATE board "
						 + " SET hit = hit + 1 "
						 + " WHERE no = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
		// 4.결과처리
			int result = pstmt.executeUpdate();
			System.out.println("조회수 "+result+"증가");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
			try {
				if (rs != null) {
						rs.close();
					}
				if (pstmt != null) {
						pstmt.close();
					}
				if (conn != null) {
						conn.close();
					}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
	}

	public void delete(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";		//프로젝트별 수정 필요
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		// 3. SQL문 준비 / 바인딩 / 실행
			String query = " DELETE board "
						 + " WHERE no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
		// 4.결과처리
			int result = pstmt.executeUpdate();
			System.out.println(result + "건 제거완료");
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
			try {
				if (rs != null) {
						rs.close();
					}
				if (pstmt != null) {
						pstmt.close();
					}
				if (conn != null) {
						conn.close();
					}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
	}

	public void modify(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";		//프로젝트별 수정 필요
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		// 3. SQL문 준비 / 바인딩 / 실행
			String query = " UPDATE board "
						 + " SET 	title = ? "
						 + "	  , content = ? "
						 + " WHERE no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());
		// 4.결과처리
			int result = pstmt.executeUpdate();
			System.out.println(result + "건 수정 완료");
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
			try {
				if (rs != null) {
						rs.close();
					}
				if (pstmt != null) {
						pstmt.close();
					}
				if (conn != null) {
						conn.close();
					}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
	}
	

}
