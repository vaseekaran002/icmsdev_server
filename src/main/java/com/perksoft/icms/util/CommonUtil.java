package com.perksoft.icms.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.perksoft.icms.contants.Constants;

@Component
public class CommonUtil {

	@Autowired
	private Gson gson;

	public ResponseEntity<String> generateEntityResponse(String message, String status, Object responseData) {
		Map<String, Object> responseMap = new LinkedHashMap<>();
		ResponseEntity<String> responseEntity = null;

		if (Constants.SUCCESS.equalsIgnoreCase(status)) {

			responseMap.put("status", Constants.SUCCESS);
			responseMap.put("message", message);
			responseMap.put("data", responseData);
			responseEntity = new ResponseEntity<>(gson.toJson(responseMap), HttpStatus.OK);
		} else {

			responseMap.put("status", Constants.FAILURE);
			responseMap.put("message", message);

			if (Constants.EXCEPTION.equalsIgnoreCase(status)) {
				responseEntity = new ResponseEntity<>(gson.toJson(responseMap), HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseEntity = new ResponseEntity<>(gson.toJson(responseMap), HttpStatus.CONFLICT);
			}
		}
		return responseEntity;
	}

}
