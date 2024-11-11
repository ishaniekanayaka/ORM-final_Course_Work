package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.DTO.StudentDTO;
import lk.ijse.DTO.tm.StudentTM;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.util.regex.RegExFactory;
import lk.ijse.util.regex.RegExType;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private DatePicker cmbDob;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rBtnFemale;

    @FXML
    private RadioButton rBtnMale;

    @FXML
    private AnchorPane rootStudent;

    @FXML
    private TableView<StudentTM> tblStudents;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    private RegExFactory regExFactory;

    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
    ObservableList<StudentTM> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setStudentID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            getAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        regExFactory = RegExFactory.getInstance();
    }

    private void refreshTable() throws IOException {
        obList.clear();
        getAll();
        setStudentID();
    }

    private void setCellValueFactory() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }


    private void getAll() throws IOException {
        List<StudentDTO> allStudents = studentBO.getAllStudents();

        if (!(allStudents.isEmpty())) {

            for (StudentDTO studentDTO : allStudents) {

                obList.add(new StudentTM(studentDTO.getStudent_id(),
                        studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact(),
                        studentDTO.getGender(), studentDTO.getDob()));

                tblStudents.setItems(obList);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Empty Students :( !!!").show();
        }
    }


    private void setStudentID() throws IOException {
        String studentID = studentBO.generateNewStudentID();

        if (studentID == null) {
            txtId.setText("ST000001");
        } else {
            String[] split = studentID.split("[S][T]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("ST%06d", lastDigits);
            txtId.setText(newID);
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        StudentTM selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                studentBO.deleteStudent(selectedItem.getStudent_id());
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted").show();
                refreshTable();
                clearAll();

            } else {
                new Alert(Alert.AlertType.ERROR, "Select Student first!").show();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
    }



    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String gender = (rBtnMale.isSelected()) ? "Male" : "Female";
        Date dob = Date.valueOf(cmbDob.getValue());

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudent_id(id);
        studentDTO.setName(name);
        studentDTO.setAddress(address);
        studentDTO.setContact(contact);
        studentDTO.setGender(gender);
        studentDTO.setDob(dob);

        if(validity()) {
            if (checkRegex()) {

                boolean isAdded = studentBO.addStudent(studentDTO);

                if (isAdded) {
                   refreshTable();
                    clearAll();
                    new Alert(Alert.AlertType.CONFIRMATION, "Student Added Successful :)!!!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student not Added :( !!!").show();
                }
            } else {

                new Alert(Alert.AlertType.ERROR, "Invalid types !!!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields :( !!!").show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String gender = (rBtnMale.isSelected()) ? "Male" : "Female";
        Date dob = Date.valueOf(cmbDob.getValue());

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudent_id(id);
        studentDTO.setName(name);
        studentDTO.setAddress(address);
        studentDTO.setContact(contact);
        studentDTO.setGender(gender);
        studentDTO.setDob(dob);

        if(validity()) {
            if(checkRegex()) {

                boolean isAdded = studentBO.updateStudent(studentDTO);

                if (isAdded) {
                    refreshTable();
                    clearAll();
                    btnSave.setDisable(false);
                    new Alert(Alert.AlertType.CONFIRMATION, "Student Update Successful :)!!!").show();
                    clearAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student not Updated :( !!!").show();

                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid types !!!").show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields :( !!!").show();
        }

    }


    @FXML
    void tblStudentOnMouseClicked(MouseEvent event) {
        StudentTM selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnSave.setDisable(true);
                txtId.setText(selectedItem.getStudent_id());
                txtName.setText(selectedItem.getName());
                txtAddress.setText(selectedItem.getAddress());
                if (selectedItem.getGender().equals("Male")) {
                    rBtnMale.setSelected(true);
                } else {
                    rBtnFemale.setSelected(true);
                }
                txtContact.setText(selectedItem.getContact());
                cmbDob.setValue(selectedItem.getDob().toLocalDate());
            } else {
                btnUpdate.setDisable(true);
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean checkRegex() {
        return RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtName.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.CITY).matcher(txtAddress.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.MOBILE).matcher(txtContact.getText()).matches() && cmbDob.getValue() != null;
    }

    private boolean validity() {

        return !txtId.getText().isEmpty() &&
                !txtName.getText().isEmpty() &&
                !txtAddress.getText().isEmpty() &&
                !txtContact.getText().isEmpty() &&
                (rBtnMale.isSelected() || rBtnFemale.isSelected()) &&
                cmbDob.getValue() != null;
    }

    private void clearAll() {
        txtContact.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.clear();
    }

}
