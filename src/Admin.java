import java.io.*;
import java.util.*;
public class Admin extends User implements AdminInterface {

    private static Admin admin = null;

    //Constructor for Admin
    private Admin(String username, String password) {
        super(username, password);
    }


    //Login Method for Admin
    public static Admin login(String username, String password) {
        if (username.equals("Admin") && password.equals("Admin001")) {
            if (admin == null) {
                admin = new Admin(username, password);
            }
            return admin;
        } else {
            return null;
        }
    }


    //Creates a new Course section
    @Override
    public CourseSection createCourseSection(String courseName, String courseId, int maxStudent, String instructor, String location, int sectionNumber) {

        List<CourseSection> courseSectionList = Data.getSingletonInstance().getCourseSectionArrayList();
        for (CourseSection courseSection : courseSectionList) {
            if (courseSection.getSectionNumber() == sectionNumber && (courseSection.getCourseId().equals(courseId)))
                return null;

        }

        CourseSection newCourseSection = new CourseSection(courseName, courseId, maxStudent, instructor, sectionNumber, location);
        courseSectionList.add(newCourseSection);

        return newCourseSection;
    }

    //Deletes a Course section by putting the course name and section number into the parameter of the method
    @Override
    public boolean deleteCourseSection(String courseId, int sectionNumber) {
        List<CourseSection> courseSectionList = Data.getSingletonInstance().getCourseSectionArrayList();
        int indexToRemove = -1;
        for (int i = 0; i < courseSectionList.size(); i++) {
            CourseSection courseSection = courseSectionList.get(i);
            if (courseSection.getCourseId().equals(courseId) && courseSection.getSectionNumber() == sectionNumber) {
                indexToRemove = i;
            }
        }
        if (indexToRemove != -1) {
            courseSectionList.remove(indexToRemove);
            return true;
        } else {
            return false;
        }

    }


    //Method that edits a course section(This will allow the admin to edit any information on the course section
    // except for Course ID and name)
    @Override
    public CourseSection editCourseSection(String courseId, int sectionNumber) {
    
        Scanner scanner = new Scanner(System.in);
        
        CourseSection courseSectionToEdit = null;
        for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {
            if (courseSection.getSectionNumber() == sectionNumber && courseSection.getCourseId().equals(courseId)) {
                courseSectionToEdit = courseSection;
                break;
            }
        }
        //If the course array list in Data list cannot find any matches, it returns null
        if (courseSectionToEdit == null) {
            System.out.println("Failed to find a matching course section.");
            return null;
        }

        boolean shouldExitEditOperation = false;
        while (!shouldExitEditOperation) {
            System.out.println(
                    "Which information would you like to edit on this course?\n" +
                            "1: Maximum number of Students to be registered in the class\n" +
                            "2: Course instructor\n" +
                            "3: Section number\n" +
                            "4: Course location\n" +
                            "5: (Exit edit)\n"
            );

            String editOperationChoice = scanner.nextLine();

            switch (editOperationChoice) {
                case "1":
                    System.out.println("Enter the new maximum number of students to be registered in the class: ");
                    int newMaxNumOfStudents;
                    try {
                        newMaxNumOfStudents = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Edit failed. Only numeric values are allowed for max number of students.");
                        break;
                    }
                    courseSectionToEdit.setMaxStudent(newMaxNumOfStudents);
                    break;
                case "2":
                    System.out.println("Enter the new course instructor's name: ");
                    String newCourseInstructorName = scanner.nextLine();
                    courseSectionToEdit.setInstructorName(newCourseInstructorName);
                    break;
                case "3":
                    System.out.println("Enter the new section number: ");
                    int newSectionNumber;
                    try {
                        newSectionNumber = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Edit failed. Only numeric values are allowed for section number.");
                        break;
                    }
                    boolean alreadyAssigned = false;
                    for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {
                        if (courseSection.getSectionNumber() == newSectionNumber
                                && (courseSection.getCourseId().equals(courseSectionToEdit.getCourseId()))) {
                            alreadyAssigned = true;
                            System.out.println(
                                    "Edit failed." +
                                    "That section number has already been assigned to " +
                                    "an existing course section with same course id."
                            );
                            break;
                        }
                    }
                    if (!alreadyAssigned) {
                        courseSectionToEdit.setSectionNumber(newSectionNumber);
                    }
                    break;
                case "4":
                    System.out.println("Enter the new course location: ");
                    String newCourseLocation = scanner.nextLine();
                    courseSectionToEdit.setLocation(newCourseLocation);
                    break;
                case "5":
                    shouldExitEditOperation = true;
                    break;

                default:
                    System.out.println("Invalid Number");
            }
        }
        return courseSectionToEdit;
    }

    //Method that displays information for a given course section by course Id and course section number
    @Override
    public void displayCourseSectionInfo(String courseId, int sectionNumber) {

        for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {
            if (courseSection.getCourseId().equals(courseId) && courseSection.getSectionNumber() == sectionNumber) {
                System.out.println(courseSection);
            }
        }
    }

    //Register a Student by asking the admin the student's first name, last name, username, and password
    @Override
    public Student registerStudent(String firstName, String lastName, String username, String password) {
        List<Student> studentList = Data.getSingletonInstance().getStudentArrayList();
        for (Student student: studentList) {
            if (student.getUsername().equals(username)) {
                return null;
            }
        }

        Student newStudent = new Student(username, password, firstName, lastName);
        studentList.add(newStudent);

        return newStudent;
    }

    //Prints out all the information of all the course sections in the Course ArrayList
    @Override
    public void viewAllCourseSections() {

        for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {

            System.out.println(courseSection);

        }
    }

    //Method that gives list of course sections that are full(reached the maximum number of students)
    @Override
    public void viewFullCourseSection() {
        System.out.println("Here is the list of classes that are full: ");
        for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {
            if (courseSection.isFull())
                System.out.println(courseSection);
        }
        System.out.println();
    }

    //Method that makes the admin to write to a txt file the list of course sections that are full.
    @Override
    public void writeFullCourseSectionToFile(String fileName) throws IOException {
        try (
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            StringBuilder coursesThatAreFull = new StringBuilder();
            for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {
                if (courseSection.isFull()) {
                    coursesThatAreFull.append(courseSection.getCourseName());
                    coursesThatAreFull.append("\n");
                }
            }
            bufferedWriter.write(coursesThatAreFull.toString());
            }
        }



    //Method that allows the admin to view the list of course sections that a given student is registered in. (By entering
    //Student username as parameter)
    @Override
    public String getStudentRegisteredCourseSectionsString(String username) {  //The admin should first insert student's username as parameter

        for (Student student : Data.getSingletonInstance().getStudentArrayList()) {
            String studentUsername = student.getUsername();

            if (studentUsername.equals(username)) {
                return student.getRegisteredClassString();
            }
        }
        return null;
    }

    //Method that sorts the courses based on the current number of students registered
    @Override
    public void sortCourseSections() {
        Collections.sort(Data.getSingletonInstance().getCourseSectionArrayList(), Collections.reverseOrder());
    }

    //Method that returns string of the names of students in a specific course section(By entering course id and section number as parameters)
    @Override
    public String getStudentsInSpecificCourseSectionString(String courseId, int sectionNumber) {

        for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {
            if (courseSection.getCourseId().equals(courseId) && courseSection.getSectionNumber() == sectionNumber)
                return courseSection.getStudentListString();
        }
        return null;
    }
}
