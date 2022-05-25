package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse Tester = new Warehouse();
        int Queries = StdIn.readInt();
        String action, name;
        int day, id, stock, demand, amount, restockAmount;
        while(Queries != 0) {
            action = StdIn.readString();
            if (action.equals("add")) {
                day = StdIn.readInt();
                id = StdIn.readInt();
                name = StdIn.readString();
                stock = StdIn.readInt();
                demand = StdIn.readInt();
                Tester.addProduct(id, name, stock, day, demand);
            }
            else if (action.equals("restock")){
                id = StdIn.readInt();
                restockAmount = StdIn.readInt();
                Tester.restockProduct(id, restockAmount);
            }
            else if(action.equals("purchase")){
                day = StdIn.readInt();
                id = StdIn.readInt();
                amount = StdIn.readInt();
                Tester.purchaseProduct(id, day, amount);
            }
            else if(action.equals("delete")){
                id = StdIn.readInt();
                Tester.deleteProduct(id);
            }
            Queries--;
        }
        StdOut.println(Tester);
    }
}
