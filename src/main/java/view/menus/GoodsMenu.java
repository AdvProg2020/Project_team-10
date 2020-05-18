package view.menus;

import view.CommandProcessor;

import static view.CommandProcessor.showAllCategories;

public class GoodsMenu extends Menu {

    public GoodsMenu(Menu parentMenu) {
        super("goods menu", parentMenu);
        subMenus.put(1, getViewCategories());
        subMenus.put(2, new SortMenu(this));
        subMenus.put(3, new FilteringMenu(this));
        subMenus.put(4, getShowProducts());
        subMenus.put(5, getProductMenu());
    }

    private Menu getViewCategories() {
        return new LastMenu("view categories", this) {
            @Override
            public void show() {
                showAllCategories();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getShowProducts() {
        return new LastMenu("show products", this) {
            @Override
            public void show() {
                CommandProcessor.showProductsInGoodsMenu();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getProductMenu() {
        return new LastMenu("product menu", this) {
            @Override
            public void show() {
                CommandProcessor.enterProductMenu(this.parentMenu);
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }



}
