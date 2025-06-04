package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import dto.Member;
import service.ServiceFactory;
import service.custom.MemberService;
import util.CrudUtil;
import util.ServiceType;

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

    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);

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
                boolean isAdded = memberService.addMember(member);

                if(isAdded){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member added successfully!");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Member added failed! Please try again");
                    alert.showAndWait();
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
            return memberService.getNextMemberId();
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
