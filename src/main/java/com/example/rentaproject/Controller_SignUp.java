package com.example.rentaproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_SignUp implements Initializable {

    @FXML
    private TextField Birthday_textField;

    @FXML
    private TextField Email_TextField;

    @FXML
    private TextField FirstName_TextField;

    @FXML
    private TextField LastName_TextField;

    @FXML
    private Button Login_Button;

    @FXML
    private TextField Password_TextField;

    @FXML
    private Button SignUp_Button;

    @FXML
    private TextField Username_textfield;



    @FXML
    private void signUp(ActionEvent event) throws SQLException, IOException {

        DatabaseUtilities.signUpUser(event, Username_textfield.getText(), Password_TextField.getText(), FirstName_TextField.getText(), LastName_TextField.getText(), Email_TextField.getText(), Birthday_textField.getText());
        DatabaseUtilities.changeScene(event, "Login.fxml", "Login", null, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        SignUp_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!Username_textfield.getText().isEmpty() && !Password_TextField.getText().isEmpty())
                {
                    try {
                        DatabaseUtilities.signUpUser(event, Username_textfield.getText(), Password_TextField.getText(), FirstName_TextField.getText(), LastName_TextField.getText(), Email_TextField.getText(), Birthday_textField.getText() );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    System.out.println("User already exists!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("This user already exists!");
                    alert.show();
                }
            }
        });


        // Back to Login screen

        Login_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DatabaseUtilities.changeScene(event, "login.fxml", "log in!", null, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
