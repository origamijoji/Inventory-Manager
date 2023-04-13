package Inventory_Manager.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a part to allParts list
     *
     * @param newPart part to be appended
     */
    public static void addPart(Part newPart) {
        System.out.println("part added");
        allParts.add(newPart);
    }

    /**
     * Adds a product to allProducts list
     *
     * @param newProduct part to be appended
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Returns the first part with the given id
     * O(n) time. O(1) space; n = length of allParts
     *
     * @param partId ID belonging to the part being queried
     * @return the first Product possessing the given ID
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Returns the first product with the given id
     * O(n) time. O(1) space; n = length of allProducts
     *
     * @param productId ID belonging to the product being queried
     * @return the first Product possessing the given ID
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Returns a list of parts containing the given string
     * O(n) time. O(n) space (+ .contains); n = length of allParts
     *
     * @param name name belonging to the part[s] being queried
     * @return an ObservableList containing all parts with similar names
     */
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> partsQuery = FXCollections.observableArrayList();
        String query = name.toLowerCase();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(query)) {
                partsQuery.add(part);
            }
        }
        return partsQuery;
    }

    /**
     * Returns a list of products containing the given string
     * O(n) time. O(n) space (+ .contains); n = length of allParts
     *
     * @param name name belonging to the product[s] being queried
     * @return an ObservableList containing all products with similar names
     */
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> productsQuery = FXCollections.observableArrayList();
        String query = name.toLowerCase();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(query)) {
                productsQuery.add(product);
            }
        }
        return productsQuery;
    }

    /**
     * Sets part at index to specified part
     *
     * @param index        id belonging to part being updated
     * @param selectedPart new part being updated to
     */
    public static void updatePart(int index, Part selectedPart) {
        for(int i = 0; i < allParts.size(); i++) {
            if(allParts.get(i).getId() == index) {
                allParts.set(i, selectedPart);
                return;
            }
        }
    }

    /**
     * Sets product at index to specified product
     *
     * @param index      id belonging to product being updated
     * @param newProduct new product being updated to
     */
    public static void updateProduct(int index, Product newProduct) {
        for(int i = 0; i < allProducts.size(); i++) {
            if(allProducts.get(i).getId() == index) {
                allProducts.set(i, newProduct);
                return;
            }
        }
    }

    /**
     * Finds and deletes a part
     * O(2n) time. O(1) space; n = length of allParts
     *
     * @param selectedPart part to be queried and deleted
     * @return a boolean whether the deletion passed or not
     */
    public static boolean deletePart(Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == selectedPart.getId()) {
                allParts.remove(i);
                return true;
            }
        }
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getName() == selectedPart.getName()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and deletes a product
     * O(2n) time. O(1) space; n = length of allProducts
     *
     * @param selectedProduct part to be queried and deleted
     * @return a boolean whether the deletion passed or not
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == selectedProduct.getId()) {
                allProducts.remove(i);
                return true;
            }
        }
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName() == selectedProduct.getName()) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all parts
     *
     * @return an ObservableList containing all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Returns all products
     *
     * @return an ObservableList containing all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
