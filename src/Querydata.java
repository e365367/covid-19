import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Querydata {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/covid-19?useSSL=false&characterEncoding=utf8", "root", "admin");

			PreparedStatement ps = conn
					.prepareStatement("SELECT confirmed, recovered, deaths, region FROM region WHERE country=?");
			{
				ps.setObject(1, "China");
				ResultSet ret = ps.executeQuery();
				while (ret.next()) {
					System.out.println("region: " + ret.getString(4) + "\t" + " confirmed: " + ret.getString(1) + "\t"
							+ " revovered: " + ret.getString(2) + "\t" + " deaths: " + ret.getString(3));
				}
				System.out.println("≤È—Ø≥…π¶");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}