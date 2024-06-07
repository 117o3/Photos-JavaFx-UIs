package photosfx.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/**
 * Model for Album class. Each album has a name, ArrayList of Photos and ArrayList of Dates
 */

public class Album implements Serializable{

    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Photos> photos;
    private ArrayList<Date> photoDates;

    /**
     * 
   	 * Album Constructor 
   	 * Initializes new constructor with name and empty lsit of photos
   	 * @param name 
   	 */
    public Album(String name){
        this.name = name;
        photos = new ArrayList<Photos>();
        photoDates = new ArrayList<>();
        
    }

    /**
   	 * Getter method to return list of photos in album
   	 * @return ArrayList<photos>
   	 */
    public ArrayList<Photos> getAlbumPhotos() {
        return this.photos;

    }

    /**
   	 * Getter Method for number of photos in album
   	 * @return int size of list
   	 */
    public int getPhotoQuantity() {
        return this.photos.size();
    }

    /**
   	 * Getter method for name of album
   	 * @return String name
   	 */
    public String getName(){
        return this.name;
    }
    
    /**
   	 * Setter Method for name
   	 * @param String name
   	 * @return void
   	 */
    public void setName(String name){
        this.name = name;
    }

    /**
   	 * Method to get add a photo
   	 * @param Photos photo to be added
     * @param Date date of photos last modification
   	 * @return void
   	 */
    public void addPhoto(Photos pic, Date date){
        this.photos.add(pic);
        this.photoDates.add(date);
        System.out.println(date);
    }

    /**
   	 * Method to remove a photo from album
   	 * @param Photos picture
   	 * @return void
   	 */
    public void removePhoto(Photos pic){
        if(photos.contains(pic)) photos.remove(pic);
        for (Date date: this.photoDates) {
            if (date == pic.date()) {
                photoDates.remove(date);
            }
        }
    }

    /**
   	 * Method to get string name of album
   	 * @param null
   	 * @return String name
   	 */
    public String toString() {
		return this.name;
	}

    /**
     * Getter method for oldest photo in album
     * @return Date of the oldest photo
     */
    public Date getOldestPhotoDateTime() {
        return this.photoDates.stream()
                         .min(Date::compareTo)
                         .orElse(null);
    }

    /**
     * Getter method for latest photo in album 
     * @return Date of the latest photo
     */
    public Date getLatestPhotoDateTime() {
        return this.photoDates.stream()
                         .max(Date::compareTo)
                         .orElse(null);
    }
}