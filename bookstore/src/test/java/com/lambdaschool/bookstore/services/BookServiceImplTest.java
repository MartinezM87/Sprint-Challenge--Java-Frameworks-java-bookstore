package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.models.Wrote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void findAll()
    {
        assertEquals(5,
            bookService.findAll()
                .size());
    }

    @Test
    public void findBookById()
    {
        assertEquals("Flatterland",
            bookService.findBookById(26)
                .getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void notFindBookById()
    {
        assertEquals("Flatterland",
            bookService.findBookById(789)
                .getTitle());
    }

    @Test
    public void delete()
    {
        bookService.delete(27);
        assertEquals(4,
            bookService.findAll()
                .size());
    }

    @Test
    public void save()
    {
        Section section = new Section("fiction");
        section.setSectionid(22);
        Author author = new Author("John", "Mitchell");
        author.setAuthorid(15);
        Book newBook = new Book("Choke",
            "longnumber",
            1000,
            section);
        newBook.getWrotes()
            .add(new Wrote(author, new Book()));

//        System.out.println("book is " + newBook.toString());
//        System.out.println("author is " + author.toString());

        Book addBook = bookService.save(newBook);
        assertNotNull(addBook);
        Book foundBook = bookService.findBookById(addBook.getBookid());
        assertEquals(addBook.getTitle(),
            foundBook.getTitle());
    }

    @Test
    public void update()
    {
    }

    @Test
    public void deleteAll()
    {
    }
}