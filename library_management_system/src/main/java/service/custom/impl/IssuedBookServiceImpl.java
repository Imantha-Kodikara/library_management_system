package service.custom.impl;

import dto.IssuedBook;
import entity.IssuedBooksEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.IssuedBookRepository;
import service.custom.IssuedBookService;
import util.RepositoryType;

import java.sql.SQLException;

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
}
