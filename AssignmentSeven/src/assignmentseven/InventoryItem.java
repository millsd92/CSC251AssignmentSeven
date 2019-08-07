package assignmentseven;

/******************************************************************************
 *                             InventoryItem.java                             *
 * Author:      David Mills                                                   *
 * Version:     1.0.1                                                         *
 * Date:        Mar 6, 2019                                                   *
 *                                                                            *
 * Description:                                                               *
 *      This is the InventoryItem class that stores a string for UPC, a       *
 *      string for description, and a double for price. It only has gets and  *
 *      sets as class methods. The class is final to prevent inheritance.     *
 *                                                                            *
 * Version History:                                                           *
 *           1.0.0:                                                           *
 *      Started the class. It is directly modeled after the program's         *
 *      requirements.                                                         *
 *                                                                            *
 *           1.0.1:                                                           *
 *      Made the class final to prevent potential overloading. It helps       *
 *      suppress warnings that NetBeans was flagging for potential overloaded *
 *      method use in the constructor. I also added the overloaded toString() *
 *      method to aid in displaying the contents of the object to the user.   *
 *                                                                            *
 * @author David Mills                                                        *
 * @version 1.0.1                                                             *
 ******************************************************************************/
public final class InventoryItem
{
    // Class-level variables.
    private String upc;
    private String description;
    private double price;
    
    //************************* CONSTRUCTORS *************************//
    
    /**
     * Default constructor.
     */
    public InventoryItem()
    {
        upc = "0-00000-00000-0";
        description = "None";
        price = 0.01f;
    }
    
    public InventoryItem(String upc, String description, double price)
    {
        setUpc(upc);
        setDescription(description);
        setPrice(price);
    }
    
    //**************************** SETTERS ***************************//
    public void setUpc(String upc)
    {
        if (!upc.isEmpty())
            this.upc = upc;
        else
            throw new EmptyArgumentException("UPC must not be empty!");
    }
    
    public void setDescription(String description)
    {
        if (!description.isEmpty())
            this.description = description;
        else
            throw new EmptyArgumentException("Description must not be empty!");
    }
    
    public void setPrice(double price)
    {
        if (!(price <= 0.0f))
            this.price = price;
        else
            throw new IllegalArgumentException("Price must be non-zero,"
                    + " non-negative decimal.");
    }
        
    //**************************** GETTERS ***************************//
        
    public String getUpc() { return upc; }
    
    public String getDescription() { return description; }
    
    public double getPrice() { return price; }
    
    //************************ CLASS METHODS *************************//
    
    @Override
    public String toString()
    {
        return "UPC:\t\t" + getUpc() + "\nDescription:\t" + getDescription()
                + "\nPrice:\t$" + String.format("%.2f", getPrice());
    }
}
