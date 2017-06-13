package bierbest.scenes;

import bierbest.Main;
import bierbest.model.ClientModel;
import bierbest.model.Request;
import bierbest.model.Response;
import bierbest.model.ServerConnection;
import bierbest.model.payloads.ClientData;
import bierbest.model.payloads.MessageAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javassist.bytecode.stackmap.TypeData;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(TypeData.ClassName.class.getName());
    public static final String USERNAME_ID = "username";
    public static final String PASSWORD_ID = "password";
    @FXML
    public TextField usernameLogin;
    @FXML
    public PasswordField passwordLogin;
    @FXML
    public TextField usernameRegistration;
    @FXML
    public PasswordField passwordRegistration;
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
    public BorderPane loginPane;
    @FXML
    public VBox loginForm;
    @FXML
    public VBox registrationForm;
    @FXML
    public Region logInUnderscore;
    @FXML
    public Region signUpUnderscore;
    @FXML
    public VBox errorVbox;
    @FXML
    public Label errorText;

    private static ClientModel currentUser;
    private Preferences preferences;
    private Stage root;

    public LoginController() {
        currentUser = new ClientModel();
        preferences = Preferences.userRoot().node(LoginController.class.getName());
    }

    public void initialize() throws Exception {
        usernameLogin.setText(preferences.get(LoginController.USERNAME_ID, ""));
        passwordLogin.setText(preferences.get(LoginController.PASSWORD_ID, ""));
    }

    public static ClientModel getCurrentUser() {
        return currentUser;
    }

    public void setStage(Stage root) {
        this.root = root;
    }

    public void logIn(ActionEvent actionEvent) {
        errorVbox.setVisible(false);
        if (usernameLogin.getText().isEmpty()) {
            showError("Please enter username.", usernameLogin);
            return;
        }
        if (passwordLogin.getText().isEmpty()) {
            showError("Please enter password.", passwordLogin);
            return;
        }

        ServerConnection connection = new ServerConnection(Main.SERVER_ADDRESS, Main.PORT);
        Request request = new Request(usernameLogin.getText(), passwordLogin.getText(),
                MessageAction.GET_CLIENT_DATA, null);
        connection.addSingleRequest(request);
        connection.setOnSucceeded(event -> {
            ArrayList<Response> responses = connection.getValue();
            if (responses.isEmpty()) {
                showError("Error connecting to the server", usernameLogin);
            } else if (responses.get(0).getResponseCode() == Response.SUCCESS) {
                ClientData clientData = (ClientData) responses.get(0).getPayload();
                currentUser = clientData.getClient();
                LOGGER.log(Level.INFO, "Successfully logged in");
                preferences.put(USERNAME_ID, usernameLogin.getText());
                preferences.put(PASSWORD_ID, passwordLogin.getText());
                loadHomeScene();
            } else if (responses.get(0).getResponseCode() == Response.DENIED) {
                showError("Incorrect username or password", usernameLogin);
            }
        });

        Thread thread = new Thread(connection);
        thread.start();
    }

    public void signUp(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return;
        }
        ServerConnection connection = new ServerConnection(Main.SERVER_ADDRESS, Main.PORT);
        currentUser.setUsername(usernameRegistration.getText());
        currentUser.setFirstName(firstName.getText());
        currentUser.setLastName(lastName.getText());
        currentUser.setAddress(streetAddress.getText());
        currentUser.setCity(city.getText());
        currentUser.setEmail(email.getText());
        currentUser.setPhoneNumber(phoneNumber.getText());
        currentUser.setRegistrationDate(new Date());
        Request request = new Request(usernameRegistration.getText(), passwordRegistration.getText(),
                MessageAction.ADD_CLIENT, new ClientData(currentUser));
        connection.addSingleRequest(request);
        connection.setOnSucceeded(event -> {
            ArrayList<Response> responses = connection.getValue();
            if (responses.isEmpty()) {
                showError("Error connecting to the server", usernameLogin);
            } else if (responses.get(0).getResponseCode() == Response.SUCCESS) {
                LOGGER.log(Level.INFO, "Successfully signed up");
                preferences.put(USERNAME_ID, usernameRegistration.getText());
                preferences.put(PASSWORD_ID, passwordRegistration.getText());
                loadHomeScene();
            } else if (responses.get(0).getResponseCode() == Response.FAILED) {
                showError("Username already taken.", usernameRegistration);
            }
        });

        Thread thread = new Thread(connection);
        thread.start();
    }

    private void loadHomeScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene_home.fxml"));
            Scene homeScene = new Scene(fxmlLoader.load());
            HomeController controller = fxmlLoader.getController();
            controller.setStage(root);
            root.setScene(homeScene);
            root.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        errorVbox.setVisible(false);
        if (usernameRegistration.getText().isEmpty()) {
            showError("Please enter username.", usernameRegistration);
            return false;
        }
        if (passwordRegistration.getText().isEmpty()) {
            showError("Please enter password.", passwordRegistration);
            return false;
        }
        if (firstName.getText().isEmpty()) {
            showError("Please enter first name.", firstName);
            return false;
        }
        if (lastName.getText().isEmpty()) {
            showError("Please enter last name.", lastName);
            return false;
        }
        if (streetAddress.getText().isEmpty()) {
            showError("Please enter street address.", streetAddress);
            return false;
        }
        if (city.getText().isEmpty()) {
            showError("Please enter city.", city);
            return false;
        }
        if (email.getText().isEmpty()) {
            showError("Please enter email.", email);
            return false;
        }
        if (!EmailValidator.getInstance().isValid(email.getText())) {
            showError("Please enter a valid email address.", email);
            return false;
        }
        if (phoneNumber.getText().isEmpty()) {
            showError("Please enter phone number.", phoneNumber);
            return false;
        }
        return true;
    }

    private void showError(String errorMessage, TextField focusOn) {
        errorText.setText(errorMessage);
        errorVbox.setVisible(true);
        if (focusOn != null) {
            focusOn.requestFocus();
        }
    }

    public void switchToRegistrationForm(MouseEvent mouseEvent) {
        try {
            errorVbox.setVisible(false);
            if (registrationForm == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fragment_registration_form.fxml"));
                fxmlLoader.setController(this);
                fxmlLoader.load();
            }
            loginPane.setCenter(registrationForm);
            logInUnderscore.setVisible(false);
            signUpUnderscore.setVisible(true);
            root.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToLoginForm(MouseEvent mouseEvent) {
        errorVbox.setVisible(false);
        loginPane.setCenter(loginForm);
        logInUnderscore.setVisible(true);
        signUpUnderscore.setVisible(false);
        root.sizeToScene();
    }
}
