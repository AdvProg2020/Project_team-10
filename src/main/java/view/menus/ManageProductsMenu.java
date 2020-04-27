package view.menus;

import controller.SellerManager;
import static view.CommandProcessor.*;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super("manage product", parentMenu);
        subMenus.put(1, getShowAllProducts());
        subMenus.put(2, getShowProductById());
        subMenus.put(3, getShowBuyers());
        subMenus.put(4, getEdit());

    }

    private Menu getShowAllProducts(){
        return new LastMenu("show all products" , this) {
            @Override
            public void show() {
                SellerManager.showHisProducts();
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

    private Menu getShowProductById(){
        return new LastMenu("view a product" , this) {
            @Override
            public void show() {
                processShowProductByIdForSeller();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getShowBuyers(){
        return new LastMenu("view buyers" , this) {
            @Override
            public void show() {
                processShowBuyersForSeller();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
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
