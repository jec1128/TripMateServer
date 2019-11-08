package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			}
			else 
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
		String SQL = "INSERT INTO matchingboard VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			pstmt.setString(10, board.getThema1());
			pstmt.setString(11, board.getThema2());
			pstmt.setString(12, board.getThema3());
			pstmt.setString(13, getDate());
			pstmt.setInt(14, 0);

			int result = pstmt.executeUpdate();
			if (result >= 0)
				return "success"; // insert문을 실행하면 무조건 0이상의 수가 반환됨

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "db-error"; // 데이터베이스 오류
	}

	public ArrayList<Board> getList(int pageNumber) {
		String SQL = "SELECT * FROM matchingboard WHERE delete_state = 0 ORDER BY notice_datetime DESC limit 10";
		ArrayList<Board> list = new ArrayList<Board>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
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
				board.setThema1(rs.getString(10));
				board.setThema2(rs.getString(11));
				board.setThema3(rs.getString(12));
				board.setNoticeDatetime(rs.getString(13));
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
			pstmt.setString(1,boardcode);
			rs = pstmt.executeQuery();
			while(rs.next()) {
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
				board.setThema1(rs.getString(10));
				board.setThema2(rs.getString(11));
				board.setThema3(rs.getString(12));
				board.setNoticeDatetime(rs.getString(13));
				return board;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String update(String boardCode, Board board) {
		String SQL = "UPDATE matchingboard SET destination = ?, content = ?, party_gender = ?, party_min_age = ?, party_max_age = ?, matching_start_datetime = ?, matching_end_datetime = ?, trip_thema1 = ?, trip_thema2 = ?, trip_thema3 = ?, notice_datetime = NOW() WHERE board_code = ?";
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,board.getDestination());
			pstmt.setString(2,board.getContent());
			pstmt.setInt(3, board.getGender());
			pstmt.setInt(4, board.getMinage());
			pstmt.setInt(5,board.getMaxage());
			pstmt.setString(6,board.getMatchingstartDatetime());
			pstmt.setString(7,board.getMatchingendDatetime());
			pstmt.setString(8,board.getThema1());
			pstmt.setString(9,board.getThema2());
			pstmt.setString(10,board.getThema3());
			pstmt.setString(11,boardCode);
			
			int result = pstmt.executeUpdate();
			if (result>=0) {
				return "success";
			}
			else {
				return "error";
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "error"; //데이터베이스 오류
	}
	
	public String delete(String boardCode) {
		String SQL = "UPDATE matchingboard SET delete_state = 1 WHERE board_code = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, boardCode);
			int result= pstmt.executeUpdate();
			if(result>=0)
				return "success";
			else
				return "error";
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

}
