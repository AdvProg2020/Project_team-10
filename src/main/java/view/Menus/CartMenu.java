package view.Menus;

public class CartMenu extends Menu {
    public CartMenu(Menu parentMenu) {
        super("cart menu", parentMenu);
        subMenus.put(1, getShowProducts());
        subMenus.put(2, getViewProduct());
        subMenus.put(3, getIncrease());
        subMenus.put(4, getDecrease());
        subMenus.put(5, getShowTotalPrice());
        subMenus.put(6, purchase());
        // purchase
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

    private Menu getViewProduct() {
        return new Menu("view a product", this) {
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

    private Menu getIncrease() {
        return new Menu("increase number of a product", this) {
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

    private Menu getDecrease() {
        return new Menu("view a product", this) {
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

    private Menu getShowTotalPrice() {
        return new Menu("show total price", this) {
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

    private Menu purchase() {
        return new Menu("purchase", this) {
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
