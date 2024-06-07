package photosfx.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import photosfx.model.*;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */

/**
 * Controls the "addTag" stage
 */
public class addTagController {

	@FXML Button close;
	@FXML Button edit;
	@FXML TextField value;
	@FXML TextField type;
	
	Photos select;
	Users user;
	Stage stage;
    Album album;

	/**
     * Initializes controller's private fields and sets up controller for stage
     * @param stage the Stage that this controller controls
     * @param user the current User that's accessing this stage
     * @param album the album that contains the photo the tag will be added to
     * @param select the Photo that the tag will be added to
     */
	public void start(Stage stage, Users user, Album album, Photos select) {

        this.user = user;
		this.select = select;
		this.stage = stage;
		this.album = album;
	}
	
	/**
     * Adds a tag to the photo
     * @param event the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
	public void addTag(ActionEvent event) throws IOException, ClassNotFoundException {
		String Type = type.getText();
		String Value = value.getText();
		ArrayList<Users> allUsers = deserialize.deserialize();
	
		if (Type.equals("") || Value.equals("")) {
			error("Cannot have an empty parameter");
			return;
		}
	
		Tags tag = new Tags(Type, Value);
	
		boolean tagExists = false;
		for (Tags existingTag : select.getTags()) {
			if (existingTag.getTagType().equalsIgnoreCase(tag.getTagType()) &&
				existingTag.getTagValue().equalsIgnoreCase(tag.getTagValue())) {
				tagExists = true;
				break;
			}
		}
	
		if (!tagExists) {
			select.getTags().add(tag);
			for (Users u : allUsers) {
				if (u.getUserName().equals(user.getUserName()))
					allUsers.set(allUsers.indexOf(u), user);
			}
			save.save(allUsers);
			Dialog("Successfully added Tag");
		} else {
			error("Tag already exists");
		}
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/addTag.fxml"));
		Parent parent = (Parent) loader.load();
		addTagController controller = loader.<addTagController>getController();
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		controller.start(stage, user, album, select);
		stage.setScene(scene);
		stage.show();
		}

	/**
     * Displays an error alert with the specified message
     * @param emessage the error message to be displayed
     */
	public void error(String emessage) {
    
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ALERT ERROR");
        alert.setHeaderText("ERROR");
        alert.setContentText(emessage);
        alert.showAndWait();
    }
    
	/**
     * Displays a success dialog when a tag is added successfully
     * @param emessage the success message to be displayed
     */
	public void Dialog(String emessage) {
    
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Tag Added");
        alert.setHeaderText("Success");
        alert.setContentText(emessage);
        alert.showAndWait();
    }
    
	/**
     * Closes the current window and opens the "specificAlbumView" stage
     * @param event the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
	public void close(ActionEvent event) throws IOException, ClassNotFoundException {
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/specificAlbumView.fxml"));
		Parent parent = (Parent) loader.load();
		specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		controller.start(stage,user,album);
		stage.setScene(scene);
		stage.show();
	}

}
