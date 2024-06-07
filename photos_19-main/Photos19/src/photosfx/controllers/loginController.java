package photosfx.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
 * Controls the "loginView" stage
 */
public class loginController {
	@FXML private Button enter;
	@FXML private TextField username;

	/**
     * Initializes controller's private fields and sets up controller for stage
     */
	public void start() {
    }
	private Users user;

	/**
     * Checks if the user is an admin or a regular user and opens the corresponding view
     * 
     * @param e the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
	public void enter(ActionEvent e) throws IOException, ClassNotFoundException {

		ArrayList<Users> users= deserialize.deserialize();
		if (users == null) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("ERROR");
        	alert.setHeaderText("Unable to load users");
        	alert.setContentText("An error occurred while loading user data. Please try again later.");
        	alert.showAndWait();
			return;
		}

		String name = username.getText();
		user = null;

		for (Users currentUser : users) {
            if (currentUser.getUserName().equals(name)) {
                user = currentUser;
				break;
			}
		}
		if (name.equals("admin") || user != null) {
			if (name.equals("admin")) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/photosfx/view/adminView.fxml"));
				Parent root = (Parent)loader.load();
				adminController controller = loader.getController();
				
				Stage secondaryStage = new Stage();
				controller.start(secondaryStage);
				
				Scene scene = new Scene(root);
				secondaryStage.setScene(scene);
				secondaryStage.setTitle("Photo App");
				secondaryStage.show();
				
				Stage loginStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        		loginStage.close();
			} else {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/albumView.fxml"));
				Parent parent = (Parent) loader.load();
				albumController controller = loader.<albumController>getController();
				Scene scene = new Scene(parent);
		
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				controller.start(stage,user);
				stage.setScene(scene);
				stage.show();
			
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Error");
			alert.setHeaderText("User not found.");
			alert.setContentText("This user does not exist.");

			alert.showAndWait();
		}
	
}
}

		
		
		
	

	


