package com.lib.DAO;

import com.lib.model.Librarian;

public interface LibrarianDAO extends BaseDAO<Librarian, Long> {
    Librarian findByEmailAndPassword(String email, String password);
}
