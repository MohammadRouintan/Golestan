package com.example.golestan.Controller;

import com.example.golestan.Database.CourseDB;
import com.example.golestan.Database.ProfessorDB;
import com.example.golestan.Database.StudentDB;
import com.example.golestan.MainApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfDashboard {
    private static String username;

    public static void setUsername(String username) {
        ProfDashboard.username = username;
    }


    @FXML
    private TableColumn<StudentDB, Integer> idColumn;

    @FXML
    private TableColumn<StudentDB, String> nameStuColumn;

    @FXML
    private TableColumn<StudentDB, Float> scoreColumn;

    @FXML
    private TableView<StudentDB> stuList;

    @FXML
    private TableColumn<CourseDB, String> codeColumn;

    @FXML
    private TableView<CourseDB> courseList;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<CourseDB, String> nameColumn;

    @FXML
    private Button quitButton;

    @FXML
    private Label showName;

    @FXML
    void logoutClicked(ActionEvent event) throws IOException {
        SceneController control = new SceneController();
        control.switchScene(MainApplication.window, "Login.fxml");
    }

    @FXML
    void quitClicked(ActionEvent event) {
        SceneController control = new SceneController();
        control.closeProgram(MainApplication.window);
    }

    @FXML
    void editScore(TableColumn.CellEditEvent<StudentDB, Float> event) throws SQLException {
        StudentDB studentDB = stuList.getSelectionModel().getSelectedItem();
        studentDB.setCourseCode(courseList.getSelectionModel().getSelectedItem().getCode());
        studentDB.setScore(event.getNewValue());
        studentDB.addScore();
    }

    public void initialize() throws SQLException {
        ProfessorDB professorDB = new ProfessorDB();
        professorDB.setUsername(username);
        ResultSet resultSet1 = professorDB.findProf();
        String firstname = "", lastname = "";
        while (resultSet1.next()) {
            firstname = resultSet1.getString("Firstname");
            lastname = resultSet1.getString("Lastname");
            showName.setText(firstname + " " + lastname);
        }

        CourseDB courseDB = new CourseDB();
        ObservableList<CourseDB> courseNames = FXCollections.observableArrayList();
        ResultSet resultSet = courseDB.findCourseWithProf(firstname, lastname);
        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String code = resultSet.getString("Code");
                courseNames.add(new CourseDB(name, code));
            }

            nameColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("name"));
            codeColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("code"));
            courseList.setItems(courseNames);
        }

        courseList.getSelectionModel().selectedItemProperty().addListener(event -> {
            String currentCourse = courseList.getSelectionModel().getSelectedItem().getCode();
            StudentDB studentDB = new StudentDB();
            try {
                ObservableList<StudentDB> studentList = FXCollections.observableArrayList();
                studentDB.setCourseCode(currentCourse);
                ResultSet resultSet2 = studentDB.findCourseWithCode();
                if (resultSet2.isBeforeFirst()) {
                    while (resultSet2.next()) {
                        int studentId = resultSet2.getInt("StudentId");
                        String name = resultSet2.getString("Firstname") + " " + resultSet2.getString("Lastname");
                        float score = resultSet2.getFloat("Score");
                        studentList.add(new StudentDB(studentId, name, score));
                    }
                    idColumn.setCellValueFactory(new PropertyValueFactory<StudentDB, Integer>("studentId"));
                    nameStuColumn.setCellValueFactory(new PropertyValueFactory<StudentDB, String>("name"));
                    scoreColumn.setCellValueFactory(new PropertyValueFactory<StudentDB, Float>("score"));
                    stuList.setItems(studentList);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        scoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
    }
}
