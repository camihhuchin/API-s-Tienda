package com.product;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase CRUD de categorías.
 * 
 * @author Camila Hernandez
 * @version 1.0
 * 
 */
public class Category {
    private int category_id;
    private String category;
    private String tag;
    private int status;

    private static int id = 1;
    private static List<Category> categories = new ArrayList<>();

    /**
     * Constructor de la clase.
     * 
     * @param category Nombre de la categoría.
     * @param tag      Tag de la categoría.
     * 
     *                 Status por defecto: activa.
     *                 Id consecutivo.
     */
    public Category(String category, String tag) {
        this.category_id = id;
        this.category = category;
        this.tag = tag;
        this.status = 1;
    }
    
    public Category(int category_id, String category, String tag, int status) {
        this.category_id = category_id;
        this.category = category;
        this.tag = tag;
        this.status = status;
    }

    /**
     * Método getter de la propiedad category_id.
     * 
     * @return category_id
     */
    public int getCategory_id() {
        return category_id;
    }

    /**
     * Método getter de la propiedad category.
     * 
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Método getter de la propiedad tag.
     * 
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Método getter de la propiedad status.
     * 
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Método setter de la propiedad category_id.
     * 
     * @param category_id
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Método para la representación de las categorías.
     * 
     * @return Categoría de la forma {id, nombre, tag, status}
     */
    @Override
    public String toString() {
        return "{ " + category_id +
                ", \"" + category + '\"' +
                ", \"" + tag + '\"' +
                ", " + status +
                " }";
    }

    /**
     * Método para obtener todas las categorías (Activas).
     * 
     * @return Lista de categorías activas.
     */
    public static List<Category> getCategories() {
        List<Category> list = new ArrayList<>();
        for (Category c : categories) {
            if (c.getStatus() == 1) {
                list.add(c);
            }
        }
        return list;
    }

    /**
     * Metodo para almacenar una categoría.
     * Evitar duplicados.
     * 
     * @param category
     * @return true si se ha almacenado correctamente, false en caso contrario (Si
     *         ya existe una categoría con el mismo nombre o tag).
     */
    public static boolean createCategory(Category category) {
        for (Category c : categories) {
            if (c.getCategory().equals(category.getCategory()) || c.getTag().equals(category.getTag())) {
                return false;
            }
        }
        id++;
        categories.add(category);
        return true;
    }

    /**
     * Método para desactivar una categoría.
     * 
     * @param categoryId
     * @return true si se ha desactivado correctamente, false en caso contrario (Si
     *         no existe una categoría con ese id).
     */
    public static boolean deleteCategory(int categoryId) {
        for (Category c : categories) {
            if (c.getCategory_id() == categoryId) {
                c.setStatus(0);
                return true;
            }
        }
        return false;
    }
}
