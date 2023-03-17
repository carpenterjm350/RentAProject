package com.example.rentaproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_Login implements Initializable {

    @FXML
    public Button SignUp_Button;
    public TextField Password_Textfield;
    public TextField Username_TextField;
    @FXML
    private Button Login_Button;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Login_Button.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                try {
                    DatabaseUtilities.loginUser(event, Username_TextField.getText(), Password_Textfield.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } {}
            }
        });

        // Sign up
        SignUp_Button.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                try{
                    DatabaseUtilities.changeScene(event, "SignUp.fxml", "Sign Up!", null, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

}
