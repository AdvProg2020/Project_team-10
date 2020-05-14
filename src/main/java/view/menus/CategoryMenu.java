package view.menus;

import view.CommandProcessor;

public class CategoryMenu extends Menu  {
    public CategoryMenu(Menu parentMenu) {
        super("category menu", parentMenu);
        subMenus.put(1 , getShowAllCategory());
        subMenus.put(2 , getEditCategory());
        subMenus.put(3, getAddCategory());
        subMenus.put(4 , getRemoveCategory());
    }

    private Menu getShowAllCategory(){
        return new LastMenu("show all category", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getEditCategory(){
        return new LastMenu("edit", this) {
            @Override
            public void show() {
                CommandProcessor.processEditCategory();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getAddCategory(){
        return new LastMenu("add" , this) {
            @Override
            public void show() {
                CommandProcessor.processAddCategory(false , null);
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getRemoveCategory(){
        return new LastMenu("remove" , this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }
}
