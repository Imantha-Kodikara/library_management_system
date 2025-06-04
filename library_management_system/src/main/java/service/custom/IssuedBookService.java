package service.custom;

import dto.IssuedBook;
import service.SuperService;

import java.sql.SQLException;

public interface IssuedBookService extends SuperService {
    boolean addIssuedBook(IssuedBook issuedBook) throws SQLException;
    int borrowedCount(Integer memberId) throws SQLException;
}
