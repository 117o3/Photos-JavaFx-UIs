package photosfx.controllers;

import java.util.ArrayList;
import java.util.Optional;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import photosfx.model.*;

import javafx.scene.text.Text;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */

/**
 * Opens up the "specificAlbumView" stage
 */
public class specificAlbumViewController {
	
	@FXML ListView<Photos> photosList;
	@FXML TitledPane titlePane;
	@FXML TextField captions, dateField;

	@FXML ListView<Tags> tagsList;
	@FXML Button addTag;
	@FXML Button add;
	@FXML Button delTag;
	@FXML Button delete;
	@FXML Button quit;
	@FXML Button back;

	@FXML Button move, prev, next;
	@FXML Button copy;
	@FXML ImageView imageShow;
	private Stage stage;
	private Users user;
	private Album album;
	private ObservableList<Photos> obs;
	private ObservableList<Tags> tags;
	public String starter = "file:///";
	
	/**
     * Initializes controller's private fields and sets up controller for stage
     * 
     * @param stage the Stage that this controller controls
     * @param user the User whose album is being viewed
     * @param select the Album user selected to view
     */
    public void start (Stage stage, Users user, Album select) {
        
        this.user = user;
		this.album = select;
		this.stage=stage;
		titlePane.setText(select.getName());
		displayPhotos();
	}
    
	/**
     * Opens up the "addPhotoView" stage
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
	@FXML
	public void add (ActionEvent event) throws IOException {
        
        try {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/addPhotoView.fxml"));
		    Parent parent = (Parent) loader.load();
            addPhotoController controller = loader.<addPhotoController>getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.start(user,album);
            stage.setScene(scene);
	    } catch (Exception exception) {
		    exception.printStackTrace();
	
        }
    }
    
	/**
     * Goes back to the "albumView"
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
	@FXML
    public void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/albumView.fxml"));
		Parent parent = (Parent) loader.load();
		albumController controller = loader.<albumController>getController();
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		controller.start(stage,user);
		stage.setScene(scene);
		stage.show();
	}

	/**
     * Closes the application
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
	@FXML
	public void quit(ActionEvent event) throws IOException {
	stage.close();
	}
	
	/**
     * Deletes the photo selected within the album
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
	@FXML
	public void delete(ActionEvent event) throws IOException, ClassNotFoundException {
		Photos select = photosList.getSelectionModel().getSelectedItem();
		if (select == null) {
            
            Dialog("No selected photo");
			return;
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Photo");
        alert.setHeaderText("Delete Photo?");

		Optional<ButtonType> result = alert.showAndWait();

		ArrayList<Users> allUsers = deserialize.deserialize();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			album.getAlbumPhotos().remove(select);		
            for(Users u: allUsers) {
                if(u.getUserName().equals(user.getUserName())) allUsers.set(allUsers.indexOf(u), user);
			}
			save.save(allUsers);
			displayPhotos();
		} else {
			return;
		}
		
		
		
	}
    
	/**
     * Opens the "copyView" stage
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
	@FXML
	public void copy(ActionEvent event) throws IOException {
		Photos select = photosList.getSelectionModel().getSelectedItem();
		
		if (select == null) {
            
            Dialog("No selected photo");
			return;
		}
        
        try {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/copyView.fxml"));
			Parent parent = (Parent) loader.load();
			copyController controller = loader.<copyController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(stage,user,album,select);
			stage.setScene(scene);
        
        } catch (Exception exception) {
            
            exception.printStackTrace();
		}
		
	}

	/**
     * Opens the "moveView" stage
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
	@FXML
	public void move(ActionEvent event) throws IOException {
		Photos select = photosList.getSelectionModel().getSelectedItem();
		
		if (select == null) {
            
            Dialog("No selected photo");
			return;
		}
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/moveView.fxml"));
			Parent parent = (Parent) loader.load();
			moveController controller = loader.<moveController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(stage,user,album,select);
			stage.setScene(scene);
		} catch (Exception exception) {
            
            exception.printStackTrace();
		}
		
	}
    
	/**
     * Opens the "addTag" stage
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
	@FXML
	public void addTag(ActionEvent event) throws IOException, ClassNotFoundException {
		Photos select = photosList.getSelectionModel().getSelectedItem();
		
		if (select == null) {
            
            Dialog("No selected photo");
			return;
		}
        
        try {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/addTag.fxml"));
			Parent parent = (Parent) loader.load();
			addTagController  controller = loader.<addTagController >getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(stage,user,album,select);
			stage.setScene(scene);
        
        } catch (Exception exception) {
        
            exception.printStackTrace();
		}
        
        ArrayList<Users> savedUsers = deserialize.deserialize();
		for (Users u : savedUsers) {
			if (u.equals(user)) savedUsers.set(savedUsers.indexOf(u), user);
        }
        
		save.save(savedUsers);
	}
	
	/**
     * Deletes the selected tag from the selected photo within the album
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
	@FXML
	public void delTag(ActionEvent event) throws IOException, ClassNotFoundException {
		Photos select = photosList.getSelectionModel().getSelectedItem();
		Tags selectTag = tagsList.getSelectionModel().getSelectedItem();
        
        if (select == null || selectTag==null) {
        
            Dialog("No selected photo or Tag");
			return;
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Tag");
        alert.setHeaderText("Delete Tag?");
        Optional<ButtonType> result = alert.showAndWait();
        ArrayList<Users> allUsers = deserialize.deserialize();
		select.getTags().remove(selectTag);
        
		if (result.isPresent() && result.get() == ButtonType.OK) {
			for(Users u: allUsers) {
			
				if(u.getUserName().equals(user.getUserName())) allUsers.set(allUsers.indexOf(u), user);
			}
			
			save.save(allUsers);
			displayPhotos();
		} else {
			return;
		}
		
	}

	/**
     * Opens the "editCaptionView" stage
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
	@FXML
	public void edit (ActionEvent event) throws IOException {
		Photos select = photosList.getSelectionModel().getSelectedItem();

        if (select == null) {

            Dialog("No selected photo");
			return;
		}

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photosfx/view/editCaptionView.fxml"));
			Parent parent = (Parent) loader.load();
			editCaption controller = loader.<editCaption>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(stage,user,album,select);
			stage.setScene(scene);

        } catch (Exception exception) {

            exception.printStackTrace();
		}
		
	}
	
	/**
     * Selects the next picture
     * 
     * @param e the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
	@FXML
	public void next(ActionEvent e) throws IOException {
		int count = photosList.getSelectionModel().getSelectedIndex();
		photosList.getSelectionModel().select(count+1);
		return;
	}
    
	/**
     * Selects the previous picture
     * 
     * @param e the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
	@FXML
	public void prev(ActionEvent e) throws IOException {

		int count = photosList.getSelectionModel().getSelectedIndex();

        if (count-1 >=0) photosList.getSelectionModel().select(count-1);
        return;

    }
	
	/**
     * Helper method that populates the ListView with the current User's photos
     */
	private void displayPhotos () {
        
        ArrayList<Photos> photos = album.getAlbumPhotos();
		obs = FXCollections.observableArrayList();
		
		for (Photos picture : photos) obs.add(picture);
		
		photosList.setItems(obs);

		photosList.setCellFactory(param -> new ListCell<Photos>() {
			private final ImageView imageView = new ImageView();
			private final Text captionText = new Text();

			@Override
			protected void updateItem(Photos photo, boolean empty) {
				super.updateItem(photo, empty);

				if (empty || photo == null) {
					setText(null);
					setGraphic(null);
				} else {
					String pathName = starter + photo.getPath();
					Image image = new Image(pathName, 60, 60, true, true);
					imageView.setImage(image);

					captionText.setText(photo.getCaption());

					VBox vBox = new VBox(5);
					vBox.getChildren().addAll(imageView, captionText);

					setGraphic(vBox);
				}
			}
		});

		photosList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Photos>() {
    	    @Override
    	    public void changed(ObservableValue<? extends Photos> obsList, Photos initialPicture, Photos updatedPicture) {
    	    	if(updatedPicture!=null) {
    	    		
    	    		String pathName = starter + updatedPicture.getPath();
  	
    	    		Image image = new Image(pathName, true);
    	    		imageShow.setImage(image);
    	    		captions.setText(updatedPicture.getCaption());
    	    		dateField.setText(updatedPicture.getDate());
    	    		tags = FXCollections.observableArrayList();
                
                    for (Tags tag : updatedPicture.getTags()) {
                
                        tags.add(tag);
    	    		}
                
                    tagsList.setItems(tags);
    	    		tagsList.setCellFactory(param -> new ListCell<Tags>() {
    	    	
    	    			public void updateItem (Tags tag, boolean check) {
                
                            super.updateItem(tag, check);
                
                            if (check) {
                
                                setText(null);
    	    				} else {
                                
                                setText(tag.toString());
    	    				}
    	    			}
    	    		});
    	    	}
    	    }
    	});		
	}

	/**
     * Displays an error message
     * 
     * @param emessage the error message to be displayed
     */
	 public void Dialog(String emessage) {
		   Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("ERROR");
			alert.setContentText(emessage);
			alert.showAndWait();
	}
}
