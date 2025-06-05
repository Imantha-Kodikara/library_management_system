package repository.custom.impl;

import dto.Book;
import entity.BookEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import repository.custom.BookRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    @Override
    public boolean add(BookEntity entity) throws SQLException {
            return CrudUtil.execute("INSERT INTO books (isbn, title, author, genre, total_copies, available_copies, availability_status) VALUES (?, ?, ?, ?, ?, ?, ? )",
                    entity.getIsbn(), entity.getTitle(), entity.getAuthor(), entity.getGenre(), entity.getTotalCopies(), entity.getAvailableCopies(), entity.getAvailabilityStatus()
            );
    }

    @Override
    public boolean update(BookEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return false;
    }

    @Override
    public BookEntity searchById(Integer bookID) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM books WHERE book_id = ?", bookID);
        if(resultSet.next()){
            return new BookEntity(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getInt(7),
                    resultSet.getString(8)
            );
        }
        return null;
    }

    @Override
    public List<BookEntity> getAll() {
        return List.of();
    }

    @Override
    public String getNextId() {
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

    @Override
    public boolean isAvailableBook(String bookTitle) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT available_copies FROM books WHERE title = ?", bookTitle);
        if(resultSet.next()){
            if(resultSet.getInt(1) > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public ObservableList<String> getAllBooksTitle() throws SQLException {
        ObservableList<String> booksTitles = FXCollections.observableArrayList(); //creating observable arraylist to store books titles

        ResultSet resultSet = CrudUtil.execute("SELECT*FROM books");
        while (resultSet.next()){
            String bookTitle = resultSet.getString(3);
            booksTitles.add(bookTitle);
        }
        return booksTitles;
    }

    @Override
    public int getBookId(String bookTitle) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT book_id FROM books WHERE title = ?", bookTitle);
        resultSet.next();
        int bookId = resultSet.getInt(1);

        return bookId;
    }

    @Override
    public void reduceAvailableCopies(Integer bookId) throws SQLException {
        CrudUtil.execute("UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?", bookId);
    }
}

