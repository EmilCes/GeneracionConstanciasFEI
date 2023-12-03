package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import model.dao.DigitalSignDAO;
import model.pojo.DigitalSign;
import model.pojo.Project;
import model.pojo.Student;

import model.pojo.Teacher;

import java.io.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectRecordGenerator {

    private static void generateProjectRecord(Project project, List<Student> studentsList, String outputPath) {
        Rectangle customPageSize = PageSize.A4;
        Document document = new Document(customPageSize);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, fileOutputStream);
            Teacher teacher = (Teacher) UserSingleton.getInstance().getUser();

            document.open();

            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 11);

            document.add(new Paragraph("A quien corresponda", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("El que suscribe, Director de la Facultad de Estadística e Informática, de la Universidad Veracruzana ", font));
            document.add(new Paragraph("\n"));

            Paragraph paragraph = new Paragraph("HACE CONSTAR", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_CENTER);

            document.add(paragraph);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("que el Mtro. (a) "
                    + teacher.getFullName() + " "
                    + " participó en un proyecto con las siguientes características:",
                    font
            ));

            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(4);
            PdfPCell projectDone = new PdfPCell(new Phrase("Proyecto realizado", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            projectDone.setHorizontalAlignment(1);
            table.addCell(projectDone);

            PdfPCell duration = new PdfPCell(new Phrase("Duracion", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            duration.setHorizontalAlignment(1);
            table.addCell(duration);

            PdfPCell place = new PdfPCell(new Phrase("Lugar donde se desarrollo", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            place.setHorizontalAlignment(1);
            table.addCell(place);

            PdfPCell studentsName = new PdfPCell(new Phrase("Nombre de las y los alumnos involucrados", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            studentsName.setHorizontalAlignment(1);
            table.addCell(studentsName);

            PdfPCell projectName = new PdfPCell(new Phrase(project.getProjectDone(), font));
            projectName.setHorizontalAlignment(1);
            table.addCell(projectName);

            PdfPCell projectDuration = new PdfPCell(new Phrase(project.getDuration(), font));
            projectDuration.setHorizontalAlignment(1);
            table.addCell(projectDuration);

            PdfPCell projectPlace = new PdfPCell(new Phrase(project.getProjectPlace(), font));
            projectPlace.setHorizontalAlignment(1);
            table.addCell(projectPlace);

            String students = "";
            for (Student student : studentsList) {
                students = students + student.getFullName() + "\n";
            }

            PdfPCell projectStudents = new PdfPCell(new Phrase(students, font));
            projectStudents.setHorizontalAlignment(1);
            table.addCell(projectStudents);

            document.add(table);

            document.add(new Paragraph("Impacto obtenido:", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph(project.getImpactObtained(), font));
            document.add(new Paragraph("\n"));

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = currentDate.format(format);

            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("A petición del interesado y para los fines legales que al mismo convenga, se extiende la presente en la ciudad de Xalapa-Enríquez, Veracruz el dia "
                    + formattedDate, font));

            document.add(new Paragraph("\n\n"));
            Paragraph attParagraph = new Paragraph("A t e n t a m e n t e", font);
            attParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(attParagraph);

            Paragraph signParagraph = new Paragraph("\"Lis de Veracruz: Ciencia, Arte, Luz\"", font);
            signParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(signParagraph);

            Paragraph director = new Paragraph("Dr. Luis Gerardo Montané Jiménez", font);
            director.setAlignment(Element.ALIGN_CENTER);
            document.add(director);

            DigitalSign sign = DigitalSignDAO.getLastDigitalSignRegistered();
            Paragraph digitalSign = new Paragraph(sign.getSign(), font);
            digitalSign.setAlignment(Element.ALIGN_BOTTOM);
            document.add(new Paragraph("\n\n\n"));
            document.add(digitalSign);

            Utilities.showSimpleDialog("Constancia Generada", "La constancia fue generada correctamente", Alert.AlertType.INFORMATION);


        } catch (DocumentException | FileNotFoundException e){
            System.err.println(e.getMessage());
        } finally {
            document.close();
        }
    }

    public static void openDirectoryChooserAndConvert(Stage primaryStage, Project project, List<Student> studentsList) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Ubicación de Descarga");
        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            LocalDate date = LocalDate.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = date.format(format);

            String outputPath = selectedDirectory.getAbsolutePath() + "/ConstanciaDeProyectoDeCampo" + formattedDate + ".pdf";

            try {
                generateProjectRecord(project, studentsList, outputPath);
            } catch (Exception e) {
                System.out.println("Error al generar el archivo:\n" + e.getMessage());
            }
        }
    }
}