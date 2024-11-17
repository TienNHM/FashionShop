package com.fashionshop.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class LogData {

	/// Static method, call API https://sheetdb.io/api/v1/2wffl2gnws1xc (POST
	/// method) to save log data
	public static void saveLog(String function, HttpServletRequest request, String response, String exception) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			String dateStr = formatter.format(date);
			String url = "https://sheetdb.io/api/v1/2wffl2gnws1xc";
			String requestStr = logRequestParameters(request);

			String data = "id=INCREMENT&timestamp=" + dateStr + "&creationTime=" + dateStr + "&request=" + requestStr
					+ "&response=" + response + "&exception=" + exception + "&function=" + function;

			String fullUrl = url + "?" + data;

			// // Call API to save log data (Java servlet)
			// URL urlObj = new URL(fullUrl);
			// HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			// conn.setRequestMethod("POST");
			// conn.setRequestProperty("Content-Type", "application/json");
			// conn.setDoOutput(true);
			// OutputStream os = conn.getOutputStream();
			// os.write(data.getBytes());
			// os.flush();
			// os.close();

			// // Get response from API
			// BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			// String output;
			// while ((output = br.readLine()) != null) {
			// 	System.out.println(output);
			// }
			// conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Log request parameters as JSON string
	private static String logRequestParameters(HttpServletRequest request) {
		
		if (request == null) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		Enumeration<String> parameterNames = request.getParameterNames();
		
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			
			sb.append("\"" + paramName + "\":\"" + paramValue + "\"");
		}

		return "{" + sb.toString() + "}";
	}
}
