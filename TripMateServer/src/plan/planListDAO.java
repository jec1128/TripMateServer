package plan;

import java.sql.*;
import java.util.*;

import board.Board;

public class planListDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public planListDAO() throws ClassNotFoundException, SQLException {
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
	
	public int getIndex() {

		String SQL = "SELECT COUNT(*) FROM tripplan";

		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1;// ù ��° �÷��� ���
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println("\nERROR: �ڵ� ���� ����\n");
		return 0;
	}
	
	public String createCode() {

		int serial = getIndex();

		String suffix = String.format("%03d", serial);
		String planListCode = "PLANLIST-" + suffix;

		//System.out.println(planListCode + "�ڵ� ������");

		return planListCode;
	}
	
	public String AddList(planList planlist) {
		try {
			String SQL = "INSERT INTO tripplan VALUES(?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, createCode());
			pstmt.setString(2, planlist.getPlanPlace());
			pstmt.setString(3, planlist.getPlanTitle());
			pstmt.setString(4, planlist.getStartDate());
			pstmt.setString(5, planlist.getEndDate());
			pstmt.setInt(6, 0);
			int result = pstmt.executeUpdate();
			if (result >= 0)
				return "success";
			/*
			String SQL2 = "select plan_code, plan_place, plan_title, trip_start_date, trip_end_date from tripplan";

			pstmt = conn.prepareStatement(SQL2);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String code = rs.getString("plan_code");
				String place = rs.getString("plan_place");
				String title = rs.getString("plan_title");
				String start = rs.getString("trip_start_date");
				String end = rs.getString("trip_end_date");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "db-error";
	}
	
	public ArrayList<planList> ListView(int num) {
		String SQL2 = "select plan_code, plan_place, plan_title, date_format(trip_start_date, '%y/%m/%d'), date_format(trip_end_date, '%y/%m/%d') from tripplan where delete_state = 0 order by plan_code desc";
		ArrayList<planList> planlist = new ArrayList<planList>();
		try {
			pstmt = conn.prepareStatement(SQL2);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				planList plan = new planList();
				
				plan.setPlanCode(rs.getString(1));
				plan.setPlanPlace(rs.getString(2));
				plan.setPlanTitle(rs.getString(3));
				plan.setStartDate(rs.getString(4));
				plan.setEndDate(rs.getString(5));
				planlist.add(plan);				
				
			}
		}catch (Exception e) {
	         e.printStackTrace();
		}
		return planlist;
	}
	
}
