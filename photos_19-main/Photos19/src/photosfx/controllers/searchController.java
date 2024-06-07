package photosfx.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import photosfx.model.*;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */

/**
 * Opens up the "searchView" stage
 */
public class searchController {
	@FXML ImageView image_view;

	@FXML Button quit;
    @FXML Button andSearch;
    @FXML Button orSearch;
    @FXML Button dateSearch;
	@FXML ListView<Photos> photoListView;
	@FXML ListView<Tags> tags;
	@FXML ListView<Tags> para_ListView;

	@FXML TextField caption;
	@FXML TextField date;
	@FXML
	private ChoiceBox<String> tagChoice, tagValue;
	@FXML
	private DatePicker fromDate, toDate;
	@FXML Button createAlbum;
	@FXML Button back;
	@FXML Button logout;
	@FXML Button removeTag;
	@FXML Button addTag;
    
    private ObservableList<Photos> obsList=FXCollections.observableArrayList();;
	private ObservableList<Tags> tagsObsList;
	private Users user;
	private Stage stage;

    /**
     * Initializes controller's private fields and sets up controller for stage
     * 
     * @param stage the Stage that this controller controls
     * @param user the user who is searching
     */
    public void start(Stage stage, Users user) {
        
        this.user=user;
        this.stage=stage;
        
        ArrayList<String> tagtype = new ArrayList<String>();
        ArrayList<String> tagvalue = new ArrayList<String>();
        tagtype.add(0, "Tag Type");
        tagvalue.add(0, "Tag Value");
        ArrayList<Album> allalbums = user.getAlbums();
        
        for (Album curralbum : allalbums) {
        
            ArrayList<Photos> allphoto = curralbum.getAlbumPhotos();
            for (Photos photo : allphoto) {
        
                ArrayList<Tags> tag = photo.getTags();
                for (Tags t : tag) {
        
                    if (!tagtype.contains(t.getTagType())) tagtype.add(t.getTagType());
                    if (!tagvalue.contains(t.getTagValue())) tagvalue.add(t.getTagValue());
                
                }

            }

        }
        
        tagChoice.setItems(FXCollections.observableArrayList(tagtype));
        tagChoice.setValue("Tag Type");

        tagValue.setItems(FXCollections.observableArrayList(tagvalue));
        tagValue.setValue("Tag Value");
        
    }
    

    /**
     * Populates the ListView based on the User's search parameters using a disjunctive combination
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
    public void orSearch(ActionEvent event) throws IOException{
        
        photoListView.getItems().clear();
        ArrayList<Album> albumList = user.getAlbums();
        for (Album album : albumList) {
            ArrayList<Photos> photolist = album.getAlbumPhotos();
            for (Photos photo : photolist) {
                boolean added = false;
                ArrayList<Tags> phototag = photo.getTags();
                String[] photodate = photo.getDate().toString().split(" ");
                Alert alert = new Alert(AlertType.ERROR);
                DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
                TemporalAccessor accessor = parser.parse(photodate[1]);
                int month = accessor.get(ChronoField.MONTH_OF_YEAR);

                LocalDate photoDate = LocalDate.of(Integer.parseInt(photodate[5]), month,
                        Integer.parseInt(photodate[2]));
                
                if (fromDate.getValue() != null) {

             
                    String frdate = fromDate.getValue().toString();
                    String[] fromdate = frdate.split("-");
                    LocalDate f_date = LocalDate.of(Integer.parseInt(fromdate[0]), Integer.parseInt(fromdate[1]),
                            Integer.parseInt(fromdate[2]));

       
                    String todate = toDate.getValue().toString();
                    String[] tdate = todate.split("-");
                    LocalDate t_date = LocalDate.of(Integer.parseInt(tdate[0]), Integer.parseInt(tdate[1]),
                            Integer.parseInt(tdate[2]));

                    if (photoDate.isAfter(f_date) && photoDate.isBefore(t_date)) {

                        if (photoListView.getItems().contains(photo)) continue; 
                        else {
                            
                            added = true;
                            obsList.add(photo);
                            render();
                        }

                    }
                }

                if (tagChoice.getSelectionModel().getSelectedItem().toString() != null && tagValue.getSelectionModel().getSelectedItem().toString() != null && added == false) {
                    
                    for (Tags currTag : tags.getItems()) {
                    
                        for (Tags pTag : phototag) {
                    
                            if (currTag.getTagType().matches(pTag.getTagType()) && currTag.getTagValue().matches(pTag.getTagValue())) {
                    
                                if (photoListView.getItems().contains(photo)) {
                    
                                    continue;
                    
                                } else {
                    
                                    obsList.add(photo);
                                    render();
                                }

                            }

                        }
                    }

                }
            }
        }
    }

    /**
     * Populates the ListView based on the User's search parameters using a conjunctive combination
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
    public void andSearch(ActionEvent event) throws IOException {
        photoListView.getItems().clear();
        ArrayList<Album> albumList = user.getAlbums();
    
        for (Album album : albumList) {
            ArrayList<Photos> photolist = album.getAlbumPhotos();
    
            for (Photos photo : photolist) {
                boolean hasAllTags = true;
                ArrayList<Tags> phototag = photo.getTags();
                String[] photodate = photo.getDate().toString().split(" ");
    
                DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
                TemporalAccessor accessor = parser.parse(photodate[1]);
                int month = accessor.get(ChronoField.MONTH_OF_YEAR);
                LocalDate photoDate = LocalDate.of(Integer.parseInt(photodate[5]), month, Integer.parseInt(photodate[2]));
    
                if (fromDate.getValue() != null) {
                    String frdate = fromDate.getValue().toString();
                    String[] fromdate = frdate.split("-");
                    LocalDate f_date = LocalDate.of(Integer.parseInt(fromdate[0]), Integer.parseInt(fromdate[1]), Integer.parseInt(fromdate[2]));
    
                    String todate = toDate.getValue().toString();
                    String[] tdate = todate.split("-");
                    LocalDate t_date = LocalDate.of(Integer.parseInt(tdate[0]), Integer.parseInt(tdate[1]), Integer.parseInt(tdate[2]));
    
                    if (photoDate.isAfter(f_date) && photoDate.isBefore(t_date)) {
                        if (obsList.contains(photo))
                            continue;
                    } else {
                        hasAllTags = false;
                    }
                }
    
                if (tagChoice.getSelectionModel().getSelectedItem() != null && tagValue.getSelectionModel().getSelectedItem() != null && hasAllTags) {
                    for (Tags currTag : tags.getItems()) {
                        boolean found = false;
                        for (Tags pTag : phototag) {
                            if (currTag.getTagType().matches(pTag.getTagType()) && currTag.getTagValue().matches(pTag.getTagValue())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            hasAllTags = false;
                            break;
                        }
                    }
                }
    
                if (hasAllTags && !obsList.contains(photo)) {
                    obsList.add(photo);
                    render();
                }
            }
        }
    }
    
    /**
     * Populates the ListView based on the User's search parameters using a date range
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
    public void dateSearch(ActionEvent event) throws IOException {
        photoListView.getItems().clear();
    
        ArrayList<Album> albumList = user.getAlbums();
        for (Album album : albumList) {
            ArrayList<Photos> photolist = album.getAlbumPhotos();
            for (Photos photo : photolist) {
                String[] photodate = photo.getDate().toString().split(" ");
                DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
                TemporalAccessor accessor = parser.parse(photodate[1]);
                int month = accessor.get(ChronoField.MONTH_OF_YEAR);
                LocalDate photoDate = LocalDate.of(Integer.parseInt(photodate[5]), month, Integer.parseInt(photodate[2]));
    
                if (fromDate.getValue() != null && toDate.getValue() != null) {
                    String frdate = fromDate.getValue().toString();
                    String[] fromdate = frdate.split("-");
                    LocalDate f_date = LocalDate.of(Integer.parseInt(fromdate[0]), Integer.parseInt(fromdate[1]), Integer.parseInt(fromdate[2]));
    
                    String todate = toDate.getValue().toString();
                    String[] tdate = todate.split("-");
                    LocalDate t_date = LocalDate.of(Integer.parseInt(tdate[0]), Integer.parseInt(tdate[1]), Integer.parseInt(tdate[2]));
    
                    if (photoDate.isAfter(f_date.minusDays(1)) && photoDate.isBefore(t_date.plusDays(1)) && !obsList.contains(photo)) {
                        obsList.add(photo);
                        render();
                    }
                }
            }
        }
    }
    
    /**
     * Populates the ListView based on the User's search tag preferences
     * 
     * @param event the ActionEvent that prompted the button
     */
    public void addTag(ActionEvent event) {
        ObservableList<Tags> tagList = tags.getItems();
    
        String selectedTagType = tagChoice.getSelectionModel().getSelectedItem();
        String selectedTagValue = tagValue.getSelectionModel().getSelectedItem();
    
        if (selectedTagType == null || selectedTagType.equals("Tag Type") ||
                selectedTagValue == null || selectedTagValue.equals("Tag Value")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Search View Error");
            alert.setHeaderText("Invalid Tag Selection");
            alert.setContentText("Please select a valid tag type and tag value.");
            alert.showAndWait();
            return;
        }
    
        boolean isValidPair = false;
        for (Album album : user.getAlbums()) {
            for (Photos photo : album.getAlbumPhotos()) {
                for (Tags tag : photo.getTags()) {
                    if (tag.getTagType().equals(selectedTagType) && tag.getTagValue().equals(selectedTagValue)) {
                        isValidPair = true;
                        break;
                    }
                }
                if (isValidPair) {
                    break;
                }
            }
            if (isValidPair) {
                break;
            }
        }
    
        if (!isValidPair) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Search View Error");
            alert.setHeaderText("Invalid Tag Pair");
            alert.setContentText("The selected tag type and tag value do not form a valid pair.");
            alert.showAndWait();
            return;
        }
    
        Tags newTag = new Tags(selectedTagType, selectedTagValue);
    
        for (Tags currentTag : tagList) {
            if (currentTag.getTagType().equals(newTag.getTagType()) &&
                    currentTag.getTagValue().equals(newTag.getTagValue())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Search View Error");
                alert.setHeaderText("Tag Add Error");
                alert.setContentText("Already Selected Tag");
                alert.showAndWait();
                return;
            }
        }
    
        tags.getItems().add(newTag);
        tags.getSelectionModel().select(tags.getItems().size() - 1);
        tagChoice.getSelectionModel().selectFirst();
        tagValue.getSelectionModel().selectFirst();
    }

    /**
     * Removes tags from the ListView containing User's tag search preferences
     * 
     * @param event the ActionEvent that prompted the button
     */
    public void removeTag(ActionEvent event) {
        
        tags.getItems().remove(tags.getSelectionModel().getSelectedItem());
        tags.refresh();
        tags.getSelectionModel().select(0);

    }
    
    /**
     * Creates an album based on the Photo search results
     * 
     * @param event the ActionEvent that prompted the button
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     * @throws IOException if an I/O error occurs
     */
    public void createAlbum(ActionEvent event) throws ClassNotFoundException, IOException {
        if (photoListView.getItems().isEmpty()) {
       
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Search View Error");
            alert.setHeaderText("No Photos Error.");
            alert.setContentText("Please Search Photos to Create Album.");
            alert.showAndWait();

        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            Album newAlbum = new Album("Search Result " + dateFormat.format(cal.getTime()).toString());
            user.getAlbums().add(newAlbum);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Album Created");
            alert.setHeaderText("Success");
            alert.setContentText("Album Created!");
            alert.showAndWait();

            for (Photos currphoto : photoListView.getItems()) {
                newAlbum.getAlbumPhotos().add(currphoto);
            
            }
            ArrayList<Users> savedUsers = deserialize.deserialize();
            for (Users u : savedUsers) {
       
                if (u.equals(user)) {
       
                    savedUsers.set(savedUsers.indexOf(u), user);
                }
            }
            save.save(savedUsers);
            
        }
        
    }
    /**
     * Displays the pictures from the search results in the ListView
     */
    private void render() {
    photoListView.setItems(obsList);

    photoListView.setCellFactory(param -> new ListCell<Photos>() {
        private ImageView imageView = new ImageView();
        @Override
            public void updateItem (Photos photo, boolean empty) {
            super.updateItem(photo, empty);
            
            if (empty) {
            
                setText (null);
                setGraphic(null);
            } else {
            
                String path = "file:///" + photo.getPath();
                Image image = new Image(path, 50, 50, true, true);
                imageView.setImage(image);
                setGraphic(imageView);
            }
            }
        });

    }
    
    /**
     * Opens the "loginView" stage
     * 
     * @param event the ActionEvent that prompted the button
     */
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
     * Goes back to the "albumView" stage
     * 
     * @param event the ActionEvent that prompted the button
     * @throws IOException if an I/O error occurs
     */
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
     * Exits the application
     * @param event the ActionEvent that prompted the button
     */
    public void quit(ActionEvent event) {
        
        stage.close();
    }



}
