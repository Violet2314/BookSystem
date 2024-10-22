package bookSystem.service.imp;

import bookSystem.mapper.BookMapper;
import bookSystem.pojo.Book;
import bookSystem.pojo.PageBean;
import bookSystem.service.BookService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImp implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public PageBean getBook(Integer page, Integer size, String bookName, String writer, LocalDate startDate, LocalDate endDate) {
        PageHelper.startPage(page,size);

        List<Book> bookList = bookMapper.getBook(bookName,writer,startDate,endDate);
        Page<Book> p = (Page<Book>) bookList;

        PageBean now = new PageBean(p.getTotal(),p.getResult());
        return now;
    }

    @Override
    @Transactional
    public void borrowBook(Integer bookId, Integer userId) {
        bookMapper.stockUpdateBorrow(bookId);
        bookMapper.addUserBookRecord(bookId,userId);
    }

    @Override
    public List<Book> getBorrowed(Integer userId) {
        return bookMapper.getBorrowed(userId);
    }

    @Override
    @Transactional
    public void returnBook(Integer bookId, Integer Id) {
        bookMapper.stockUpdateReturn(bookId);
        bookMapper.earseUserBookRecord(bookId,Id);
    }

    @Override
    public void addBook(Book book) {
        bookMapper.addBook(book);
    }

    @Override
    public void changeBook(Integer bookId, Integer stock) {
        bookMapper.changeBook(bookId,stock);
    }
}
