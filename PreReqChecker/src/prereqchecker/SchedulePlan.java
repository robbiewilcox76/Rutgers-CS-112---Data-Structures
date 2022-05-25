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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }

		    StdIn.setFile(args[0]);
        int numOfCourses = StdIn.readInt();
        HashMap<String, ArrayList<String>> classes = new HashMap();
        for(int i=0; i<numOfCourses; i++) {
            String course = StdIn.readString();
            classes.put(course, new ArrayList<String>());
            }

        int queries = StdIn.readInt();
        while(!StdIn.isEmpty()) {
            String class1 = StdIn.readString();
            String preReq1 = StdIn.readString();
            classes.get(class1).add(preReq1);
            }
        StdIn.setFile(args[1]);    
        String target = StdIn.readString();
        int numTaken = StdIn.readInt();
        ArrayList<String> taken = new ArrayList();
        while(!StdIn.isEmpty()) { 
            taken.add(StdIn.readString());
        }
        HashSet<String> MasterSet = new HashSet();
        for(int s=0; s<taken.size(); s++) {
            HashSet<String> Little = dfs(taken.get(s), classes);
            MasterSet.add(taken.get(s));
            for(String course : Little) {
                MasterSet.add(course);
                //System.out.println(course + " " + "Added"); //testing
            }
        }
/*        for(String s : MasterSet) {
            System.out.println("Masterset " + s);
        }
*/        HashSet<String> NeedToTake = needtoTake(dfs(target, classes), MasterSet);
        int semester = 1;
/*        for(String s: NeedToTake) {
            System.out.println(s);
        } */
        HashMap<Integer, ArrayList<String>> Schedule = new HashMap();
        boolean ready = false;
        while(!ready){
//            System.out.println("new sem");
            Schedule.put(semester, new ArrayList<String>());
            HashSet<String> Eclass = eligible(MasterSet, classes);
            for(String s: NeedToTake) { 
//                System.out.println(" ");

/*                for(String y: Eclass) {
                    System.out.println(y);
                } 
*/                if(Eclass.contains(s) && !MasterSet.contains(s)) { 
                    Schedule.get(semester).add(s);
                    MasterSet.add(s);
                }
                else {
                    for(String i : Eclass) {
                        if(!MasterSet.contains(i) && NeedToTake.contains(i)) {
                            Schedule.get(semester).add(i);
                            MasterSet.add(i);
                            }   
                        }
                    }
                }
                semester++;
                ready = true;
                for(String z: NeedToTake) {
                    if(!MasterSet.contains(z)) {
                        ready = false;
                    }
                }
            }
            StdOut.setFile(args[2]);
            StdOut.println(semester-1);
            for(int z : Schedule.keySet()) {
                StdOut.println(" ");
                ArrayList<String> s = Schedule.get(z);
                for(int i=0; i<s.size(); i++) {
                    StdOut.print(s.get(i)+ " ");
                }
            }
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
    public static HashSet needtoTake(HashSet<String> TargetSet, HashSet<String> MasterSet)  {
        HashSet<String> NeedToTake = new HashSet();
        for(String s : TargetSet) {
            if(!MasterSet.contains(s)) {
                NeedToTake.add(s);
            }
        }
        return NeedToTake;
    }
    public static HashSet<String> eligible(HashSet<String> MasterSet, HashMap<String, ArrayList<String>> classes) {
        HashSet<String> EClass = new HashSet();
        for(String s: classes.keySet()) {
            if(!MasterSet.contains(s)) {
                ArrayList<String> p = classes.get(s);
                boolean good = true;
                for(int i=0; i<p.size(); i++) {
                    if(!MasterSet.contains(p.get(i))) {
                        good = false;;
                    }
                }
                if(good == true) {
                    EClass.add(s);
                }   
            }
        }
        return EClass;
    }
}

