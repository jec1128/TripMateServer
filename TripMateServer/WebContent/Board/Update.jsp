<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import = "org.json.simple.JSONArray" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%@ page import = "org.json.simple.parser.JSONParser"%>
<%@ page import = "org.json.simple.parser.ParseException" %>
<%@ page import = "user.UserDAO" %>
<%@ page import = "board.Board" %>
<%@ page import = "board.BoardDAO" %>
<%@ page import = "DateTime.format" %>

<%
	request.setCharacterEncoding("UTF-8");
	String boardCode = request.getParameter("boardcode");
	String nickname = request.getParameter("nickname");
	UserDAO userDAO = new UserDAO();
	String usercode = userDAO.codeSearch(nickname);
	String destination = request.getParameter("destination");
	String content = request.getParameter("content");
	String gender1 = request.getParameter("gender");
	int gender = Integer.parseInt(gender1);
	String minage1 = request.getParameter("minage");
	int minage = Integer.parseInt(minage1);
	String maxage1 = request.getParameter("maxage");
	int maxage = Integer.parseInt(maxage1);
	
	String date1 = request.getParameter("date");
	String starttime1 = request.getParameter("starttime");
	String endtime1 = request.getParameter("endtime");
	
	format dateformat = new format();
	
	String startdatetime = dateformat.changeDateFormat(date1 + starttime1);
	String enddatetime = dateformat.changeDateFormat(date1 + endtime1);
	
	String thema1 = request.getParameter("thema1");
	String thema2 = request.getParameter("thema2");
	String thema3 = request.getParameter("thema3"); 
	
	
	BoardDAO boardDAO = new BoardDAO();
	Board board = new Board(usercode,destination,content,gender,minage,maxage,startdatetime,enddatetime,thema1,thema2,thema3); 

	String result = boardDAO.update(boardCode,board);
	System.out.println("update : " + result);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject1 = new JSONObject();

	jObject1.put("msg", result);
	jArray.add(0, jObject1);
	jsonMain.put("update", jArray);
	out.print(jsonMain.toJSONString());
	out.flush(); 
%>
	