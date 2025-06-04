package repository.custom.impl;

import entity.IssuedBooksEntity;
import repository.custom.IssuedBookRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
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
}
