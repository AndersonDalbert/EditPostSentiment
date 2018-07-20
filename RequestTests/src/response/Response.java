package response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Response {

	private JSONObject data;
	
	public Response(String data) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(data);
			JSONArray dataArray = (JSONArray) json.get("data");
			this.data = (JSONObject) dataArray.get(0);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private JSONArray getLogs() {
		return (JSONArray) this.data.get("logs");
	}
	
	private JSONObject getLastRequest() {
		return (JSONObject) this.getLogs().get(0);
	}
	
	public String getSentiment() {
		return (String) this.data.get("sentiment");
	}
	
	public String getLastRequestField() {
		return (String) this.getLastRequest().get("field");
	}
	
	public String getOldValue() {
		if (getLastRequestField().equals("sentiment") ) {
			return (String) getLastRequest().get("old_value");
		}
		throw new RuntimeException("Last logged operation didn't changed a sentiment");
	}
	
	public String getNewValue() {
		if (getLastRequestField().equals("sentiment") ) {
			return (String) getLastRequest().get("new_value");
		}
		throw new RuntimeException("Last logged operation didn't changed a sentiment");
	}

}
