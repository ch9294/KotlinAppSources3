<%@ page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%
	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "1234";
	
	Connection db = DriverManager.getConnection(url, id, pw);
	
	String sql = "select mobile_idx, mobile_img, mobile_str1 from mobile_table order by mobile_idx desc";
	PreparedStatement pstmt = db.prepareStatement(sql);
	
	ResultSet rs = pstmt.executeQuery();
	
	JSONArray root = new JSONArray();
	
	while(rs.next()){
		int mobile_idx = rs.getInt("mobile_idx");
		String mobile_str1 = rs.getString("mobile_str1");
		String mobile_img = rs.getString("mobile_img");
		
		JSONObject obj = new JSONObject();
		obj.put("mobile_idx", mobile_idx);
		obj.put("mobile_str1", mobile_str1);
		obj.put("mobile_img", mobile_img);
		
		root.add(obj);
	}
	
	db.close();
%>
<%= root.toJSONString() %>