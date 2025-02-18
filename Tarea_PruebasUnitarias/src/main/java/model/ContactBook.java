/*
Enunciado:
Una reconocida empresa de desarrollo de software requiere que usted desarrolle un
módulo para la gestión de contactos de los empleados de una empresa.
El módulo permitirá manejar una agenda centralizada con la información de todas
las personas que trabajan en la empresa y de esta manera poder contar con los datos
actualizados para aquellos casos en los que se requiera contactar a una persona por
diferentes medios. En principio solo se requiere que la agenda maneje el nombre
completo de la persona, el correo electrónico institucional y su número de
teléfono móvil.
 */

package model;


import java.util.ArrayList;
import java.util.List;
import model.Contact;

public class ContactBook {


    private List<Contact> contacts;


    public ContactBook() {
        contacts = new ArrayList<Contact>();
    }


    public List<Contact> getContacts() {
        return contacts;
    }


    public boolean add(String name,String email, String phone) {
        boolean result = false;

        if (search(email)==null) {
            contacts.add(new Contact(name, email,phone));
            result = true;
        }

        return result;

    }


    public Contact search(String email) {
        for(Contact c:contacts) {
            if(c.getEmail().equals(email)) {
                return c;
            }
        }

        return null;
    }


    public Contact remove(String email) {
        Contact contact = search(email);

        if(contact!=null) {
            contacts.remove(contact);
        }

        return contact;
    }




}
