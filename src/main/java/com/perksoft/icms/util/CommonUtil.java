package com.perksoft.icms.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perksoft.icms.contants.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonUtil {

	@Autowired
	private ObjectMapper objectMapper;

	public ResponseEntity<String> generateEntityResponse(String message, String status, Object responseData) {
		Map<String, Object> responseMap = new LinkedHashMap<>();
		ResponseEntity<String> responseEntity = null;

		try {
			if (Constants.SUCCESS.equalsIgnoreCase(status)) {

				responseMap.put("status", Constants.SUCCESS);
				responseMap.put("message", message);
				responseMap.put("data", responseData);
				String json = objectMapper.writeValueAsString(responseData);
				responseEntity = new ResponseEntity<>(json, HttpStatus.OK);
			} else {

				responseMap.put("status", Constants.FAILURE);
				responseMap.put("message", message);

				if (Constants.EXCEPTION.equalsIgnoreCase(status)) {
					responseEntity = new ResponseEntity<>(objectMapper.writeValueAsString(responseMap),
							HttpStatus.INTERNAL_SERVER_ERROR);
				} else {
					responseEntity = new ResponseEntity<>(objectMapper.writeValueAsString(responseMap),
							HttpStatus.CONFLICT);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("End of API resonse {}", responseEntity);
		return responseEntity;
	}

}
