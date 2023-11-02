package com.banking.cs213project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TransactionManagerController {
    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private DatePicker dateInput;

    @FXML
    private Button openAccountButton;

    @FXML
    private Button closeAccount;

    public void openButtonClick(ActionEvent event){
        System.out.println(firstNameInput.getText());
    }


}