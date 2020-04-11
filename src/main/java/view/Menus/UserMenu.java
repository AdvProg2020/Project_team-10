package view.Menus;

import model.Buyer;
import model.Seller;

public class UserMenu extends Menu {
    private boolean isLogged;
    private String username;

    public UserMenu(Menu parentMenu) {
        super("user menu", parentMenu);
        subMenus.put(1, getRegisterMenu());
        subMenus.put(2, getLoginMenu());
        subMenus.put(3, getLogoutMenu());
        subMenus.put(4, getEditProfileMenu());
        subMenus.put(5, getShowProfileMenu());
        // admin
        subMenus.put(6 , new ManageUsersMenu(this));
        subMenus.put(7 , new ManageAllProductsMenu(this));
        subMenus.put(8 , new DiscountMenu(this));
        subMenus.put(9 , new ManageRequestMenu(this));
        subMenus.put(10 , getCreateDiscountCode());
        //-------------------------
        // seller
        // get view company
        // view history
        subMenus.put(13 , new ManageProductsMenu(this));
        //add product
        //remove
        //show category
        subMenus.put(17 , new OffsMenuForSeller(this));
        //balance
        //-----------
        //buyer
        subMenus.put(19 , new CartMenu(this));
        subMenus.put(20 , new PurchaseMenu(this));
        subMenus.put(21 , new OrderMenu(this));
        //balance
        //discount code show


    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

//    private int completeShow() {
//        if (Controller.Manager.getRoleByUsername(username) instanceof Buyer) {
//
//
//        } else if (Controller.Manager.getRoleByUsername(username) instanceof Seller) {
//
//        } else {
//            System.out.println();
//        }
//    }

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

    private Menu getCreateDiscountCode(){
        return new Menu("create discount code" , this) {
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