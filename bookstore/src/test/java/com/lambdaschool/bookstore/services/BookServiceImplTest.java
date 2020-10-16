package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import jdk.dynalink.linker.LinkerServices;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Autowired
    private SectionService sectionService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);
        List<Book> bookList = bookService.findAll();
        for (Book b : bookList){
            System.out.println(b.getBookid() + " " + b.getTitle());
        }
        List<Section> sectionList = sectionService.findAll();
        for (Section s : sectionList){
            System.out.println(s.getSectionid() + " " + s.getName());
        }
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void a_findAll()
    {
        assertEquals(5, bookService.findAll().size());
    }

    @Test
    public void b_findBookById()
    {
        assertEquals("Flatterland", bookService.findBookById(26).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void c_notFindBookById()
    {
        assertEquals("Flatterland", bookService.findBookById(2).getTitle());
    }

    @Test
    public void d_delete()
    {
        bookService.delete(30);
        assertEquals(4, bookService.findAll().size());
    }

    @Test
    public void e_save()
    {

        Book b1 = new Book();
        b1.setSection(sectionService.findSectionById(21));
        b1.setTitle("test");
        b1.setIsbn("12345678");
        b1.setBookid(0);
        b1.setCopy(2020);
        Book addBook = bookService.save(b1);
        assertEquals("test", addBook.getTitle());
    }

    @Test
    public void f_update()
    {
    }

    @Test
    public void g_deleteAll()
    {
        bookService.deleteAll();
        assertEquals(0, bookService.findAll().size());
    }
}