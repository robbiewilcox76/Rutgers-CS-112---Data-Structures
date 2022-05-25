package prereqchecker;
import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

	StdIn.setFile(args[0]);
    StdOut.setFile(args[1]);
    int numOfCourses = StdIn.readInt();
    HashMap<String, ArrayList<String>> classes = new HashMap();
    for(int i=0; i<numOfCourses; i++) {
        String course = StdIn.readString();
        classes.put(course, new ArrayList<String>());
        //System.out.println(course); //testing
        }

    int queries = StdIn.readInt();
    while(!StdIn.isEmpty()) {
        String class1 = StdIn.readString();
        String preReq1 = StdIn.readString();
        classes.get(class1).add(preReq1);
        //System.out.println( class1 + " " + preReq1); //testing
        }
            for(String courseid : classes.keySet()) {
                ArrayList<String> prereqs = classes.get(courseid);
                StdOut.print("\n" + courseid + " ");
                for(int i=0; i<prereqs.size(); i++) {
                    StdOut.print(prereqs.get(i) + " ");
                }
                //System.out.println(" "); 
            }
    }
}
