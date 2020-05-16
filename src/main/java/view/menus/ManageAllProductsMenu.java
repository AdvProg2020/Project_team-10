package view.menus;

import view.CommandProcessor;

import static view.CommandProcessor.processRemoveProduct;

public class ManageAllProductsMenu extends Menu {
    public ManageAllProductsMenu(Menu parentMenu) {
        super("manage all products", parentMenu);
        subMenus.put(1 , getRemove());
    }

    private Menu getRemove(){
        return new LastMenu("remove" , this) {
            @Override
            public void show() {
                processRemoveProduct();
                super.show();
            }

            @Override
            public void execute() {
               super.execute();
            }
        };
    }
}
