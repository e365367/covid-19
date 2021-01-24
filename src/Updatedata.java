import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import com.alibaba.fastjson.JSONObject;

public class Updatedata {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/covid-19?useSSL=false&characterEncoding=utf8", "root", "admin");

			int count = 0;
			int iso = 0;
			int n = 0;
			int m = 0;
			String country = "";
			System.out.print("请输入要修改数据的国家名: "); // 打印提示
			Getdata getdata = new Getdata();
			JSONObject jsonobject = JSONObject.parseObject(getdata.result);// 获得的字符串变为JSONobject类型
			System.out.println(getdata.result);
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
					PreparedStatement ps = conn.prepareStatement("UPDATE country SET country=? WHERE iso=?");//需要调参
					ps.setObject(1, country);
					ps.setObject(2, 156);
				} else {
					long confirmed = region.getLong("confirmed");
					long recovered = region.getLong("recovered");
					long deaths = region.getLong("deaths");
					String region1 = json_region;
					PreparedStatement ps = conn.prepareStatement("UPDATE region SET country=? WHERE iso=?");//需要调参
					ps.setObject(1, country);
					ps.setObject(2, 156);
					m += ps.executeUpdate();
					System.out.println("更新" + region1 + "数据成功");
					break;
				}
			}
			System.out.println("共更新" + m + "条地区数据");
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}