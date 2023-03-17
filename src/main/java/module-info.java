module calculator {
    requires javafx.controls;
    requires javafx.fxml;
    opens ru.sibsutis.piratetigo.calculator.forms to javafx.fxml;
    exports ru.sibsutis.piratetigo.calculator to javafx.graphics;
    exports ru.sibsutis.piratetigo.calculator.number;
    exports ru.sibsutis.piratetigo.calculator.editor;
    exports ru.sibsutis.piratetigo.calculator.forms;
}