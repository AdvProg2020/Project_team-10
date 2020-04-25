package view.menus;

public class DigestMenu extends Menu {

    public DigestMenu( Menu parentMenu) {
        super("digest", parentMenu);
        subMenus.put(1, getAddToCart());
        // get select seller ***
    }
    private Menu getAddToCart() {
        return new LastMenu("add to cart", this) {
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
