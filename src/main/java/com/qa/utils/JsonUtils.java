package com.qa.utils;

import java.io.FileReader;

import com.google.gson.*;

public class JsonUtils {

	public static JsonObject getTestData(String file, String string) {
		try {
			return JsonParser.parseReader(new FileReader("src/test/resources/data/" + file)).getAsJsonObject();
		} catch (Exception e) {
			throw new RuntimeException("Failed to read JSON");
		}
	}

	
}
