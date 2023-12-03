package model.pojo;

public class EducationalProgram {
    private int responseCode;
    private int idEducationalProgram;
    private String educationalProgramName;

    public EducationalProgram() {
    }

    public EducationalProgram(int responseCode, int idEducationalProgram, String educationalProgramName) {
        this.responseCode = responseCode;
        this.idEducationalProgram = idEducationalProgram;
        this.educationalProgramName = educationalProgramName;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getIdEducationalProgram() {
        return idEducationalProgram;
    }

    public void setIdEducationalProgram(int idEducationalProgram) {
        this.idEducationalProgram = idEducationalProgram;
    }

    public String getEducationalProgramName() {
        return educationalProgramName;
    }

    public void setEducationalProgramName(String educationalProgramName) {
        this.educationalProgramName = educationalProgramName;
    }

    @Override
    public String toString() {
        return educationalProgramName;
    }
}
