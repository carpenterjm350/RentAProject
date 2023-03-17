package com.example.rentaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_Rent implements Initializable {

    @FXML
    public TableColumn<Rentable, String> Time_Column;
    @FXML
    public TextField Time_TextField;

    @FXML
    private TableColumn<Rentable, String> Amount_column;

    @FXML
    private Button BudRent_Button;

    @FXML
    private Button DalRent_Button;

    @FXML
    private Button Logout_button;

    @FXML
    private TableColumn<Rentable, String> Name_Column;

    @FXML
    private Button PieRent_Button;

    @FXML
    private TableColumn<Rentable, String> Price_column;

    @FXML
    private TextField ProductName_TextField;

    @FXML
    private TableView<Rentable> Table;

    @FXML
    private Label Username_Label;

    @FXML
    private Tab YourRented_Tab;

    private int index;


    public static ObservableList<Rentable> rentableTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            Username_Label.setText(DatabaseUtilities.staticUsername);

            BudRent_Button.setDisable(false);
            DalRent_Button.setDisable(false);
            PieRent_Button.setDisable(false);

    }

    @FXML
    private void update() throws SQLException {
        setTable();
    }

    private void setTable() throws SQLException {
        rentableTable = DatabaseUtilities.getData();

        Name_Column.setCellValueFactory(new PropertyValueFactory<Rentable, String>("name"));
        Price_column.setCellValueFactory(new PropertyValueFactory<Rentable, String>("price"));
        Time_Column.setCellValueFactory(new PropertyValueFactory<Rentable, String>("time"));


        Table.setItems(rentableTable);
    }




    public void getDataBuy (MouseEvent mouseEvent)
    {

        index = Table.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1)
            return;


        ProductName_TextField.setText(Name_Column.getCellData(index));
        Time_TextField.setText(Time_Column.getCellData(index));
    }

    @FXML
    private void RentButton(ActionEvent event) throws SQLException {


            if(event.getSource().equals(BudRent_Button))
            {
                Controller_Cart.cartItems.add("0");
                BudRent_Button.setDisable(true);
            }
            else if (event.getSource().equals(DalRent_Button))
            {
                Controller_Cart.cartItems.add("1");
                DalRent_Button.setDisable(true);
            }
            else if (event.getSource().equals(PieRent_Button))
            {
                Controller_Cart.cartItems.add("2");
                PieRent_Button.setDisable(true);
            }
    }


    @FXML
    private void cart(ActionEvent event) throws IOException {
        DatabaseUtilities.changeScene(event, "Cart.fxml", "Cart", null, null);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        DatabaseUtilities.changeScene(event, "Login.fxml", "Sign Up!", null, null);
    }
}
