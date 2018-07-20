package https_service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;



import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ChangeSentimentHttpsRequest {

	private final String URL = "https://es.bm3.elife.com.br/edit_post.json?key=m07633i5t65Rh3L7chS8374z8kidDSrqU671WJL85s16IFHSax143q28e4S6E75z";
	private final String ACCEPT = "*/*";
	private final String REFERER = "https://app.buzzmonitor.com.br/reports/5a607b86626d2d247add2900";
	private final String ORIGIN = "https://app.buzzmonitor.com.br";
	private final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
	private final String CONTENT_TYPE = "text/plain";
	
	private String response;
	private int responseCode;
	
	public ChangeSentimentHttpsRequest() {	}
	
	public void sendPost(String sentiment) throws IOException {
		
		trustAll();
		URL url = new URL(URL);
		HttpsURLConnection request = (HttpsURLConnection) url.openConnection();
		
		String data = "{\"authentication_params\":{\"bm_user\":\"100000724400369\",\"api_key\":\"jhbuAGntkAU3A0_q1f98Dg\"},\"buzzmonitor_username\":\"100000724400369\",\"changes\":{\"monitoring_posts\":[],\"twitter_dms\":[],\"twitter_mentions\":[],\"facebook_pages_wall\":[{\"brand\":\"100000724400369_Other Pages\",\"login\":\"100000724400369\",\"source\":\"fbm\",\"other_pages\":true,\"posts\":[{\"elasticsearch_index\":\"bm-posts-saas-2018-7\",\"elasticsearch_id\":\"facebook-100000724400369_Other Pages-10156195797472535-10156211362362535\",\"sentiment\":\"" + sentiment + "\",\"tags_to_add\":[{\"_id\":\"5afee2cb626d2d7e6ee12c01\",\"created_at\":\"2018-05-18T11:27:23.421-03:00\",\"deleted_at\":null,\"name\":\"Sentiment_Edited\",\"owner_id\":\"5a6078f1626d2d24b38e0100\",\"source\":null,\"sub_tags\":[],\"updated_at\":\"2018-05-18T11:27:23.421-03:00\",\"value\":\"20180717124246_add_time_to_tag_func###100000724400369_add_username_to_tag_func###\",\"selection\":\"blank\",\"user_login\":\"100000724400369\"}],\"tags_to_edit\":[],\"tags_to_remove\":[],\"page_id\":\"348593577534\"}]}],\"facebook_private_messages\":[],\"linkedin_updates\":[]}}";
		System.out.println(data);

		request.setRequestMethod("POST");
		request.setRequestProperty("Accept", ACCEPT);
		request.setRequestProperty("Referer", REFERER);
		request.setRequestProperty("Origin", ORIGIN);
		request.setRequestProperty("User-Agent", USER_AGENT);
		request.setRequestProperty("Content-Type", CONTENT_TYPE);
		
		request.setDoOutput(true);
		DataOutputStream outputStream = new DataOutputStream( request.getOutputStream() );

		outputStream.writeBytes(data);
		outputStream.flush();
		outputStream.close();

		setResponseCode( request.getResponseCode() );
		System.out.println( this.getResponseCode() );

		InputStreamReader streamReader = new InputStreamReader( request.getInputStream() );
		setResponse( buildReturn(streamReader) );
		System.out.println( getResponse() );
	}
	
	private String buildReturn(InputStreamReader reader) throws IOException 
	{
		BufferedReader in = new BufferedReader(reader);
		String line = null;
		StringBuffer messageBuilder = new StringBuffer();
		while ( (line = in.readLine()) != null) {
		   messageBuilder.append(line);
		}
		return messageBuilder.toString();
	}
	
	public String getResponse() {
		return response;
	}
	
	private void setResponse(String response) {
		this.response = response;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	private void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	/**
	 * Ignore certificate verification to avoid machine-specific errors of stored certificates
	 */
	private void trustAll() {
		TrustManager[] trustAllCerts = new TrustManager[] {
			    new X509TrustManager() 
			    {
			        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			            return null;
			        }
			        public void checkClientTrusted(
			            java.security.cert.X509Certificate[] certs, String authType) {
			        }
			        public void checkServerTrusted(
			            java.security.cert.X509Certificate[] certs, String authType) {
			        }
			    }
			};

			// Install the all-trusting trust manager
			try {
			    SSLContext sc = SSLContext.getInstance("SSL");
			    sc.init(null, trustAllCerts, new java.security.SecureRandom());
			    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
	}
}

