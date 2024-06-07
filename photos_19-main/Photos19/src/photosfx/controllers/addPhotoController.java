package photosfx.controllers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import photosfx.model.*;
import javafx.event.ActionEvent;

/** 
 * Controls the "addPhotoView" stage
 */
public class addPhotoController {

    @FXML TextField cap;
	@FXML TextField path;
	@FXML Button close;
	@FXML Button add;
	@FXML Button getUrl;

    private Users user;
    private Album album;
    private File file;

    /**
     * @author Jonathan Van
     * @author Christine Yue
     */
    /**
     * Initializes controller's fields.
     *
     * @param user  the current User
     * @param album the current user's album to which a photo will be added
     */
    public void start(Users user, Album album) {

    this.user=user;
	this.album = album;
    }
	
    
    /**
     * Displays a success dialog with the specified message.
     *
     * @param emessage the success message to be displayed
     */
    public void Success(String emessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Photo Added");
        alert.setHeaderText("Success");
        alert.setContentText(emessage);
        alert.showAndWait();
    }

    /**
     * Adds the chosen photo to the current user's album.
     *
     * @param event the ActionEvent that triggered the method
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
    public void add (ActionEvent event) throws IOException, ClassNotFoundException {    

        ArrayList<Users> saved = deserialize.deserialize();
        
        if (file == null) {
            if (!path.getText().isEmpty()) {
                String relativePath = path.getText();
                file = new File(relativePath);
            }
        }
        
        if (file.exists()) {
            
            String caption = cap.getText();
            Photos photo = new Photos(file, caption);
            album.addPhoto(photo, photo.date());
        } else album.addPhoto(new Photos(file) ,null);
        
        ArrayList<Album> temp = user.getAlbums();        
        for( Album t: temp) {
        
            if(t.getName().equals(album.getName())) {
        
                temp.set(temp.indexOf(t),album);
                user.setAlbums(temp);
            }
        }
        
        for (Users u : saved) {
        
            if (u.equals(user)) saved.set(saved.indexOf(u), user);
        
        }
        
        save.save(saved);
        
        try {
            Success("Success");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photsofx/view/specificAlbumView.fxml"));
            Parent parent = (Parent) loader.load();
            specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.start(stage,user, album);
            stage.setScene(scene);
            stage.show();
        
        } 
        
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
            
    /**
     * Prompts the user to choose a file and gets its path.
     *
     * @param event the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     */
    public void getUrl (ActionEvent event) throws IOException {
        FileChooser browser = new FileChooser();
        file = browser.showOpenDialog(null);
        //System.out.println(file);
        if (file != null) {
            path.setText(file.getPath());
        }
    }

    /**
     * Closes the add photo window and opens the "specificAlbumView" stage.
     *
     * @param e the ActionEvent that triggered the method
     */
    public void close(ActionEvent e) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/specificAlbumView.fxml"));
            Parent parent = (Parent) loader.load();
            specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            controller.start(stage,user, album);
            stage.setScene(scene);
            stage.show();
        } 
        
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    

}


