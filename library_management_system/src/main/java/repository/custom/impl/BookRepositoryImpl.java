package repository.custom.impl;

import dto.Book;
import entity.BookEntity;
import javafx.scene.control.Alert;
import repository.custom.BookRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    @Override
    public boolean add(BookEntity entity) {
        try {
            return CrudUtil.execute("INSERT INTO books (isbn, title, author, genre, total_copies, available_copies, availability_status) VALUES (?, ?, ?, ?, ?, ?, ? )",
                    entity.getIsbn(), entity.getTitle(), entity.getAuthor(), entity.getGenre(), entity.getTotalCopies(), entity.getAvailableCopies(), entity.getAvailabilityStatus()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public BookEntity searchById(Integer integer) {
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
}

