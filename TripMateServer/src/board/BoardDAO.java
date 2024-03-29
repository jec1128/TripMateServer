package board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import board.Board;
import user.UserDAO;

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
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public int getNext() {
		String SQL = "SELECT COUNT(*) as cnt FROM matchingboard";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int size = rs.getInt(1);
				return size + 1;
			} else
				return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public String makeCode() {
		String idx = "BOARD-";
		int num = getNext();
		if (num == -1)
			return "error";
		String code = idx + num;
		return code;
	}

	public String write(Board board) {
		String SQL = "INSERT INTO matchingboard VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			String code = makeCode();
			if ("error".equals(code)) {
				return "error";
			}
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, makeCode());
			pstmt.setString(2, board.getUserCode());
			pstmt.setString(3, board.getDestination());
			pstmt.setString(4, board.getContent());
			pstmt.setInt(5, board.getGender());
			pstmt.setInt(6, board.getMinage());
			pstmt.setInt(7, board.getMaxage());
			pstmt.setString(8, board.getMatchingstartDatetime());
			pstmt.setString(9, board.getMatchingendDatetime());
			pstmt.setString(10, board.getPurpose());
			pstmt.setString(11, getDate());
			pstmt.setInt(12, 0);
			pstmt.setInt(13, getNext());
			int result = pstmt.executeUpdate();
			if (result >= 0)
				return "success"; // insert문을 실행하면 무조건 0이상의 수가 반환됨

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "db-error"; // 데이터베이스 오류
	}

	public ArrayList<Board> getList(int pageNumber) {
		String SQL = "SELECT * FROM matchingboard WHERE board_number < ? AND delete_state = 0 ORDER BY notice_datetime DESC limit 10";
		ArrayList<Board> list = new ArrayList<Board>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();

				board.setBoardCode(rs.getString(1));
				board.setUserCode(rs.getString(2));
				board.setDestination(rs.getString(3));
				board.setContent(rs.getString(4));
				board.setGender(rs.getInt(5));
				board.setMinage(rs.getInt(6));
				board.setMaxage(rs.getInt(7));
				board.setMatchingstartDatetime(rs.getString(8));
				board.setMatchingendDatetime(rs.getString(9));
				board.setPurpose(rs.getString(10));
				board.setNoticeDatetime(rs.getString(11));
				list.add(board);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public Board getBoard(String boardcode) {
		String SQL = "SELECT * FROM matchingboard WHERE board_code = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, boardcode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setBoardCode(rs.getString(1));
				board.setUserCode(rs.getString(2));
				board.setDestination(rs.getString(3));
				board.setContent(rs.getString(4));
				board.setGender(rs.getInt(5));
				board.setMinage(rs.getInt(6));
				board.setMaxage(rs.getInt(7));
				board.setMatchingstartDatetime(rs.getString(8));
				board.setMatchingendDatetime(rs.getString(9));
				board.setPurpose(rs.getString(10));
				board.setNoticeDatetime(rs.getString(11));
				return board;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String update(String boardCode, Board board) {
		String SQL = "UPDATE matchingboard SET destination = ?, content = ?, party_gender = ?, party_min_age = ?, party_max_age = ?, matching_start_datetime = ?, matching_end_datetime = ?, trip_purpose = ?, notice_datetime = NOW() WHERE board_code = ?";
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, board.getDestination());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getGender());
			pstmt.setInt(4, board.getMinage());
			pstmt.setInt(5, board.getMaxage());
			pstmt.setString(6, board.getMatchingstartDatetime());
			pstmt.setString(7, board.getMatchingendDatetime());
			pstmt.setString(8, board.getPurpose());
			pstmt.setString(9, boardCode);

			int result = pstmt.executeUpdate();
			if (result >= 0) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // 데이터베이스 오류
	}

	public String delete(String boardCode) {
		String SQL = "UPDATE matchingboard SET delete_state = 1 WHERE board_code = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, boardCode);
			int result = pstmt.executeUpdate();
			if (result >= 0)
				return "success";
			else
				return "error";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	public ArrayList<Board> getMatchingList(String destination, int gender, int minage, int maxage, int userage,
			String startdatetime, String purpose) {
		String SQL = null;

		SQL = "SELECT * FROM matchingboard WHERE delete_state = 0 AND trip_purpose = ? AND destination = ? AND party_min_age <= ? AND party_max_age >= ? AND matching_start_datetime < "
				+ "'" + startdatetime + "'" + " AND matching_end_datetime > " + "'" + startdatetime + "'"
				+ " ORDER BY notice_datetime DESC limit 20";

		ArrayList<Board> list = new ArrayList<Board>();
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, purpose);
			pstmt.setString(2, destination);
			pstmt.setInt(3, userage); // 조건 검색하는 사람의 나이
			pstmt.setInt(4, userage);
			rs = pstmt.executeQuery();
			int count = 0;
			UserDAO userDAO = new UserDAO();
			while (rs.next()) {
				count++;
				int age1 = userDAO.usercodeToAge(rs.getString(2));
				int gender1 = userDAO.usercodeToGender(rs.getString(2));
				if (gender == 2) {
					if (age1 >= minage && age1 <= maxage) { // 이 게시글의 작성자의 나이를 매칭을 원하는 나이와 성별을 비교해서 걸러냄
						Board board = new Board();
						board.setBoardCode(rs.getString(1));
						board.setUserCode(rs.getString(2));
						board.setDestination(rs.getString(3));
						board.setContent(rs.getString(4));
						board.setGender(rs.getInt(5));
						board.setMinage(rs.getInt(6));
						board.setMaxage(rs.getInt(7));
						board.setMatchingstartDatetime(rs.getString(8));
						board.setMatchingendDatetime(rs.getString(9));
						board.setPurpose(rs.getString(10));
						board.setNoticeDatetime(rs.getString(11));
						list.add(board);
					}
				} else {
					if (age1 >= minage && age1 <= maxage && gender == gender1) { // 이 게시글의 작성자의 나이를 매칭을 원하는 나이와 성별을 비교해서
																					// 걸러냄
						Board board = new Board();
						board.setBoardCode(rs.getString(1));
						board.setUserCode(rs.getString(2));
						board.setDestination(rs.getString(3));
						board.setContent(rs.getString(4));
						board.setGender(rs.getInt(5));
						board.setMinage(rs.getInt(6));
						board.setMaxage(rs.getInt(7));
						board.setMatchingstartDatetime(rs.getString(8));
						board.setMatchingendDatetime(rs.getString(9));
						board.setPurpose(rs.getString(10));
						board.setNoticeDatetime(rs.getString(11));
						list.add(board);
					}

				}
			}
			System.out.println(count);
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
