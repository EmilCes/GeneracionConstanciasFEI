package utils;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import model.dao.TeacherDAO;
import model.dao.UserDAO;
import model.pojo.Teacher;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ImportTeachers {
    public void importTeachers() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            List<Teacher> teachers = readTeachersFromCSV(selectedFile);

            for (Teacher teacher : teachers) {
                TeacherDAO.saveTeacher(teacher);
                Teacher lastTeacherResgistered = TeacherDAO.getLastTeacherRegistered();
                saveUser(teacher, lastTeacherResgistered.getIdTeacher());
            }
            Utilities.showSimpleDialog("Importación Correcta", "Los profesores fueron impartidos correctamente.",
                    Alert.AlertType.INFORMATION);
        }
    }

    private void saveUser(Teacher teacher, int idTeacher){
        String username = teacher.getName() + "_" + teacher.getFirstLastName();
        username = username.replaceAll("\\s", "");
        String password = teacher.getName() + "_" + teacher.getFirstLastName();
        password = password.replaceAll("\\s", "");
        int userType = Constants.TEACHER;

        Teacher teacherAccount = new Teacher();
        teacherAccount.setUsername(username);
        teacherAccount.setPassword(password);
        teacherAccount.setIdUserType(userType);
        teacherAccount.setIdTeacher(idTeacher);

        UserDAO.saveUser(teacherAccount);
    }

    private List<Teacher> readTeachersFromCSV(File file) {
        List<Teacher> teachers = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                String name = parts[0].trim();
                String firstLastName = parts[1].trim();
                String secondLastName = parts[2].trim();
                String institutionalEmail = parts[3].trim();
                int personalNumber = Integer.parseInt(parts[4].trim());
                String alternativeEmail = parts[5].trim();
                String birthdate = parts[6].trim();

                Date birthdateFormatted = new SimpleDateFormat("yyyy/MM/dd").parse(birthdate);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String formattedBirthdate = format.format(birthdateFormatted);

                Teacher teacher = new Teacher(name, firstLastName, secondLastName, institutionalEmail, personalNumber, alternativeEmail, birthdate);
                teachers.add(teacher);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Utilities.showSimpleDialog("Error", "No se pudo encontrar el archivo.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            Utilities.showSimpleDialog("Error", "Error al leer el archivo CSV.", Alert.AlertType.ERROR);
        }

        return teachers;
    }
}
