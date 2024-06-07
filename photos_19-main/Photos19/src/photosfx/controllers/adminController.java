package photosfx.controllers;


import java.io.IOException;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import photosfx.model.Users;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */

/** 
 * Controls the "adminView" stage
 */
public class adminController {

	@FXML ListView<Users> ListViewer;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button logout;
	@FXML Button quit;

	private ObservableList<Users> users;
	Stage stage;
    
	/**
     * Initializes controller's private fields and sets up controller for stage
     * @param stage the Stage that this controller controls
     * @throws ClassNotFoundException if the class cannot be found
     * @throws IOException if an I/O error occurs
     */
    public void start(Stage stage) throws ClassNotFoundException, IOException {
		this.stage=stage;
		displayUsers();
	}
	
	/**
     * Opens the "adduserView" stage
     * @param e the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
	public void addUser (ActionEvent e) throws IOException, ClassNotFoundException {

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/adduserView.fxml"));
				Parent parent = (Parent) loader.load();
				adduserController controller = loader.<adduserController>getController();
				Scene scene = new Scene(parent);
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				controller.start(stage);
				stage.setScene(scene);
				stage.show();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			
			displayUsers();

		}
	
	
	/**
     * Deletes the selected user
     * @param e the ActionEvent that triggered the method
     * @throws ClassNotFoundException if the class cannot be found
     * @throws IOException if an I/O error occurs
     */
	public void deleteUser (ActionEvent e) throws ClassNotFoundException, IOException{
		Users selectedUser = ListViewer.getSelectionModel().getSelectedItem();
		if (selectedUser == null) {
            
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("ERROR");
        	alert.setHeaderText("Unable to remove User");
        	alert.setContentText("Select a User to remove");
        	alert.showAndWait();

			return;
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Remove User");
        alert.setHeaderText("Remove User?");

		Optional<ButtonType> result = alert.showAndWait();
		ArrayList<Users> storedUsers = deserialize.deserialize();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			for (Users user : storedUsers) {
				if (user.getUserName().equals(selectedUser.getUserName())) {
					storedUsers.remove(user);
					break;
				}
			}
		} else {
			return;
		}

        save.save(storedUsers);

		displayUsers();
	}
    
	/**
     * Closes the current window
     * @param event the ActionEvent that triggered the method
     */
	public void closeWindow(ActionEvent event) {
        
        stage.close();
		
    }
    
	/**
     * Logs out the user and returns to the login window
     * @param event the ActionEvent that triggered the method
     */
	public void logOut(ActionEvent event) {

		try {
		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/loginView.fxml"));
			Parent parent = (Parent) loader.load();
			loginController controller = loader.<loginController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start();
			stage.setScene(scene);
			stage.show();
        
        } catch (Exception exception) {
        
            exception.printStackTrace();
		}
	}

	/**
     * Displays all users in the ListView
     * @throws ClassNotFoundException if the class cannot be found
     * @throws IOException if an I/O error occurs
     */
	public void displayUsers () throws ClassNotFoundException, IOException {
        users = FXCollections.observableArrayList();
        
		ArrayList<Users> savedUsers = deserialize.deserialize();
        
        for (Users user : savedUsers) users.add(user);
		    
        ListViewer.setItems(users);
		ListViewer.setCellFactory(new Callback<ListView<Users>, ListCell<Users>>(){  
            @Override
            public ListCell<Users> call(ListView<Users> p) {                 
                ListCell<Users> cell = new ListCell<Users>(){ 
                    @Override
                    protected void updateItem(Users user, boolean bln) {
                        super.updateItem(user, bln);
                        if (user != null) setText(user.getUserName());
                        else setText(null);
                    }
 
                };
                 
                return cell;
            }
        });
	}
}
