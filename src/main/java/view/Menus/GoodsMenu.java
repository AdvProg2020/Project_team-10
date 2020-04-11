package view.Menus;

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
        return new Menu("view categories", this) {
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

    private Menu getShowProducts() {
        return new Menu("show products", this) {
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
