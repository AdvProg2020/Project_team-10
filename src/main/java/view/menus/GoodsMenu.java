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
        subMenus.put(5, new ProductMenu(this));
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

                //TODO
            }

            @Override
            public void execute() {
                //TODO
            }
        };
    }


}
