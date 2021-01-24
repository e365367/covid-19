import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import com.alibaba.fastjson.JSONObject;

public class Insertdata {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/covid-19?useSSL=false&characterEncoding=utf8", "root", "admin");
			//数据库名为:covid-19
			int count = 0;
			int iso = 0;
			int n = 0;
			int m = 0;
			String country = "";
			System.out.print("请输入要插入数据的国家名: "); 
			Getdata getdata = new Getdata();
			System.out.println(getdata.result);
			System.out.println("开始");
			JSONObject jsonobject = JSONObject.parseObject(getdata.result);// 获得的字符串变为JSONobject类型
			Set<String> jsonkeys = jsonobject.keySet();// 各地区的名字，即key
			Iterator<String> iterator = jsonkeys.iterator();
			while (iterator.hasNext()) {
				count++;
				String json_region = iterator.next();// 各地区名，即key
				String reigonvalus = jsonobject.getString(json_region);// 单个地区的数据
				JSONObject region = JSONObject.parseObject(reigonvalus);// 单个地区的数据，变为JSONobject类型
				if (count == 1) {
					iso = region.getInteger("iso");
					country = region.getString("country");
					long confirmed = region.getLong("confirmed");
					long recovered = region.getLong("recovered");
					long deaths = region.getLong("deaths");
					long population = region.getLong("population");

					PreparedStatement ps = conn.prepareStatement(
							"INSERT INTO country (confirmed, recovered, deaths, iso, population, country) VALUES (?,?,?,?,?,?)");
					ps.setObject(1, confirmed);
					ps.setObject(2, recovered);
					ps.setObject(3, deaths);
					ps.setObject(4, iso);
					ps.setObject(5, population);
					ps.setObject(6, country);
					m = ps.executeUpdate();
					System.out.println("插入" + country + "数据成功");
				} else {
					long confirmed = region.getLong("confirmed");
					long recovered = region.getLong("recovered");
					long deaths = region.getLong("deaths");
					String region1 = json_region;
					PreparedStatement ps = conn.prepareStatement(
							"INSERT INTO region (confirmed, recovered, deaths, region, iso, country) VALUES (?,?,?,?,?,?)");
					ps.setObject(1, confirmed);
					ps.setObject(2, recovered);
					ps.setObject(3, deaths);
					ps.setObject(4, region1);
					ps.setObject(5, iso);
					ps.setObject(6, country);
					n += ps.executeUpdate();
					System.out.println("插入" + region1 + "数据成功");
				}
			}
			System.out.println("共插入" + m + "条国家数据");
			System.out.println("共插入" + n + "条地区数据");
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
