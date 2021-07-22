import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class CourseRegistrationSystem {

    public static void main(String args[]) throws IOException, ClassNotFoundException {

        try (
                Scanner scanner = new Scanner(System.in);
        ) {
            // data loading block
            boolean isDataLoaded = false;
            while (!isDataLoaded) {
                System.out.println("Is this the first time launching this program? [y/n]");
                String yOrN = scanner.nextLine();
                switch (yOrN) {
                    case "y":
                        System.out.println("Please enter relative path of the csv seed file: ");
                        String filePath = scanner.nextLine();
//                        String filePath = "MyUniversityCourses.csv";
                        try {
                            Data.loadFromCSVAndPopulate(filePath);
                        } catch (FileNotFoundException e) {
                            System.out.println(filePath + " was not found.");
                            System.exit(1);
                        }
                        isDataLoaded = true;
                        break;

                    case "n":
                        try {
                            Data.loadData("./data.ser");
                        } catch (FileNotFoundException e) {
                            System.out.println("Binary file named 'data.ser' not found. Please launch with csv seed file.");
                            System.exit(1);
                        }
                        isDataLoaded = true;
                        break;

                    default:
                        System.out.println("[" + yOrN + "]" + " is not a valid choice.");
                }
            }

            // console block - level 1: select S/A
            while (true) {
                System.out.println("Are you a student or an admin? [S/A] ");
                String sOrA = scanner.nextLine();
                if (sOrA.equals("A")) {
                    runAdminConsole(scanner);
                } else if (sOrA.equals("S")) {
                    runStudentConsole(scanner);
                } else {
                    System.out.println("[" + sOrA + "]" + " is not a valid choice.");
                }
            }
        }
    }

    public static void runStudentConsole(Scanner scanner) throws IOException {
        System.out.println("Enter your Username: ");
        String id = scanner.nextLine();
        System.out.println("Enter your password: ");
        String passw = scanner.nextLine();
        Student student = Student.login(id, passw);
        if (student == null) {
            System.out.println("Login failed. You have entered the wrong username and password.");
            System.exit(0);
        }
        while (true) {
            System.out.println(
                    "Choose your operation: [ 1 ~ 6 ]\n" +
                            "1: View all courses\n" +
                            "2: View all courses that are not full\n" +
                            "3: Register in a course\n" +
                            "4: Withdraw from a course\n" +
                            "5: View all courses that the current student is registered in\n" +
                            "6: Exit Program"
            );
            String operationChoice = scanner.nextLine();
            switch (operationChoice) {
                case "1":
                    student.viewAllCourse();
                    break;
                case "2":
                    student.viewCoursesNotFull();
                    break;
                case "3":
                    System.out.println("Enter the course id: ");
                    String courseID = scanner.nextLine();
                    System.out.println("Enter the section number: ");
                    int sectNum;
                    try {
                        sectNum = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Register failed. Only numeric values are allowed for section number.");
                        break;
                    }
                    student.register(courseID, sectNum);
                    break;

                case "4":
                    System.out.println("Enter the course id: ");
                    String cID = scanner.nextLine();
                    System.out.println("Enter the section number: ");
                    int sectionNumber;
                    try {
                        sectionNumber = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Withdraw failed. Only numeric values are allowed for section number.");
                        break;
                    }

                    boolean isRemoved = student.withdraw(cID, sectionNumber);
                    if (isRemoved) {
                        System.out.println("You have successfully withdrawn from the course section.");
                    } else {
                        System.out.println("Withdraw failed. You are currently not registered to the course section.");
                    }

                    break;

                case "5":
                    String registeredClassString = student.getRegisteredClassString();
                    System.out.println(registeredClassString);
                    break;
                case "6":
                    Data.saveData("./data.ser");
                    System.exit(0);
                default:
                    System.out.println("[" + operationChoice + "]" + " is not a valid choice.");
            }
        }
    }

    public static void runAdminConsole(Scanner scanner) throws IOException {
        System.out.println("Enter your Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your Password: ");
        String password = scanner.nextLine();
        Admin admin = Admin.login(username, password);
        if (admin == null) {
            System.out.println("Login failed. You have entered the wrong username and password.");
            System.exit(0);
        }
        // console block - level 2: select Submenu
        while (true) {
            System.out.println("Would you like to see the Course Management Menu or Report Menu?: [C / R] ");
            String submenuChoice = scanner.nextLine();
            switch (submenuChoice) {
                case "C":
                    // console block - level 3: Course Management Submenu
                    boolean shoudExitCM = false;
                    while (!shoudExitCM) {
                        System.out.println(
                                "Choose your operation: [1 ~ 7]\n" +
                                        "1: Create new course section\n" +
                                        "2: Delete a course section\n" +
                                        "3: Edit a course section\n" +
                                        "4: Display information for a given course section\n" +
                                        "5: Register a student\n" +
                                        "6: Exit Course Management Menu\n" +
                                        "7: Exit Program"
                        );
                        String operationChoice = scanner.nextLine();
                        switch (operationChoice) {
                            case "1":
                                System.out.println("Enter the Course Name: ");
                                String courseName = scanner.nextLine();
                                System.out.println("Enter the Course Id: ");
                                String courseId = scanner.nextLine();
                                System.out.println("Enter the maximum number of students to be registered in the course: ");
                                int maxStudent = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Enter the name of course instructor: ");
                                String instructor = scanner.nextLine();
                                System.out.println("Enter the course location: ");
                                String location = scanner.nextLine();
                                System.out.println("Enter the section number: ");
                                int sectionNumber = scanner.nextInt();
                                scanner.nextLine();
                                CourseSection createdCourseSection = admin.createCourseSection(courseName, courseId, maxStudent, instructor, location, sectionNumber);
                                if (createdCourseSection == null) {
                                    System.out.println("Failed to register. Course Section with that course id and section number already exists.");
                                }
                                break;
                            case "2":
                                System.out.println("Enter id of the course for the course section to be deleted: ");
                                courseId = scanner.nextLine();
                                System.out.println("Enter section number of the course section to be deleted: ");
                                try {
                                    sectionNumber = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Delete failed. Only numeric values are allowed for section number.");
                                    break;
                                }
                                boolean isDeleted = admin.deleteCourseSection(courseId, sectionNumber);
                                if (isDeleted) {
                                    System.out.println("The course section has been deleted.");
                                } else {
                                    System.out.println("Delete failed. No matches of course section were found.");
                                }
                                break;
                            case "3":
                                System.out.println("Enter id of the course to be edited: ");
                                String idOfCourseToBeEdited = scanner.nextLine();
                                System.out.println("Enter the section number: ");
                                int sectionNum;
                                try {
                                    sectionNum = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Edit failed. Only numeric values are allowed for section number.");
                                    break;
                                }
                                admin.editCourseSection(idOfCourseToBeEdited, sectionNum);
                                break;
                            case "4":
                                System.out.println("Enter id of the Course: ");
                                String courseID = scanner.nextLine();
                                System.out.println("Please enter the course section number: ");
                                int sn;
                                try {
                                    sn = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Display failed. Only numeric values are allowed for section number.");
                                    break;
                                }

                                admin.displayCourseSectionInfo(courseID, sn);
                                break;
                            case "5":
                                System.out.println("Enter Student's First Name: ");
                                String firstName = scanner.nextLine();
                                System.out.println("Enter Student's Last Name: ");
                                String lastName = scanner.nextLine();
                                System.out.println("Enter Student's new Username: ");
                                String uname = scanner.nextLine();
                                System.out.println("Enter Student's new Password: ");
                                String pword = scanner.nextLine();
                                Student createdStudent = admin.registerStudent(firstName, lastName, uname, pword);
                                if (createdStudent == null) {
                                    System.out.println("Failed to register. Student of same username already exists.");
                                }
                                break;
                            case "6":
                                shoudExitCM = true;
                                break;
                            case "7":
                                Data.saveData("./data.ser");
                                System.exit(0);
                            default:
                                System.out.println("[" + operationChoice + "]" + " is not a valid choice.");
                        }
                    }
                    break;
                case "R":
                    // console block - level 3: Report Submenu
                    boolean shouldExitR = false;
                    while (!shouldExitR) {
                        System.out.println(
                                "Choose your operation: [1 ~ 8]\n" +
                                        "1: View all courses\n" +
                                        "2: View all courses that are full\n" +
                                        "3: Write to a file the list of course that are full\n" +
                                        "4: View the names of the students that are registered in a specific course\n" +
                                        "5: View the list of courses that a given student is registered in\n" +
                                        "6: Sort the courses based on the current number of students registered\n" +
                                        "7: Exit Report Menu\n" +
                                        "8: Exit Program"
                        );
                        String operationChoice = scanner.nextLine();
                        switch (operationChoice) {
                            case "1":
                                admin.viewAllCourseSections();
                                break;
                            case "2":
                                admin.viewFullCourseSection();
                                break;
                            case "3":
                                System.out.println("Enter output file name: ");
                                String fileName = scanner.nextLine();
                                admin.writeFullCourseSectionToFile(fileName);
                                break;
                            case "4":
                                System.out.println("Enter the course id: ");
                                String courseid = scanner.nextLine();
                                System.out.println("Enter the course section number: ");
                                int courseSectionNumber;
//
                                try {
                                    courseSectionNumber = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("View failed. Only numeric values are allowed for section number.");
                                    break;
                                }

                                String courseStudentListString = admin.getStudentsInSpecificCourseSectionString(courseid, courseSectionNumber);
                                if (courseStudentListString == null) {
                                    System.out.println("Course with that course id and section number does not exist!");
                                } else {
                                    System.out.println(courseStudentListString);
                                }
                                break;
                            case "5":
                                System.out.println("Enter Student's username: ");
                                String usrname = scanner.nextLine();
                                String studentCourseListString = admin.getStudentRegisteredCourseSectionsString(usrname);
                                if (studentCourseListString == null) {
                                    System.out.println("Student with that username does not exist!");
                                } else {
                                    System.out.println(studentCourseListString);
                                }
                                break;
                            case "6":
                                admin.sortCourseSections();
                                break;
                            case "7":
                                shouldExitR = true;
                                break;
                            case "8":
                                Data.saveData("./data.ser");
                                System.exit(0);
                            default:
                                System.out.println("[" + operationChoice + "]" + " is not a valid choice.");

                        }
                    }
                    break;
                default:
                    System.out.println("[" + submenuChoice + "]" + " is not a valid choice.");
            }
        }
    }
}


