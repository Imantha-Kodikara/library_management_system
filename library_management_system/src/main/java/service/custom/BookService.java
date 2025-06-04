package service.custom;

import dto.Book;
import service.SuperService;

import java.util.List;

public interface BookService extends SuperService {
    boolean addBook(Book book);
    boolean updateBook(Book book);
    boolean deleteById(Integer bookID);
    Book searchById(Integer bookID);
    List<Book> getAll();
    String getNextBookId();

}
