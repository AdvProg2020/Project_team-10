package view.Menus;

public class ManageRequestMenu extends Menu {
    public ManageRequestMenu(Menu parentMenu) {
        super("manage requests", parentMenu);
        subMenus.put(1, getDetail());
        subMenus.put(2, getAccept());
        subMenus.put(3, getDecline());
    }

    private Menu getDetail() {
        return new Menu("detail", this) {
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

    private Menu getAccept() {
        return new Menu("accept", this) {
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

    private Menu getDecline() {
        return new Menu("decline", this) {
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
