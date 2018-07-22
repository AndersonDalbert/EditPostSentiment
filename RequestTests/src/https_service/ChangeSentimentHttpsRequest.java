package https_service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/** 
 * This class is responsible for make a Https request correspondent to a curl command. It changes the sentiment of
 * a Facebook post stored into Elife/Buzzmonitor Elasticsearch database.
 */ 
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
	
	/**
	 * Get the string output from the request
	 * @return response
	 */
	public String getResponse() {
		System.out.println(this.response);
		return response;
	}
	
	/**
	 * Returns request response code. 200 means that the request was successful.
	 * @return request response code
	 */
	public int getResponseCode() {
		return responseCode;
	}
	
	/**
	 * This method makes a request through a Https conection. The request should change the sentiment
	 * of a Facebook post. 
	 * @param sentiment The new sentiment of the post
	 * @throws IOException possible if URL or output stream creation fails or if occur an error in headers certificate
	 */
	public void sendPost(String sentiment) throws IOException 
	{	
		HttpsURLConnection request = startHttpsConnection();
		setRequestHeaders(request);
		addDataToRequest(sentiment, request);
		updateClassAttributes(request);
		
//		printResponse();
	}

	private HttpsURLConnection startHttpsConnection() throws MalformedURLException, IOException 
	{
		trustAll();
		final URL url = new URL(URL);
		HttpsURLConnection request = (HttpsURLConnection) url.openConnection();
		return request;
	}

	private void addDataToRequest(String sentiment, HttpsURLConnection request) throws IOException 
	{
		request.setDoOutput(true);
		
		final String data = "{\"authentication_params\":{\"bm_user\":\"100000724400369\",\"api_key\":\"jhbuAGntkAU3A0_q1f98Dg\"},\"buzzmonitor_username\":\"100000724400369\",\"changes\":{\"monitoring_posts\":[],\"twitter_dms\":[],\"twitter_mentions\":[],\"facebook_pages_wall\":[{\"brand\":\"100000724400369_Other Pages\",\"login\":\"100000724400369\",\"source\":\"fbm\",\"other_pages\":true,\"posts\":[{\"elasticsearch_index\":\"bm-posts-saas-2018-7\",\"elasticsearch_id\":\"facebook-100000724400369_Other Pages-10156195797472535-10156211362362535\",\"sentiment\":\"" + sentiment + "\",\"tags_to_add\":[{\"_id\":\"5afee2cb626d2d7e6ee12c01\",\"created_at\":\"2018-05-18T11:27:23.421-03:00\",\"deleted_at\":null,\"name\":\"Sentiment_Edited\",\"owner_id\":\"5a6078f1626d2d24b38e0100\",\"source\":null,\"sub_tags\":[],\"updated_at\":\"2018-05-18T11:27:23.421-03:00\",\"value\":\"20180717124246_add_time_to_tag_func###100000724400369_add_username_to_tag_func###\",\"selection\":\"blank\",\"user_login\":\"100000724400369\"}],\"tags_to_edit\":[],\"tags_to_remove\":[],\"page_id\":\"348593577534\"}]}],\"facebook_private_messages\":[],\"linkedin_updates\":[]}}";
		
		DataOutputStream outputStream = new DataOutputStream( request.getOutputStream() );
		writeToOutputStream(data, outputStream);
	}

	private void updateClassAttributes(HttpsURLConnection request) throws IOException 
	{
		setResponseCode( request.getResponseCode() );
		
		InputStream streamReader = 
				( this.responseCode < HttpsURLConnection.HTTP_BAD_REQUEST ) ? 
						request.getInputStream() :
						request.getErrorStream();
				
		setResponse( buildReturn(streamReader) );
	}

	private void writeToOutputStream(final String data, DataOutputStream outputStream) throws IOException 
	{
		outputStream.writeBytes(data);
		outputStream.flush();
		outputStream.close();
	}

	private void setRequestHeaders(HttpsURLConnection request) throws ProtocolException 
	{
		request.setRequestMethod("POST");
		request.setRequestProperty("Accept", ACCEPT);
		request.setRequestProperty("Referer", REFERER);
		request.setRequestProperty("Origin", ORIGIN);
		request.setRequestProperty("User-Agent", USER_AGENT);
		request.setRequestProperty("Content-Type", CONTENT_TYPE);
	}

	private String buildReturn(InputStream inputStream) throws IOException 
	{
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while(result != -1) {
		    buf.write((byte) result);
		    result = bis.read();
		}
		// StandardCharsets.UTF_8.name() > JDK 7
		return buf.toString("UTF-8");
	}
	
	/**
	 * Ignore certificate verification to avoid machine-specific errors of stored certificates
	 */
	private void trustAll() 
	{
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
	
	private void setResponse(String response) {
		this.response = response;
	}
	
	private void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
}

