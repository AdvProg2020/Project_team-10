package view.Menus;

public class ProductMenu extends Menu {

    public ProductMenu(Menu parentMenu) {
        super("product menu", parentMenu);
        subMenus.put(1, new DigestMenu(this));
        subMenus.put(2, getAttributes());
        subMenus.put(3, getCompare());


    }

    private Menu getAttributes() {
        return new Menu("attributes", this) {
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

    private Menu getCompare() {
        return new Menu("compare", this) {
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
