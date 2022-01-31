package ua.bondar.course.bondarsite.model;

import java.util.ArrayList;
import java.util.List;

public enum CategoryProduct {
    Phone("Phone"),
    Notepad("Notepad"),
    Tablet("Tablet"),
    Desktop("Desktop"),
    Monitor("Monitor");

    CategoryProduct(String category) {
    }


    public static CategoryProduct getCategoryProduct(String category){
        switch (category){
            case "Phone": return Phone;
            case "Notepad": return Notepad;
            case "Tablet": return Tablet;
            case "Desktop": return Desktop;
            case "Monitor": return Monitor;
            default: return null;
        }
    }

    public String getStringCategory(){
        switch (this){
            case Phone: return "Phone";
            case Tablet: return "Tablet";
            case Desktop: return "Desktop";
            case Notepad: return "Notepad";
            case Monitor: return "Monitor";
            default: return null;
        }
    }


    public static String getStringCategory(CategoryProduct categoryProduct){
        switch (categoryProduct){
            case Phone: return "Phone";
            case Tablet: return "Tablet";
            case Desktop: return "Desktop";
            case Notepad: return "Notepad";
            case Monitor: return "Monitor";
            default: return null;
        }
    }

    public static List<CategoryProduct> getAllCategoryProduct(){
        List<CategoryProduct> list = new ArrayList<>();
        list.add(Phone);
        list.add(Notepad);
        list.add(Tablet);
        list.add(Desktop);
        list.add(Monitor);
        return list;
    }

    public static List<String> getAllCategoryProductInString(){
        List<CategoryProduct> list = getAllCategoryProduct();
        List<String> stringList = new ArrayList<>();
        for(CategoryProduct categ  : list){
            stringList.add(getStringCategory(categ));
        }
        return stringList;
    }
}

