package service.custom;

import dto.Book;
import javafx.collections.ObservableList;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface BookService extends SuperService {
    boolean addBook(Book book) throws SQLException;
    boolean updateBookAvailableCopies(Integer bookID) throws SQLException;
    boolean deleteById(Integer bookID);
    Book searchById(Integer bookID) throws SQLException;
    List<Book> getAll();
    String getNextBookId() throws SQLException;
    boolean isAvailableBook(String bookTitle) throws SQLException;
    ObservableList<String> getAllBooksTitles() throws SQLException;
    int getBookId(String bookTitle) throws SQLException;
    void reduceAvailableCopies(Integer bookId) throws SQLException;
}
