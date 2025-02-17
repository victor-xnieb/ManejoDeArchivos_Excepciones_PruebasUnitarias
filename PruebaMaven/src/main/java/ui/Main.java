package ui;


import exceptions.PersonAllReadyExist;
import model.Person;
import model.Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private Controller cont;

    Main() {
        cont = new Controller();
    }


    public static void main( String[] args ) {
        Main myApp = new Main();
        myApp.Run();
    }


    private void Run() {
        System.out.println("Hola cómo estas.\n");

        try {
            cont.addPerson("Name1", 10, 1.70, 70);
            cont.addPerson("Name2", 20, 1.70, 70);
            cont.addPerson("Name3", 30, 1.70, 70);
            cont.addPerson("Name4", 40, 1.70, 70);
            cont.addPerson("Name5", 50, 1.70, 70);
            //cont.addPerson("Name5", 50, 1.70, 70);
            cont.addPerson("Name6", 50, 1.70, 70);


        } catch (PersonAllReadyExist error) {

            System.out.println("No puedes ingresar la misma persona a la lista");
        }

        cont.saveInformationInOurFileJson();
        cont.saveInformationInOurFileCsv();

        cont.loadCsv();

        cont.loadJson();

        System.out.println("DATA PATH: " + cont.fileProperties());

    }

}
