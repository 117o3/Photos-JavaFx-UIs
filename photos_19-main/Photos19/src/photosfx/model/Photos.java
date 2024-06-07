package photosfx.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;

import java.util.Date;

/**
 * Model for Photos class. Each photo has a name, caption, file path, date, and ArrayList of <Tags>
 */
public class Photos implements Serializable{
	   
    private static final long serialVersionUID = 1L;
    private String name;
    private String caption;
    private File file;
	private Date date;
    private ArrayList<Tags> tags;

    /**
	 * Constructor to make a photo with only a File and no caption
	 * @param File file 
	 * 
	 */
    public Photos(File file){
        
        this.file = file;
        this.caption = "";
		this.date = new Date(file.lastModified());
        this.tags = new ArrayList<Tags>();

    }
    
    /**
   	 * Constructor to make a photo with a File and Caption
   	 * @param File file
   	 * @param String caption of photo
   	 */
    public Photos(File file, String caption){
        
        this.file = file;
        this.caption = caption;
		this.date = new Date(file.lastModified());
        this.tags = new ArrayList<Tags>();

    }

    /**
	 * Getter method for name of photo
	 * @return String of name
	 */
    public String getName() {
    
        return name;
    }

    /**
   	 * Getter method for caption of photo
   	 * @return String of caption
   	 * 
   	 */
    public String getCaption() {
        
        return this.caption;
    }
    
    /**
   	 * Setter method for photo caption
   	 * @param String of new Caption
   	 * @return void
   	 * 
   	 */
    public void setCaption (String newCaption) {   
        this.caption = newCaption;
    }

    /**
   	 * Method to remove caption
   	 * @return void
   	 * 
   	 */
    public void removeCaption () {
        
        this.caption = "";
    }

    /**
   	 * Getter method for photo tags
   	 * @return ArrayList<Tags>
   	 * 
   	 */
    public ArrayList<Tags> getTags(){
        
        return this.tags;
    }

    /**
   	 * Getter method for number of tags
   	 * @return int size of list
   	 * 
   	 */
    public int getTagsQuantity(){

        return this.tags.size();
    }
    

    /**
   	 * Getter method for Date String of photo
   	 * @return String of date format
   	 * 
   	 */
	public String getDateString(){
		return new SimpleDateFormat("MM/dd/yy").format(this.getDate());
	}

    /**
   	 * Getter method for Date
   	 * @return String of date
   	 * 
   	 */
    public String getDate(){

        return this.date+"";
    }
    
    /**
   	 * Getter method for date object of photo
   	 * @return date
   	 */
	public Date date(){
		return this.date;
	}

    /**
   	 * Getter method for photo file
   	 * @return String path to photo file
   	 * 
   	 */
	public File getFile() {
		return this.file;
	}

    /**
   	 * Compare method for photo
   	 * @param Photo photo of 
   	 * @return boolean if true or false
   	 * 
   	 */
    public boolean equals(Photos p) {
        
        return this.name.equals(p.name);
    } 

    /**
   	 * Getter method for path to photo
   	 * @return String of path
   	 */
    public String getPath() {
        
        return this.file.getPath();
	}
    
}