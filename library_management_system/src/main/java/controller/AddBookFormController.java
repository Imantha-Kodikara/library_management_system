package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import dto.Book;
import service.ServiceFactory;
import service.custom.BookService;
import util.CrudUtil;
import util.ServiceType;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBookFormController implements Initializable {

    @FXML
    private ComboBox<String> combAvailabilityStatus;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtAvailableCopies;

    @FXML
    private JFXTextField txtBookTitle;

    @FXML
    private JFXTextField txtGenre;

    @FXML
    private JFXTextField txtIsbn;

    @FXML
    private JFXTextField txtTotalCopies;

    @FXML
    private JFXTextField txtBookId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combAvailabilityStatus.setItems(FXCollections.observableArrayList("Available","Borrowed")); //Setting the values of combo box
        txtBookId.setEditable(false);
        txtBookId.setText("Book Id : "+getNextBookId());
    }

    BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);

    @FXML
    void btnAddOnClick(ActionEvent event) {
        if (isFilled() && isValidNumbers()){
            Book book = new Book(
                    txtIsbn.getText(),
                    txtBookTitle.getText(),
                    txtAuthor.getText(),
                    txtGenre.getText(),
                    Integer.parseInt(txtTotalCopies.getText()),
                    Integer.parseInt(txtAvailableCopies.getText()),
                    combAvailabilityStatus.getValue()
            );
            boolean isAdded = bookService.addBook(book);

            if (isAdded){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book added successfully!");
                alert.showAndWait();
                clearFields();
                txtBookId.setText(getNextBookId());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Book adding failed! Please try again");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all the fields!");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    void btnClearOnClick(ActionEvent event) {
        clearFields();
    }

    //-----------------------------Generating next User Id------------------------------

    public String getNextBookId(){
       return bookService.getNextBookId();
    }

    //------------------------------Checking all the fields are filled-----------------------

    public boolean isFilled(){
        return !txtIsbn.getText().isEmpty() &&
                !txtBookTitle.getText().isEmpty() &&
                !txtAuthor.getText().isEmpty() &&
                !txtGenre.getText().isEmpty() &&
                !txtTotalCopies.getText().isEmpty() &&
                !txtAvailableCopies.getText().isEmpty() &&
                combAvailabilityStatus.getValue() != null;
    }

    public void clearFields(){
        txtIsbn.setText("");
        txtBookTitle.setText("");
        txtAuthor.setText("");
        txtGenre.setText("");
        txtTotalCopies.setText("");
        txtAvailableCopies.setText("");
    }

    //----------------------------checking avilable copies and total copies are exactly the numbers-----------------------

    public boolean isValidNumbers(){
        String regex = "\\d+";
        return txtTotalCopies.getText().matches(regex) && txtAvailableCopies.getText().matches(regex);
    }


}
