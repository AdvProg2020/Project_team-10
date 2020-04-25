package view.menus;

public class SortMenu extends Menu {
    public SortMenu(Menu parentMenu) {
        super("sort menu", parentMenu);
        subMenus.put(1, getShowAvailableShort());
        subMenus.put(2, getSort());
        subMenus.put(3, getShowCurrentSort());
        subMenus.put(4, getDisableSort());

    }

    private Menu getShowAvailableShort() {
        return new LastMenu("show available sort", this) {
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

    private Menu getSort() {
        return new LastMenu("sort", this) {
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

    private Menu getShowCurrentSort() {
        return new LastMenu("current sort", this) {
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

    private Menu getDisableSort() {
        return new LastMenu("disable sort", this) {
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
