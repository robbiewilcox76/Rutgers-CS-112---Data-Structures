package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse Tester = new Warehouse();
        int Queries = StdIn.readInt();
        String AddorRestock, name;
        int day, id, stock, demand, restockAmount;
        while(Queries != 0) {
            AddorRestock = StdIn.readString();
            if (AddorRestock.equals("add")) {
                day = StdIn.readInt();
                id = StdIn.readInt();
                name = StdIn.readString();
                stock = StdIn.readInt();
                demand = StdIn.readInt();
                //System.out.println("Into addProduct"); //testing
                Tester.addProduct(id, name, stock, day, demand);
                //System.out.println("out of addProduct"); //testing
            }
            else {
                id = StdIn.readInt();
                restockAmount = StdIn.readInt();
                //System.out.println("into restock " + id + " " + restockAmount); //testing
                Tester.restockProduct(id, restockAmount);
                //System.out.println("out of restock"); //testing
            }
            Queries--;
        }
        StdOut.println(Tester);
    }
}
