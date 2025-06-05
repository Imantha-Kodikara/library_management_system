package service.custom.impl;

import dto.Book;
import entity.BookEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.BookRepository;
import service.custom.BookService;
import util.RepositoryType;
import util.ServiceType;

import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {
    BookRepository bookRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.BOOK);
    @Override
    public boolean addBook(Book book) throws SQLException {
        BookEntity entity = new ModelMapper().map(book, BookEntity.class);
        return bookRepository.add(entity);
    }

    @Override
    public boolean updateBookAvailableCopies(Integer bookID) throws SQLException {
        return bookRepository.updateBookAvailableCopies(bookID);
    }

    @Override
    public boolean deleteById(Integer bookID) {
        return false;
    }

    @Override
    public Book searchById(Integer bookID) throws SQLException {

        BookEntity bookEntity = bookRepository.searchById(bookID);
        return  new Book(
                bookEntity.getIsbn(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getGenre(),
                bookEntity.getTotalCopies(),
                bookEntity.getAvailableCopies(),
                bookEntity.getAvailabilityStatus()
        );
    }

    @Override
    public List<Book> getAll() {
        return List.of();
    }

    @Override
    public String getNextBookId() throws SQLException {
        return bookRepository.getNextId();
    }

    @Override
    public boolean isAvailableBook(String bookTitle) throws SQLException {
        return bookRepository.isAvailableBook(bookTitle);
    }

    @Override
    public ObservableList<String> getAllBooksTitles() throws SQLException {
        return bookRepository.getAllBooksTitle();
    }

    @Override
    public int getBookId(String bookTitle) throws SQLException {
        return bookRepository.getBookId(bookTitle);
    }

    @Override
    public void reduceAvailableCopies(Integer bookId) throws SQLException {
        bookRepository.reduceAvailableCopies(bookId);
    }
}
