package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dataService.userService.UserStrategyReaderService;
import sql.DBHelper;

public class UserStrategyReader implements UserStrategyReaderService {
	private int len = 9;

	public double[] getStrategy(String name, String tableName) {
		double[] arr = new double[len];
		try {
			String sql = "select * from " + tableName + " where user='" + name + "'";

			DBHelper database = DBHelper.getInstance();
			PreparedStatement statement = null;

			statement = database.conn.prepareStatement(sql);
			ResultSet set = statement.executeQuery();

			if (set.next()) {
				for (int i = 0; i < arr.length; i++) {
					arr[i] = set.getDouble(i + 2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
}
