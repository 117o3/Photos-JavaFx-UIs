package photosfx.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import photosfx.model.*;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */

/**
 * Controls the "copyView" stage
 */
public class copyController {

    private ObservableList<Album> obsList;

	@FXML TextField caption;
	@FXML ListView<Album> albumsList;
	@FXML Button close;
	@FXML Button copy;
	private Stage stage;
	private Users user;
	private Photos photo;
	private Album album;

     /**
     * Initializes controller's private fields and sets up controller for stage
     * @param stage the Stage that this controller controls
     * @param user the current User that's accessing this stage
     * @param album the album that the photo is being copied from
     * @param photo the photo to be copied
     */
    public void start(Stage stage, Users user,Album album, Photos photo) {
       
        this.stage=stage;
        this.user=user;
        this.photo=photo;
        this.album=album;
        displayAlbums();
    }

   /**
     * Opens the "specificAlbumView" stage
     * @param event the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     */
    public void close(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/specificAlbumView.fxml"));
        Parent parent = (Parent) loader.load();
        specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.start(stage,user,album);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Copies the selected photo to the selected album
     * @param event the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
    public void copy(ActionEvent event) throws IOException, ClassNotFoundException {
        Album select = albumsList.getSelectionModel().getSelectedItem();

        if (select == null) {

            errDialog("No selected album.");
            return;
        }

        ArrayList<Users> allUsers = deserialize.deserialize();
        select.addPhoto(photo, photo.date());
        

        for(Users u: allUsers) {

            if(u.getUserName().equals(user.getUserName())){

                allUsers.set(allUsers.indexOf(u), user);
            }
        }
        
        
        save.save(allUsers);
        Dialog("Successfully copied photo");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/specificAlbumView.fxml"));
        Parent parent = (Parent) loader.load();
        specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.start(stage,user,album);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Displays the available albums in a listview to select album to copy in
     */ 
    private void displayAlbums() {
        obsList = FXCollections.observableArrayList();


        for (Album album : user.getAlbums()) {
            obsList.add(album);
        }


        albumsList.setItems(obsList);


    }

    /**
     * Displays an error dialog with the specified message
     * @param emessage the error message to be displayed
     */
    public void errDialog(String emessage) {
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR");
        alert.setContentText(emessage);
        alert.showAndWait();
    }

    /**
     * Displays a success dialog with the specified message
     * @param emessage the success message to be displayed
     */
    public void Dialog(String emessage) {
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText(emessage);
        alert.showAndWait();
    }
}

