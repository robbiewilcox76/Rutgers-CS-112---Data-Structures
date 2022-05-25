package warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse Tester = new Warehouse();
        int Queries = StdIn.readInt();
        String AddorPurchase, name;
        int day, id, stock, demand, amount;
        while(Queries != 0) {
            AddorPurchase = StdIn.readString();
            if (AddorPurchase.equals("add")) {
                day = StdIn.readInt();
                id = StdIn.readInt();
                name = StdIn.readString();
                stock = StdIn.readInt();
                demand = StdIn.readInt();
                Tester.addProduct(id, name, stock, day, demand);
            }
            else {
                day = StdIn.readInt();
                id = StdIn.readInt();
                amount = StdIn.readInt();
                Tester.purchaseProduct(id, day, amount);
            }
            Queries--;
        }
        StdOut.println(Tester);
    }
}
