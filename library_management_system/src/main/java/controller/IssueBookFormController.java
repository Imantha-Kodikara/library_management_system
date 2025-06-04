package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import dto.IssuedBook;
import service.ServiceFactory;
import service.custom.BookService;
import service.custom.IssuedBookService;
import service.custom.MemberService;
import util.CrudUtil;
import util.ServiceType;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);
    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);
    IssuedBookService issuedBookService = ServiceFactory.getInstance().getServiceType(ServiceType.ISSUED_BOOK);

    @FXML
    void btnClearOnClick(ActionEvent event) {

    }

    @FXML
    void btnIssueOnClick(ActionEvent event) {
        Integer memberId = Integer.parseInt(String.valueOf(combSelectMemberId.getValue()));
        String bookTitle = combSelectBookTitle.getValue();
       if(borrowedCount(memberId) < 3 && isAvailableBook(bookTitle)){
           IssuedBook issuedBook = new IssuedBook(
                   memberId,
                   getBookID(combSelectBookTitle.getValue()),
                   dateIssuingDate.getValue()
           );
           try {
               boolean isAdded = issuedBookService.addIssuedBook(issuedBook);

               if (isAdded){
                   Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book issued successfully!");
                   alert.showAndWait();

                   //----------Reducing available book copies---------------------------------

                   bookService.reduceAvailableCopies(getBookID(combSelectBookTitle.getValue()));
               }else{
                   Alert alert = new Alert(Alert.AlertType.ERROR, "Book Issuing failed! Please try again");
                   alert.showAndWait();
               }

           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }else{
           Alert alert = new Alert(Alert.AlertType.ERROR, "Book Issuing Failed! Please check member borrowed count or book availability");
           alert.showAndWait();
       }
    }

    //------------To set up all book titles to combo box--------------------------------------

    public ObservableList<String> getAllBooksTitles(){
        try {
            return bookService.getAllBooksTitles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------To setup all members id s to combo box-----------------------------------------

    public ObservableList<Integer> getAllMembersId(){
        try {
            return memberService.getAllMembersId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//----------------------Checking book available or not-----------------------------------------------

    public boolean isAvailableBook(String bookTitle){
        try {
            return bookService.isAvailableBook(bookTitle);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------------Calculating borrowing count--------------------------------------------------

    public int borrowedCount(Integer memberId){
        try {
            return issuedBookService.borrowedCount(memberId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //-----------------------To get book Id------------------------------------------------

    public int getBookID(String bookTitle){
        try {
            return bookService.getBookId(bookTitle);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
