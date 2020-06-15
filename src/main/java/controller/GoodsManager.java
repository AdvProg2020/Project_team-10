package controller;

import model.Good;
import model.Off;
import model.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsManager {
    private static Good currentGood;
    private static String kindOfSort = "visit number";
    private static List<Good> filteredGoods = new ArrayList<>();
    private static List<Good> filteredGoodsInOffs = new ArrayList<>(getGoodsInOffs());
    private static Map<String, String> kindOfFilter = new HashMap<>();

       public static Map<String, String> getKindOfFilter() {
        return kindOfFilter;
    }

    public static void setCurrentGood(Good currentGood) {
        GoodsManager.currentGood = currentGood;
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
        GoodsManager.kindOfSort = kindOfSort;
    }

    public static void setFilteredGoodsInOffs(List<Good> filteredGoodsInOffs) {
        GoodsManager.filteredGoodsInOffs = filteredGoodsInOffs;
    }

    public static List<Good> getFilteredGoodsInOffs() {
        return filteredGoodsInOffs;
    }

    public static void setFilteredGoods(List<Good> filteredGoods) {
        GoodsManager.filteredGoods = filteredGoods;
    }

    public static List<Good> getFilteredGoods() {
        return filteredGoods;
    }

    public static List getGoodsInOffs() {
           List<Good> goodsInOffs = new ArrayList<>();
        for (Off off : Shop.getShop().getAllOffs()) {
            goodsInOffs.addAll(off.getGoods());
        }
        return goodsInOffs;
    }
}
