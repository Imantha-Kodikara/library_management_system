package repository.custom;

import dto.Book;
import entity.BookEntity;
import javafx.collections.ObservableList;
import repository.CrudRepository;
import repository.SuperRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Integer> {
    boolean isAvailableBook(String bookTitle) throws SQLException;

    ObservableList<String> getAllBooksTitle() throws SQLException;

    int getBookId(String bookTitle) throws SQLException;

    void reduceAvailableCopies(Integer bookId) throws SQLException;

}
