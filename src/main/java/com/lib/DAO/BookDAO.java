package com.lib.DAO;

import java.util.List;

import com.lib.model.Book;

public interface BookDAO extends BaseDAO<Book, Long> {
    //List<Book> findPaginated(int page, int pageSize);
    List<Book> findBooksByPage(int page, int pageSize);
    List<Book> searchBooks(String query);
    int countBooks();
	boolean delete(long id);
	int countSearchBooks(String query);
}
