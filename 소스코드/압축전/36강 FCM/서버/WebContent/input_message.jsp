<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%
	request.setCharacterEncoding("utf-8");

	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "1234";
	
	Connection db = DriverManager.getConnection(url, id, pw);
	
	String sql = "select * from fcm_table";
	PreparedStatement pstmt = db.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery();
	
	ArrayList<String> token_list = new ArrayList<String>();
	
	while(rs.next()){
		token_list.add(rs.getString("fcm_token"));
	}
	db.close();
%>
<form action="send_message.jsp" method="post">
<% for(String str1 : token_list){ %>
	<input type="checkbox" name="token" value="<%=str1 %>"/><%=str1 %><br/>
<% } %>
	<textarea name="msg"></textarea><br/>
	<button type="submit">메시지 보내기</button>
</form>














