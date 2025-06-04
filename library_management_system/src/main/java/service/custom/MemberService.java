package service.custom;

import dto.Book;
import dto.Member;
import javafx.collections.ObservableList;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface MemberService extends SuperService {
    boolean addMember(Member member) throws SQLException;
    boolean updateMember(Member member);
    boolean deleteById(Integer memberID);
    Book searchById(Integer memberID);
    List<Book> getAll();
    String getNextMemberId() throws SQLException;
    ObservableList<Integer> getAllMembersId() throws SQLException;
}
