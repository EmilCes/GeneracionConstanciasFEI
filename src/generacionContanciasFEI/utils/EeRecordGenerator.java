package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Util;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.dao.DigitalSignDAO;
import model.pojo.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EeRecordGenerator {
    private static void generateEERecord(EducativeExperience educativeExperience, EducationalProgram educationalProgram, String outputPath) {
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

            PdfPTable table = new PdfPTable(5);
            
            PdfPCell program = new PdfPCell(new Phrase("Programa Educativo", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            program.setHorizontalAlignment(1);
            table.addCell(program);

            PdfPCell ee = new PdfPCell(new Phrase("Experiencia Educativa", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            ee.setHorizontalAlignment(1);
            table.addCell(ee);

            PdfPCell block = new PdfPCell(new Phrase("Bloque", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            block.setHorizontalAlignment(1);
            table.addCell(block);

            PdfPCell section = new PdfPCell(new Phrase("Sección", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            section.setHorizontalAlignment(1);
            table.addCell(section);

            PdfPCell credits = new PdfPCell(new Phrase("Creditos", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
            section.setHorizontalAlignment(1);
            table.addCell(credits);

            PdfPCell educativeProgram = new PdfPCell(new Phrase(educationalProgram.getEducationalProgramName(), font));
            educativeProgram.setHorizontalAlignment(1);
            table.addCell(educativeProgram);

            PdfPCell educativeExperienceName = new PdfPCell(new Phrase(educativeExperience.getName(), font));
            educativeExperienceName.setHorizontalAlignment(1);
            table.addCell(educativeExperienceName);

            PdfPCell blockName = new PdfPCell(new Phrase(educativeExperience.getBlock(), font));
            blockName.setHorizontalAlignment(1);
            table.addCell(blockName);

            PdfPCell sectionName = new PdfPCell(new Phrase(educativeExperience.getSection(), font));
            sectionName.setHorizontalAlignment(1);
            table.addCell(sectionName);

            PdfPCell creditsName = new PdfPCell(new Phrase(educativeExperience.getCredits(), font));
            creditsName.setHorizontalAlignment(1);
            table.addCell(creditsName);

            document.add(table);

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

            Paragraph digitalSign = new Paragraph(DigitalSignDAO.getLastDigitalSignRegistered().getSign(), font);
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

    public static void openDirectoryChooserAndConvert(Stage primaryStage, EducativeExperience educativeExperience, EducationalProgram educationalProgram) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Ubicación de Descarga");
        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            LocalDate date = LocalDate.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = date.format(format);

            String outputPath = selectedDirectory.getAbsolutePath() + "/ConstanciaImparticionEE" + formattedDate + ".pdf";

            try {
                generateEERecord(educativeExperience, educationalProgram, outputPath);
            } catch (Exception e) {
                System.out.println("Error al generar el archivo:\n" + e.getMessage());
            }
        }
    }
}
