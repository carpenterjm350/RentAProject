package com.example.rentaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DatabaseUtilities {


    public static String user_id;
    public static String staticUsername;

    public static double totalPrice;


    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String password) throws IOException {
        Parent root = null;

        //Successful Login
        if(username != null & password != null)
        {
            FXMLLoader loader = new FXMLLoader(DatabaseUtilities.class.getResource(fxmlFile));
            root = loader.load();
            //Controller_Welcome wc = loader.getController();
            //wc.setUserData(username, password);
        }
        else{
            root = FXMLLoader.load(DatabaseUtilities.class.getResource(fxmlFile));
        }

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }






    @FXML
    public static void signUpUser(ActionEvent event, String username, String password, String first_name, String last_name, String email, String birthday) throws SQLException {

        //Define stuff
        Connection con = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsertUser = null;
        ResultSet resultSet = null;

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/users_rent", "root", "password");

            //(SELECT <all records> FROM <table> WHERE <column matches parameter>)
            psCheckUserExists = con.prepareStatement("SELECT * FROM user_info WHERE username = ?;");
            psCheckUserExists.setString(1, username);

            //Store query results
            resultSet = psCheckUserExists.executeQuery();


            if(resultSet.isBeforeFirst()){
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This user already exists!");
                alert.show();
            }
            //User does not yet exist
            else
            {
                //TODO: Validate password
                psInsertUser = con.prepareStatement("INSERT INTO user_info (username, pwd, first_name, last_name, email) VALUES (?, ?, ?, ?, ?)");
                psInsertUser.setString(1, username);
                psInsertUser.setString(2, password);
                psInsertUser.setString(3, first_name);
                psInsertUser.setString(4, last_name);
                psInsertUser.setString(5, email);
                psInsertUser.executeUpdate();

                changeScene(event, "Rent.fxml", "Rent!", username, password);
            }
        }
        catch (SQLException | IOException e)
        {

            throw new RuntimeException(e);

        }

        finally {

            if(resultSet != null) {
                resultSet.close();
            }
            if(psInsertUser != null) {
                psInsertUser.close();
            }
            if(psCheckUserExists != null) {
                psCheckUserExists.close();
            }
            if(con != null) {
                con.close();
            }
        }

    }





    @FXML
    public static void loginUser(ActionEvent event, String username, String password) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{

            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/users_rent", "root", "password");
            preparedStatement = con.prepareStatement("SELECT id, username, pwd FROM user_info WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();


            if(!resultSet.isBeforeFirst()){
                System.out.println("Username is incorrect");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username is incorrect");
                alert.show();
            }
            else
            {
                while(resultSet.next())
                {
                    String retrievedPassword = resultSet.getString("pwd");

                    if (retrievedPassword.equals(password))
                    {
                        user_id = resultSet.getString("id");
                        staticUsername = resultSet.getString("username");
                        // Successful login
                        changeScene(event, "Rent.fxml", "Rent", username, null);

                    }else{
                        //Unsuccessful login
                        System.out.println("Password did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password is incorrect");
                        alert.show();
                    }
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            if(resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
        if(con != null) {
            con.close();
        }
    }


    public static ObservableList<Rentable> getData() throws SQLException {

        ObservableList<Rentable> list = null;
        ResultSet rs2 = null;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/users_rent", "root", "password");
            list = FXCollections.observableArrayList();

            //SQL Statement
            PreparedStatement psGetAllUsers = con.prepareStatement("SELECT product_id FROM user_rented WHERE user_id = ?");
            psGetAllUsers.setString(1, user_id);
            ResultSet rs = psGetAllUsers.executeQuery();



            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM user_rentables WHERE rentable_id = ?");

            while(rs.next())
            {
                ps2.setString(1, rs.getString(1));

                rs2 = ps2.executeQuery();

                while (rs2 != null && rs2.next()) {
                    //Create new Object of the record

                        list.add(new Rentable(rs2.getString(2),
                                rs2.getString(3),
                                rs2.getString(4)));
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }


    public static ObservableList<Rentable> getDataCart()
    {
        ObservableList<Rentable> list = null;
        ResultSet rs2 = null;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/users_rent", "root", "password");
            list = FXCollections.observableArrayList();



            //PreparedStatement ps2 = con.prepareStatement("SELECT * FROM user_rentables WHERE rentable_id = ?");
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM user_rentables");
            rs2 = ps2.executeQuery();

            while (rs2.next()) {
                //Create new Object of the record
                for (String s: Controller_Cart.cartItems) {

                    int t = Integer.parseInt(s);
                    String newS = String.valueOf(t+1);

                    if (rs2.getString(1).equals(newS)){
                        list.add(new Rentable(
                                rs2.getString(1),
                                rs2.getString(2),
                                rs2.getString(3),
                                rs2.getString(4)));

                        totalPrice += Double.parseDouble(rs2.getString(3));
                    }
                }
            }

            /*for(int i = 0; i < Controller_Cart.cartItems.size(); i++)
            {
                System.out.println(i);
                ps2.setString(1, Controller_Cart.cartItems.get(i));

                rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    //Create new Object of the record
                        System.out.println("off");
                        list.add(new Rentable(
                                rs2.getString(1),
                                rs2.getString(2),
                                rs2.getString(3),
                                rs2.getString(4)));

                }
            }*/

            for (Rentable r: list) {
                System.out.println(r.name);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }


    public static void rentItem(String index) throws SQLException {
        //Define stuff
        Connection con = null;
        PreparedStatement psInsertUser = null;
        int newIndex = Integer.parseInt(index);

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/users_rent", "root", "password");

            //(SELECT <all records> FROM <table> WHERE <column matches parameter>)
            psInsertUser = con.prepareStatement("INSERT INTO user_rented (user_id, product_id) VALUES (?, ?)");
            psInsertUser.setString(1, user_id);
            psInsertUser.setString(2, Integer.toString(newIndex + 1));
            psInsertUser.executeUpdate();

        }
        catch (SQLException e)
        {

            throw new RuntimeException(e);

        }

        finally {

            if(psInsertUser != null) {
                psInsertUser.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }




}
