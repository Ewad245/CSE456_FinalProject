package com.lib.DAO;

import java.util.List;

import com.lib.model.BorrowedBook;

public interface BorrowedBookDAO extends BaseDAO<BorrowedBook, Integer> {
    void delete(int id);
    BorrowedBook findById(int id);
    List<BorrowedBook> findAll();
    List<BorrowedBook> findOverdueBooks();
    List<BorrowedBook> findByMemberId(int memberId);
}
