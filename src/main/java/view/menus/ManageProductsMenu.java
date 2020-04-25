package view.menus;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super("manage product", parentMenu);
        subMenus.put(1, getShowAllProducts());
        subMenus.put(2, getViewProduct());
        subMenus.put(3, getViewBuyers());
        subMenus.put(4, getEdit());

    }

    private Menu getShowAllProducts(){
        return new LastMenu("show all products" , this) {
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

    private Menu getViewProduct(){
        return new LastMenu("view a product" , this) {
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

    private Menu getViewBuyers(){
        return new LastMenu("view buyers" , this) {
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

    private Menu getEdit(){
        return new LastMenu("edit" , this) {
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
