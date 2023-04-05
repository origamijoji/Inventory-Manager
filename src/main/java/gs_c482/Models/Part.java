package gs_c482.Models;

public abstract class Part {
    /**
     *
     */
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * @param id    id to be set to
     * @param name  name to be set to
     * @param price price to be set to
     * @param stock current stock to be set to
     * @param min   minimum stock to be set to
     * @param max   maximum stock to be set to
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
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
     * @param id id to be set to
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
     * Returns the id of the current object
     *
     * @return an integer containing the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the name of the current object
     *
     * @return a String containing the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the price of the current object
     *
     * @return a double containing the price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Returns the stock of the current object
     *
     * @return an integer containing the current stock
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Returns the minimum stock of the current object
     *
     * @return an integer containing the minimum
     */
    public int getMin() {
        return this.min;
    }

    /**
     * Returns the maximum stock of the current object
     *
     * @return an integer containing the maximum
     */
    public int getMax() {
        return this.max;
    }
}
