package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

    }

    @FXML
    void btnAddUserOnClick(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add_user_form_view.fxml"))));
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
    void btnDeleteUserOnClick(ActionEvent event) {

    }

    @FXML
    void btnIssueBookOnClick(ActionEvent event) {

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
    void btnSearchUserOnClick(ActionEvent event) {

    }

    @FXML
    void btnTrackBorrowingHistoryOnClick(ActionEvent event) {

    }

    @FXML
    void btnUpdateBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnUpdateUserOnClick(ActionEvent event) {

    }

}
