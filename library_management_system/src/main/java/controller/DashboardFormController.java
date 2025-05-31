package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private JFXButton btnAddBook;

    @FXML
    private JFXButton btnAddUser;

    @FXML
    private JFXButton btnCalculateFine;

    @FXML
    private JFXButton btnDeleteBook;

    @FXML
    private JFXButton btnDeleteUser;

    @FXML
    private JFXButton btnIssueBook;

    @FXML
    private JFXButton btnPayFine;

    @FXML
    private JFXButton btnReturnBook;

    @FXML
    private JFXButton btnSearchBook;

    @FXML
    private JFXButton btnSearchUser;

    @FXML
    private JFXButton btnTrackBorrowingHistory;

    @FXML
    private JFXButton btnUpdateBook;

    @FXML
    private JFXButton btnUpdateUser;

    @FXML
    void btnAddBookOnClick(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add_book_form_view.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddMemberOnClick(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add_member_form_view.fxml"))));
            stage.setTitle("Add User");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnCalculateFineOnClick(ActionEvent event) {

    }

    @FXML
    void btnDeleteBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnDeleteMemberOnClick(ActionEvent event) {

    }

    @FXML
    void btnIssueBookOnClick(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/issue_book_form_view.fxml"))));
            stage.setTitle("Issue Book");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnPayFineOnClick(ActionEvent event) {

    }

    @FXML
    void btnReturnBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnSearchBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnSearchMemberOnClick(ActionEvent event) {

    }

    @FXML
    void btnTrackBorrowingHistoryOnClick(ActionEvent event) {

    }

    @FXML
    void btnUpdateBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnUpdateMemberOnClick(ActionEvent event) {

    }

}
