package board;

import java.sql.Date;

public class Board {
	private String boardCode;
	private String userCode;
	private String destination;
	private String content;
	private int gender;
	private int minage;
	private int maxage;
	private String matchingstartDatetime;
	private String matchingendDatetime;
	private String thema1;
	private String thema2;
	private String thema3;
	private String noticeDatetime;
	
	
	public Board(String userCode, String destination, String content, int gender, int minage, int maxage,
			String matchingstartDatetime, String matchingendDatetime, String thema1, String thema2, String thema3) {
		this.userCode = userCode;
		this.destination = destination;
		this.content = content;
		this.gender = gender;
		this.minage = minage;
		this.maxage = maxage;
		this.matchingstartDatetime = matchingstartDatetime;
		this.matchingendDatetime = matchingendDatetime;
		this.thema1 = thema1;
		this.thema2 = thema2;
		this.thema3 = thema3;
	}
	public Board() {
		// TODO Auto-generated constructor stub
	}
	public String getBoardCode() {
		return boardCode;
	}
	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getMinage() {
		return minage;
	}
	public void setMinage(int minage) {
		this.minage = minage;
	}
	public int getMaxage() {
		return maxage;
	}
	public void setMaxage(int maxage) {
		this.maxage = maxage;
	}
	
	public String getMatchingstartDatetime() {
		return matchingstartDatetime;
	}
	public void setMatchingstartDatetime(String matchingstartDatetime) {
		this.matchingstartDatetime = matchingstartDatetime;
	}
	public String getMatchingendDatetime() {
		return matchingendDatetime;
	}
	public void setMatchingendDatetime(String matchingendDatetime) {
		this.matchingendDatetime = matchingendDatetime;
	}
	public String getThema1() {
		return thema1;
	}
	public void setThema1(String thema1) {
		this.thema1 = thema1;
	}
	public String getThema2() {
		return thema2;
	}
	public void setThema2(String thema2) {
		this.thema2 = thema2;
	}
	public String getThema3() {
		return thema3;
	}
	public void setThema3(String thema3) {
		this.thema3 = thema3;
	}
	public String getNoticeDatetime() {
		return noticeDatetime;
	}
	public void setNoticeDatetime(String noticeDatetime) {
		this.noticeDatetime = noticeDatetime;
	}
	
	
	
}
