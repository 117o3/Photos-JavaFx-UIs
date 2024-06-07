package photosfx.model;

import java.util.ArrayList;
import java.io.*;

/**
 * Model for Users class. Each user has a username and ArrayList of Albums
 */
public class Users implements Serializable {
    
    
	private static final long serialVersionUID = 1L;
    private  String username;
    private ArrayList<Album> albums; 
    
    /**
	 * Constructor for user
	 * @return null
	 */
    public Users(String username){

        this.username = username;
 
    }

    /**
	 * Getter method for username
	 * @return String username of user
	 */
    public String getUserName(){

        return this.username;
    }

    /**
	 * Getter method for user albums
	 * @return ArrayList of Albums
	 */
    public ArrayList<Album> getAlbums() {
        
        return albums;
	}
    
    /**
	 * Method to get string representation of user
	 * @return String representation of user
	 */
	public String toString() {
        
        return this.username;
	}
    
    /**
	 * Compare method for albums
	 * @param Users the user to be compared to
	 * @return Boolean for whether this.user is the same as other user
	 */
	public boolean equals(Users other) {
        
        return this.username.equals(other.username);
    } 
    
    /**
	 * Method for adding album to user
	 * @param Album to add
	 * @return void
	 */
    public void addAlbums(Album a) {
		this.albums.add(a);
	}

    /**
	 * Method to set an ArrayList<Album> for a user
	 * @param ArrayList<Album> album to be set
	 * @return nothing
	 */
	public void setAlbums(ArrayList<Album> t){
	this.albums =t;
		
		
	}

    /**
	 * Method to remove an album for a user
	 * @param a Album to remove
	 * @return nothing
	 */
    public void deleteAlbum(Album a) {
		this.albums.remove(a);
    }
    

    
}