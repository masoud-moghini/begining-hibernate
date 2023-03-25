package hibernate;

import hibernate.orphan.Book;
import hibernate.orphan.Library;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.Test;
import utils.SessionUtil;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class OrphanRemovalTest {
    @Test
    public void orphanRemovalTest() {
        Long id = createLibrary();
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Library library = session.load(Library.class, id);
            assertEquals(library.getBooks().size(), 3);
            library.getBooks().remove(0);;
            assertEquals(library.getBooks().size(), 2);
            tx.commit();
        }
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Library l2 = session.load(Library.class, id);
            assertEquals(l2.getBooks().size(), 2);
            Query<Book> query = session
                    .createQuery("from Book b", Book.class);
            List<Book> books = query.list();
            assertEquals(books.size(), 2);
            tx.commit();
        }
    }

    public void deleteLibrary() {
        Long id = createLibrary();
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Library library = session.load(Library.class, id);
            assertEquals(library.getBooks().size(), 3);
            session.delete(library);
            tx.commit();
        }
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Library library = session.get(Library.class, id);
            assertNull(library);
            List<Book> books=session
                    .createQuery("from Book b", Book.class)
                    .list();
            assertEquals(books.size(), 0);
        }
    }

    @Test
    private Long createLibrary() {
        Library library = null;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            library = new Library();
            library.setName("orphanLib");
            session.save(library);
            Book book = new Book();
            book.setLibrary(library);
            ISBN isbn = new ISBN();
            isbn.setTitle(12);
            isbn.setGroup(212);
            isbn.setCheckDigit(612);
            isbn.setPublisher(152);
            book.setId(isbn);
            session.save(book);
            library.getBooks().add(book);
            var book1 = new Book();
            book1.setLibrary(library);
            ISBN isbn1 = new ISBN();
            isbn1.setTitle(182);
            isbn1.setGroup(212);
            isbn1.setCheckDigit(612);
            isbn1.setPublisher(152);
            book1.setId(isbn1);
            book1.setTitle("book1 2");
            session.save(book1);
            library.getBooks().add(book1);
            tx.commit();
            System.out.println(book1.getId());
            System.out.println(book.getId());
        }
        return library.getId();
    }

}
