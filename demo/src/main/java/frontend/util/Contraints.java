package frontend.util;

import javafx.scene.control.TextField;

public class Contraints {
    public static void setTextFieldDouble(TextField txt) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*([\\:]\\d*)?")) {
                txt.setText(oldValue);
            }

            if (txt.getLength() == 2 && !txt.getText().endsWith(":") && Character.isDigit(txt.getText().charAt(1))) {
                txt.setText(txt.getText() + ":");
                txt.positionCaret(txt.getLength()); // colocar o cursor no final do campo
            }
        });
    }
    public static void setTextFieldMaxLength(TextField txt, int max) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > max) {
                txt.setText(oldValue);
            }
        });
    }
}
