package view.menus;

import static view.CommandProcessor.*;

public class ManageRequestMenu extends Menu {
    public ManageRequestMenu(Menu parentMenu) {
        super("manage requests", parentMenu);
        subMenus.put(1, getShowAllRequests());
        subMenus.put(2, getDetail());
        subMenus.put(3, getAccept());
        subMenus.put(4, getDecline());
    }

    private Menu getShowAllRequests() {
        return new LastMenu("show all requests", this) {
            @Override
            public void show() {
                processShowAllRequests();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getDetail() {
        return new LastMenu("detail a request", this) {
            @Override
            public void show() {
                processShowRequestDetail();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getAccept() {
        return new LastMenu("accept", this) {
            @Override
            public void show() {
                processAcceptRequest();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getDecline() {
        return new LastMenu("decline", this) {
            @Override
            public void show() {
                processDeclineRequest();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }
}
