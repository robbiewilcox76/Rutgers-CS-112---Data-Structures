package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        int idlast = id;
        while(idlast>=10) {
            idlast%=10;
        }
        Product prod = new Product(id, name, stock, day, demand);
        sectors[idlast].add(prod);
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        int idlast = id;
        while(idlast>=10) {
            idlast%=10;
        }
        int size = sectors[idlast].getSize();
        //System.out.print("Sizes: " + size + " "); //testing
        while(size != 0) {
            sectors[idlast].swim(size);
            size--;
        }
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
        int idlast = id;
        while(idlast>=10) {
            idlast%=10;
        }
        if(sectors[idlast].getSize() == 5) {
            sectors[idlast].swap(1, 5);
            sectors[idlast].deleteLast();
            sectors[idlast].sink(1);
       }
       return;
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        int idlast = id;
        while(idlast>=10) {
            idlast%=10;
        }
        int size = sectors[idlast].getSize();
        while (size > 0) {
            if(sectors[idlast].get(size).getId() == id) {
                sectors[idlast].get(size).updateStock(amount);
                return;
            }
                size--;
            }
        return;
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        int idlast = id;
        while(idlast>=10) {
            idlast%=10;
        }
        int size = sectors[idlast].getSize();
        int sizelast = size;
        while(size > 0) {
            if(sectors[idlast].get(size).getId() == id) {
                sectors[idlast].swap(sizelast, size);
                sectors[idlast].deleteLast();
                sectors[idlast].sink(size);
                return;
            }
            size--;
        }
        return;
    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        int idlast = id;
        while(idlast>=10) {
            idlast%=10;
        }
        int size = sectors[idlast].getSize();
        while(size > 0) {
            if(sectors[idlast].get(size).getId() == id) {
                if(sectors[idlast].get(size).getStock() > amount) {
                    sectors[idlast].get(size).setLastPurchaseDay(day);
                    sectors[idlast].get(size).updateStock(-amount);
                    sectors[idlast].get(size).updateDemand(amount);
                    sectors[idlast].sink(size);
                return;
                }
            }
            size--;
        }
        return;
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        int idlast = id;
        int originalID = id;
        while(idlast>=10) {
            idlast%=10;
        }
        int copy = idlast;
        int size = sectors[idlast].getSize();
        if(size < 5) {
            addProduct(id, name, stock, day, demand);
        }
        else {
            if(idlast == 9) {
                idlast = 0;
                id-=9;
            }
            else {
                idlast++;
                id++;
            } 
            while(idlast != copy) {
                if(sectors[idlast].getSize() < 5) {
                    addProduct(id, name, stock, day, demand);
                    return;
                }
                else{             
                    if(idlast == 9) {
                        idlast = 0;
                        id-=9;
                    }
                    else {
                        idlast++;
                        id++;
                    } 
                }   
            }
            addProduct(originalID, name, stock, day, demand);
            return;
        }
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
