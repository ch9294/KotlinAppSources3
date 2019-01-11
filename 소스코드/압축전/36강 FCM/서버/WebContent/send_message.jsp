<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.google.android.gcm.server.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%
	request.setCharacterEncoding("utf-8");

	String [] token_list = request.getParameterValues("token");
	String msg = request.getParameter("msg");
	
	ArrayList<String> token = new ArrayList<String>();
	for(String str1 : token_list){
		token.add(str1);
	}
	
	Random rnd = new Random();
	String MESSAGE_ID = rnd.nextLong() + "";
	boolean SHOW_ON_IDLE = false;
	int LIVE_TIME = 1;
	int RETRY = 2;
	
	msg = URLEncoder.encode(msg, "EUC-KR");
	
	String api_key = "AAAAuwzhPUA:APA91bFuXrQMdmUuhWjpzXt2fkcF7w1YNm3duNkUThsRlf4ZL-ZuBGvdYCO8H80a7kn55LetJX4f5jk1W1FZ52e_2Kn1pNrMGp4eYaNoyCDb9GnbS0uEwMXgPniceRkRZJzbkdY6_WUvog05x1UwWbYDmBFlVxgbZw";
	
	Sender sender = new Sender(api_key);
	Message.Builder builder = new Message.Builder();
	builder.collapseKey(MESSAGE_ID);
	builder.delayWhileIdle(SHOW_ON_IDLE);
	builder.timeToLive(LIVE_TIME);
	builder.addData("msg", msg);
	Message message = builder.build();
	
	MulticastResult result1 = sender.send(message, token, RETRY);
	
	if( result1 != null){
		List<Result> result_list = result1.getResults();
		for(Result result : result_list){
			System.out.println(result.getErrorCodeName());
		}
	}
%>
전송완료










