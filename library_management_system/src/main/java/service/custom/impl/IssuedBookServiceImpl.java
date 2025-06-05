package service.custom.impl;

import dto.IssuedBook;
import entity.IssuedBooksEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.IssuedBookRepository;
import service.custom.IssuedBookService;
import util.RepositoryType;

import java.sql.SQLException;
import java.time.LocalDate;

public class IssuedBookServiceImpl implements IssuedBookService {
    IssuedBookRepository issuedBookRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.ISSUED_BOOK);
    @Override
    public boolean addIssuedBook(IssuedBook issuedBook) throws SQLException {
        IssuedBooksEntity issuedBooksEntity = new ModelMapper().map(issuedBook, IssuedBooksEntity.class);
        return issuedBookRepository.add(issuedBooksEntity);
    }

    @Override
    public int borrowedCount(Integer memberId) throws SQLException {
        return issuedBookRepository.borrowedCount(memberId);
    }

    @Override
    public ObservableList<Integer> getAllIssuedBooksMembersId() throws SQLException {
        return issuedBookRepository.getAllIssuedBooksMembersId();
    }

    @Override
    public ObservableList<Integer> getIssuedBooksForMembers(Integer id) throws SQLException {
        return issuedBookRepository.getIssuedBooksForMembers(id);
    }

    @Override
    public LocalDate getBookIssuedDate(Integer memberId, Integer bookId) throws SQLException {
        return issuedBookRepository.getBookIssuedDate(memberId, bookId);
    }
}
