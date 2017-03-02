package user;

import java.sql.PreparedStatement;

import dataService.userService.UserStrategyWriterService;
import sql.DBHelper;

public class UserStrategyWriter implements UserStrategyWriterService {

	public boolean saveStrategy(String name, double arr[], String tableName) {
		try {
			String[] s = { "days", "low_price", "high_price", "low_chg", "high_chg", "low_volume", "high_volume",
					"average", "trend" };
			String sql = "update " + tableName + " set ";
			for (int i = 0; i < arr.length; i++) {
				sql += s[i] + "=" + arr[i];
				if (i < arr.length - 1)
					sql += ",";
			}
			sql += " where user='" + name + "'";
			DBHelper database = DBHelper.getInstance();
			PreparedStatement statement = null;

			statement = database.conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
