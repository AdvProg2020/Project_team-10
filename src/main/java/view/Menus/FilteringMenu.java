package view.Menus;

public class FilteringMenu extends Menu{
    public FilteringMenu(Menu parentMenu) {
        super("filter menu", parentMenu);
        subMenus.put(1, showAvailableFilters());
        subMenus.put(2, filter());
        subMenus.put(3, currentFilters());
        subMenus.put(4, disableFilter());

    }

    private Menu showAvailableFilters() {
        return new LastMenu("show available filters", this) {
            @Override
            public void show() {
                super.show();
                //TODO
            }

            @Override
            public void execute() {
                super.execute();
                //TODO
            }
        };
    }

    private Menu filter() {
        return new LastMenu("filter", this) {
            @Override
            public void show() {
                super.show();
                //TODO
            }

            @Override
            public void execute() {
                super.execute();
                //TODO
            }
        };
    }

    private Menu currentFilters() {
        return new LastMenu("current filters", this) {
            @Override
            public void show() {
                super.show();
                //TODO
            }

            @Override
            public void execute() {
                super.execute();
                //TODO
            }
        };
    }

    private Menu disableFilter() {
        return new LastMenu("disable filter", this) {
            @Override
            public void show() {
                super.show();
                //TODO
            }

            @Override
            public void execute() {
                super.execute();
                //TODO
            }
        };
    }
}
