package repository.custom;

import dto.Member;
import entity.MemberEntity;
import javafx.collections.ObservableList;
import repository.CrudRepository;

import java.sql.SQLException;

public interface MemberRepository extends CrudRepository<MemberEntity, Integer> {
    ObservableList<Integer> getAllMembersId() throws SQLException;
}
