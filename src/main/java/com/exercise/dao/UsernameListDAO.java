package com.exercise.dao;

import java.util.List;

public interface UsernameListDAO {
	public List<String> getUserNameList();
	public List<String> getRestrictedWordsList();
	public boolean addRestrictedWord(String newRestrictedWord);
	public boolean addUsername(String newUsername);
}
