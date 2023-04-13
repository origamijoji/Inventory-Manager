package Inventory_Manager.Models;

public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for new InHouse part
     *
     * @param id        id to be set to
     * @param name      name to be set to
     * @param price     price to be set to
     * @param stock     current stock to be set to
     * @param min       maximum stock to be set to
     * @param max       minimum stock to be set to
     * @param machineId machineId to be set to
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets machineId to given value
     *
     * @param machineId machineId to be set to
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Returns machineId of current object
     *
     * @return an integer containing the machineId
     */
    public int getMachineId() {
        return this.machineId;
    }
}
