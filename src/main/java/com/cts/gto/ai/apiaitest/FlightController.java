package com.cts.gto.ai.apiaitest;


import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.gto.ai.datatypes.FlightQuery;
import com.cts.gto.ai.util.ObjectToJson;


@Controller
public class FlightController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FlightController.class);
	@Autowired
	private
    JdbcTemplate jdbcTemplate;
	
	
	
	@RequestMapping(value = "/flight", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getFlight() {
		System.out.println("invoked");
		return "flight booked";
	}
	
	@RequestMapping(value = "/bookFlight", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String bookFlight(@RequestBody() String query) {
		JSONObject jsonObject;
		String source = "";
		String destination = "";
		try {
			jsonObject = ObjectToJson.getJsonObject(query).getJSONObject("result").getJSONObject("parameters");
			System.out.println("invoked bookFlight method");
			LOGGER.info("invoked bookFlight method");
		
				
				LOGGER.info("input request geo-city:"+jsonObject.getString("destination"));

				
			
			 source = source = jsonObject.getString("source");
			 destination = destination = jsonObject.getString("destination");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String sql = "select * from flight";
//		List<Map<String, Object>> rowList = getJdbcTemplate().queryForList(sql);
//		for(Map<String, Object> row:rowList){
//			String output = (String)row.get("source");
//			System.out.println(output);
//		}
		String sql = "select id from flight where source = ? and destination = ?";
		String id = getJdbcTemplate().queryForObject(sql, new Object[]{source,destination}, String.class);
		System.out.println("requested id: "+id);
		
		
		
		
		//String jsonString = ObjectToJson.getJsonString("you want to book flight from "+source);
		//LOGGER.info(jsonString);
//		{
//	        "speech": speech,
//	        "displayText": speech,
//	        # "data": data,
//	        # "contextOut": [],
//	        "source": "apiai-weather-webhook-sample"
//	    }
		JSONObject response = new JSONObject();
		
		try {
			String speech = "Your flight from "+source +" to "+destination+" was succesfully booked by Heathrew AI support, Your flight number is: "+id;
			response.put("speech", speech);
			response.put("displayText", speech);
			response.put("source", "gitapitest-flight");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(response);
		//String jsonString = ObjectToJson.getJsonString(response);
		return response.toString();
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
