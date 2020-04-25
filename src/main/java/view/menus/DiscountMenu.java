package view.menus;

public class DiscountMenu extends Menu {
    public DiscountMenu(Menu parentMenu) {
        super("discount menu", parentMenu);
        subMenus.put(1, getShowCode());
        subMenus.put(2, getViewDiscountCode());
        subMenus.put(3, getEditDiscountCode());
        subMenus.put(4, getRemoveDiscountCode());

    }

    private Menu getShowCode(){
        return new LastMenu("show discount code" , this) {
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

    private Menu getViewDiscountCode(){
        return new LastMenu("view discount code" , this) {
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

    private Menu getEditDiscountCode(){
        return new LastMenu("edit discount code" , this) {
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

    private Menu getRemoveDiscountCode(){
        return new LastMenu("remove discount code" , this) {
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
