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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import photosfx.model.*;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */
/**
 * Controls the "addAlbumView" Stage
 */
public class addAlbumController {
	@FXML TextField albumName;
	private Users user;
	public ArrayList<Users> users;
    
	/**
     * Initializes controller's private fields and sets up controller for stage
     * @param user is the current User that's accessing this stage
     */
	public void start(Users user) {
		this.user=user;
	}
	
    /**
	 * Adds an album to the user's account.
	 * 
	 * @param event the ActionEvent that prompted the button
	 * @throws IOException if an I/O error occurs
	 * @throws ClassNotFoundException if the class cannot be found
	 */
	public void add(ActionEvent event)throws IOException, ClassNotFoundException {

        String name = albumName.getText();
		System.out.print(name);
        if(!name.isEmpty()) {

            if(user.getAlbums()!=null) {

                for (Album album : user.getAlbums()) {

                    if (album.getName().equals(name)) {

                        error("Albums cannot have the same name.");
                        return;
                    }
				}
		} 
            
		try {
		
		Album sample = new Album(name);
		user.addAlbums(sample);
		Success("Success");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/albumView.fxml"));
        Parent parent = (Parent) loader.load();
        albumController controller = loader.<albumController>getController();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.start(stage,user);
        stage.setScene(scene);
        stage.show();
        saves(user);
        return;
    }
		catch(java.lang.NullPointerException exception){
        }		
			
			Album sample = new Album(name);
			ArrayList<Album> t = new ArrayList<>();
			t.add(sample);
			user.setAlbums(t);
			Success("Success");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/albumView.fxml"));
			Parent parent = (Parent) loader.load();
			albumController controller = loader.<albumController>getController();
			Scene scene = new Scene(parent);

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(stage,user);
			stage.setScene(scene);
			stage.show();
		    saves(user);
		    return;
				
        }
}
	/**
	 * Saves the user's albums by updating the user object in the list of saved users.
	 *
	 * @param user the user whose albums need to be saved
	 */	
	public void saves(Users user){
        try {
            ArrayList<Users> savedUsers = deserialize.deserialize();
    
            for (int i = 0; i < savedUsers.size(); i++) {
                if (savedUsers.get(i).getUserName().equals(user.getUserName())) {
                    savedUsers.set(i, user);
                    break;
                }
            }
    
            save.save(savedUsers);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
	/**
	 * Displays a success dialog with the specified message.
	 *
	 * @param emessage the success message to be displayed
	 */
	public void Success(String emessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Album Added");
        alert.setHeaderText("Success");
        alert.setContentText(emessage);
        alert.showAndWait();
    }

	/**
     * Displays an error dialog with the specified error message.
     *
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
     * Closes the current stage and opens the "albumView" stage.
     *
     * @param event the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs while loading the FXML file
     * @throws ClassNotFoundException if the specified controller class cannot be found
     */
	public void close(ActionEvent event) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/albumView.fxml"));
		Parent parent = (Parent) loader.load();
		albumController controller = loader.<albumController>getController();
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		controller.start(stage,user);
		stage.setScene(scene);
		stage.show();
	}
}
