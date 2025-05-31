package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import model.IssuedBook;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IssueBookFormController implements Initializable {

    @FXML
    private JFXComboBox<String> combSelectBookTitle;

    @FXML
    private JFXComboBox<Integer> combSelectMemberId;

    @FXML
    private DatePicker dateIssuingDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combSelectMemberId.setItems(getAllMembersId());
        combSelectBookTitle.setItems(getAllBooksTitles());
    }

    @FXML
    void btnClearOnClick(ActionEvent event) {

    }

    @FXML
    void btnIssueOnClick(ActionEvent event) {
        if (borrowedCount() < 3 && isAvailableBook()){
            try {
                ResultSet resultSet = CrudUtil.execute("SELECT book_id FROM books WHERE title = ?", combSelectBookTitle.getValue());
                resultSet.next();
                int bookId = resultSet.getInt(1);
                IssuedBook issuedBook = new IssuedBook(
                        combSelectMemberId.getValue(),
                        bookId,
                        dateIssuingDate.getValue()
                );

                Boolean isAdded = CrudUtil.execute("INSERT INTO issued_books(member_id, book_id, issue_date) VALUES (?, ?, ?)",
                        issuedBook.getMemberId(),
                        issuedBook.getBookId(),
                        issuedBook.getIssueDate()
                        );

                if(isAdded){
                    CrudUtil.execute("UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?", issuedBook.getBookId());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book Issued Successfully");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Book Issuing Failed");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Book issuing failed! Please check borrowing count or book availability");
            alert.showAndWait();
        }
    }

    public ObservableList<Integer> getAllMembersId(){
        ObservableList<Integer> membersId = FXCollections.observableArrayList(); //Creating observable arraylist to store users id;

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM members");
            while (resultSet.next()){
                int memberId = resultSet.getInt(1);
                membersId.add(memberId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return membersId;
    }

    public ObservableList<String> getAllBooksTitles(){
        ObservableList<String> booksTitles = FXCollections.observableArrayList(); // creating observable arralist to store books title
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM books");
            while (resultSet.next()){
                String bookTitle = resultSet.getString(3);
                booksTitles.add(bookTitle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booksTitles;
    }

    public int borrowedCount(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) AS borrowed_count FROM issued_books WHERE member_id = ? AND return_date IS NULL", combSelectMemberId.getValue()); // Creating temporary column to find how many books borrowed in same member
            if(resultSet.next()){
                return resultSet.getInt("borrowed_count");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAvailableBook(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT available_copies FROM books WHERE title = ?", combSelectBookTitle.getValue());
            if(resultSet.next()){
                if(resultSet.getInt(1) > 0){
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }





}
