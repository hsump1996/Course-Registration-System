import java.io.Serializable;
import java.util.*;

public class CourseSection implements Comparable<CourseSection>, Serializable {

    //CourseSection Class Instance Variables
    private String courseName;
    private String courseId;
    private int maxNumberStudent;
    private String instructorName;
    private int sectionNumber;
    private String location;
    private List<Student> studentsList = new ArrayList<>();
    static final long serialVersionUID = 1L;

    //Constructor with parameters
    CourseSection(String courseName, String courseId, int max_number_student, String instructorName, int section_number, String location) {

        this.courseName = courseName;
        this.courseId = courseId;
        this.maxNumberStudent = max_number_student;
        this.sectionNumber = section_number;
        this.instructorName = instructorName;
        this.location = location;

    }

    //Getter method for Course ID
    public String getCourseId() {

        return courseId;

    }

    //Getter method for Course Section Location
    public String getLocation() {

        return location;
    }

    //Getter method for Course Section Name
    public String getCourseName() {

        return courseName;
    }

    //Getter method for instructor
    public String getInstructorName() {

        return instructorName;
    }

    //Getter method for maximum number of student
    public int getMaxNumOfStudent() {

        return maxNumberStudent;

    }

    //Getter method for current number of student
    public int getCurrentNumOfStudent() {

        return studentsList.size();

    }

    //Getter method for Course Section Number
    public int getSectionNumber() {

        return sectionNumber;

    }

    //Setter for Instructor name
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    //Setter for section number
    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    //Setter course location
    public void setLocation(String location) {
        this.location = location;
    }


    //Setter for max number of student
    public void setMaxStudent(int maxNumberStudent) {
        this.maxNumberStudent = maxNumberStudent;
    }


    //Registers a student object to Student List
    public void registerStudentInStudentList(Student student) {
        studentsList.add(student);
    }


    //Withdraws a student from the Student List
    public boolean withdrawStudentInStudentList(Student student) {
        return studentsList.remove(student);
    }

    //Method used to sort course_section by Current Number of Students
    @Override
    public int compareTo(CourseSection courseSectionToBeCompared) {

        return this.studentsList.size() - courseSectionToBeCompared.studentsList.size();

    }

    //Method to determine whether a particular course section is full
    public boolean isFull() {
        return this.studentsList.size() >= this.maxNumberStudent;
    }

    //Method to determine whether a student is registered to course section
    public boolean isStudentRegistered(Student student) {
        return studentsList.contains(student);
    }

    //Print method that returns elements of course section as a String
    @Override
    public String toString() {

        ArrayList<String> studentNamesList = new ArrayList<>();
        for (Student student : studentsList) {
            studentNamesList.add(student.getFirstName() + " " + student.getLastName());
        }
        return toStringForStudent() +
                "Current Number of Students: " + studentsList.size() + ", " +
                "List of Student Names: " + studentNamesList;
    }

    //Another version of print method that returns elements of course section, which will be needed by a student
    public String toStringForStudent() {
        return "Course Name: " + courseName + ", " +
                "Course ID: " + courseId + ", " +
                "Instructor Name: " + instructorName + ", " +
                "Section Number: " + sectionNumber + ", " +
                "Location: " + location + ", " +
                "Max Number of Students: " + maxNumberStudent;
    }

    //Method that returns String Version of Student Object's firstName + lastName from the Student List
    public String getStudentListString() {
        StringJoiner outputSJ = new StringJoiner(", ", "[", "]");
        for (Student student : studentsList) {
            outputSJ.add(student.getFirstName() + " " + student.getLastName());
            System.out.println(student.getFirstName() + " " + student.getLastName());
        }
        return outputSJ.toString();

    }
}
