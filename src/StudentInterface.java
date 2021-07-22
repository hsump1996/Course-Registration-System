public interface StudentInterface {


    //Methods of Student Interface without any implementations

    //Registers in a course section by entering course name and section number as parameters
    public void register(String courseName, int sectionNumber);

    //Withdraws from a course section by entering course section name and section number as parameters
    public boolean withdraw(String courseName, int sectionNumber);


    //Views all course sections that are not full
    public void viewCoursesNotFull();


    //View all course sections' information
    public void viewAllCourse();


    //View all course sections that the current student is registered in
    public String getRegisteredClassString();

}
