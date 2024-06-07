package photosfx.model;

import java.io.Serializable;

/**
	 * Model for Tags class. Each tag has a type and value similar to a key value pairing
     * 
*/
public class Tags implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type;
    private String value;

    /**
	 * Constructor for tage with type and value
	 * @param String type type of tag
     * @param String value value of the tag
	 */
    public Tags(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
	 * Getter method for type of tag
	 * @return String type of tag
	 */
    public String getTagType() {
        return this.type;
    }

     /**
	 * Getter method for value of tag
	 * @return String value of tag
	 */
    public String getTagValue() {
        return this.value;
    }

    /**
	 * Compare method for tags
	 * @param Object 
	 * @return Boolean whether tag is equal to this.Tag
	 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Tags other = (Tags) obj;
        return type.equals(other.type) && value.equals(other.value);
    }

    /**
	 * Method to convert to one string
	 * @return a string representation of the tag
	 */
    @Override
    public String toString() {
        return type + " : " + value;
    }
}