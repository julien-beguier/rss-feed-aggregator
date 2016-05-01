package fr.julienbeguier.requests;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestResponse {
	
	private final String	JSON_TYPE_OBJECT = "json_object";
	private final String	JSON_TYPE_ARRAY = "json_array";

	private ResponseType	type;
	private JSONObject		response;
	
	public enum ResponseType {
		JsonObject,
		JsonArray;
	}
	
	public RequestResponse(ResponseType type, String json) {
		this.type = type;
		this.response = new JSONObject();
		if (this.type == ResponseType.JsonObject) {
			this.response.put(this.JSON_TYPE_OBJECT, new JSONObject(json));
		}else {
			this.response.put(this.JSON_TYPE_ARRAY, new JSONArray(json));
		}
	}
	
	public ResponseType getResponseType() {
		return this.type;
	}
	
	public JSONObject getResponse() {
		return this.response;
	}
	
	public String getKeyJsonObject() {
		return this.JSON_TYPE_OBJECT;
	}

	public String getKeyJsonArray() {
		return this.JSON_TYPE_ARRAY;
	}
}
