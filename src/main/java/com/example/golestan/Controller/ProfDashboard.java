package com.example.golestan.Controller;

import com.example.golestan.Database.CourseDB;
import com.example.golestan.Database.ProfessorDB;
import com.example.golestan.MainApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfDashboard {
    private static String username;

    public static void setUsername(String username) {
        ProfDashboard.username = username;
    }

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
    void logoutClicked(ActionEvent event) throws IOException {
        SceneController control = new SceneController();
        control.switchScene(MainApplication.window, "Login.fxml");
    }

    @FXML
    void quitClicked(ActionEvent event) {
        SceneController control = new SceneController();
        control.closeProgram(MainApplication.window);
    }

    public void initialize() throws SQLException {
        ProfessorDB professorDB = new ProfessorDB();
        professorDB.setUsername(username);
        ResultSet resultSet1 = professorDB.findProf();
        String firstname = "", lastname = "";
        while (resultSet1.next()) {
            firstname = resultSet1.getString("Firstname");
            lastname = resultSet1.getString("Lastname");
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

        courseList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CourseDB>() {

            @Override
            public void changed(ObservableValue<? extends CourseDB> observable, CourseDB oldValue, CourseDB newValue) {

            }
        });
    }
}
