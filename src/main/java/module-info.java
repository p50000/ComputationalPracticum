module com.rshakirova.computationalpracticum {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.chart.zooming;


    opens com.rshakirova.computationalpracticum to javafx.fxml;
    exports com.rshakirova.computationalpracticum;
    exports com.rshakirova.computationalpracticum.display;
    opens com.rshakirova.computationalpracticum.display to javafx.fxml;
}