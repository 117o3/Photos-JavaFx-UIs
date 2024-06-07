package photosfx.app;

import java.io.IOException;
import java.io.Serializable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Date;
import java.time.ZoneId;

/**
 * @author Jonathan Van
 * @author Christine Yue
 */
public class Photos extends Application implements Serializable{
	private static final long serialVersionUID = 1L;
    
	/**
     * The date and time when the photo was taken.
     */
	private LocalDateTime dateTaken;

	/**
     * Initializes main method and login view
     * @param primaryStage is the Stage that this controller controls
     */
		@Override
		public void start(Stage primaryStage) {
			//System.out.println("start: Photos.java");
			try {
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/photosfx/view/loginView.fxml"));
				Scene scene = new Scene(root,300,200);
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}

	/**
     * Getter Method for Local Date
     * @return LocalDateTime representing the date and time the photo was taken
     */
    public LocalDateTime getDateTime() {
        return dateTaken;
    }

	/**
     * Helper method to convert a Date object to a LocalDateTime object
     * @param dateTime the Date object to be converted
     * @return LocalDateTime representing the date and time of the Date object
     */
    public static LocalDateTime convertToLocalDateTime(Date dateTime) {
        return dateTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
		
		
		/**
		 * The main method to launch the application.
		 * @param args command-line arguments
		 * @throws IOException if an I/O error occurs
		 */
		public static void main(String[] args) throws IOException {
				launch(args);	
			}
		}
	