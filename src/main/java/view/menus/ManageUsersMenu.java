package view.menus;

import controller.AccountManager;
import model.Account;
import model.Shop;
import view.CommandProcessor;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super("manage users", parentMenu);
        subMenus.put(1 ,getView());
        subMenus.put(2 , getDeleteUser());
        subMenus.put(3 , getCreateManager());
    }

    private Menu getView(){
        return new LastMenu("view" , this) {
            @Override
            public void show() {
                for (Account allAccount : Shop.getShop().getAllAccounts()) {
                    if (allAccount != AccountManager.getOnlineAccount()){
                        System.out.println(allAccount.getUsername());
                    }
                }
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getDeleteUser(){
        return new LastMenu("delete user" , this) {
            @Override
            public void show() {
                CommandProcessor.processDeleteAccountByAdmin();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getCreateManager(){
        return new LastMenu("create manager" , this) {
            @Override
            public void show() {
                CommandProcessor.processRegister(false);
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

}
