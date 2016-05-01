package fr.julienbeguier.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import fr.julienbeguier.requests.RequestResponse.ResponseType;

public class HttpRequest {

	private final String	PARAMETERS_URL_DELIMITER = "?";
	private final String	PARAMETERS_DELIMITER = "&";

	private static HttpRequest instance = null;

	private HttpRequest() {
	}

	public static HttpRequest getInstance() {
		if (instance == null) {
			instance = new HttpRequest();
		}
		return instance;
	}

	public RequestResponse getRequest(String urlService, String ... vars) throws IOException {
		String urlData = urlService + this.PARAMETERS_URL_DELIMITER;
		StringBuilder sb = new StringBuilder(urlData);
		for (int i = 0; i < vars.length; i++) {
			sb.append(vars[i]);
			sb.append(this.PARAMETERS_DELIMITER);
		}
		urlData = sb.toString().substring(0, sb.toString().length());

		URL url = new URL(urlData);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine + '\n');
		}
		in.close();

		System.out.println(response.toString());
		RequestResponse requestResponse;
		Object obj = new JSONTokener(response.toString()).nextValue();
		if (obj instanceof JSONObject) {
			requestResponse = new RequestResponse(ResponseType.JsonObject, response.toString());
		}else if (obj instanceof JSONArray) {
			requestResponse = new RequestResponse(ResponseType.JsonArray, response.toString());
		}else {
			requestResponse = null;
		}

		return requestResponse;
	}

	public static void main(String[] args) {
		HttpRequest hr = HttpRequest.getInstance();

		try {
			System.out.println("http://julienbeguier.fr:8080/RssFeed/api/rss/selectByCategory");
			System.out.println(hr.getRequest("http://julienbeguier.fr:8080/RssFeed/api/rss/selectByCategory", "id=9").getResponse().toString(2));

			System.out.println("http://julienbeguier.fr:8080/RssFeed/api/rss/selectByUser");
			System.out.println(hr.getRequest("http://julienbeguier.fr:8080/RssFeed/api/rss/selectByUser", "uid=1").getResponse().toString(2));

			System.out.println("http://julienbeguier.fr:8080/RssFeed/api/category/selectByUser");
			System.out.println(hr.getRequest("http://julienbeguier.fr:8080/RssFeed/api/category/selectByUser", "user=1").getResponse().toString(2));

			System.out.println("http://julienbeguier.fr:8080/RssFeed/api/rss/add");
			System.out.println(hr.getRequest("http://julienbeguier.fr:8080/RssFeed/api/rss/add", "title=yolo_truc", "url=http://google.com", "category=1", "uid=1").getResponse().toString(2));
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
}