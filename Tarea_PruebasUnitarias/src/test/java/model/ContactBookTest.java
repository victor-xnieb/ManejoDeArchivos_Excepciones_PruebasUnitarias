package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ContactBookTest {

    private ContactBook contactBook;


    public ContactBookTest() {}


    public void listWithElements() {

        contactBook = new ContactBook();

        contactBook.getContacts().add(new Contact("Juan Pablo","juanpablo@gmail.com","3201949433"));
        contactBook.getContacts().add(new Contact("Andres Zeng","Zen@gmail.com","3201789433"));
        contactBook.getContacts().add(new Contact("Victor cardona","victor@hotmail.com","3341960433"));

    }



    @Test
    public void testAddContact() {
        // init -> inicializar el escenario
        listWithElements();

        //Act -> darle las entradas a la prueba
        String name = "Federico";
        String email = "Federico@gmail.com";
        String phone = "3201789983";



        //Assert -> Comprobacion

        assertTrue(contactBook.add(name, email, phone));
        assertEquals(4, contactBook.getContacts().size());
        assertEquals("Federico", contactBook.getContacts().get(3).getName());
        assertEquals("Federico@gmail.com", contactBook.getContacts().get(3).getEmail());
        assertEquals("3201789983", contactBook.getContacts().get(3).getPhone());

    }



    @Test
    public void testAddContactWithARepeatElement() {
        // init -> inicializar el escenario
        listWithElements();

        //Act -> darle las entradas a la prueba.

        String name = "Federico";
        String email = "Zen@gmail.com";
        String phone = "3201789983";

        //  Act/Assert -> Comprobacion

        assertFalse(contactBook.add(name, email, phone));

    }



    @Test
    public void testSearchContact() {
        listWithElements();

        String email1 = "victor@hotmail.com";
        String email2 = "Federico@gmail.com";

        Contact contact = contactBook.search(email1);

        assertEquals("victor@hotmail.com", contact.getEmail());
        assertEquals("Victor cardona", contact.getName());
        assertEquals("3341960433", contact.getPhone());
        assertNull(contactBook.search(email2));

    }


    @Test
    public void testRemoveContact() {
        listWithElements();

        String email = "juanpablo@gmail.com";
        String email2 = "j@gmail.com";

        Contact removed = contactBook.remove(email);

        assertNotNull(removed);
        assertEquals(email, removed.getEmail());

        assertEquals(2, contactBook.getContacts().size());
        assertNull(contactBook.search(email));
        assertNull(contactBook.remove(email2));
    }








}
