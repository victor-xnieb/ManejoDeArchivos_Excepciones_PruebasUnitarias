package model;

import com.google.gson.Gson;

// JSON --> (JavaScript Object Notation)  -->  JSON es un formato de datos ligero y fácil de leer/escribir, basado en texto.

/*
Gson --> (Google JSON) --> Gson es una librería de Java creada por Google  -->
--> Sirve para convertir JSON a objetos Java (deserialización) y viceversa (serialización).
*/

//importaciones del metodo Controller()



import java.nio.file.Paths; //Para importar objetos Paths los cuales tienen metodos static.
import java.util.ArrayList;
import java.io.File; //Para importar objetos File
import java.nio.file.Path; //Para importar objetos Path


//importaciones del metodo inicialzed()

import java.nio.file.Files; //Para importar objetos Files los cuales tienen metodos static.
import java.io.IOException; //IOException es una checked exception, lo que significa que Java no te deja ignorarla.

//importaciones para el metodo addPerson().


import exceptions.PersonAllReadyExist;

//importaciones para el metodo loadCsv.

import java.util.List;
import java.nio.charset.StandardCharsets; // Libreria que reconoce los caracteres de nuestra region.

//importaciones para el metodo loadJson.

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


public class Controller {

    private ArrayList<Person> people;
    private ArrayList<Person> peopleLoadedCsv;
    private ArrayList<Person> peopleLoadedJson;
    private File data;
    private Path dataFolder;
    private Path dataJson;
    private Path dataCsv;


    public Controller() {

        people = new ArrayList<>();

        peopleLoadedCsv = new ArrayList<>();

        peopleLoadedJson = new ArrayList<>();

        Path dataProject = Paths.get(System.getProperty("user.dir")); //Paths.get() -> convierte el argumento en un objeto Path.

        System.out.println("Data project:" + dataProject + "\n");

        dataFolder = dataProject.resolve( "src/main/data"); //El método resolve(...) de la clase Path en
        // Java se usa para combinar rutas. Se toma una ruta base y se le agrega una subruta o archivo.

        System.out.println("Data folder:" + dataFolder + "\n");

        dataJson = dataFolder.resolve("people.json");

        System.out.println("Data Json: " + dataJson + "\n");

        dataCsv = dataFolder.resolve("people.csv");

        System.out.println("Data Csv: " + dataCsv + "\n");

        data = new File("lista.json");
               

    }


    /*
    Este metodo es declarado con una excepcion del tipo checked (IOException), las cuales
    Java te obliga a declararlas y manejarlas.

    En el codigo que hay dentro de este metodo, existen algunas lineas que pueden lanzar la excepcion
    como lo son: Files.createDirectories(dataFolder).

    Al declarar el metodo de esta manera ya estas declarando la excepcion, pero falta manejarla, y como
    lo estas declarando de esta manera, la responsabilidad de manejarla es del metedo que llame internamente
    al metodo inicialized. El manejo puede ser mediante un try catch.
    */

    public void inicialized() throws IOException{

        if(!Files.exists(dataFolder)) {
            Files.createDirectories(dataFolder);

            if(!Files.exists(dataJson)) {
                Files.createFile(dataJson);
            }

            if(!Files.exists(dataCsv)) {
                Files.createFile(dataCsv);
            }
        }

    }


    /*
    Utilizaremos el siguiente metodo para guardar informacion en nuestro archivo people.json
    el cual esta en la ruta src/main/data
     */

    public void saveInformationInOurFileJson() {
        try {
            /*
            Utilizamos este metodo por si acaso no se ha creado la carpeta
            data ni el archivo people.json
            */

            inicialized();

            /*
            El archivo json lo llenaremos con las personas que hayan sido
            creadas y guardadas en el arraylist people.

            Crearemos un objeto Gson para que este nos ayude a darle un formato adecuado
            */

            Gson gson = new Gson();
            String jsonFormat = gson.toJson(people);

            Files.writeString(dataJson,jsonFormat);


        } catch (IOException error) {
            System.out.println("No se ha podido llenar tu archivo people.json");
        }
    }


    public void saveInformationInOurFileCsv() {
        try {
            inicialized();

            String csvFormat = "";

            for(Person p: people) {
                csvFormat += p.getName() + ";" + p.getAge() + ";" + p.getHeight() + ";" + p.getWeight() + "\n";
            }

            Files.writeString(dataCsv,csvFormat);

        } catch (IOException error) {
            System.out.println("No se ha podido llenar tu archivo people.csv");
        }
    }




    public void loadCsv () {


        try {
            List<String> list = Files.readAllLines(dataCsv, StandardCharsets.UTF_8);

            System.out.println(list);

            for(String line: list) {
                String[] parts = line.split(";");

                Person person = new Person(
                        parts[0] ,
                        Integer.parseInt(parts[1]) ,
                        Double.parseDouble(parts[2]) ,
                        Double.parseDouble(parts[3])
                );

                peopleLoadedCsv.add(person);

            }


            System.out.println("lISTA CSV LOADED: " + peopleLoadedCsv + "\n");



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




    public void loadJson() {

        try {
            String json = Files.readString(dataJson);

            //System.out.println(json);

            Gson gson = new Gson();


            /*
            La siguiente linea nos permite definir el tipo de Objeto en que queremos convertir nuestro archivo json. En
            este caso, un ArrayList de tipo Person. Se usa para obtener el tipo genérico ArrayList<Person>, porque en Java no se puede
            obtener la información de tipos genéricos (ArrayList<Person>) en tiempo de ejecución.
             */
            Type peopleArray = new TypeToken<ArrayList<Person>>(){}.getType();

            /*
            En este caso, fromJson nos permite convertir la cadena de texto json en un ArrayList de tipo person.
             */
            peopleLoadedJson = gson.fromJson(json,peopleArray);

            System.out.println("LISTA JSON LOADED: " + peopleLoadedJson + "\n");

            System.out.println("PRINTED PERSON NAME: " + peopleLoadedJson.get(0).getName() + "\n");


        } catch (IOException e) {
            throw new RuntimeException("Ha habido un error al leer el archivo");

        }

    }






    public void addPerson(String name, int age, double height, double weight) throws  PersonAllReadyExist{

        Person person = new Person(name, age, height, weight);

        if(isInList(person)) {
            throw new PersonAllReadyExist("Esta persona ya ha sido creada.\n");

        } else {
            people.add(person);

        }

    }

    public boolean isInList(Person personToCheck) {
        for(Person person: people) {
            if(person.getName().equals(personToCheck.getName())) {
                return true;
            }
        }

        return false;
    }


    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }



    public String fileProperties(){

        String dataPathString = data.getAbsolutePath();
        Path dataPath = Paths.get(dataPathString);

        try {

            if(!Files.exists(dataPath)) {
                data.createNewFile();
            }


        } catch (IOException error) {
            throw new RuntimeException("Ha ocurrido un error al crear el archivo");
        }

        return dataPathString;
    }

    public  String getJson(ArrayList<Person> array) {

        Gson gson = new Gson();

        String json = gson.toJson(array);

        return json;

    }




}
