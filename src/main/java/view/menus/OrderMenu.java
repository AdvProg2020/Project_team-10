package view.menus;

public class OrderMenu extends Menu {
    public OrderMenu(Menu parentMenu) {
        super("order menu", parentMenu);
        subMenus.put(1, getShowOrders());
        subMenus.put(2, getShowAnOrder());
        subMenus.put(3, getRate());
    }

    private Menu getShowOrders() {
        return new LastMenu("show orders", this) {
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
        return new LastMenu("show an order", this) {
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
        return new LastMenu("rate", this) {
            @Override
            public void show() {
                super.show();
                //TODO
            }
            @Override
            public void execute() {
                //TODO
            }
        };
    }



}
