package view.menus;

import view.CommandProcessor;

import static view.CommandProcessor.processRemoveProductById;

public class ManageAllProductsMenu extends Menu {
    public ManageAllProductsMenu(Menu parentMenu) {
        super("manage all products", parentMenu);
        subMenus.put(1 , getRemove());
    }

    private Menu getRemove(){
        return new LastMenu("remove" , this) {
            @Override
            public void show() {
                processRemoveProductById();
                super.show();
            }

            @Override
            public void execute() {
               super.execute();
            }
        };
    }
}
