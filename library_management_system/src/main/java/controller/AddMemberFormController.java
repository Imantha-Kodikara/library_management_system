package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import model.Member;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddMemberFormController implements Initializable {

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
    private JFXTextField txtMemberId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtMemberId.setEditable(false);
        txtMemberId.setText("Member ID : "+getNextMemberId());
    }

    @FXML
    void btnAddOnClick(ActionEvent event) {
        if(isFilled() && isValidContactNumber()){
            Member member = new Member(
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtAddress.getText(),
                    txtEmail.getText(),
                    txtContactNumber.getText(),
                    dateMembershipDate.getValue()
            );
            try {
                Boolean isAdded = CrudUtil.execute("INSERT INTO members(first_name, last_name, address, email, contact_number, membership_date) VALUES(?, ?, ?, ?, ?, ?)",
                        member.getFirstName(), member.getLastName(), member.getAddress(), member.getEmail(), member.getContactNumber(), member.getMembershipDate());

                if (isAdded){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Added Successfully!");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to add member! please try again");
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
    public String getNextMemberId(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM members");

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
        txtMemberId.setText("Member ID : "+getNextMemberId());
    }
}
