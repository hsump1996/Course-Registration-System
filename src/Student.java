import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringJoiner;

public class Student extends User implements StudentInterface, Serializable {

    //Class Instance Variable of Student Class
    static final long serialVersionUID = 2L;


    //Stores names of course section that a student is currently registered in
    private ArrayList<CourseSection> courseSectionsRegistered = new ArrayList<CourseSection>();


    //Constructor of Student that accepts username, password, first name and last name as parameters
    Student(String username, String password, String firstName, String lastName) {

        super(username, password, firstName, lastName);

    }


    /**
     * Registers student from a course section
     *
     * firstName and lastName are unnecessary in this implementation of the specifications since
     * CourseSection instance has reference to the list of Students (as opposed to list of 'names of Students')
     * Not including name in the registering process could also preclude the unwanted scenario
     * where one Student attempts to register on behalf of another.
     * For example, if Student David and John are registered in the system, it wouldn't make sense for David to be
     * able to register John from the course by entering John's first name and last name.
     * Same reasoning applies to the withdraw method.
     *
     * As per the assignment specifications, Admin must be able to display information about a course section based on its Id and Section number.
     * In other words, (CourseId, Section number) pair should uniquely identify a Course Section.
     * Rather than making (CourseName, Section number) as another unique identifier, I chose to let register use courseID as well.
     * It is a fairly likely scenario where courses with different courseId but different courseName exist across different departments.
     */
    //Method that Registers a student in a course section
    @Override
    public void register(String courseId, int sectionNumber) {

        for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {
            if ((courseSection.getCourseId().equals(courseId)) && (courseSection.getSectionNumber() == sectionNumber)) {
                if (courseSection.isStudentRegistered(this)) {
                    System.out.println("You are already registered to this course section!");
                } else if (courseSection.isFull()) {
                    System.out.println("The course is full!");
                } else {
                    courseSectionsRegistered.add(courseSection);
                    courseSection.registerStudentInStudentList(this);
                }
            }
        }
    }

    /**
     * Withdraws student from a course section
     *
     * firstName and lastName are unnecessary in this implementation of the specifications since
     * CourseSection instance has reference to the list of Students (as opposed to list of 'names of Students')
     * For example, if Student David and John are registered in the system, it wouldn't make sense for David to be
     * able to withdraw John from the course by entering John's first name and last name.
     */
    //Method that withdraws student from a course section
    @Override
    public boolean withdraw(String courseId, int sectionNumber) {
        CourseSection courseToRemove = null;
        for (CourseSection courseSection : courseSectionsRegistered) {
            if (courseSection.getCourseId().equals(courseId) && courseSection.getSectionNumber() == sectionNumber) {
                courseToRemove = courseSection;
                break;
            }
        }
        if (courseToRemove == null) {
            return false;
        } else {
            courseSectionsRegistered.remove(courseToRemove);
            courseToRemove.withdrawStudentInStudentList(this);
            return true;
        }
    }

    //View all course sections
    @Override
    public void viewAllCourse() {
        System.out.println("This is the information for all the courses: ");
        for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {
            System.out.println(courseSection.toStringForStudent());
        }
        System.out.println();
    }

    //View all course sections that the current student is registered in
    @Override
    public String getRegisteredClassString() {
        StringJoiner outputSJ = new StringJoiner(", ", "[", "]");

        for (CourseSection courseSection : courseSectionsRegistered) {
            outputSJ.add(courseSection.getCourseName() + "-" + courseSection.getSectionNumber());
        }

        return outputSJ.toString();
    }

    //View all course sections that are not full
    @Override
    public void viewCoursesNotFull() {
        System.out.println("These are the courses that are not full: ");
        for (CourseSection courseSection : Data.getSingletonInstance().getCourseSectionArrayList()) {
            if (!courseSection.isFull()) {
                System.out.println(courseSection.toStringForStudent());
            }
        }
        System.out.println();
    }


    //Login Method for Student
    public static Student login(String username, String password) {

        Student loggedInStudent = null;

        for (Student student : Data.getSingletonInstance().getStudentArrayList()) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                loggedInStudent = student;
                break;
            }
        }
        return loggedInStudent;
    }
}
