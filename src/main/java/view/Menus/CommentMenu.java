package view.Menus;

public class CommentMenu extends Menu {

    public CommentMenu(Menu parentMenu) {
        super("comments", parentMenu);
        subMenus.put(1, getAddComment());
    }

    private Menu getAddComment() {
        return new Menu("add comment", this) {
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
