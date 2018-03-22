package com.exercise.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.exercise.dao.UsernameListDAO;
import com.exercise.dao.UsernameListImpDAO;
import com.exercise.model.Result;

public class ValidateUsername {
	private static final String SAMPLE_USER = "sampleUser";
	private static final int MINIMUM_CHARACTERS = 6;
	private static final int MAX_SUGGESTIONS_ATTEMPTS = 3;
	private static final int NOT_FOUNT_VALUE = -1;
	private static final int MINIMUM_SUGGESTIONS = 14;
	private static final String USERNAME_DOESN_T_MEET_MINIMUM_RULES = "Username doesn't meet minimum rules, please verify and try it again.";
	private static final String[] PREFIX_SUGGESTIONS = { "Sir", "Mr", "Lord", "Amazing", "Dr", "Social", "Super" };
	private static final String[] SUFIX_SUGGESTIONS = { "TheKing", "Jr", "2018", "1", "2", "3", "4" };
	private UsernameListDAO usernameListDAO = new UsernameListImpDAO();
	private List<String> usernameList = new ArrayList<String>();
	private String baseSuggestion = "";

	/**
	 * <p>
	 * Check Username
	 * </p>
	 * 
	 * 
	 * @param username
	 *            String cointaining the <b>username</b> to validate against
	 *            database
	 * @return Result Object containing two parts. First part is a boolean (true
	 *         if username is valid, false if not), second part is a list of
	 *         suggested <b>usernames</b> in case that the provided is invalid
	 * @throws Exception
	 *             In case that the username doesn't meet the minimum
	 *             requirements. So far the size of the <b>username</b> is the
	 *             only requirement, must be 6.
	 * @author Jairo
	 */
	public Result checkUsername(String username) throws Exception {
		List<String> suggestedList;

		baseSuggestion = username;// Base of the suggestions
		Result result = new Result();

		if (!verifyRules(username))
			throw new Exception(USERNAME_DOESN_T_MEET_MINIMUM_RULES);

		usernameList = usernameListDAO.getUserNameList();

		if (usernameList.indexOf(username) == NOT_FOUNT_VALUE && !containsRestrictedWords(username)) {
			result.setUsernameValid(true);
			result.setSugestions(null);
			usernameListDAO.addUsername(username);
		} else {
			suggestedList = generateMinimumSuggestionsList();
			result.setUsernameValid(false);
			result.setSugestions(suggestedList);
		}

		return result;
	}

	/**
	 * <p>
	 * Add restricted word
	 * </p>
	 * 
	 * Adds a new restricted word to the list comming from database.
	 * 
	 * @param newRestrictedWord
	 *            String cointaining the <b>restricted word</b> to be added to
	 *            the list
	 * @return <b>True: </b>If the restricted word was successfully added<br/>
	 *         <b>False: </b>If the restricted word was not added, it means that
	 *         something wrong happened<br/>
	 * @author Jairo
	 */
	public boolean addRestrictedWord(String newRestrictedWord) {
		return usernameListDAO.addRestrictedWord(newRestrictedWord);
	}

	private boolean verifyRules(String username) {
		boolean result = true;
		if (username.length() < MINIMUM_CHARACTERS) {
			result = false;
		}
		return result;
	}

	private List<String> getSuggestedUsernames() {
		List<String> suggestedUsernames = new ArrayList<String>();
		String suggestion = "";

		for (String prefix : PREFIX_SUGGESTIONS) {
			suggestion = prefix + baseSuggestion;
			if (usernameList.indexOf(suggestion) == NOT_FOUNT_VALUE) {
				suggestedUsernames.add(suggestion);
			}
		}

		for (String sufix : SUFIX_SUGGESTIONS) {
			suggestion = baseSuggestion + sufix;
			if (usernameList.indexOf(suggestion) == NOT_FOUNT_VALUE) {
				suggestedUsernames.add(suggestion);
			}
		}
		return suggestedUsernames;
	}

	/**
	 * <p>
	 * Contains Restricted Words
	 * </p>
	 * 
	 * Verifies if the given username contains restricted words provided by the
	 * database.
	 * <p>
	 * <b>Note:</b> this method also removes the restricted word from the base
	 * username to suggest, in order to avoid suggestion of an username with a
	 * restricted word
	 * </p>
	 * 
	 * @param username
	 * @return <b>True:</b> if the username contains a restricted word.<br/>
	 *         <b>False:</b> if the username does not contain a restricted word.
	 * @author Jairo
	 */
	private boolean containsRestrictedWords(String username) {
		boolean restrictedWordsUsed = false;
		List<String> restrictedWords = usernameListDAO.getRestrictedWordsList();
		for (String restrictedWord : restrictedWords) {
			if (username.toLowerCase().indexOf(restrictedWord) != NOT_FOUNT_VALUE) {
				restrictedWordsUsed = true;
				removeRestrictedWordFromBase(restrictedWord);
			}
		}

		return restrictedWordsUsed;

	}

	private void removeRestrictedWordFromBase(String restrictedWord) {
		StringBuilder sb = new StringBuilder("");
		int indexOfRestrictedWord = baseSuggestion.toLowerCase().indexOf(restrictedWord);
		int wordSize = restrictedWord.length();
		int endOfRestrictedWord = indexOfRestrictedWord + wordSize;

		sb.append(baseSuggestion.substring(0, indexOfRestrictedWord));
		if(endOfRestrictedWord < baseSuggestion.length()){
			sb.append(baseSuggestion.substring(endOfRestrictedWord));
		}
		
		baseSuggestion = sb.toString();
	}

	private List<String> generateMinimumSuggestionsList() {
		List<String> possibleSuggestedList = new ArrayList<>();
		if(baseSuggestion.length() < 6)
			baseSuggestion = SAMPLE_USER + baseSuggestion;

		for (int attempt = 0; attempt < MAX_SUGGESTIONS_ATTEMPTS; attempt++) {
			possibleSuggestedList.addAll(getSuggestedUsernames());
			if (possibleSuggestedList.size() >= MINIMUM_SUGGESTIONS) {
				break;
			}
			baseSuggestion = baseSuggestion + baseSuggestion;

		}
		
		return possibleSuggestedList.stream().sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
	}

}
