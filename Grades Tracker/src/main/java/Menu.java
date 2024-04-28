import entities.*;
import jakarta.persistence.EntityManager;
import services.CatalogueService;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private final CatalogueService catalogueService;
    private final Scanner scanner;
    public Menu(EntityManager entityManager) {
        this.catalogueService = new CatalogueService(entityManager);
        this.scanner = new Scanner(System.in);
    }
    private Account login() {
        System.out.println("Login:");
        do {
            System.out.print("Username: ");
            String username = this.scanner.nextLine();

            System.out.print("Password: ");
            String password = this.scanner.nextLine();

            if(catalogueService.authenticate(username, password))
            {
                System.out.println("Logged in");
                return catalogueService.findAccount(username, password);
            }

            System.out.println("Wrong username or password. Please try again.");
        } while (true);
    }

    private int getInput() {
        do {
            try {
                return this.scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        } while (true);
    }
    private void showTeacherMenu() {
        System.out.println("Teacher menu:");
        System.out.println("0. Exit");
        System.out.println("1. Add grade");
        System.out.println("2. Remove grade");
        System.out.println("3. Update grade");
        System.out.println("4. Show grades");
        System.out.println("5. Show students");
        System.out.println("6. Show disciplines");
    }

    private int getGradeValue() {
        System.out.println("Pick grade value:");
        do {
            int input = getInput();
            if (input >= 1 && input <= 10)
                return input;
            System.out.println("Invalid grade value");
        } while (true);
    }

    private Grade getAvailableGrade(Discipline discipline, Student student) {
        List<Grade> grades = catalogueService.findAllGrades(discipline, student);
        int index = 0;
        for (Grade grade: grades)
        {
            System.out.println(index + " " + grade.getValue() + " date: " + grade.getDate());
            index++;
        }
        System.out.println("Pick grade");
        do {
            int input = getInput();
            if (input < grades.size() && input >= 0)
                return grades.get(input);
            System.out.println("Invalid option");
        } while (true);
    }
    private Student getAvailableStudent(Discipline discipline) {
        List<Student> students = catalogueService.findAllStudents(discipline);
        int index = 0;
        for (Student student: students)
        {
            System.out.println(index + " " + student.getFirstName() + " " + student.getLastName());
            index++;
        }
        System.out.println("Pick student");
        do {
            int input = getInput();
            if (input < students.size() && input >= 0)
                return students.get(input);
            System.out.println("Invalid option");
        } while (true);
    }

    private Student getAvailableStudent() {
        List<Student> students = catalogueService.findAllStudents();
        int index = 0;
        for (Student student: students)
        {
            System.out.println(index + " " + student.getFirstName() + " " + student.getLastName());
            index++;
        }
        System.out.println("Pick student");
        do {
            int input = getInput();
            if (input < students.size() && input >= 0)
                return students.get(input);
            System.out.println("Invalid option");
        } while (true);
    }
    private Discipline getAvailableDiscipline(Teacher teacher) {
        List<Discipline> disciplines = catalogueService.findAllDisciplines(teacher);
        int index = 0;
        for (Discipline discipline: disciplines)
        {
            System.out.println(index + " " + discipline.getName());
            index++;
        }
        System.out.println("Pick discipline");
        do {
            int input = getInput();
            if (input < disciplines.size() && input >= 0)
                return disciplines.get(input);
            System.out.println("Invalid option");
        } while (true);
    }

    private Discipline getAvailableDiscipline(Student student) {
        List<Discipline> disciplines = catalogueService.findAllDisciplines()
                .stream()
                .filter(x -> x.getStudents().contains(student))
                .collect(Collectors.toList());
        int index = 0;
        for (Discipline discipline: disciplines)
        {
            System.out.println(index + " " + discipline.getName());
            index++;
        }
        System.out.println("Pick discipline");
        do {
            int input = getInput();
            if (input < disciplines.size() && input >= 0)
                return disciplines.get(input);
            System.out.println("Invalid option");
        } while (true);
    }
    private void addGrade(Teacher teacher) {
        Discipline discipline = getAvailableDiscipline(teacher);
        Student student = getAvailableStudent(discipline);
        int gradeValue = getGradeValue();
        System.out.println(discipline.getName() + " " + student.getFirstName() + " " + student.getLastName() + " " + gradeValue);
        catalogueService.save(new Grade(gradeValue, student, discipline));
        System.out.println("Added");
    }
    private void updateGrade(Teacher teacher) {
        Discipline discipline = getAvailableDiscipline(teacher);
        Student student = getAvailableStudent(discipline);
        Grade grade = getAvailableGrade(discipline, student);
        int gradeValue = getGradeValue();
        grade.setValue(gradeValue);
        catalogueService.update(grade);
        System.out.println("Updated");
    }

    private void removeGrade(Teacher teacher) {
        Discipline discipline = getAvailableDiscipline(teacher);
        Student student = getAvailableStudent(discipline);
        Grade grade = getAvailableGrade(discipline, student);
        catalogueService.remove(grade);
        System.out.println("Removed");
    }

    private double getAverageFromGrades(List<Grade> grades) {
        double average = 0;
        for (Grade grade: grades) {
            average += grade.getValue();
        }
        average /= grades.size();
        return average;
    }
    private void showGradesMenu() {
        System.out.println("Grades menu");
        System.out.println("0. Exit");
        System.out.println("1. Show all grades");
        System.out.println("2. Show grades for discipline");
        System.out.println("3. Show grades for student");
        System.out.println("4. Show grades for student and discipline");
        System.out.println("5. Show average for student for each discipline");
        System.out.println("6. Show average for all");
        System.out.println("7. Show grades for discipline ordered by date");
    }

    private void processShowGradesOption(int option, Teacher teacher) {
        Comparator<Grade> gradeComparator = Comparator.comparing(Grade::getDate);
        Student student;
        Discipline discipline;
        switch (option) {
            case 1:
                for (Grade grade: catalogueService.findAllGrades()) {
                    System.out.print(grade.getStudent().getFirstName() + " " + grade.getStudent().getLastName());
                    System.out.println(" " + grade.getDiscipline().getName() + " " + grade.getValue());
                }
                break;
            case 2:
                discipline = getAvailableDiscipline(teacher);
                catalogueService.findAllGrades()
                        .stream()
                        .filter(x -> x.getDiscipline() == discipline)
                        .forEach(y -> System.out.println(
                                        y.getValue() + " " +
                                        y.getStudent().getFirstName() + " " +
                                        y.getStudent().getLastName()));
                break;
            case 3:
                student = getAvailableStudent();
                catalogueService.findAllGrades()
                        .stream()
                        .filter(x -> x.getStudent() == student)
                        .forEach(x -> System.out.println(x.getValue() + " " + x.getDiscipline().getName()));
                break;
            case 4:
                discipline = getAvailableDiscipline(teacher);
                student = getAvailableStudent(discipline);
                catalogueService.findAllGrades()
                        .stream()
                        .filter(x -> x.getDiscipline() == discipline && x.getStudent() == student)
                        .forEach(x -> System.out.println(x.getValue() + " date: " + x.getDate()));
                break;
            case 5:
                student = getAvailableStudent();
                for (Discipline d: catalogueService.findAllDisciplines()) {
                    if (!catalogueService.hasGrades(d, student))
                        continue;
                    double average = getAverageFromGrades(catalogueService.findAllGrades(d, student));
                    System.out.println(d.getName() + " " + average);
                }
                break;
            case 6:
                for (Student s: catalogueService.findAllStudents())
                {
                    double average = getAverageFromGrades(catalogueService.findAllGrades(s));
                    System.out.println(s.getFirstName() + " " + s.getLastName() + " " + average);
                }
                break;
            case 7:
                discipline = getAvailableDiscipline(teacher);
                catalogueService.findAllGrades()
                        .stream()
                        .filter(x -> x.getDiscipline() == discipline)
                        .sorted(gradeComparator)
                        .forEach(x -> System.out.println(
                                x.getStudent().getFirstName() + " " +
                                x.getStudent().getLastName() + " " +
                                x.getValue() + " " +
                                x.getDate()));
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
    private void showGrades(Teacher teacher) {
        do {
            showGradesMenu();
            int input = getInput();
            if (input == 0)
                return;
            processShowGradesOption(input, teacher);
        } while (true);
    }

    private void showStudentsMenu() {
        System.out.println("Students menu");
        System.out.println("0. Exit");
        System.out.println("1. Show all relevant students");
        System.out.println("2. Show all students");
        System.out.println("3. Show all relevant students alphabetically");
        System.out.println("4. Show all students alphabetically");
    }

    private List<Student> getAllRelevantStudents(Teacher teacher) {
        List<Student> students = new ArrayList<>();
        for (Discipline discipline: catalogueService.findAllDisciplines(teacher))
            for (Student student: catalogueService.findAllStudents(discipline))
                if (!students.contains(student))
                    students.add(student);
        return students;
    }

    private void processShowStudentsOption(int option, Teacher teacher) {
        Comparator<Student> comparator = (Student a, Student b) ->
        {
          if (a.getFirstName().compareTo(b.getFirstName()) == 0)
              return a.getLastName().compareTo(b.getLastName());
          return a.getFirstName().compareTo(b.getFirstName());
        };
        List<Student> students;
        switch (option) {
            case 1:
                catalogueService.findAllStudents()
                        .stream()
                        .filter(x -> x.getDisciplines().stream().anyMatch(y -> y.getTeachers().contains(teacher)))
                        .forEach(x -> System.out.println(x.getFirstName() + " " + x.getLastName()));
                break;
            case 2:
                catalogueService.findAllStudents().forEach(x -> System.out.println(x.getFirstName() + " " + x.getLastName()));
                break;
            case 3:
                catalogueService.findAllStudents()
                        .stream()
                        .filter(x -> x.getDisciplines().stream().anyMatch(y -> y.getTeachers().contains(teacher)))
                        .sorted(comparator)
                        .forEach(x -> System.out.println(x.getFirstName() + " " + x.getLastName()));
                break;
            case 4:
                catalogueService.findAllStudents()
                        .stream()
                        .sorted(comparator)
                        .forEach(x -> System.out.println(x.getFirstName() + " " + x.getLastName()));
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
    private void showStudents(Teacher teacher) {
        do {
            showStudentsMenu();
            int input = getInput();
            if (input == 0)
                return;
            processShowStudentsOption(input, teacher);
        } while (true);
    }
    private void showDisciplinesMenu() {
        System.out.println("Disciplines menu");
        System.out.println("0. Exit");
        System.out.println("1. Show all disciplines");
        System.out.println("2. Show available disciplines");
        System.out.println("3. Show all disciplines alphabetically");
        System.out.println("4. Show available disciplines alphabetically");
    }
    private void processShowDisciplinesOption(int option, Teacher teacher) {
        Comparator<Discipline> comparator = Comparator.comparing(Discipline::getName);
        List<Discipline> disciplines;
        switch (option) {
            case 1:
                for (Discipline discipline: catalogueService.findAllDisciplines())
                    System.out.println(discipline.getName());
                break;
            case 2:
                for (Discipline discipline: catalogueService.findAllDisciplines(teacher))
                    System.out.println(discipline.getName());
                break;
            case 3:
                disciplines = catalogueService.findAllDisciplines();
                disciplines.sort(comparator);
                for (Discipline discipline: disciplines)
                    System.out.println(discipline.getName());
                break;
            case 4:
                disciplines = catalogueService.findAllDisciplines(teacher);
                disciplines.sort(comparator);
                for (Discipline discipline: disciplines)
                    System.out.println(discipline.getName());
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
    private void showDisciplines(Teacher teacher) {
        do {
            showDisciplinesMenu();
            int input = getInput();
            if (input == 0)
                return;
            processShowDisciplinesOption(input, teacher);
        } while (true);
    }
    private void processTeacherOption(int option, Teacher teacher) {
        switch (option) {
            case 1:
                addGrade(teacher);
                break;
            case 2:
                removeGrade(teacher);
                break;
            case 3:
                updateGrade(teacher);
                break;
            case 4:
                showGrades(teacher);
                break;
            case 5:
                showStudents(teacher);
                break;
            case 6:
                showDisciplines(teacher);
                break;
            default:
                System.out.println("Invalid option");
        }
    }
    private void runTeacherMenu(Teacher teacher) {
        do {
            showTeacherMenu();
            int input = getInput();
            if (input == 0)
                return;
            processTeacherOption(input, teacher);
        } while (true);
    }
    private void showStudentMenu() {
        System.out.println("Student menu:");
        System.out.println("0. Exit");
        System.out.println("1. Show grades");
        System.out.println("2. Show disciplines");
        System.out.println("3. Show grades from discipline");
        System.out.println("4. Show averages for disciplines");
        System.out.println("5. Show average");
    }

    private double getAverageForStudentAndDiscipline(Student student, Discipline discipline) {
        List<Integer> grades = catalogueService.findAllGrades()
                .stream()
                .filter(x ->x.getStudent() == student)
                .filter(x -> x.getDiscipline() == discipline)
                .map(Grade::getValue)
                .collect(Collectors.toList());
        return (double) grades.stream().reduce(0, Integer::sum) / grades.size();
    }

    private List<Discipline> getDisciplinesForStudent(Student student) {
        return catalogueService.findAllDisciplines()
                .stream()
                .filter(x -> x.getStudents().contains(student))
                .collect(Collectors.toList());
    }
    private void processStudentOption(int option, Student student) {
        Discipline discipline;
        switch (option) {
            case 1:
                catalogueService.findAllGrades()
                        .stream()
                        .filter(x -> x.getStudent() == student)
                        .forEach(x -> System.out.println(
                                x.getDiscipline() + " " +
                                x.getValue()
                        ));
                break;
            case 2:
                catalogueService.findAllDisciplines()
                        .stream()
                        .filter(x -> x.getStudents().contains(student))
                        .forEach(x -> System.out.println(x.getName()));
                break;
            case 3:
                discipline = getAvailableDiscipline(student);
                catalogueService.findAllGrades()
                        .stream()
                        .filter(x -> x.getStudent() == student)
                        .filter(x -> x.getDiscipline() == discipline)
                        .forEach(x -> System.out.println(
                                x.getValue() + " " + x.getDate()
                        ));
                break;
            case 4:
            {
                List<Discipline> disciplines = catalogueService.findAllDisciplines()
                        .stream()
                        .filter(x -> x.getStudents().contains(student))
                        .collect(Collectors.toList());
                for (Discipline d: disciplines) {
                    double average = getAverageForStudentAndDiscipline(student, d);
                    System.out.println(d.getName() + " " + average);
                }
                break;
            }
            case 5:
                List<Discipline> disciplines = catalogueService.findAllDisciplines()
                        .stream()
                        .filter(x -> x.getStudents().contains(student))
                        .collect(Collectors.toList());
                List<Double> averages = new ArrayList<>();
                for (Discipline d: disciplines) {
                    double average = getAverageForStudentAndDiscipline(student, d);
                    averages.add(average);
                }
                double average = (double) averages.stream().reduce(0.0, Double::sum) / averages.size();
                System.out.println("Average: " + " " + average);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
    private void runStudentMenu(Student student) {
        do {
            showStudentMenu();
            int input = getInput();
            if (input == 0)
                return;
            processStudentOption(input, student);
        } while (true);
    }
    public void runMenu() {
        Account account = login();
        if (account.getTeacher() != null)
            runTeacherMenu(account.getTeacher());
        else
            runStudentMenu(account.getStudent());

        this.scanner.close();
    }
}
