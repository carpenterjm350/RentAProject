package com.example.rentaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_Cart implements Initializable {

    public TableColumn<Rentable, String> Id_Column;
    public Label TotalPrice_Label;
    @FXML
    private Button Buy_Button;

    @FXML
    private Button Cancel_Button;

    @FXML
    private TableColumn<Rentable, String> Name_Column;

    @FXML
    private TableColumn<Rentable, String> Price_column;

    @FXML
    private TextField ProductName_TextField;

    @FXML
    private TextField ProductPrice_TextField1;

    @FXML
    private TableView<Rentable> Table;

    @FXML
    private TableColumn<Rentable, String> Time_Column;

    int index;

    public static ArrayList<String> cartItems = new ArrayList<>();
    public static ObservableList<Rentable> rentableTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            setTable();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTable() throws SQLException {
        Controller_Rent.rentableTable = DatabaseUtilities.getDataCart();

        Id_Column.setCellValueFactory(new PropertyValueFactory<Rentable, String>("id"));
        Name_Column.setCellValueFactory(new PropertyValueFactory<Rentable, String>("name"));
        Price_column.setCellValueFactory(new PropertyValueFactory<Rentable, String>("price"));
        Time_Column.setCellValueFactory(new PropertyValueFactory<Rentable, String>("time"));

        TotalPrice_Label.setText("Total: " + DatabaseUtilities.totalPrice);

        Table.setItems(Controller_Rent.rentableTable);
    }

    @FXML
    public void getDataBuy (MouseEvent mouseEvent)
    {

        index = Table.getSelectionModel().getSelectedIndex();

        if(index <= -1)
            return;


        ProductName_TextField.setText(Name_Column.getCellData(index));
        ProductPrice_TextField1.setText(Price_column.getCellData(index));
    }

    @FXML
    private void buy() throws SQLException {
        for(int i = 0; i < cartItems.size(); i++)
        {
            DatabaseUtilities.rentItem(cartItems.get(i));
        }

        cartItems.clear();
        Table.setItems(rentableTable);
        TotalPrice_Label.setText("Total: 0");
    }

    @FXML
    private void cancel()
    {
        cartItems.remove(index);
        DatabaseUtilities.totalPrice = 0;
        Controller_Rent.rentableTable = DatabaseUtilities.getDataCart();

        Table.setItems(Controller_Rent.rentableTable);
        TotalPrice_Label.setText(("Total: " + DatabaseUtilities.totalPrice));
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        DatabaseUtilities.changeScene(event, "Rent.fxml", "Rent", null, null);
    }

}
