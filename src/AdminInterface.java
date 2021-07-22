import java.io.IOException;

public interface AdminInterface {

    //Course Management

    //Create a new course section
    public CourseSection createCourseSection(String courseName, String courseId, int maxStudent, String instructor, String location, int sectionNumber);

    //Delete a course section
    public boolean deleteCourseSection(String courseName, int sectionNumber);

    //Edit a course section
    public CourseSection editCourseSection(String courseName, int sectionNumber);

    //Display information for a given course by course ID
    public void displayCourseSectionInfo(String courseId, int sectionNumber);

    //Register a student
    public Student registerStudent(String firstName, String lastName, String username, String password);


    //Reports

    //View all course sections
    public void viewAllCourseSections();

    //View all course sections that are full
    public void viewFullCourseSection();

    //Write to a file the list of course sections that are full
    public void writeFullCourseSectionToFile(String fileName) throws IOException;


    //View the list of course sections that a given student is registered in
    public String getStudentRegisteredCourseSectionsString(String username);
    

    //View the names of the students that are registered in a specific course section
    public String getStudentsInSpecificCourseSectionString(String courseName, int sectionNumber);


    //Sort the course sections based on the current number of students registered
    public void sortCourseSections();

}
