package view.menus;

import view.CommandProcessor;

public class ProductMenu extends Menu {

    public ProductMenu(Menu parentMenu) {
        super("product menu", parentMenu);
        subMenus.put(1, new DigestMenu(this));
        subMenus.put(2, getAttributes());
        subMenus.put(3, getCompare());
        subMenus.put(4, new CommentMenu(this));

    }

    private Menu getAttributes() {
        return new LastMenu("attributes", this) {
            @Override
            public void show() {
                CommandProcessor.showProductAttribute();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getCompare() {
        return new LastMenu("compare", this) {
            @Override
            public void show() {
                CommandProcessor.processCompare();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }


}
