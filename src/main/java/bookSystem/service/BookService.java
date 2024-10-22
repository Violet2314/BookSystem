package bookSystem.service;

import bookSystem.pojo.Book;
import bookSystem.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    PageBean getBook(Integer page,Integer size,String bookName, String writer, LocalDate startDate, LocalDate endDate);

    void borrowBook(Integer bookId, Integer userId);

    List<Book> getBorrowed(Integer userId);

    void returnBook(Integer bookId, Integer Id);

    void addBook(Book book);

    void changeBook(Integer bookId, Integer stock);
}
