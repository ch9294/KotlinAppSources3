<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String method = request.getMethod();

	System.out.println("요청방식 : " + method);
	
	request.setCharacterEncoding("utf-8");
	
	String data1 = request.getParameter("data1");
	String data2 = request.getParameter("data2");
	String [] data3 = request.getParameterValues("data3");
	
	System.out.println("data1 : " + data1);
	System.out.println("data2 : " + data2);
	if(data3 != null){
		for(String str : data3){
			System.out.println("data3 : " + str);
		}
	}
%>
{
	"value1" : 100,
	"value2" : 11.11,
	"value3" : "문자열 데이터"
}