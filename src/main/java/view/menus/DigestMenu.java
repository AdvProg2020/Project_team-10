package view.menus;

import controller.GoodsManager;
import model.Good;
import view.CommandProcessor;

public class DigestMenu extends Menu {

    public DigestMenu( Menu parentMenu) {
        super("digest", parentMenu);
        subMenus.put(1, getAddToCart());
        // get select seller ***
    }

    @Override
    public void show() {
//        System.out.println(GoodsManager.getCurrentGood().digestToString());
        super.show();
    }

    private Menu getAddToCart() {
        return new LastMenu("add to cart", this) {
            @Override
            public void show() {
                CommandProcessor.processAddToCart();
                this.parentMenu.parentMenu.show();
            }

            @Override
            public void execute() {
                this.parentMenu.parentMenu.execute();
            }
        };
    }
}
