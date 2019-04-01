
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * Filename:   CourseSchedulerUtil.java
 * Project:    p4
 * Authors:    Debra Deppeler, Yuanhang Wang
 * 
 * Use this class for implementing Course Planner
 * @param <T> represents type
 */

public class CourseSchedulerUtil<T> {
    
    // can add private but not public members
    
    /**
     * Graph object
     */
    private GraphImpl<T> graphImpl;
    
    
    /**
     * constructor to initialize a graph object
     */
    public CourseSchedulerUtil() {
        this.graphImpl = new GraphImpl<T>();
    }
    
    /**
    * createEntity method is for parsing the input json file 
    * @return array of Entity object which stores information 
    * about a single course including its name and its prerequisites
     * @throws org.json.simple.parser.ParseException 
     * @throws IOException 
     * @throws Exception like FileNotFound, JsonParseException
    */
    @SuppressWarnings("rawtypes")
    public Entity[] createEntity(String fileName) throws org.json.simple.parser.ParseException, IOException {
        JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(fileName));
        JSONArray coursesList =  (JSONArray) jo.get("courses"); //A JSNArray of courses in the file
        Iterator arrayItr = coursesList.iterator();		// Iterator of the coursesList
        Entity[] entities = new Entity[coursesList.size()]; //Entities to return
        Map courseMap;						//A map used to find course name and prereq for each class
        int i = 0;     						//Index for the entities to store entity
        // Iterate over the courses.
        while(arrayItr.hasNext()) {
        	Entity course = new Entity<>();		//Entity to be added in entities
        	courseMap = (Map) arrayItr.next(); 
        	course.setName(courseMap.get("name"));
        	//A json array contains the prereq.
        	JSONArray prereqJo = (JSONArray) courseMap.get("prerequisites");
        	Object[] prereq = new Object[prereqJo.size()];
        	//Iterate over the prereqJo and add each prereq course to the object array
        	for (int j = 0; j < prereqJo.size(); j++) {
        		prereq[j] = prereqJo.get(j);
        	}
        	course.setPrerequisites(prereq);
        	entities[i++] = course;
        }
        return entities;
    }
    
    
    /**
     * Construct a directed graph from the created entity object 
     * @param entities which has information about a single course 
     * including its name and its prerequisites
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void constructGraph(Entity[] entities) {
    	//Remove courses from previous json file
    	if (graphImpl.getAllVertices().size() != 0) {
    		this.graphImpl = new GraphImpl<T>();
    	}
    	
    	for (Entity<T> entity : entities) {
    		// Add classes in entities to the graph
    		graphImpl.addVertex(entity.getName());
    		// For each class in the preq list, first add the class; then add the edge
    		for (T preq : entity.getPrerequisites()) {
    			graphImpl.addVertex(preq);
    			graphImpl.addEdge(entity.getName(), preq);
    		}
    	}
    }
    
    
    /**
     * Returns all the unique available courses
     * @return the sorted list of all available courses
     */
    public Set<T> getAllCourses() {
    	if (graphImpl == null) return null;
    	return graphImpl.getAllVertices();
    }
    
    
    /**
     * To check whether all given courses can be completed or not
     * @return boolean true if all given courses can be completed,
     * otherwise false
     * @throws Exception
     */
    public boolean canCoursesBeCompleted() throws Exception {
    	ArrayList<T> visited = new ArrayList<T>();
		LinkedList<T> queue = new LinkedList<>();
		for (T course : this.getAllCourses()) {
			// Adding unvisited courses to the stack
			if (!visited.contains(course)) {
				queue.addLast(course);
			}
			// If the stack contains no courses, jump to the next loop
			if (queue.isEmpty()) {
				continue;
			}
			// Use BFS(breadth first search) to traverse through all nodes and look for
			// cycles
			while (!queue.isEmpty()) {
				T curr = queue.removeFirst();   // current vertex
				visited.add(curr);              // mark as visited
				for (T successor : graphImpl.getAdjacentVerticesOf(curr)) {
					// This means the current vertex has a successor of a visited vertex
					// i.e. There is a cycle, and thus return false since you cannot take 
					// two(or more) courses that are prereq of each other
					if (visited.contains(successor)) {
						return false;
					}
					queue.addLast(successor);
				}
			}
			// Here we the visited list since after each foreach loop, we are going to the next
			// unconnected component. If there are cycles, they must be in the same component. 
			visited  = new ArrayList<T>();     
		}
    	return true;
    }
    
    
    /**
     * The order of courses in which the courses has to be taken
     * @return the list of courses in the order it has to be taken
     * @throws Exception when courses can't be completed in any order
     */
    public List<T> getSubjectOrder() throws Exception {
    	// First, see if we can take all the courses. If not, throw an exception
    	if (!canCoursesBeCompleted()) throw new Exception();
    	ArrayList<T> noPd = new ArrayList<>();   // Stores vertices with no predecessors
    	// Then, look for courses that has no predecessor
    	for (T course : getAllCourses()) {
    		boolean hasPred = false;
    		// Check if other courses has prereq of the course
    		for (T otherCourse : getAllCourses()) {
    			if (graphImpl.getAdjacentVerticesOf(otherCourse).contains(course)) {
    				hasPred = true;
    				break;
    			}
    		}
    		if (!hasPred) {
    			noPd.add(course);
    		}
		}
    	// Use stack to find the topological order
    	ArrayList<T> visited = new ArrayList<>(); // Stores the vertices visited
    	Stack<T> stack = new Stack<>();           // A stack
    	LinkedList<T> courseList = new LinkedList<>();   // The list recording order of the courses
    	// First push all courses without a prereq into the stack, such that they will be the last
    	// ones to be popped
    	for (T course: noPd) {
    		visited.add(course);
    		stack.push(course);
    	}
    	while (!stack.isEmpty()) {
    		T curCourse = stack.peek();
    		boolean allVisited = true;
    		// Check if all prereq are visited. If so, pop and add to courseList
    		for (T prereq : graphImpl.getAdjacentVerticesOf(curCourse)) {
    			if (!visited.contains(prereq)) {
    				allVisited = false;
    				stack.push(prereq);
    				visited.add(prereq);
    			}
    		}
    		// If all prereq are visited, add the course to the courseList at the front.
    		// In this way, it must have a lower index than all its requisites.
    		if (allVisited) {
    			courseList.addLast(stack.pop());
    		}
    	}
    	return courseList;
    }

        
    /**
     * The minimum course required to be taken for a given course
     * @param courseName 
     * @return the number of minimum courses needed for a given course
     */
    public int getMinimalCourseCompletion(T courseName) throws Exception {
    	// Subtracting one since the helper method counts the current course.
    	return getMinHelper(courseName) - 1;
    }
    
    /**
     * A recursive method to compute the minimum course required (including the 
     * current class!!). 
     * @param courseName
     * @return
     * @throws Exception 
     */
    private int getMinHelper(T courseName) throws Exception {
    	if(!canCoursesBeCompleted()) return 0;
    	// If the course has no prereq, return 1
    	if (graphImpl.getAdjacentVerticesOf(courseName).size() == 0) {
    		return 1;
    	// Otherwise, find the prereq course with fewest prereq.
    	} else {
    		int min = 0;
    		for (T course : graphImpl.getAdjacentVerticesOf(courseName)) {
    			min += getMinHelper(course);
    		}
    		return min + 1;
    	}
	}
}
