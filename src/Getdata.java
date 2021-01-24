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
		Scanner scanner = new Scanner(System.in); // 创建Scanner对象
		String country = scanner.nextLine(); // 读取一行输入并获取字符串
		try {
			URL url = new URL("https://covid-api.mmediagroup.fr/v1/cases?country=" + country);
			// 打开和url之间的连接
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) url.openConnection();
			// 请求方式
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 11; Windows NT 5.1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 连接并发送HTTP请求:
			conn.connect();
			// 判断HTTP响应是否200:
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("bad response");
			}
			BufferedReader read = null;
			read = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
