<%@ page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	request.setCharacterEncoding("utf-8");

	String token = request.getParameter("token");
	
	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "1234";
	
	Connection db = DriverManager.getConnection(url, id, pw);
	
	String sql = "select * from fcm_table where fcm_token=?";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setString(1, token);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs.next() == false){
		String sql2 = "insert into fcm_table (fcm_idx, fcm_token) values (fcm_seq.nextval, ?)";
		PreparedStatement pstmt2 = db.prepareStatement(sql2);
		pstmt2.setString(1, token);
		pstmt2.execute();
	}
	db.close();
%>
OK













