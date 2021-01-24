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
			//���ݿ���Ϊ:covid-19
			int count = 0;
			int iso = 0;
			int n = 0;
			int m = 0;
			String country = "";
			System.out.print("������Ҫ�������ݵĹ�����: "); 
			Getdata getdata = new Getdata();
			System.out.println(getdata.result);
			System.out.println("��ʼ");
			JSONObject jsonobject = JSONObject.parseObject(getdata.result);// ��õ��ַ�����ΪJSONobject����
			Set<String> jsonkeys = jsonobject.keySet();// �����������֣���key
			Iterator<String> iterator = jsonkeys.iterator();
			while (iterator.hasNext()) {
				count++;
				String json_region = iterator.next();// ������������key
				String reigonvalus = jsonobject.getString(json_region);// ��������������
				JSONObject region = JSONObject.parseObject(reigonvalus);// �������������ݣ���ΪJSONobject����
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
					System.out.println("����" + country + "���ݳɹ�");
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
					System.out.println("����" + region1 + "���ݳɹ�");
				}
			}
			System.out.println("������" + m + "����������");
			System.out.println("������" + n + "����������");
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
