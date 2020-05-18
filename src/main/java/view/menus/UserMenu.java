package view.menus;

import controller.AccountManager;
import model.Admin;
import model.Buyer;
import model.Seller;
import model.Shop;
import view.CommandProcessor;

import static view.CommandProcessor.*;

public class UserMenu extends Menu {

    private static String username;

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
        subMenus.put(11, new CategoryMenu(this));
        // seller
        subMenus.put(12, getViewCompanyInfo());
        subMenus.put(13, getViewSalesHistory());
        subMenus.put(14, new ManageProductsMenu(this));
        subMenus.put(15, getAddProduct());
        subMenus.put(16, getRemoveProduct());
        subMenus.put(17, getShowCategory());
        subMenus.put(18, new OffsMenuForSeller(this));
        subMenus.put(19, getBalanceForSeller());
        //buyer
        subMenus.put(20, new CartMenu(this));
        subMenus.put(21, new OrderMenu(this));
        subMenus.put(22, getBalanceForBuyer());
        subMenus.put(23, getShowDiscountCodesForBuyer());
        subMenus.put(24, getShowCredit());
    }

    public static void setUsername(String username) {
        UserMenu.username = username;
    }

    private int completeShow() {
        if (Shop.getShop().getAccountByUsername(username) instanceof Buyer) {
            for (Integer subNumber : subMenus.keySet()) {
                if (subNumber > 19 && subNumber < 25) {
                    System.out.println(subNumber - 17 + ": " + subMenus.get(subNumber).getName());
                }
            }
            return 9;
        } else if (Shop.getShop().getAccountByUsername(username) instanceof Seller) {
            for (Integer subNumber : subMenus.keySet()) {
                if (subNumber > 11 && subNumber < 20) {
                    System.out.println(subNumber - 9 + ": " + subMenus.get(subNumber).getName());
                }
            }
            return 12;
        } else {
            for (Integer subNumber : subMenus.keySet()) {
                if (subNumber > 5 && subNumber < 12) {
                    System.out.println(subNumber - 3 + ": " + subMenus.get(subNumber).getName());
                }
            }
            return 10;
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
        Menu.scanner.nextLine();
        if (isLogged) {
            if (selectedMenu == 1) {
                nextMenu = subMenus.get(4);
            } else if (selectedMenu == 2) {
                nextMenu = subMenus.get(5);
            } else if ((selectedMenu == 12 && Shop.getShop().getAccountByUsername(username) instanceof Seller) ||
                    (selectedMenu == 9 && Shop.getShop().getAccountByUsername(username) instanceof Buyer) ||
                    (selectedMenu == 10 && Shop.getShop().getAccountByUsername(username) instanceof Admin)) {
                nextMenu = this.parentMenu;
            } else if ((selectedMenu == 11 && Shop.getShop().getAccountByUsername(username) instanceof Seller) ||
                    (selectedMenu == 8 && Shop.getShop().getAccountByUsername(username) instanceof Buyer) ||
                    (selectedMenu == 9 && Shop.getShop().getAccountByUsername(username) instanceof Admin)) {
                getLogoutMenu().show();
                getLogoutMenu().execute();
                nextMenu = this;
            } else {
                if (Shop.getShop().getAccountByUsername(username) instanceof Buyer) {
                    if (selectedMenu > 9 || selectedMenu < 1) {
                        System.out.println("you must choose one of following options");
                        nextMenu = this;
                    } else {
                        nextMenu = subMenus.get(selectedMenu + 17);
                    }
                } else if (Shop.getShop().getAccountByUsername(username) instanceof Seller) {
                    if (selectedMenu > 12 || selectedMenu < 1) {
                        System.out.println("you must choose one of following options");
                        nextMenu = this;
                    } else {
                        nextMenu = subMenus.get(selectedMenu + 9);
                    }
                } else {
                    if (selectedMenu > 10 || selectedMenu < 1) {
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
                CommandProcessor.processEditProfile();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getShowProfileMenu() {
        return new LastMenu("show profile", this) {
            @Override
            public void show() {
                CommandProcessor.showPersonalInfo();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getCreateDiscountCode() {
        return new LastMenu("create discount code", this) {
            @Override
            public void show() {
                CommandProcessor.processAddDiscountCode(null, false);
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
                showCompanyInfo();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getViewSalesHistory() {
        return new LastMenu("view sales history", this) {
            @Override
            public void show() {
                showSalesHistory();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getAddProduct() {
        return new LastMenu("add product", this) {
            @Override
            public void show() {
                processAddOrEditProduct(true);
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getRemoveProduct() {
        return new LastMenu("remove product", this) {
            @Override
            public void show() {
                processRemoveProductById();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getShowCategory() {
        return new LastMenu("show category", this) {
            @Override
            public void show() {
                showAllCategories();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getBalanceForSeller() {
        return new LastMenu("view balance", this) {
            @Override
            public void show() {
                viewBalance();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
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

    private Menu getShowDiscountCodesForBuyer() {
        return new LastMenu("view discount codes", this) {
            @Override
            public void show() {
                showAllDiscountsCodeForBuyer();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getShowCredit() {
        return new LastMenu("credit", this) {
            @Override
            public void show() {
                System.out.println("credit: " + AccountManager.getOnlineAccount().getCredit());
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

}
