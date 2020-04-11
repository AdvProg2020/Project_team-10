package view.Menus;

public class OffsMenuForSeller extends Menu {
    public OffsMenuForSeller( Menu parentMenu) {
        super("offs menu", parentMenu);
        subMenus.put(1, getShowOffs());
        subMenus.put(2, getView());
        subMenus.put(3, getEdit());
        subMenus.put(4, getAddOff());

    }

    private Menu getShowOffs(){
        return new Menu("show offs" , this) {
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

    private Menu getView(){
        return new Menu("view an off" , this) {
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

    private Menu getEdit(){
        return new Menu("edit" , this) {
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

    private Menu getAddOff(){
        return new Menu("add off" , this) {
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
