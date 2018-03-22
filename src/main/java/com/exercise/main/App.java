package com.exercise.main;

import java.util.Scanner;

import com.exercise.model.Result;
import com.exercise.validation.ValidateUsername;

/**
 * <b>Validate Username exercise</b> Class for verification if a username is
 * already taken in the database.
 * 
 * 
 * @author Jairo
 *
 */
public class App {
	private static Scanner input = new Scanner(System.in);
	private static ValidateUsername validateUsername = new ValidateUsername();

	public static void main(String[] args) {
		boolean exit = false;
		while (!exit) {
			switch (printMenu()) {
			case 1:
				newScreen();
				verifyUserNameOption();
				break;
			case 2:
				newScreen();
				addRestrictedWord();
				break;
			case 0:
				// Perform "quit" case.
				exit = true;
				break;
			default:
				// The user input an unexpected choice.
			}
		}
	}

	private static int printMenu() {
		int selection;

		/***************************************************/

		newScreen();
		System.out.println("Choose from these choices");
		System.out.println("-------------------------\n");
		System.out.println("1 - Verify if username is valid");
		System.out.println("2 - Add a restricted word");
		System.out.println("0 - Quit");

		selection = input.nextInt();
		return selection;
	}

	private static void verifyUserNameOption() {
		String userNameInput = "";
		Result result = new Result();
		try {
			System.out.println("Please enter a username:\n");
			input.nextLine();
			userNameInput = input.nextLine();
			result = validateUsername.checkUsername(userNameInput);
			printResult(result);
			System.out.println("Press enter to continue...");
			input.nextLine();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out
					.println("Validation returned an exception, please insert an username with at least 6 characters");
		}

	}

	private static void addRestrictedWord() {
		String newRestrictedWord = "";
		boolean result = false;
		System.out.println("Please enter a new restricted word:\n");
		input.nextLine();
		newRestrictedWord = input.nextLine();
		result = validateUsername.addRestrictedWord(newRestrictedWord);
		if (result) {
			System.out.println("Word successfully added\n");

		} else {
			System.out.println("Something went wrong, please contact your administrator\n");
		}
		System.out.println("Press enter to continue...");
		input.nextLine();

	}

	private static void newScreen() {

		System.out.print("\n\n\n\n\n\n\n\n");
	}

	private static void printResult(Result result) {
		newScreen();
		if (result.isUsernameValid()) {
			System.out.println("The username is valid");
		} else {
			System.out.println("Username is not valid, you can select one from the next list: ");
			for (String suggestedValue : result.getSugestions()) {
				System.out.println(suggestedValue);
			}
		}
	}
}
