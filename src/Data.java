import java.io.*;
import java.util.*;

 public class Data implements Serializable {

     //Class Instance variable of Data Class
     private static Data data;
     static final long serialVersionUID = 3L;

     //An ArrayList of Students where Student Objects are stored
     private List<Student> studentArrayList = new ArrayList<>();

     //An ArrayList of Courses where Course Objects are stored
     private List<CourseSection> courseSectionArrayList = new ArrayList<>();


     //Private constructor of Data so that Data can only have one instance
     //We don't want to have multiple instances of Data (Singleton Class)
     private Data() { }

     @Override
     public boolean equals(Object obj) {

         if (!(obj instanceof Data))
             return false;
         Data opponentData = (Data) obj;
         if ((this.studentArrayList.equals(opponentData.getStudentArrayList()))
                 && (this.courseSectionArrayList.equals(opponentData.getCourseSectionArrayList()))){
             return true;
         } else {
             return false;
         }
     }


     //Method that returns data singleton instance (Since only one instance of a data can be created)
     public static Data getSingletonInstance() { return data; }


     //Method that populates the data in CSV to courseSectionArrayList in the Data Class
     public static void loadFromCSVAndPopulate(String filePath) throws IOException {
         try (
             FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
         ) {
             bufferedReader.readLine(); //Since the first line of the CSV contains titles, it skips the first line
             String secondLine = null;
             data = new Data();
             while ((secondLine = bufferedReader.readLine()) != null) {
                 String[] temporaryStringList = secondLine.split(",");
                 String courseName = temporaryStringList[0];
                 String courseId = temporaryStringList[1];
                 int maxNumberStudent = Integer.parseInt(temporaryStringList[2]);
                 String instructorName = temporaryStringList[5];
                 int sectionNumber = Integer.parseInt(temporaryStringList[6]);
                 String location = temporaryStringList[7];
                 List<CourseSection> courseSectionList = data.getCourseSectionArrayList();
                 courseSectionList.add(new CourseSection(courseName, courseId, maxNumberStudent, instructorName, sectionNumber, location));
             }
         }
     }

     //Method that serializes the CourseSectionArrayList and StudentArrayList in Data class
     public static void saveData(String filePath) throws IOException {

         try (
             FileOutputStream fout = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fout);

         ) {
             oos.writeObject(data);
         }
     }

     //Method that Deserializes the CourseSectionArrayList and StudentArrayList in Data class
     public static void loadData(String filePath) throws IOException, ClassNotFoundException {
         try (
                 FileInputStream finpt = new FileInputStream(filePath);
                 ObjectInputStream ois = new ObjectInputStream(finpt);

         ) {
             data = (Data) ois.readObject();
         }
     }

     //Method that returns the studentArrayList in Data Class
     public List<Student> getStudentArrayList() {
         return studentArrayList;
     }


     //Method that returns the courseArrayList in Data Class
     public List<CourseSection> getCourseSectionArrayList() {
         return courseSectionArrayList;
     }

 }

