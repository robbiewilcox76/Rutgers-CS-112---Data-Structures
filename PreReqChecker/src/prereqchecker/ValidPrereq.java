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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
	    StdIn.setFile(args[0]);
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
            }

        StdIn.setFile(args[1]);
        String course1 = StdIn.readString();
        String course2 = StdIn.readString();
        HashSet<String> preReqList = dfs(course2, classes);

        StdOut.setFile(args[2]);
        if(preReqList.contains(course1)) {StdOut.println("NO");}
            else {StdOut.println("YES");}

        }

    public static HashSet dfs(String course, HashMap<String, ArrayList<String>> classes) {
        HashSet<String> preReqList = new HashSet();
        for(int i=0; i< classes.get(course).size(); i++) {
            preReqList.add(classes.get(course).get(i));
            dfsRecursive(classes.get(course).get(i), preReqList, classes);
        }
        return preReqList;
    }
    public static void dfsRecursive(String course, HashSet<String> preReqList, HashMap<String, ArrayList<String>> classes) {
        HashMap<String, Boolean> check = new HashMap();
        ArrayList<String> x = classes.get(course);
        for(int i=0; i<x.size(); i++) {
            check.put(x.get(i), false);
        }
        check.put(course, true);
        for(int i=0; i<x.size(); i++) {
            if(check.get(x.get(i)) == false) {
                preReqList.add(classes.get(course).get(i));
                dfsRecursive(x.get(i), preReqList, classes);
            }
        }
    }
}
