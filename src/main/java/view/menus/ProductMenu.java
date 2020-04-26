package view.menus;

public class ProductMenu extends Menu {

    public ProductMenu(Menu parentMenu) {
        super("view a product", parentMenu);
        subMenus.put(1, new DigestMenu(this));
        subMenus.put(2, getAttributes());
        subMenus.put(3, getCompare());

    }

    private Menu getAttributes() {
        return new LastMenu("attributes", this) {
            @Override
            public void show() {
                super.show();
                //TODO
            }

            @Override
            public void execute() {
                //TODO
            }
        };
    }

    private Menu getCompare() {
        return new LastMenu("compare", this) {
            @Override
            public void show() {
                super.show();
                //TODO
            }

            @Override
            public void execute() {
                //TODO
            }
        };
    }


}