package com.lib.DAO;

import java.util.List;

import com.lib.model.Member;

public interface MemberDAO extends BaseDAO<Member, Integer> {
    void delete(int id);
    Member findById(int id);
    Member findByEmailAndPass(String email, String pass);
    Member findByEmail(String email);
}
