package Inventory_Manager.Models;

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the company name to given value
     *
     * @param companyName companyName to be set to
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns the company name of the current object
     *
     * @return a string containing the company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
