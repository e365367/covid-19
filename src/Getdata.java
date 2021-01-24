import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Getdata {

	String result = "";
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in); // ����Scanner����
		String country = scanner.nextLine(); // ��ȡһ�����벢��ȡ�ַ���
		try {
			URL url = new URL("https://covid-api.mmediagroup.fr/v1/cases?country=" + country);
			// �򿪺�url֮�������
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) url.openConnection();
			// ����ʽ
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 11; Windows NT 5.1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ���Ӳ�����HTTP����:
			conn.connect();
			// �ж�HTTP��Ӧ�Ƿ�200:
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("bad response");
			}
			BufferedReader read = null;
			read = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;// ѭ����ȡ
			while ((line = read.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
