package view.menus;

import model.Admin;
import model.Buyer;
import model.Seller;
import model.Shop;

import static view.CommandProcessor.*;

public class UserMenu extends Menu {

    private String username;

    public UserMenu(Menu parentMenu) {
        super("user menu", parentMenu);
        //register
        subMenus.put(1, getRegisterMenu());
        subMenus.put(2, getLoginMenu());
        //login
        subMenus.put(3, getLogoutMenu());
        subMenus.put(4, getShowProfileMenu());
        subMenus.put(5, getEditProfileMenu());

        // admin
        subMenus.put(6, new ManageUsersMenu(this));
        subMenus.put(7, new ManageAllProductsMenu(this));
        subMenus.put(8, new DiscountMenu(this));
        subMenus.put(9, new ManageRequestMenu(this));
        subMenus.put(10, getCreateDiscountCode());
        // seller
        subMenus.put(11, getViewCompanyInfo());
        subMenus.put(12, getViewSalesHistory());
        subMenus.put(13, new ManageProductsMenu(this));
        subMenus.put(14, getAddProduct());
        subMenus.put(15, getRemoveProduct());
        subMenus.put(16, getShowCategory());
        subMenus.put(17, new OffsMenuForSeller(this));
        subMenus.put(18, getBalanceForSeller());
        //buyer
        subMenus.put(19, new CartMenu(this));
        //subMenus.put(20, new PurchaseMenu(this));
        subMenus.put(20, new OrderMenu(this));
        subMenus.put(21, getBalanceForBuyer());
        subMenus.put(22, getDiscountCodeShow());
    }

    private int completeShow() {
        if (Shop.getShop().getRoleByUsername(username) instanceof Buyer) {
            for (Integer subNumber : subMenus.keySet()) {
                if (subNumber > 18 && subNumber < 24) {
                    System.out.println(subNumber - 16 + ": " + subMenus.get(subNumber).getName());
                }
            }
            return 8;
        } else if (Shop.getShop().getRoleByUsername(username) instanceof Seller) {
            for (Integer subNumber : subMenus.keySet()) {
                if (subNumber > 10 && subNumber < 19) {
                    System.out.println(subNumber - 8 + ": " + subMenus.get(subNumber).getName());
                }
            }
            return 12;
        } else {
            for (Integer subNumber : subMenus.keySet()) {
                if (subNumber > 5 && subNumber < 11) {
                    System.out.println(subNumber - 3 + ": " + subMenus.get(subNumber).getName());
                }
            }
            return 9;
        }
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
                if (subNumber == 4 || subNumber == 5) {
                    System.out.println(subNumber - 3 + ": " + subMenus.get(subNumber).getName());
                }
            }
            int backNumber = completeShow();
            System.out.println(backNumber - 1 + ": logout");
            System.out.println(backNumber + ": back");
        }
    }

    @Override
    public void execute() {
        Menu nextMenu;
        int selectedMenu = scanner.nextInt();
        if (isLogged) {
            if (selectedMenu == 1) {
                nextMenu = subMenus.get(4);
            } else if (selectedMenu == 2) {
                nextMenu = subMenus.get(5);
            } else if ((selectedMenu == 12 && Shop.getShop().getRoleByUsername(username) instanceof Seller) ||
                    (selectedMenu == 8 && Shop.getShop().getRoleByUsername(username) instanceof Buyer) ||
                    (selectedMenu == 9 && Shop.getShop().getRoleByUsername(username) instanceof Admin)) {
                nextMenu = this.parentMenu;
            } else if ((selectedMenu == 11 && Shop.getShop().getRoleByUsername(username) instanceof Seller) ||
                    (selectedMenu == 7 && Shop.getShop().getRoleByUsername(username) instanceof Buyer) ||
                    (selectedMenu == 8 && Shop.getShop().getRoleByUsername(username) instanceof Admin)) {
                getLogoutMenu().show();
                getLogoutMenu().execute();
                nextMenu = this;
            } else {
                if (Shop.getShop().getRoleByUsername(username) instanceof Buyer) {
                    if (selectedMenu > 8 || selectedMenu < 1) {
                        System.out.println("you must choose one of following options");
                        nextMenu = this;
                    } else {
                        nextMenu = subMenus.get(selectedMenu + 16);
                    }
                } else if (Shop.getShop().getRoleByUsername(username) instanceof Seller) {
                    if (selectedMenu > 12 || selectedMenu < 1) {
                        System.out.println("you must choose one of following options");
                        nextMenu = this;
                    } else {
                        nextMenu = subMenus.get(selectedMenu + 8);
                    }
                } else {
                    if (selectedMenu > 9 || selectedMenu < 1) {
                        System.out.println("you must choose one of following options");
                        nextMenu = this;
                    } else {
                        nextMenu = subMenus.get(selectedMenu + 3);
                    }
                }
            }
        } else {
            if (selectedMenu > 3 || selectedMenu < 1) {
                System.out.println("you must choose one of following options");
                nextMenu = this;
            } else if (selectedMenu == 3) {
                nextMenu = this.parentMenu;
            } else if (selectedMenu == 2) {
                getLoginMenu().show();
                getLoginMenu().execute();
                nextMenu = this;
            } else {
                getRegisterMenu().show();
                getRegisterMenu().execute();
                nextMenu = this;
            }
        }
        nextMenu.show();
        nextMenu.execute();
    }


    private Menu getEditProfileMenu() {
        return new LastMenu("edit profile", this) {
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

    private Menu getShowProfileMenu() {
        return new LastMenu("show profile", this) {
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

    private Menu getCreateDiscountCode() {
        return new LastMenu("create discount code", this) {
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

    private Menu getViewCompanyInfo() {
        return new LastMenu("view company information", this) {
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

    private Menu getViewSalesHistory() {
        return new LastMenu("view sales history", this) {
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

    private Menu getAddProduct() {
        return new LastMenu("add product", this) {
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

    private Menu getRemoveProduct() {
        return new LastMenu("remove product", this) {
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

    private Menu getShowCategory() {
        return new LastMenu("show category", this) {
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

    private Menu getBalanceForSeller() {
        return new LastMenu("view balance", this) {
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

    private Menu getBalanceForBuyer() {
        return new LastMenu("view balance", this) {
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

    private Menu getDiscountCodeShow() {
        return new LastMenu("view discount codes", this) {
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
