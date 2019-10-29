package board;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public BoardDAO() throws ClassNotFoundException, SQLException {
		try {
			String URL = "jdbc:mysql://localhost:3306/tripmate?serverTimezone=Asia/Seoul&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
			String id = "root";
			String password = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, id, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public int getNext() {
		String SQL = "SELECT user_code FROM user ORDER BY user_code DESC Limit 1"; // 가장 밑에 있는 숫자 가져오기
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String code = rs.getString(1);
				String number1 = code.substring(6);  // USER- 5개 생략 하고 숫자만
				int number = Integer.parseInt(number1);
				return number + 1;
			}
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}
	public String makeCode() {
		String idx = "BOARD-";
		int num = getNext();
		if(num == -1)
			return "error";
		String code = idx + num;
		return code;
	}
	
	public int write(String bbsTitle, int userNumber, String bbsContent,String bbsfileName, String bbsfileRealName) {
		String SQL = "INSERT INTO bbs VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,getNext());
			pstmt.setInt(2, userNumber);
			pstmt.setString(3, bbsTitle);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6,1);
			pstmt.setString(7,bbsfileName);
			pstmt.setString(8, bbsfileRealName);
			pstmt.setInt(9, 0);
			pstmt.setInt(10, 0);
			pstmt.setInt(11, 0);
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1; //데이터베이스 오류
	}
}
