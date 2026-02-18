import java.sql.DriverManager;

class Storage {
	
	static String source = "jdbc:mysql://127.0.0.1/start" +
							"?user=me&password=password";
	
	static User getUserByEmail(String email) {
		User user = null;
		try {
			var sql = " select * from users where email = ? ";
			var cn = DriverManager.getConnection(source);
			var ps = cn.prepareStatement(sql);
			ps.setString(1, email);
			var rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.number    = rs.getInt("number");
				user.email     = rs.getString("email");
				user.password  = rs.getString("password");
				user.firstName = rs.getString("first_name");
				user.lastName  = rs.getString("last_name");
				user.type      = rs.getString("type");
			}
			rs.close(); ps.close(); cn.close();
		} catch (Exception e) { }
		return user;
	}
	
}
