package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import model.Book;
import util.CrudUtil;

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
            try {
                Boolean isAdded = CrudUtil.execute("INSERT INTO books (isbn, title, author, genre, total_copies, available_copies, availability_status) VALUES (?, ?, ?, ?, ?, ?, ? )",
                        book.getIsbn(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getTotalCopies(), book.getAvailableCopies(), book.getAvailabilityStatus()
                );

                if(isAdded){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book Added Successfully");
                    alert.showAndWait();

                    txtBookId.setText("Book Id : "+getNextBookId());
                    clearFields();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Book adding failed! Please try again");
                    alert.showAndWait();
                    return;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM books");
            if(!resultSet.next()) return "1";

            int id = 0;
            while (resultSet.next()){
                id++;
            }
            return (id + 2)+"";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
