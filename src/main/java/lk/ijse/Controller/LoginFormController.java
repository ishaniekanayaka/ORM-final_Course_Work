package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.DTO.UserDTO;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;

import javax.swing.*;
import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private RadioButton btnRadio;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;
    
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/View/dashboard_form.fxml"))));
        stage.setTitle("DashBoard ");
        stage.centerOnScreen();
        stage.show();
/*
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        
            UserDTO userDTO = new UserDTO(null , password, userName, null);

            boolean isValid = userBO.validUser(userDTO);

            if (isValid) {
                AnchorPane anchorPane = null;
                try {
                    anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                    root.getChildren().clear();
                    root.getChildren().add(anchorPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {

                new Alert(Alert.AlertType.ERROR, "Invalid user name or password :( !!!").show();
            }*/
        }

    @FXML
    void btnRadioOnAction(ActionEvent event) {
/*
        if (!passClicked) {
            passClicked = true;
            String passwordText = txtPassword.getText();
            txtShowPassword.setText(passwordText);
            txtPassword.setVisible(false);
            txtShowPassword.setVisible(true);
        } else {
            passClicked = false;
            txtPassword.setVisible(true);
            txtShowPassword.setVisible(false);
        }*/
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
       // btnLogin.fire();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
      //  txtPassword.requestFocus();
    }
    
    

}
