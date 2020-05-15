package controller;

import model.Good;

import java.util.List;

public class GoodsManager {
    private static Good currentGood;
    private static String kindOfSort;

    public static void showAvailableFilter(){

    }

    public static void filter(List<String> filterInfo){

    }

    public static void showCurrentFilters(){

    }

    public static void disableFilter(String filter){

    }


    public static void sort(String sortType){

    }

    public static void currentSort(){

    }

    public static void disableSort(String sort){

    }

    public static void showProducts(){}

    public static void showProductById(String id){}

    public static void addToCart(){}

    public static void showAttributes(){}

    public static void compareProduct(String id){}

    public static void showComments(){}

    public static void addComment(){}

    public static void showOffs(){}

    public static String getKindOfSort() {
        return kindOfSort;
    }

    public static Good getCurrentGood() {
        return currentGood;
    }

    public static void setKindOfSort(String kindOfSort) {
        kindOfSort = kindOfSort;
    }
}
