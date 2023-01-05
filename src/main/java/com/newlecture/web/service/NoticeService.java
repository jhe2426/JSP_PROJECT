package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
// 데이터베이스에 연결되어 값을 출력하거나, 입력,수정,삭제를 하는 클래스
public class NoticeService implements NoticeDAO{

	
	public int pubNoticeAll(int[] oids, int[] cids){
		
		List<String> oidsList = new ArrayList<String>();
		// 정수형 배열을 문자열 배열로 형변환하는 과정
		for(int i=0; i<oids.length; i++)
			//String.valueOf 형변환해주는 메서드
			oidsList.add(String.valueOf(oids[i]));
		
		List<String> cidsList = new ArrayList<String>();
		// 정수형 배열을 문자열 배열로 형변환하는 과정
		for(int i=0; i<cids.length; i++)
			//String.valueOf 형변환해주는 메서드
			oidsList.add(String.valueOf(cids[i]));
	
		return pubNoticeAll(oidsList,cidsList);
	}
	public int pubNoticeAll(List<String> oids, List<String> cids){
		//String.join() 여러개의 문자열을 구분자를 넣어 하나의 문자열로 만들어주는 메서드
		String oidsCSV = String.join(",", oids);
		String cidsCSV = String.join(",", cids);
		return pubNoticeAll(oidsCSV,cidsCSV);
	}
	public int pubNoticeAll(String oidsCSV, String cidsCSV){ //CSV는 콤마로 구분된 값
		int result = 0;
		// 선택적이 아니라 항상 open이랑 close값들이 들어 오므로 쿼리문이 2개가 필요한 것
		String sqlOpen = String.format("UPDATE NOTICE SET PUB=1 WHERE ID IN (%s)",oidsCSV);
		String sqlClose = String.format("UPDATE NOTICE SET PUB=0 WHERE ID IN (%s)",cidsCSV);

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"system","k404");
			Statement stOpen = con.createStatement();
			result += stOpen.executeUpdate(sqlOpen);
			
			Statement stClose = con.createStatement();
			result += stClose.executeUpdate(sqlClose);
			
			stClose.close();
			stOpen.close();
			con.close(); 
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public int insertNotice(Notice notice){
		
		
				int result = 0;
				
				String sql = "INSERT INTO NOTICE(TITLE, CONTENT, WRITER_ID, PUB, FILES) VALUES(?,?,?,?,?)";
				
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection(url,"system","k404");
					PreparedStatement st = con.prepareStatement(sql);
					st.setString(1,notice.getTitle());
					st.setString(2, notice.getContent());
					st.setString(3,notice.getWriterId());
					st.setBoolean(4, notice.getPub());
					st.setString(5,notice.getFiles());
					result = st.executeUpdate();

					st.close();
					con.close(); 
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				
				return result;
	}


	
	
	
	
	
	public List<NoticeView> getNoticeList(){
		return getNoticeList("title","",1);
	}
	
	public List<NoticeView> getNoticeList(int page){
		
		
		// String query 검색어가 들어 가는 매개변수
		// 검색어가 없기 때문에 전체의 게시판 목록을 알 수 있으므로 저렇게 사용하는 것
		return getNoticeList("title","",page);
		
	}

	/*
	 * 같은 이름의 메서드에서 매개변수 갯수만 다르므로(오버로드 메서드) 매개변수가 가장 많은 메서드를 구현해주고 다른 메서드는 
	 * 그 내용을 사용하게 되어 코드의 중복을 없앨 수 있다.
	 */
	public List<NoticeView> getNoticeList(String field/*TITLE(제목), WRITER_ID(작성자)*/, String query/*A*/ ,int page){
		
		List<NoticeView> list = new ArrayList<NoticeView>();
		String sql =" SELECT * FROM("
				+ " SELECT ROWNUM NUM, N.* " // ?로 String값으로(String형식으로) 집어넣게 되면 ''가 감싸기 때문에 field부분은 밑에 처럼 삽입한 것
				+ " FROM (SELECT * FROM NOTICE_VIEW WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
				+ ") "
				+ "WHERE NUM BETWEEN ? AND ?"; 
		// 첫 번째 ? -> 1,11,21,31... 등차수열 -> an = 1+(page-1)*10
		// 두 번째 ? -> 10,20,30,40... -> page*10
		

		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"system","k404");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			ResultSet rs = st.executeQuery();


			while(rs.next()) { 
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			Date regdate = rs.getDate("REGDATE");
			String writerId = rs.getString("WRITER_ID");
			String hit = rs.getString("HIT");
			String files = rs.getString("FILES");
			//String content = rs.getString("CONTENT");
			int cmtCount = rs.getInt("CMT_COUNT");
			boolean pub = rs.getBoolean("PUB");
			NoticeView notice = new NoticeView(
					id,
					title,
					regdate,
					writerId,
					hit,
					files,
					pub,
					//content,
					cmtCount
					);
			
			list.add(notice);
			}  
			
			
			
			
			rs.close();
			st.close();
			con.close(); 
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
				
		return list;
	}
	
	public List<NoticeView> getNoticePubList(String field, String query, int page) {
		
		List<NoticeView> list = new ArrayList<NoticeView>();
		String sql =" SELECT * FROM("
				+ " SELECT ROWNUM NUM, N.* " // ?로 String값으로(String형식으로) 집어넣게 되면 ''가 감싸기 때문에 field부분은 밑에 처럼 삽입한 것
				+ " FROM (SELECT * FROM NOTICE_VIEW WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
				+ ") "
				+ "WHERE PUB=1 AND NUM BETWEEN ? AND ?"; 
		// 첫 번째 ? -> 1,11,21,31... 등차수열 -> an = 1+(page-1)*10
		// 두 번째 ? -> 10,20,30,40... -> page*10
		

		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"system","k404");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			ResultSet rs = st.executeQuery();


			while(rs.next()) { 
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			Date regdate = rs.getDate("REGDATE");
			String writerId = rs.getString("WRITER_ID");
			String hit = rs.getString("HIT");
			String files = rs.getString("FILES");
			//String content = rs.getString("CONTENT");
			int cmtCount = rs.getInt("CMT_COUNT");
			boolean pub = rs.getBoolean("PUB");
			NoticeView notice = new NoticeView(
					id,
					title,
					regdate,
					writerId,
					hit,
					files,
					pub,
					//content,
					cmtCount
					);
			
			list.add(notice);
			}  	
			
			rs.close();
			st.close();
			con.close(); 
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
				
		return list;
	}
	
	public int getNoticeCount() {
		return getNoticeCount("title", "");
	}
	
	public int getNoticeCount(String field, String query) {
		int count = 0;
		String sql =" SELECT COUNT(ID) COUNT FROM("
				+ " SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICE WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
				+ ") ";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"system","k404");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
			count = rs.getInt("count");
			
			rs.close();
			st.close();
			con.close(); 
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return count;
	}
	
	public Notice getNotice(int id) {
		Notice notice = null;
		String sql = "SELECT * FROM NOTICE WHERE ID =?";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"system","k404");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();


			if(rs.next()) { 
			int nid = rs.getInt("ID");
			String title = rs.getString("TITLE");
			Date regdate = rs.getDate("REGDATE");
			String writerId = rs.getString("WRITER_ID");
			String hit = rs.getString("HIT");
			String files = rs.getString("FILES");
			String content = rs.getString("CONTENT");
			boolean pub = rs.getBoolean("PUB");
			notice = new Notice(
					nid,
					title,
					regdate,
					writerId,
					hit,
					files,
					content,
					pub
					);
			

			}  
				
			rs.close();
			st.close();
			con.close(); 
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return notice;
	}
	public Notice getNextNotice(int id) {
		Notice notice = null;
		String sql = "SELECT * FROM NOTICE "
				+ "WHERE ID = (SELECT ID FROM NOTICE WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID = ?) "
				+ "AND ROWNUM = 1"
				+ ")";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"system","k404");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();


			if(rs.next()) { 
			int nid = rs.getInt("ID");
			String title = rs.getString("TITLE");
			Date regdate = rs.getDate("REGDATE");
			String writerId = rs.getString("WRITER_ID");
			String hit = rs.getString("HIT");
			String files = rs.getString("FILES");
			String content = rs.getString("CONTENT");
			boolean pub = rs.getBoolean("PUB");
			notice = new Notice(
					nid,
					title,
					regdate,
					writerId,
					hit,
					files,
					content,
					pub
					);
			

			}  
				
			rs.close();
			st.close();
			con.close(); 
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return notice;
	}
	public Notice getPrevNotice(int id) {
		Notice notice = null;
		String sql = "SELECT * FROM NOTICE "
				+ "WHERE ID = (SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC) "
				+ "WHERE REGDATE < (SELECT REGDATE  FROM NOTICE  WHERE ID = ? ) "
				+ "AND ROWNUM = 1);";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"system","k404");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();


			if(rs.next()) { 
			int nid = rs.getInt("ID");
			String title = rs.getString("TITLE");
			Date regdate = rs.getDate("REGDATE");
			String writerId = rs.getString("WRITER_ID");
			String hit = rs.getString("HIT");
			String files = rs.getString("FILES");
			String content = rs.getString("CONTENT");
			boolean pub = rs.getBoolean("PUB");
			notice = new Notice(
					nid,
					title,
					regdate,
					writerId,
					hit,
					files,
					content,
					pub
					);
			

			}  
				
			rs.close();
			st.close();
			con.close(); 
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return notice;
	}
	public int deleteNoticeAll(int[] ids) {
		// 몇 개의 게시물을 삭제했는지 알기 위해만든 변수
		int result = 0;
		String params ="";
		//ids.length가 3이다.
		// i는 0,1,2까지 반복을 할 것
		// 그니깐 마지막 id에는 ,를 붙이면 안되므로
		// ids.length의 길이에 1을 빼주면 i가 반복하다가 마지막의 인덱스와 숫자가 같게 되니깐
		// ids.length-1보가 작은 i 인덱스에만 ,를 넣어주게 되면 마지막 문자에는 ,가 들어가지 않게 됨
		for(int i=0; i<ids.length; i++) {
			params += ids[i];
			if(i < ids.length-1) // 마지막 id에는 쉼표가 들어가지 않게 하기위해	
				params += ","; // ids.length에 -1를 뺀 것
		}
		String sql = "DELETE NOTICE WHERE ID IN ("+params+")";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"system","k404");
			Statement st = con.createStatement();
			//executeUpdate는 int를 반환함
			// 여기에서는 삭제된 갯수가 반환이 됨
			result = st.executeUpdate(sql);

			st.close();
			con.close(); 
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return result;
		
	}
	
	
}
