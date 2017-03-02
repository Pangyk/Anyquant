package user;

import java.sql.PreparedStatement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import dataService.userService.RegisterCheckService;
import sql.DBHelper;

public class RegisterCheck implements RegisterCheckService {

	public int check(String name, String password) {
		try {
			String sql = "insert into userpass(name,password) values('" + name + "','" + password + "')";
			DBHelper database = DBHelper.getInstance();
			PreparedStatement statement = null;

			statement = database.conn.prepareStatement(sql);
			statement.executeUpdate();
			// sql = "insert into buy_strategy"
			// +
			// "(user,days,low_volume,high_volume,low_price,high_price,low_chg,high_chg,average,trend)
			// values('"
			// + name + "',0,0,0,0,0,0,0,0,0)";
			// statement.executeUpdate(sql);
			// sql = "insert into sell_strategy"
			// +
			// "(user,days,low_volume,high_volume,low_price,high_price,low_chg,high_chg,average,trend)
			// values('"
			// + name + "',0,0,0,0,0,0,0,0,0)";
			// statement.executeUpdate(sql);
			return 0;
		} catch (MySQLIntegrityConstraintViolationException e) {
			// duplication
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}
}
