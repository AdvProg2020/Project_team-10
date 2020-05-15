package view.menus;

import controller.GoodsManager;
import view.CommandProcessor;

import static view.CommandProcessor.showAvailableSort;


public class SortMenu extends Menu {
    public SortMenu(Menu parentMenu) {
        super("sort menu", parentMenu);
        subMenus.put(1, getSort());
        subMenus.put(2, getShowCurrentSort());
        subMenus.put(3, getDisableSort());

    }


    private Menu getSort() {
        return new LastMenu("sort", this) {
            @Override
            public void show() {
                showAvailableSort();
            }

            @Override
            public void execute() {
                CommandProcessor.getKindOfSort(this);
            }
        };
    }

    private Menu getShowCurrentSort() {
        return new LastMenu("current sort", this) {
            @Override
            public void show() {
                System.out.println("current sort is : " + GoodsManager.getKindOfSort());
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getDisableSort() {
        return new LastMenu("disable sort", this) {
            @Override
            public void show() {
                GoodsManager.setKindOfSort("visit number");
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }
}
