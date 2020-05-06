package view.menus;

import view.CommandProcessor;

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
                CommandProcessor.showAllOrders();
                super.show();
            }
            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getShowAnOrder() {
        return new LastMenu("show an order", this) {
            @Override
            public void show() {
                int id;
                System.out.println("please enter order id: \n");
                id = Menu.scanner.nextInt();
                System.out.println();
                CommandProcessor.showOrder(id);
                super.show();
            }
            @Override
            public void execute() {
                super.execute();
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
