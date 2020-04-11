package view.Menus;

public class SortMenu extends Menu {
    public SortMenu(Menu parentMenu) {
        super("sort menu", parentMenu);
        subMenus.put(1, getShowAvailableShort());
        subMenus.put(2, getSort());
        subMenus.put(3, getShowCurrentSort());
        subMenus.put(4, getDisableSort());

    }

    private Menu getShowAvailableShort() {
        return new Menu("show available sort", this) {
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

    private Menu getSort() {
        return new Menu("sort", this) {
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

    private Menu getShowCurrentSort() {
        return new Menu("current sort", this) {
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

    private Menu getDisableSort() {
        return new Menu("disable sort", this) {
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
