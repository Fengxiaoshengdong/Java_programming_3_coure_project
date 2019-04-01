
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.parser.ParseException; 


/**
 * Filename:   TestGraph.java
 * Project:    p4
 * Authors:    Debra Deppeler
 * 
 * Use this class for to test CourseScheduleUtil and GraphImpl
 * Edit as you desired.  No need to submit as we will use our
 * own test classes.
 */

public class TestGraph
{
    public static void main(String[] args)
    {
    
        System.out.println("************Course Planner************");
        System.out.println("1.Enter input filename:");
        System.out.println("2.Print all the available courses:");
        System.out.println("3.Is it possible to take all courses?");
        System.out.println("4.Print one of the possible order in which courses can be taken:");
        System.out.println("5.Minimum number of courses needed to be taken for a given course:");
        System.out.println("6.Quit ");
        
        Scanner s = new Scanner(System.in);
        boolean flag = true;
        
        @SuppressWarnings("rawtypes")
        Entity[] entities = null;

        CourseSchedulerUtil<String> courses = new CourseSchedulerUtil<String>();
        
        while(flag) {
            System.out.println("Enter the choice: ");
            int choice = s.nextInt();
            switch(choice) {
            case 1:
                Scanner s1 = new Scanner(System.in);
                String jsonFileName = null;
                try {
                    System.out.println("Enter the json filename:");
                    jsonFileName = s1.nextLine();
                    entities = courses.createEntity(jsonFileName);
                    courses.constructGraph(entities);
//                    courses.getGraphImpl().printGraph();
                    System.out.println("Successfully parsed the json file and created graph." + " " + jsonFileName);
                } catch (ParseException e) {
                	System.out.println("format!!");
                } catch (FileNotFoundException e) {
                	System.err.println("file not found" + "\"" + jsonFileName + "\"");
                } catch (Exception e) {
                	e.printStackTrace();
                    System.err.println(e);
                } 
                break;
            case 2:
                try {
                    Set<String> result = courses.getAllCourses();
                    System.out.println("All available courses:");
                    StringBuilder sb = new StringBuilder();
                    for (String course : result) {
                        sb.append(course).append(",");
                    }
                    String availableCourses = sb.toString();
                    System.out.println(availableCourses.substring(0,availableCourses.length()-1));
                } catch (Exception e) {
                    System.out.println("Exception occured.");
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    boolean result = courses.canCoursesBeCompleted();
                    if (result) {
                        System.out.println("Yes");
                    } else {
                        System.out.println("No");
                    }
                    
                } catch (Exception e) {
                    System.out.println("Exception occured.");
                }
                break;
            case 4:
                try {
                    List<String> result = courses.getSubjectOrder();
                    System.out.println("Course Ordering:");
                    StringBuilder sb = new StringBuilder();
                    for (String course : result) {
                        sb.append(course).append(",");
                    }
                    String courseOrdering = sb.toString();
                    System.out.println(courseOrdering.substring(0,courseOrdering.length()-1));
                } catch (Exception e) {
                    System.err.println("Exception occured.");
                }
                break;
            case 5:
                Scanner s2 = new Scanner(System.in);
                try {
                    System.out.println("Enter the courseName:");
                    String cName = s2.nextLine();
                    int min = courses.getMinimalCourseCompletion(cName);
                    System.out.println("Minimum number of courses needed: " + min);
                } catch (Exception e) {
                        System.err.println("The above course not present");
                } 
                break;
            case 6:
                System.out.println("Quit");
                flag = false;
                break;
            default:
                System.out.println("Enter the correct choice:");
            }
        }
        s.close();
    }
}
