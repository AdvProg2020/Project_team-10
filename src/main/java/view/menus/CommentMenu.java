package view.menus;

import view.CommandProcessor;

public class CommentMenu extends Menu {

    public CommentMenu(Menu parentMenu) {
        super("comments", parentMenu);
        subMenus.put(1, getShowComments());
        subMenus.put(2, getAddComment());
    }

    private Menu getShowComments() {
        return new LastMenu("show comments", this) {
            @Override
            public void show() {
                CommandProcessor.showComments();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getAddComment() {
        return new LastMenu("add comment", this) {
            @Override
            public void show() {
                CommandProcessor.processAddComment();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }
}
