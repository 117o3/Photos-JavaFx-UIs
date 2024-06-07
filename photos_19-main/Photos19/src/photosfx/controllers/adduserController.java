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
import photosfx.model.Users;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */

/** 
 * Controls the "adduserview" stage
 */
public class adduserController {
    
    @FXML 
	private Button addUserButton;
	@FXML 
	private Button close;
	@FXML
	private TextField userNameTextField;
    
    boolean check = false;
	FXMLLoader loader;
	Parent parent;
	Stage stage;
    
	/**
     * Initializes controller's private fields and sets up controller for stage
     * @param stage the Stage that this controller controls
     */
    public void start (Stage stage) {
    
        this.stage=stage;
		
	}
	
	/**
     * Returns back to the previous admin view
     * @param e the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
	public void close (ActionEvent e) throws IOException, ClassNotFoundException {
	
		loader = new FXMLLoader(getClass().getResource("/photosfx/view/adminView.fxml"));

			parent = (Parent) loader.load();
	
		adminController controller = loader.<adminController>getController();
		
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		controller.start(stage);
		stage.setScene(scene);
		stage.show();
		
	}

	/**
     * Displays a success dialog when a user is added successfully
     * @param emessage the success message to be displayed
     */
	public void Dialog(String emessage) {
    
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User Added");
        alert.setHeaderText("Success");
        alert.setContentText(emessage);
        alert.showAndWait();
    }
	
	/**
     * Adds a user to the user list
     * @param e the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
	public void addUser (ActionEvent e) throws IOException, ClassNotFoundException {
		
		Users newUser = new Users(userNameTextField.getText());
		
		if (!newUser.getUserName().isEmpty()) {

			
				ArrayList<Users> storedUsers = deserialize.deserialize();
			
				for (Users currentUser : storedUsers) {
					if (currentUser.getUserName().equals(newUser.getUserName())) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR ADMIN");
						alert.setHeaderText("User error");
						alert.setContentText("user exists");

						alert.showAndWait();
						return;
					}
					
				}

				storedUsers.add(newUser);
				
				save.save(storedUsers);
				Dialog("Successfully added User");
			
				loader = new FXMLLoader(getClass().getResource("/photosfx/view/adminView.fxml"));

				parent = (Parent) loader.load();
		
			adminController controller = loader.<adminController>getController();
			
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			controller.start(stage);
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
}
