package view.Menus;

public class OrderMenu extends Menu {
    public OrderMenu(Menu parentMenu) {
        super("order menu", parentMenu);
        subMenus.put(1, getShowOrders());
        subMenus.put(2, getShowAnOrder());
        subMenus.put(3, getRate());
    }

    private Menu getShowOrders() {
        return new Menu("show orders", this) {
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

    private Menu getShowAnOrder() {
        return new Menu("show an order", this) {
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

    private Menu getRate() {
        return new Menu("rate", this) {
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
