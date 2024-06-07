# Photos-JavaFx-UIs

## Photos - Single-User Photo Application
PhotosXX is a JavaFX-based photo application designed to store and manage photos in one or more albums. This project is developed by Christine Yue for Software Methodology.

### Features

Date of Photo: Photos are tagged with the last modification date of the photo file, acting as a proxy for the date the photo was taken.

Tags: Photos can be tagged with attributes such as location and names of people in the photo for easy searching and grouping.

Stock Photos and User Photos: The application supports two sets of photos - stock photos pre-loaded with the application and user photos loaded/imported by the user during runtime.

Login: Users can log in with their username. Password implementation is optional.

Admin Subsystem: An admin user can manage users by listing, creating, and deleting users.

Non-admin User Subsystem: After logging in, users can create, delete, and rename albums, add, remove, and caption photos, search for photos by date range or tag type-value pairs, and more.

Logout: Users can log out at the end of the session, saving all updates made to disk.

Quit Application: Users can safely quit the application at any time, ensuring all updates are saved.

Error Handling: All errors and exceptions are handled gracefully within the GUI setup.

### Model

The model includes data objects and code to store and retrieve photos for a user. Serialization and deserialization are implemented using the java.io.Serializable interface and java.io.ObjectOutputStream/java.io.ObjectInputStream classes.

### Implementation

Developed using JavaFX and FXML for the GUI.

Utilized Git and GitHub for collaborative code maintenance.

Documented each class with Javadoc tags, including authorship information.

### Development Environment

Used VSCode as the development environment.

Project structure includes docs for Javadoc documentation and data for stock photos.
