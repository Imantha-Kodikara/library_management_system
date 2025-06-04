package repository.custom;

import dto.Book;
import entity.BookEntity;
import repository.CrudRepository;
import repository.SuperRepository;

import java.sql.ResultSet;
import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Integer> {

}
