package controller;

import com.jfoenix.controls.JFXButton;
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
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class ReturnBookFormController implements Initializable {
    @FXML
    private JFXButton btnCompleteReturn;

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

        btnCompleteReturn.setDisable(true);

    }

    @FXML
    void btnCheckFineDetailsOnClick(ActionEvent event) {
        if(!isValidDate(combMemberId.getValue(), combBookId.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Return date cannot be before issued date!");
            alert.showAndWait();
        }else if(!isFilled()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select all the fields!");
        }else{
            try {
                Member member = memberService.searchById(combMemberId.getValue());
                Book book = bookService.searchById(combBookId.getValue());
                LocalDate issuedDate = issuedBookService.getBookIssuedDate(combMemberId.getValue(), combBookId.getValue());
                LocalDate returningDate = dateReturningDate.getValue();
                int daysBetween = (int) ChronoUnit.DAYS.between(issuedDate, returningDate); // Calculate the no of dates between issued date and returning date


                lblMemberName.setText(member.getFirstName());
                lblAddress.setText(member.getAddress());
                lblContactNumber.setText(member.getContactNumber());
                lblReturningBook.setText(book.getTitle());
                lblIssuedDate.setText(String.valueOf(issuedDate));
                lblReturningDate.setText(String.valueOf(returningDate));
                lblTotalFine.setText(calculateFine(daysBetween));

                btnCompleteReturn.setDisable(false);


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

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


    //------------------------Calculating fine-----------------------------------------

    String calculateFine(int daysBetween){
        return daysBetween > 14 ? ((daysBetween - 14) * 10) +"" : "00.00";
    }

    @FXML
    void btnCompleteReturnOnClick(ActionEvent event) {
        if(updateFields()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book Returned Successfully!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Book returning failed!");
            alert.showAndWait();
        }
    }

    boolean updateFields(){
        try {
          return  bookService.updateBookAvailableCopies(combBookId.getValue()) &&
            issuedBookService.updateReturnedDateAndStatus(combMemberId.getValue(), combBookId.getValue(), dateReturningDate.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
