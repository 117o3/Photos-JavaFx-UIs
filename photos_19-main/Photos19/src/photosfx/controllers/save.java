package photosfx.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


import photosfx.model.Users;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */

/**
 * Class to save the user data
 */
public class save {
	 /**
     * The file path where the user data will be saved
     */
	public static final String path = "userAccounts.dat";
	
	/**
     * Saves the user data to a file
     * 
     * @param users the ArrayList of Users to be saved
     */
	public static void save(ArrayList<Users> users) {
	
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("userAccounts.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(users);

			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
