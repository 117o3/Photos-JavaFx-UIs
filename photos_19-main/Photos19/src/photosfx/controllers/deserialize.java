package photosfx.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import photosfx.model.Users;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */

/**
 * Deserializes the data
 */
public class deserialize {

	/**
     * The file path where the serialized data is stored
     */
	private static final String FILE_PATH = "path/to/users.ser";

	
	/**
     * Deserializes an ArrayList of Users from the "userAccounts.dat" file
     * 
     * @return ArrayList of Users
     * @throws IOException if an I/O error occurs while reading the file
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
	public static ArrayList<Users> deserialize() throws IOException, ClassNotFoundException {
		ArrayList<Users> storedUsers = new ArrayList<>();//null;
		try {
			FileInputStream fileIn = new FileInputStream("userAccounts.dat");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			storedUsers = (ArrayList<Users>) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException | ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
			return storedUsers;

	}
} 