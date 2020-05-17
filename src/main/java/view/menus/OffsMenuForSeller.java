package view.menus;

import view.CommandProcessor;

public class OffsMenuForSeller extends Menu {
    public OffsMenuForSeller( Menu parentMenu) {
        super("offs menu", parentMenu);
        subMenus.put(1, getShowOffs());
        subMenus.put(2, getView());
        subMenus.put(3, getEdit());
        subMenus.put(4, getAddOff());

    }

    private Menu getShowOffs(){
        return new LastMenu("show offs" , this) {
            @Override
            public void show() {
                CommandProcessor.showAllOffsForSeller();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getView(){
        return new LastMenu("view an off" , this) {
            @Override
            public void show() {
                CommandProcessor.showAnOff();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getEdit(){
        return new LastMenu("edit" , this) {
            @Override
            public void show() {
                CommandProcessor.processEditOff();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getAddOff(){
        return new LastMenu("add off" , this) {
            @Override
            public void show() {
                CommandProcessor.processAddOrEditOff(false, -2);
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

}
