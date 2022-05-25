package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse Tester = new Warehouse();
        int Queries = StdIn.readInt();
        String AddorDelete, name;
        int day, id, stock, demand;
        while(Queries != 0) {
            AddorDelete = StdIn.readString();
            if (AddorDelete.equals("add")) {
                day = StdIn.readInt();
                id = StdIn.readInt();
                name = StdIn.readString();
                stock = StdIn.readInt();
                demand = StdIn.readInt();
                Tester.addProduct(id, name, stock, day, demand);
            }
            else {
                id = StdIn.readInt();
                Tester.deleteProduct(id);
            }
            Queries--;
        }
        StdOut.println(Tester);
    }
}
