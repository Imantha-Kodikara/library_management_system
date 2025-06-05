package repository.custom.impl;

import dto.Member;
import entity.MemberEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.custom.MemberRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberRepositoryImpl implements MemberRepository {
    @Override
    public boolean add(MemberEntity entity) throws SQLException {
            return CrudUtil.execute("INSERT INTO members(first_name, last_name, address, email, contact_number, membership_date) VALUES(?, ?, ?, ?, ?, ?)",
                    entity.getFirstName(), entity.getLastName(), entity.getAddress(), entity.getEmail(), entity.getContactNumber(), entity.getMembershipDate());
    }

    @Override
    public boolean update(MemberEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return false;
    }

    @Override
    public MemberEntity searchById(Integer memberId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM members WHERE member_id = ?", memberId);

        if(resultSet.next()){
            return new MemberEntity(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getDate(7).toLocalDate()
            );
        }
        return null;
    }

    @Override
    public List<MemberEntity> getAll() {
        return List.of();
    }

    @Override
    public String getNextId() throws SQLException {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM members");

            if(!resultSet.next()) return "1"; //if empty table, return 1

            int id = 0;
            while (resultSet.next()){
                id++;
            }
            return (id+2)+""; //return id + 2

    }

    @Override
    public ObservableList<Integer> getAllMembersId() throws SQLException {
        ObservableList<Integer> membersId = FXCollections.observableArrayList();//creating observable arraylist to store user id
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM members");
        while (resultSet.next()){
            int memberId = resultSet.getInt(1);
            membersId.add(memberId);
        }
        return membersId;
    }



}
