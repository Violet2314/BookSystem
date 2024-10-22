package bookSystem.mapper;

import bookSystem.pojo.Book;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> getBook(String bookName, String writer, LocalDate startDate, LocalDate endDate);

    @Update("UPDATE book SET stock = stock - 1 WHERE book_id = #{bookId} AND stock > 0")
    void stockUpdateBorrow(Integer bookId);

    @Insert("INSERT INTO user_haved_borrow(user_id, book_id) VALUES(#{userId}, #{bookId})")
    void addUserBookRecord(Integer bookId, Integer userId);

    List<Book> getBorrowed(Integer userId);
    
    @Update("UPDATE book SET stock = stock + 1 WHERE book_id = #{bookId}")
    void stockUpdateReturn(Integer bookId);

    void earseUserBookRecord(Integer bookId,Integer Id);

    @Insert("INSERT INTO book(book_name, writer, publish_data, stock) VALUES" +
            "(#{bookName},#{writer},#{publishData},#{stock})")
    void addBook(Book book);

    @Update("UPDATE book SET stock = #{stock} WHERE book_id = #{bookId}")
    void changeBook(Integer bookId, Integer stock);
}
