package model.pojo;

import java.util.ArrayList;

public class EducationalProgramResponse {
    private int responseCode;
    private ArrayList<EducationalProgram> educationalPrograms;

    public EducationalProgramResponse() {
    }

    public EducationalProgramResponse(int responseCode, ArrayList<EducationalProgram> educationalPrograms) {
        this.responseCode = responseCode;
        this.educationalPrograms = educationalPrograms;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<EducationalProgram> getEducationalPrograms() {
        return educationalPrograms;
    }

    public void setEducationalPrograms(ArrayList<EducationalProgram> educationalPrograms) {
        this.educationalPrograms = educationalPrograms;
    }
}
