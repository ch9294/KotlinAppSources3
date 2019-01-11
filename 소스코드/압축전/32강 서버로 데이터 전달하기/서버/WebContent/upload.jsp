<%@ page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
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
	
	System.out.println(mobile_str1);
	System.out.println(mobile_str2);
	System.out.println(mobile_img);
%>