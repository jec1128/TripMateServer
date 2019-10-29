package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() throws ClassNotFoundException, SQLException {
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

	public int getNext() {
		//String SQL = "SELECT COUNT(*) as cnt FROM user";
		String SQL = "SELECT user_code FROM user ORDER BY user_code DESC Limit 1"; // 가장 밑에 있는 숫자 가져오기
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String code = rs.getString(1);
				String number1 = code.substring(5);  // USER- 5개 생략 하고 숫자만
				int number = Integer.parseInt(number1);
				return number + 1;
			}
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public String login(String userID, String userPassword) {
		String SQL = "SELECT user_pw From user WHERE user_id = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equals(userPassword)) {
					return "success"; // 로그인 성공
				} else
					return "password-wrong"; // 로그인 실패
			}
			return "no-id"; // id가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // DB오류
	}

	public String register(User user) {
		String SQL = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			String code = makeCode();
			if("error".equals(code)) {
				return "error" ;
			}
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, makeCode());
			pstmt.setString(2, user.getUserID());
			pstmt.setString(3, user.getUserPassword());
			pstmt.setString(4, user.getUserNickname());
			pstmt.setInt(5, user.getUserAge());
			pstmt.setInt(6, user.getUserGender());
			/*
			 * pstmt.setString(7, user.getUserInterest1()); pstmt.setString(8,
			 * user.getUserInterest2()); pstmt.setString(9, user.getUserInterest3());
			 */
			pstmt.setString(7, user.getUserEmail());
			pstmt.setInt(8, 0);
			int result = pstmt.executeUpdate();
			if (result >= 0)
				return "success"; // insert문을 실행하면 무조건 0이상의 수가 반환됨

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "db-error"; // 데이터베이스 오류
	}

	public String checkIdDuplication(String id) {
		String SQL = "SELECT * From user";
		ArrayList<User> list = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserID(rs.getString(2));
				list.add(user);
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getUserID().equals(id)) {
					return "duplication";
				}
			}
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // DB오류
	}

	public String checkNicknameDuplication(String nickname) {
		String SQL = "SELECT * From user";
		ArrayList<User> list = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserNickname(rs.getString(4));
				list.add(user);
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getUserNickname().equals(nickname)) {
					return "duplication";
				}
			}
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // DB오류
	}

	public String emailSearch(String id) {
		String SQL = "SELECT user_email From user WHERE user_id = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String email = rs.getString(1);
				return email;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	public String nicknameSearch(String id) {
		String SQL = "SELECT user_nick From user WHERE user_id = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String nickname = rs.getString(1);
				return nickname;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	public String makeCode() {
		String idx = "USER-";
		int num = getNext();
		if(num == -1)
			return "error";
		String code = idx + num;
		return code;
	}

}
