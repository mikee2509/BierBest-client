package bierbest.scenes;

import bierbest.model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public BorderPane loginPane;
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField streetAddress;
    @FXML
    public TextField city;
    @FXML
    public TextField email;
    @FXML
    public TextField phoneNumber;
    @FXML
    public VBox loginForm;
    @FXML
    public VBox registrationForm;
    @FXML
    public Region logInUnderscore;
    @FXML
    public Region signUpUnderscore;


    private Stage root;
    public static User currentUser;


    public LoginController() {
        currentUser = new User();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setStage(Stage root) {
        this.root = root;
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        currentUser.setUserName(username.getText());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene_home.fxml"));
        Scene homeScene = new Scene(fxmlLoader.load());
        HomeController controller = fxmlLoader.getController();
        controller.setStage(root);
        root.setScene(homeScene);
        root.centerOnScreen();
        //controller.searchBeers(null);
    }

    public void registrationForm(MouseEvent mouseEvent) throws IOException {
        if (registrationForm == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fragment_registration_form.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();
        }
        loginPane.setCenter(registrationForm);
        logInUnderscore.setVisible(false);
        signUpUnderscore.setVisible(true);
        root.sizeToScene();
    }

    public void signUp(ActionEvent actionEvent) {
        System.out.println("Test");
    }

    public void loginForm(MouseEvent mouseEvent) {
        loginPane.setCenter(loginForm);
        logInUnderscore.setVisible(true);
        signUpUnderscore.setVisible(false);
        root.sizeToScene();
    }
}
