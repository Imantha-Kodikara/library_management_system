package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import model.User;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddUserFormController implements Initializable {

    @FXML
    private DatePicker dateMembershipDate;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtUserId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUserId.setEditable(false);
        txtUserId.setText("User ID : "+getNextUserId());
    }

    @FXML
    void btnAddOnClick(ActionEvent event) {
        if(isFilled() && isValidContactNumber()){
            User user = new User(
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtAddress.getText(),
                    txtEmail.getText(),
                    txtContactNumber.getText(),
                    dateMembershipDate.getValue()
            );
            try {
                Boolean isAdded = CrudUtil.execute("INSERT INTO users(first_name, last_name, address, email, contact_number, membership_date) VALUES(?, ?, ?, ?, ?, ?)",
                        user.getFirstName(), user.getLastName(), user.getAddress(), user.getEmail(), user.getContactNumber(), user.getMembershipDate());

                if (isAdded){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "User Added Successfully!");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to add user! please tru again");
                    alert.showAndWait();
                    return;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            clearTextFields();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all the fields or Check your contact number!");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    void btnClearOnClick(ActionEvent event) {

    }

    //-----------------------Finding Next User Id------------------------
    public String getNextUserId(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM users");

            if(!resultSet.next()) return "1"; //if empty table, return 1

            int id = 0;
            while (resultSet.next()){
                id++;
            }
            return (id+2)+""; //return id + 2
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------------------Checking every fields are set or not-------------------

    public boolean isFilled(){
        return !txtFirstName.getText().isEmpty() &&
                !txtLastName.getText().isEmpty() &&
                !txtAddress.getText().isEmpty() &&
                !txtEmail.getText().isEmpty() &&
                !txtContactNumber.getText().isEmpty() &&
                dateMembershipDate.getValue() != null;
    }

    //---------------------------validating Contact number-------------------------------

    public boolean isValidContactNumber(){
        return txtContactNumber.getText().startsWith("0") &&
                txtContactNumber.getText().length() == 10 &&
                txtContactNumber.getText().matches("\\d{10}");
    }

    //------------------------------Clearing Text Fields----------------------------------

    public void clearTextFields(){
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtContactNumber.setText("");
        txtUserId.setText("User ID : "+getNextUserId());
    }
}
