package gs_c482.Models;

import gs_c482.Models.Part;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for new Product
     *
     * @param id    id to be set to
     * @param name  name to be set to
     * @param price price to be set to
     * @param stock current stock to be set to
     * @param min   minimum stock to be set to
     * @param max   maximum stock to be set to
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets id to given value
     *
     * @param id ID to be set to
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets name to given value
     *
     * @param name name to be set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets price to given value
     *
     * @param price price to be set to
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets current stock to given value
     *
     * @param stock stock to be set to
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets minimum stock to given value
     *
     * @param min min to be set to
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets maximum stock to given value
     *
     * @param max max to be set to
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Returns the id of current object
     *
     * @return an integer containing the id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the current object
     *
     * @return a String containing the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the current object
     *
     * @return a double containing the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the stock of the current object
     *
     * @return an integer containing the current stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Returns the minimum stock of the current object
     *
     * @return an integer containing the minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * Returns the maximum stock of the current object
     *
     * @return an integer containing the maximum
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds part as an associated part
     *
     * @param part part to be appended to associatedParts list
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Finds and deletes part as an associated part by looping through ID, then name if not found.
     * O(2n) time. O(1) space; n = length of associatedParts
     *
     * @param selectedAssociatedPart part to be queried and deleted
     * @return a boolean whether the deletion passed or not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getId() == selectedAssociatedPart.getId()) {
                associatedParts.remove(i);
                return true;
            }
        }
        for (int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getName() == selectedAssociatedPart.getName()) {
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of associated parts
     *
     * @return an ObservableList containing all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}
