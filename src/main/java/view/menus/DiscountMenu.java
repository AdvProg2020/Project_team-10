package view.menus;

import view.CommandProcessor;

public class DiscountMenu extends Menu {
    public DiscountMenu(Menu parentMenu) {
        super("discount menu", parentMenu);
        subMenus.put(1, getShowCode());
        subMenus.put(2, getViewDiscountCode());
        subMenus.put(3, getEditDiscountCode());
        subMenus.put(4, getRemoveDiscountCode());

    }

    private Menu getShowCode() {
        return new LastMenu("show all discount code", this) {
            @Override
            public void show() {
                CommandProcessor.showAllDiscountCodesForAdmin();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getViewDiscountCode() {
        return new LastMenu("show discount code[code]", this) {
            @Override
            public void show() {
                CommandProcessor.showDiscountForAdmin();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getEditDiscountCode() {
        return new LastMenu("edit discount code", this) {
            @Override
            public void show() {
                CommandProcessor.processEditDiscountCode();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getRemoveDiscountCode() {
        return new LastMenu("remove discount code", this) {
            @Override
            public void show() {
                CommandProcessor.processRemoveDiscountCode();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }
}
