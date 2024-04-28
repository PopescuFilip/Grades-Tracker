import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import repositories.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        AccountRepo accountRepo = new AccountRepo(entityManager);
        DisciplineRepo disciplineRepo = new DisciplineRepo(entityManager);
        TeacherRepo teacherRepo = new TeacherRepo(entityManager);
        StudentRepo studentRepo = new StudentRepo(entityManager);
        GradeRepo gradeRepo = new GradeRepo(entityManager);

        accountRepo.printAll();
        Menu menu = new Menu(entityManager);
        menu.runMenu();


        disciplineRepo.printAll();
        studentRepo.printAll();
        //gradeRepo.printAll();
        //teacherRepo.printAll();

        entityManager.close();
        entityManagerFactory.close();

    }

    public static void addDisciplines(DisciplineRepo disciplineRepo) {
        disciplineRepo.save(new Discipline("Math"));
        disciplineRepo.save(new Discipline("PE"));
        disciplineRepo.save(new Discipline("History"));
        disciplineRepo.save(new Discipline("English"));
        disciplineRepo.save(new Discipline("Physics"));
    }
    public static void addAccounts(AccountRepo accountRepo, DisciplineRepo disciplineRepo) {
        accountRepo.save(new Account("user", "pass", new Student("Dani", "Marc")));
        accountRepo.save(new Account("angela", "1234", new Student("Octavian", "Deleanu")));
        accountRepo.save(new Account("adrian", "adrian", new Student("Cosmin", "Agapie")));
        accountRepo.save(new Account("andreea", "4321", new Teacher("Andreea", "Sandu")));
        accountRepo.save(new Account("sebastian", "5555", new Teacher("Sebastian", "Botoc")));
        accountRepo.save(new Account("alina180", "4321", new Teacher("Alina", "Negulescu")));
        accountRepo.save(new Account("steffan", "1805", new Teacher("Stefan", "Parlog")));
        accountRepo.save(new Account("karma", "seen", new Teacher("Carmen", "Sin")));
        accountRepo.save(new Account("filip", "panda", new Student("Filip", "Popescu")));
        Teacher teacher = new Teacher("firstName", "lastName");
        Student student = new Student("firstName", "lastName");
        accountRepo.save(new Account("teacher", "pass", teacher));
        accountRepo.save(new Account("student", "pass", student));
        for (Discipline discipline: disciplineRepo.findAll())
        {
            discipline.addTeacher(teacher);
            discipline.addStudent(student);
            disciplineRepo.update(discipline);
        }
    }
    public static void addStudents(StudentRepo studentRepo) {
        studentRepo.save(new Student("Dani", "Marc"));
        studentRepo.save(new Student("Octavian", "Deleanu"));
        studentRepo.save(new Student("Cosmin", "Agapie"));
    }

    public static void addGrades(GradeRepo gradeRepo, StudentRepo studentRepo, int id) {
        Student student = studentRepo.find(id);
        //gradeRepo.save(new Grade(8, student));
        //gradeRepo.save(new Grade(4, student));
        //gradeRepo.save(new Grade(6, student));
        //gradeRepo.save(new Grade(10, student));

    }
}
