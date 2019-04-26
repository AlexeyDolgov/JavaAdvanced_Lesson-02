package ua.lviv.lgs.MagazineShop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConnectionUtils {
	private static String url = "jdbc:mysql://localhost/magazine_shop?serverTimezone=" + TimeZone.getDefault().getID();
	private static String userName = "root";
	private static String userPassword = "111111";

	public static Connection openConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

		return DriverManager.getConnection(url, userName, userPassword);
	}
}
