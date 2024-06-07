package photosfx.controllers;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Optional;
import java.text.SimpleDateFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import photosfx.model.*;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */

/**
 * Controls the "albumView" stage
 */
public class albumController {
	@FXML ListView<Album> albumsListView;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button edit;
	@FXML Button search;
	@FXML Button logout;
	@FXML Button quit;
	@FXML TextField albumname;
	@FXML TextField num;
	@FXML TextField date;

    Users current;
	Album select;
	Stage stage;

	private ObservableList<Album> obsList;
	
	/**
     * Initializes controller's private fields and sets up controller for stage
     * @param stage the Stage that this controller controls
     * @param user the current User that's accessing this stage
     */
	public void start(Stage stage, Users user) {

		this.stage = stage;
		this.current= user;
		displayAlbums();
		select = albumsListView.getSelectionModel().getSelectedItem();
	}
	
	/**
     * Opens the "addAlbumView" stage
     * @param event the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
    @FXML
	public void add(ActionEvent event) throws IOException, ClassNotFoundException {
	    try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/addAlbumView.fxml"));
			Parent parent = (Parent) loader.load();
			addAlbumController controller = loader.<addAlbumController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(current);
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
    
	 /**
     * Deletes the selected album
     * @param e the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
    @FXML
	public void delete (ActionEvent e) throws IOException, ClassNotFoundException {
		select = albumsListView.getSelectionModel().getSelectedItem();
		if (select == null) {
			Dialog("No selected album.");
			return;
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Album");
        alert.setHeaderText("Delete Album?");
        Optional<ButtonType> result = alert.showAndWait();
        ArrayList<Users> saved = deserialize.deserialize();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (Users u : saved) {
            
                if (current.equals(u)) {
            
                    current.deleteAlbum(select);
                    saved.set(saved.indexOf(u), current);
                }
                save.save(saved);
            }
            displayAlbums();
        } else {
			return;
		}
            
    }

	/**
     * Opens the "editView" stage
     * @param e the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
    @FXML
	public void edit (ActionEvent e) throws IOException, ClassNotFoundException {
	try {
			select = albumsListView.getSelectionModel().getSelectedItem();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/editView.fxml"));
			Parent parent = (Parent) loader.load();
			editController controller = loader.<editController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			controller.start(stage, current, select);
			stage.setScene(scene);
			stage.show();

        } catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
     * Opens the "searchView" stage
     * @param e the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
    @FXML
    public void search(ActionEvent e) throws IOException, ClassNotFoundException {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/searchView.fxml"));
			Parent parent = (Parent) loader.load();
			searchController controller = loader.<searchController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			controller.start(stage,current);
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	/**
     * Exits the application
     * @param e the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
    @FXML
	public void quit (ActionEvent e) throws IOException, ClassNotFoundException {
		stage.close();
		
	}
	
	/**
     * Opens the "loginView" stage
     * @param event the ActionEvent that triggered the method
     */
    @FXML
	public void logout(ActionEvent event) {

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
     * Displays User albums in listview
     */
	private void displayAlbums () {
		try {
            obsList = FXCollections.observableArrayList();
            

            for (Album album : current.getAlbums()) {
                obsList.add(album);
            }

            albumsListView.setItems(obsList);
            albumsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {

                @Override
                public void changed(ObservableValue<? extends Album> observable, Album oldValue, Album newValue) {
                    if(newValue!=null) {
                        num.setText("Photos: \t"+newValue.getPhotoQuantity());
                        albumname.setText("Album: \t"+"\""+newValue.getName()+"\"");
                        if (newValue.getPhotoQuantity() == 0) {
                            date.setText("No Photos");
                        } else {
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                            String oldestDateTime = formatter.format(newValue.getOldestPhotoDateTime());
                            String latestDateTime = formatter.format(newValue.getLatestPhotoDateTime());
                            
                            date.setText("Date Range: \t" + oldestDateTime + " - " + latestDateTime);
                        }  
                    }
                    else {
                        albumname.setText("");
                        num.setText("");
                        date.setText("");
                    }
                }
            });         		
            albumsListView.setOnMouseClicked (new EventHandler<MouseEvent>() {
                @Override
                public void handle (MouseEvent click) {
                    Album selectedAlbum = albumsListView.getSelectionModel().getSelectedItem();
                    if (click.getClickCount() == 2) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/specificAlbumView.fxml"));
                            Parent parent = (Parent) loader.load();
                            specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
                            Scene scene = new Scene(parent);
                    
                            Stage stage = (Stage) ((Node) click.getSource()).getScene().getWindow();
                            controller.start(stage,current, selectedAlbum);
                            stage.setScene(scene);
                            stage.show();
                        } 
                        catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }
                    else {
                        select=selectedAlbum;
                    }
                }
            });
            albumsListView.getSelectionModel().select(0);
        } catch(NullPointerException e) {
		}
	
}
	/**
     * Displays an error dialog with the specified message
     * @param emessage the error message to be displayed
     */
	public void Dialog(String emessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Delete Album");
        alert.setHeaderText("ERROR");
        alert.setContentText(emessage);
        alert.showAndWait();
}
	 
}

	

