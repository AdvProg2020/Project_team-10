package view.Menus;

public class UserMenu extends Menu {
    private boolean isLogged;

    public UserMenu(Menu parentMenu) {
        super("user menu", parentMenu);
        subMenus.put(1, getRegisterMenu());
        subMenus.put(2, getLoginMenu());
        subMenus.put(3, getLogoutMenu());
        subMenus.put(4, getEditProfileMenu());
        subMenus.put(5, getShowProfileMenu());


    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }


    @Override
    public void show() {
        System.out.println(this.getName());
        if (!isLogged) {
            for (Integer subNumber : subMenus.keySet()) {
                if (subNumber == 1 || subNumber == 2) {
                    System.out.println(subNumber + ": " + subMenus.get(subNumber).getName());
                }
            }
            System.out.println("3: back");
        } else {
            for (Integer subNumber : subMenus.keySet()) {
                if (subNumber == 3 || subNumber == 4 || subNumber == 5) {
                    System.out.println(subNumber - 2 + ": " + subMenus.get(subNumber).getName());
                }
            }
            System.out.println("4: back");
        }
    }

    @Override
    public void execute() {
        Menu nextMenu = null;
        int selectedMenu = scanner.nextInt();
        if (isLogged) {
            if (selectedMenu == 4) {
                nextMenu = this.parentMenu;
            } else {
                nextMenu = subMenus.get(selectedMenu + 2);
            }
        } else {
            if (selectedMenu == 3) {
                nextMenu = this.parentMenu;
            } else {
                nextMenu = subMenus.get(selectedMenu);
            }
        }
        nextMenu.show();
        nextMenu.execute();
    }

    private Menu getRegisterMenu() {
        return new Menu("register", this) {
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

    private Menu getLoginMenu() {
        return new Menu("login", this) {
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

    private Menu getLogoutMenu() {
        return new Menu("logout", this) {
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

    private Menu getEditProfileMenu() {
        return new Menu("edit profile", this) {
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

    private Menu getShowProfileMenu() {
        return new Menu("show profile", this) {
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
