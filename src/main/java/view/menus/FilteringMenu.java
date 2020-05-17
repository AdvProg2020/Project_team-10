package view.menus;

import controller.GoodsManager;
import view.CommandProcessor;

public class FilteringMenu extends Menu{
    public FilteringMenu(Menu parentMenu) {
        super("filter menu", parentMenu);
        subMenus.put(1, filter());
        subMenus.put(2, currentFilters());
        subMenus.put(3, disableFilter());

    }


    private Menu filter() {
        return new LastMenu("filter", this) {
            @Override
            public void show() {
                CommandProcessor.showAvailableFilters();
            }

            @Override
            public void execute() {
                CommandProcessor.getKindOfFilter(this);
            }
        };
    }

    private Menu currentFilters() {
        return new LastMenu("current filters", this) {
            @Override
            public void show() {
                CommandProcessor.showCurrentFilters();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu disableFilter() {
        return new LastMenu("disable filter", this) {
            @Override
            public void show() {
                CommandProcessor.showAvailableFilters();
            }

            @Override
            public void execute() {
                CommandProcessor.disableFilter(this);
            }
        };
    }
}
