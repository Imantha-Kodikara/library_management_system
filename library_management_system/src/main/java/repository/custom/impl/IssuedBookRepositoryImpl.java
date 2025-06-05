package repository.custom.impl;

import entity.IssuedBooksEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.custom.IssuedBookRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class IssuedBookRepositoryImpl implements IssuedBookRepository {
    @Override
    public boolean add(IssuedBooksEntity entity) throws SQLException {
       return CrudUtil.execute("INSERT INTO issued_books(member_id, book_id, issue_date) VALUES (?, ?, ?)",
                entity.getMemberId(),
                entity.getBookId(),
                entity.getIssueDate()
        );
    }

    @Override
    public boolean update(IssuedBooksEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return false;
    }

    @Override
    public IssuedBooksEntity searchById(Integer integer) {
        return null;
    }

    @Override
    public List<IssuedBooksEntity> getAll() {
        return List.of();
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public int borrowedCount(Integer memberId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) AS borrowed_count FROM issued_books WHERE member_id = ? AND return_date IS NULL", memberId);// Creating temporary column to find how many books borrowed in same member
        if (resultSet.next()){
            return resultSet.getInt("borrowed_count");
        }
            return 0;
        }

    @Override
    public ObservableList<Integer> getAllIssuedBooksMembersId() throws SQLException {
        ObservableList<Integer> issuedBooksMembersId = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT DISTINCT member_id FROM issued_books WHERE status = ?", "issued");
        while (resultSet.next()){
            int memberId = resultSet.getInt(1);
            issuedBooksMembersId.add(memberId);
        }
        return issuedBooksMembersId;
    }

    @Override
    public ObservableList<Integer> getIssuedBooksForMembers(Integer id) throws SQLException {
        ObservableList<Integer> issuedBooksForMembers = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT book_id FROM issued_books WHERE member_id = ? AND status = ?", id, "issued");
        while (resultSet.next()){
            int bookId = resultSet.getInt(1);
            issuedBooksForMembers.add(bookId);
        }
        return issuedBooksForMembers;
    }

    @Override
    public LocalDate getBookIssuedDate(Integer memberId, Integer bookId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT issue_date FROM issued_books WHERE member_id = ? AND book_id = ? AND status = ?", memberId, bookId, "issued");
        if(resultSet.next()){
            return resultSet.getDate(1).toLocalDate();
        }
        return null;
    }

    @Override
    public boolean updateReturnedDateAndStatus(Integer memberId, Integer bookId, LocalDate returnedDate) throws SQLException {
        return CrudUtil.execute("UPDATE issued_books SET return_date = ? , status = ? WHERE member_id = ? AND book_id = ?", returnedDate, "returned", memberId, bookId);
    }
}
