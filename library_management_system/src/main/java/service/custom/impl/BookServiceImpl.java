package service.custom.impl;

import dto.Book;
import entity.BookEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.BookRepository;
import service.custom.BookService;
import util.RepositoryType;
import util.ServiceType;

import java.util.List;

public class BookServiceImpl implements BookService {
    BookRepository bookRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.BOOK);
    @Override
    public boolean addBook(Book book) {
        BookEntity entity = new ModelMapper().map(book, BookEntity.class);
        return bookRepository.add(entity);
    }

    @Override
    public boolean updateBook(Book book) {
        return false;
    }

    @Override
    public boolean deleteById(Integer bookID) {
        return false;
    }

    @Override
    public Book searchById(Integer bookID) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return List.of();
    }

    @Override
    public String getNextBookId() {
        return bookRepository.getNextId();
    }
}
