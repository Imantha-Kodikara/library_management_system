package repository.custom;

import entity.IssuedBooksEntity;
import javafx.collections.ObservableList;
import repository.CrudRepository;

import java.sql.SQLException;
import java.time.LocalDate;

public interface IssuedBookRepository extends CrudRepository<IssuedBooksEntity, Integer> {
    int borrowedCount(Integer memberId) throws SQLException;
    ObservableList<Integer> getAllIssuedBooksMembersId() throws SQLException;
    ObservableList<Integer> getIssuedBooksForMembers(Integer id) throws SQLException;
    LocalDate getBookIssuedDate(Integer memberId, Integer bookId) throws SQLException;
}
