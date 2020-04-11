package view.Menus;

public class ManageAllProductsMenu extends Menu {
    public ManageAllProductsMenu(Menu parentMenu) {
        super("manage all products", parentMenu);
        subMenus.put(1 , getRemove());
    }

    private Menu getRemove(){
        return new Menu("remove" , this) {
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
