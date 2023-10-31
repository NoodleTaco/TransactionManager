module com.banking.cs213project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.banking.cs213project3 to javafx.fxml;
    exports com.banking.cs213project3;
}