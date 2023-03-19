package my_mvc_app.dao;

import my_mvc_app.models.Person;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    // блок инициализации
    {
        people = new ArrayList<>();

    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void saveToFile(Person person){
        try (Writer fileWriter = new FileWriter("C:\\Users\\Professional\\IdeaProjects\\8_3 spring mvc\\FromFormToFileSpringMvc\\src\\main\\resources\\data.txt",true)) {
            fileWriter.write(person.getId() +
                    ", " + person.getName() +
                    ", " + person.getAge() +
                    ", " + person.getEmail() + "\n");
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> showFile(){
        String s = "";
        try (Reader fileReader = new FileReader("C:\\Users\\Professional\\IdeaProjects\\8_3 spring mvc\\FromFormToFileSpringMvc\\src\\main\\resources\\data.txt")) {
            int temp;
            for (; ; ) {
                temp = fileReader.read();
                if (temp == -1) break;
                s += (char) temp;
            }
            System.out.println("Text from TextFileWriter.txt:");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern p = Pattern.compile(".*@?.*", Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE);
        Matcher m = p.matcher(s);

        List<String> emailList = new ArrayList<>();
        while (m.find()){
            String email = m.group();
            emailList.add(email);
        }
        return emailList;
    }



    public void update(int id, Person person) {
        Person personUpdate = show(id);
        personUpdate.setName(person.getName());
        personUpdate.setAge(person.getAge());
        personUpdate.setEmail(person.getEmail());
    }

    public void delete(int id){
        people.removeIf(person -> person.getId() == id);//объект удаляется при тру
    }

}
