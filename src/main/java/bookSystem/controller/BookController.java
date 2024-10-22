package bookSystem.controller;

import bookSystem.pojo.Book;
import bookSystem.pojo.PageBean;
import bookSystem.pojo.Result;
import bookSystem.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/getbook")
    public Result getBook(@RequestParam String bookName, @RequestParam String writer,
                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer size) {
        log.info("读取第{}页，每页{}条数据",page,size);
        PageBean now = bookService.getBook(page, size, bookName, writer, startDate, endDate);
        return Result.success(now);
    }

    @PutMapping("/book/borrow")
    public Result borrowBook(@RequestParam Integer bookId,@RequestParam Integer userId){
        log.info("用户{},借了id为{}的书",bookId,userId);
        bookService.borrowBook(bookId,userId);
        return Result.success();
    }

    @GetMapping("/borrowedbooks")
    public Result getBorrowed(@RequestParam Integer userId){

        Map<String, Object> data = new HashMap<>();
        List<Book> books = bookService.getBorrowed(userId);
        data.put("rows",books);
        return Result.success(data);
    }

    @PutMapping("/book/return")
    public Result returnBook(@RequestParam Integer bookId,@RequestParam Integer Id){
        bookService.returnBook(bookId,Id);
        return Result.success();
    }

    @PostMapping("/book/add")
    public Result addBook(@RequestBody Book book){
        bookService.addBook(book);
        return Result.success();
    }

    @PutMapping("/book/change")
    public Result changeBook(@RequestParam Integer bookId,@RequestParam Integer stock){
        bookService.changeBook(bookId,stock);
        return Result.success();
    }
}
