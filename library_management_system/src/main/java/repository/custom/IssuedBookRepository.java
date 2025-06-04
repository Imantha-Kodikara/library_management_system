package repository.custom;

import entity.IssuedBooksEntity;
import repository.CrudRepository;

import java.sql.SQLException;

public interface IssuedBookRepository extends CrudRepository<IssuedBooksEntity, Integer> {
    int borrowedCount(Integer memberId) throws SQLException;
}
