package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse tester = new Warehouse();
        int Queries = StdIn.readInt();
        int day, id, stock, demand;
        String name;
        while(Queries != 0) {
            day = StdIn.readInt();
            id = StdIn.readInt();
            name = StdIn.readString();
            stock = StdIn.readInt();
            demand = StdIn.readInt();
            tester.betterAddProduct(id, name, stock, day, demand);
            //System.out.println(day + " " + id + " " + name + " " + stock + " " + id);
            Queries--;
        }
        StdOut.println(tester);
    }
}
