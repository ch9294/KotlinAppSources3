<%@ page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%

	String str1 = request.getParameter("mobile_idx");
	int mobile_idx = Integer.parseInt(str1);

	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "1234";
	
	Connection db = DriverManager.getConnection(url, id, pw);
	
	String sql = "select mobile_img, mobile_str1, mobile_str2 from mobile_table where mobile_idx=?";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setInt(1, mobile_idx);
	
	ResultSet rs = pstmt.executeQuery();
	rs.next();
	
	JSONObject root = new JSONObject();

	String mobile_str1 = rs.getString("mobile_str1");
	String mobile_str2 = rs.getString("mobile_str2");
	String mobile_img = rs.getString("mobile_img");
	
	root.put("mobile_str2", mobile_str2);
	root.put("mobile_str1", mobile_str1);
	root.put("mobile_img", mobile_img);
		
	
	db.close();
%>
<%= root.toJSONString() %>