package view;

import javafx.scene.control.TextField;

public class ZipCode extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if ((start >= 0 && start <= 9) || end == 9) {
            if (text.matches("[0-9]") || text.isEmpty()) {
                super.replaceText(start, end, text);
            }
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }

}
