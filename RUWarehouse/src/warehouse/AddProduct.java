package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse x = new Warehouse();
        int NumofProducts = StdIn.readInt();
        int day=0;
        int lastdig=0;
        int stock=0;
        int demand=0;
        int id, idlast;
        String name = null;
        while(!StdIn.isEmpty()) {
            day = StdIn.readInt();
            id = StdIn.readInt();
            //idlast=id;
            //while(idlast>10) {
                //idlast = idlast%10;
            //}
            name = StdIn.readString();
            stock = StdIn.readInt();
            demand = StdIn.readInt();
            //System.out.println(day +" "+ id +" " +name +" "+ stock + " "+demand);
            x.addProduct(id, name, stock, day, demand);
        }
        StdOut.println(x);
    }
}
