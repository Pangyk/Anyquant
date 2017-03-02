package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dataService.userService.SignInCheckService;
import sql.DBHelper;

public class SignInCheck implements SignInCheckService {

	public int check(String name, String password) {
		try {
			String sql = "select password from userpass where name='" + name + "'";
			DBHelper database = DBHelper.getInstance();
			PreparedStatement statement = null;

			statement = database.conn.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			
			// no username found
			if (!set.next())
				return 1;
			else{
				String pass = set.getString(1);
				//correct
				if(pass.equals(password))
					return 0;
				//incorrect password
				else
					return 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}
}
