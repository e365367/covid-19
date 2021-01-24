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
			System.out.print("������Ҫ�޸����ݵĹ�����: "); // ��ӡ��ʾ
			Getdata getdata = new Getdata();
			JSONObject jsonobject = JSONObject.parseObject(getdata.result);// ��õ��ַ�����ΪJSONobject����
			System.out.println(getdata.result);
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
					PreparedStatement ps = conn.prepareStatement("UPDATE country SET country=? WHERE iso=?");//��Ҫ����
					ps.setObject(1, country);
					ps.setObject(2, 156);
				} else {
					long confirmed = region.getLong("confirmed");
					long recovered = region.getLong("recovered");
					long deaths = region.getLong("deaths");
					String region1 = json_region;
					PreparedStatement ps = conn.prepareStatement("UPDATE region SET country=? WHERE iso=?");//��Ҫ����
					ps.setObject(1, country);
					ps.setObject(2, 156);
					m += ps.executeUpdate();
					System.out.println("����" + region1 + "���ݳɹ�");
					break;
				}
			}
			System.out.println("������" + m + "����������");
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}