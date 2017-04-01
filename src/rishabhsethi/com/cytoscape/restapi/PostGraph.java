package rishabhsethi.com.cytoscape.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostGraph{
	private String email;
	private String password;
	private String POST_PARAMS;
	private String POST_URL;
	
	private String sendPOST(String POST_URL, String POST_PARAMS) throws IOException {
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			String output = response.toString();
			return output;
		} else {
			System.out.println("POST request not worked");
			return "{}";
		}
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String updateGraph(String graphname, String graphpath) throws IOException{
		POST_PARAMS = "username="+email+"&password="+password+"&graphname=@"+graphpath;		
		POST_URL = "http://graphspace.org/api/users/"+email+"/graph/update/"+graphname+"/";
		
		return sendPOST(POST_URL, POST_PARAMS);
	}
	
	public String addGraph(String graphname, String graphpath) throws IOException{
		POST_PARAMS = "username="+email+"&password="+password+"&graphname=@"+graphpath;
		POST_URL = "http://graphspace.org/api/users/"+email+"/graph/add/"+graphname+"/";
		
		return sendPOST(POST_URL, POST_PARAMS);
	}
	
}