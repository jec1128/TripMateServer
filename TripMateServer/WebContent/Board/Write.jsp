<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import = "org.json.simple.JSONArray" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%@ page import = "org.json.simple.parser.JSONParser"%>
<%@ page import = "org.json.simple.parser.ParseException" %>
<%@ page import = "board.Board" %>
<%@ page import = "board.BoardDAO" %>

<%
	request.setCharacterEncoding("UTF-8");
	String destination = request.getParameter("destination");
	String content = request.getParameter("content");
	String gender1 = request.getParameter("gender");
	int gender = Integer.parseInt(gender1);
	String minage1 = request.getParameter("minage");
	int minage = Integer.parseInt(minage1);
	String maxage1 = request.getParameter("maxage");
	int maxage = Integer.parseInt(maxage1);
	String date1 = request.getParameter("date");
	
	
	
	String thema1 = request.getParameter("thema1");
	String thema2 = request.getParameter("thema2");
	String thema3 = request.getParameter("thema3"); 
	String email = request.getParameter("email");
/* 
	UserDAO userDAO = new UserDAO();
	User user = new User(id, password, nickname, age, gender, email); 

	String result = userDAO.register(user);
	System.out.println("register : " + result);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject1 = new JSONObject();

	jObject1.put("msg", result);
	jArray.add(0, jObject1);
	jsonMain.put("register", jArray);
	out.print(jsonMain.toJSONString());
	out.flush(); */
%>
	
