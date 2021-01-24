import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Deletedata {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/covid-19?useSSL=false&characterEncoding=utf8", "root", "admin");

			PreparedStatement ps = conn.prepareStatement("DELETE FROM country WHERE country=?");
			{
				ps.setObject(1, "India");
				int n = ps.executeUpdate();
				System.out.println("³É¹¦É¾³ý");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}