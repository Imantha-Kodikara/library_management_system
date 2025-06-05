package service.custom;

import dto.IssuedBook;
import javafx.collections.ObservableList;
import service.SuperService;

import java.sql.SQLException;
import java.time.LocalDate;

public interface IssuedBookService extends SuperService {
    boolean addIssuedBook(IssuedBook issuedBook) throws SQLException;
    int borrowedCount(Integer memberId) throws SQLException;
    ObservableList<Integer> getAllIssuedBooksMembersId() throws SQLException;
    ObservableList<Integer> getIssuedBooksForMembers(Integer id) throws SQLException;
    LocalDate getBookIssuedDate(Integer memberId, Integer bookId) throws SQLException;
    boolean updateReturnedDateAndStatus(Integer memberId, Integer BookId, LocalDate returnedDate) throws SQLException;
}
