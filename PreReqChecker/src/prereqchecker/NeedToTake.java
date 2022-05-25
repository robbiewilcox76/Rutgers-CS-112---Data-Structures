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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
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
    String target = StdIn.readString();
    String t = StdIn.readString();
    ArrayList<String> taken = new ArrayList();
    while(!StdIn.isEmpty()) {
        taken.add(StdIn.readString());
    }
/*    for(int i=0; i<taken.size(); i++) {
        System.out.println(taken.get(i));
    }*/
    HashSet<String> MasterSet = new HashSet(); 
    for(int i=0; i<taken.size(); i++) {
        HashSet<String> Little = dfs(taken.get(i), classes);
        MasterSet.add(taken.get(i));
        for(String course: Little) {
            MasterSet.add(course);
            }
        }
    HashSet<String> TargetSet = dfs(target, classes);
    HashSet<String> NeedToTake = new HashSet();
    StdOut.setFile(args[2]);
    for(String s : TargetSet) {
        if(!MasterSet.contains(s)) {
            NeedToTake.add(s);
            StdOut.println(s);
        }
    }

/*    for(String s: TargetSet) {
        System.out.println(s);
        } */
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
