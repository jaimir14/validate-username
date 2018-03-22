package com.exercise.dao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UsernameListImpDAO implements UsernameListDAO {
	private static final String PATH_DATABASE_JSON = "database.json";
	private static final String RESTRICTED_WORDS_MODEL = "restrictedWords";
	private static final String USERNAMES_MODEL = "usernames";
	// Cache lists for a better performance
	private static List<String> userNameList = new ArrayList<>();
	private static List<String> restrictedWordsList = new ArrayList<>();

	public List<String> getUserNameList() {
		if (userNameList.isEmpty()) {
			userNameList = getListFromDatabase(USERNAMES_MODEL);
		}
		return userNameList;
	}

	public List<String> getRestrictedWordsList() {
		if (restrictedWordsList.isEmpty()) {
			restrictedWordsList = getListFromDatabase(RESTRICTED_WORDS_MODEL);
		}
		return restrictedWordsList;
	}

	
	public boolean addRestrictedWord(String restrictedWord){
		boolean result = false;
		if(!restrictedWord.isEmpty()){
			if (restrictedWordsList.isEmpty()) {
				restrictedWordsList = getListFromDatabase(RESTRICTED_WORDS_MODEL);
			}
			
			restrictedWordsList.add(restrictedWord);// Simulation of an insertion to database.
			result = true;
			
		}
		return result;
		
	}

	private List<String> getListFromDatabase(String keyString) {
		JSONParser parser = new JSONParser();
		Object obj;
		List<String> result = new ArrayList<String>();
		try {
			
			File file = new File(getClass().getClassLoader().getResource(PATH_DATABASE_JSON).getPath());
			obj = parser.parse(new FileReader(file));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray jsonArray = (JSONArray) jsonObject.get(keyString);

			for (Object stringValue : jsonArray) {
				result.add((String) stringValue);
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public boolean addUsername(String newUsername) {
		boolean result = false;
		if(!newUsername.isEmpty()){
			if (userNameList.isEmpty()) {
				userNameList = getListFromDatabase(USERNAMES_MODEL);
			}
			
			userNameList.add(newUsername);// Simulation of an insertion to database.
			result = true;			
		}
		return result;
	}
}
