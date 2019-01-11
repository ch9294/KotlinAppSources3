<%@ page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="java.sql.*" %>
<%
	request.setCharacterEncoding("utf-8");

	String path = getServletContext().getRealPath("upload");
	// System.out.println(path);
	int max = 1024 * 1024 * 100;
	DefaultFileRenamePolicy policy = new DefaultFileRenamePolicy();
	
	MultipartRequest mr = new MultipartRequest(request, path, max, "utf-8", policy);
	
	String mobile_str1 = mr.getParameter("mobile_str1");
	String mobile_str2 = mr.getParameter("mobile_str2");
	String mobile_img = mr.getFilesystemName("mobile_img");

//	System.out.println(mobile_str1);
//	System.out.println(mobile_str2);
//	System.out.println(mobile_img);

	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "1234";
	
	Connection db = DriverManager.getConnection(url, id, pw);
	
	String sql = "insert into mobile_table (mobile_idx, mobile_img, mobile_str1, mobile_str2) values (mobile_seq.nextval, ?, ?, ?)";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setString(1, mobile_img);
	pstmt.setString(2, mobile_str1);
	pstmt.setString(3, mobile_str2);
	
	pstmt.execute();
	
	db.close();
%>
OK












