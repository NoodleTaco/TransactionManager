package com.banking.cs213project3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}