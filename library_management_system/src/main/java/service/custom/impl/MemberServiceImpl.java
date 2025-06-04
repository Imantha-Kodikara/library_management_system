package service.custom.impl;

import dto.Book;
import dto.Member;
import entity.MemberEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.MemberRepository;
import service.custom.MemberService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.List;

public class MemberServiceImpl implements MemberService {

    MemberRepository memberRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.MEMBER);

    @Override
    public boolean addMember(Member member) throws SQLException {
        MemberEntity entity = new ModelMapper().map(member, MemberEntity.class);
        return memberRepository.add(entity);
    }

    @Override
    public boolean updateMember(Member member) {
        return false;
    }

    @Override
    public boolean deleteById(Integer memberID) {
        return false;
    }

    @Override
    public Book searchById(Integer memberID) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public String getNextMemberId() throws SQLException {
        return memberRepository.getNextId();
    }

    @Override
    public ObservableList<Integer> getAllMembersId() throws SQLException {
        return memberRepository.getAllMembersId();
    }
}
