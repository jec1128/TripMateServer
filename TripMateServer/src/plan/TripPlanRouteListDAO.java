package plan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TripPlanRouteListDAO {

	private Connection conn;
	private ResultSet rs;

	public TripPlanRouteListDAO() {
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
	
	public String makeCode() {
		String idx = "List-";
		int num = getNext();
		if (num == -1)
			return "error";
		String code = idx + num;
		return code;
	}

	// �Խñ� ���� ����
	public int getNext() {

		String SQL = "SELECT list_code FROM tripplanlist ORDER BY list_code DESC";

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1;// ù ��° �Խù��� ���
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\nERROR:�Խù� �ε��� ����\n");
		return 0;
	}

	// userId�� userCode ��������
	public String getUserCode(String id) {
		String SQL = "SELECT user_code FROM user WHERE user_id = ?";
		String userCode = "";

		System.out.println("dddd," + id);

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				userCode = rs.getString("user_code");
			}
			System.out.println(userCode);
			return userCode;
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\nERROR:�Խù� �ε��� ����\n");

		return userCode;

	}

	// ������� ���� ����Ʈ ��������
	public ArrayList<TripPlanRouteListModel> getRouteList(String userCode) {

		String SQL = "SELECT * FROM tripplanlist WHERE delete_state = 0 AND user_code = ?";
		ArrayList<TripPlanRouteListModel> list = new ArrayList<TripPlanRouteListModel>();

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userCode);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				TripPlanRouteListModel model = new TripPlanRouteListModel();	
				  
				model.setUser_code(rs.getString(1));
				model.setPlan_code(rs.getString(2));	
				model.setPlan_date(rs.getString(3));
				model.setTitle(rs.getString(4));
				model.setLocationx(rs.getDouble(5));
				model.setLocationy(rs.getDouble(6));
				model.setIndex(rs.getInt(7));
				model.setMemo(rs.getString(8));

				list.add(model);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\nERROR:�Խù� �ε��� ����\n");

		return list;

	}

	// ���� ����Ʈ �߰��ϱ�

	public String tripRouteInsert(TripPlanRouteListModel tripRoute) {

		String SQL = "INSERT INTO tripplanlist VALUES(?,?,?,?,?,?,?,?,?)";

		  
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, tripRoute.getUser_code());
			pstmt.setString(2, tripRoute.getPlan_code());
			pstmt.setString(3, tripRoute.getPlan_date());
			System.out.println("�Ȱ��� Ȯ���ϱ�");
			pstmt.setString(4, tripRoute.getTitle());
			pstmt.setDouble(5, tripRoute.getLocationx());
			pstmt.setDouble(6, tripRoute.getLocationy());
			pstmt.setInt(7, tripRoute.getIndex());
			pstmt.setString(8, tripRoute.getMemo());
			pstmt.setInt(9, 0);

			int result = pstmt.executeUpdate();

			if (result >= 0) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // �����ͺ��̽� ����
	}

	// ���� ����Ʈ �����ϱ�

}
