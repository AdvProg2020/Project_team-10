package view.menus;

import controller.AccountManager;
import controller.BuyerManager;
import model.Account;
import model.Admin;
import model.Buyer;
import view.CommandProcessor;
import view.Purchase;

import static view.CommandProcessor.processDecreaseNumberOfProductInCart;
import static view.CommandProcessor.processIncreaseNumberOfProductInCart;

public class CartMenu extends Menu {
    public CartMenu(Menu parentMenu) {
        super("cart menu", parentMenu);
        subMenus.put(1, getShowProducts());
        subMenus.put(2, getProductMenu());
        subMenus.put(3, getIncrease());
        subMenus.put(4, getDecrease());
        subMenus.put(5, getShowTotalPrice());
        subMenus.put(6, purchase());

    }

    private Menu getShowProducts() {
        return new LastMenu("show products", this) {
            @Override
            public void show() {
                CommandProcessor.showProductsInCart();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getIncrease() {
        return new LastMenu("increase number of a product", this) {
            @Override
            public void show() {
                processIncreaseNumberOfProductInCart();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getDecrease() {
        return new LastMenu("decrease number of a product", this) {
            @Override
            public void show() {
                processDecreaseNumberOfProductInCart();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getShowTotalPrice() {
        return new LastMenu("show total price", this) {
            @Override
            public void show() {
//                System.out.println("total price : " + BuyerManager.getTotalPrice());
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu purchase() {
        return new LastMenu("purchase", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (((Buyer) AccountManager.getOnlineAccount()).getCart().size() == 0) {
                    System.err.println("your cart is empty");
                } else {
                    Purchase.setCartMenu(this.parentMenu);
                    Purchase.giveReceiverInformation();
                    Purchase.giveDiscountCode();
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    private Menu getProductMenu() {
        return new LastMenu("product menu", this) {
            @Override
            public void show() {
                CommandProcessor.enterProductMenu(this.parentMenu);
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }


}
