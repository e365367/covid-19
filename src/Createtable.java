import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Createtable {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/covid-19?useSSL=false&characterEncoding=utf8", "root", "admin");
			String table_country = "create table countrys(" +
                    "country varchar(50) NOT NULL," +
                    "confirmed int(20) NOT NULL, " +
                    "recovered int(20) NOT NULL," +
                    "deaths int(20) NOT NULL," +
                    "population int(20) NOT NULL,"+
                    "iso int(11) NOT NULL,"+
                    "PRIMARY KEY (iso,country))DEFAULT CHARSET=utf8";
			String table_region = "create table regions(" +
                    "region varchar(50) NOT NULL," +
                    "country varchar(50) NOT NULL," +
                    "confirmed int(20) NOT NULL, " +
                    "recovered int(20) NOT NULL," +
                    "deaths int(20) NOT NULL," +
                    "iso int(11) NOT NULL,"+
                    "PRIMARY KEY (iso,region))DEFAULT CHARSET=utf8";
			Statement ps = conn.createStatement();
			ps.executeUpdate(table_region);
			ps.executeUpdate(table_country);
			System.out.println("成功创建表");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}