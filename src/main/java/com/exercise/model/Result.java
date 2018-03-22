package com.exercise.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result {
	private boolean isUsernameValid;
	private List<String> sugestions;

	public Result() {
		isUsernameValid = false;
		sugestions = new ArrayList<String>();
	}

	public Result(boolean isUsernameValid, List<String> sugestions) {
		this.isUsernameValid = isUsernameValid;
		this.sugestions = sugestions;
	}

	public boolean isUsernameValid() {
		return isUsernameValid;
	}

	public void setUsernameValid(boolean isUsernameValid) {
		this.isUsernameValid = isUsernameValid;
	}

	public List<String> getSugestions() {
		return sugestions;
	}

	public void setSugestions(List<String> sugestions) {
		this.sugestions = sugestions;
	}

}
