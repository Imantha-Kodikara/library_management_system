package controller;

import dto.Book;
import dto.Member;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import service.ServiceFactory;
import service.custom.BookService;
import service.custom.IssuedBookService;
import service.custom.MemberService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class ReturnBookFormController implements Initializable {

    @FXML
    private ComboBox<Integer> combBookId;

    @FXML
    private ComboBox<Integer> combMemberId;

    @FXML
    private DatePicker dateReturningDate;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblContactNumber;

    @FXML
    private Label lblIssuedDate;

    @FXML
    private Label lblMemberName;

    @FXML
    private Label lblReturningBook;

    @FXML
    private Label lblReturningDate;

    @FXML
    private Label lblTotalFine;


    IssuedBookService issuedBookService = ServiceFactory.getInstance().getServiceType(ServiceType.ISSUED_BOOK);
    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);
    BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combMemberId.setItems(getAllIssuedBooksMembersId()); // Set values -> members Id s of already borrowed books
        combMemberId.setOnAction(event -> {
            Integer selectedMemberId = combMemberId.getValue();
            if(selectedMemberId != null){
                ObservableList<Integer> bookIds = getIssuedBooksForMembers(selectedMemberId);
                combBookId.setItems(bookIds);// set values -> books Id s of already borrow from above selected member
            }
        });
    }

    @FXML
    void btnCheckFineDetailsOnClick(ActionEvent event) {
        if(!isValidDate(combMemberId.getValue(), combBookId.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Return date cannot be before issued date!");
            alert.showAndWait();
        }else if(!isFilled()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select all the fields!");
        }else{

        }
    }

    ObservableList<Integer> getAllIssuedBooksMembersId(){
        try {
            return issuedBookService.getAllIssuedBooksMembersId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<Integer> getIssuedBooksForMembers(Integer memberId){
        try {
            return issuedBookService.getIssuedBooksForMembers(memberId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //-----------------------Checking return date is after the issued date---------------------------

    boolean isValidDate(Integer memberId, Integer bookId){
        try {
            LocalDate returnDate = dateReturningDate.getValue();
            LocalDate issuedDate = issuedBookService.getBookIssuedDate(combMemberId.getValue(), combBookId.getValue());

            return issuedDate.isBefore(returnDate); //compare two dates
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //-----------------------checking all the fields are filled----------------------------------------

    boolean isFilled(){
        return combBookId.getValue() != null && combMemberId.getValue() != null && dateReturningDate.getValue() != null;
    }



    @FXML
    void btnCompleteReturnOnClick(ActionEvent event) {

    }


}
