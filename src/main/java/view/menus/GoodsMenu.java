package view.menus;

public class GoodsMenu extends Menu {

    public GoodsMenu(Menu parentMenu) {
        super("goods menu", parentMenu);
        subMenus.put(1, getViewCategories());
        // filter
        subMenus.put(2, new SortMenu(this));
        subMenus.put(3, getShowProducts());
        subMenus.put(4, new ProductMenu(this));


    }

    private Menu getViewCategories() {
        return new LastMenu("view categories", this) {
            @Override
            public void show() {
                super.show();
                //TODO
            }

            @Override
            public void execute() {
                super.execute();
                //TODO
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
