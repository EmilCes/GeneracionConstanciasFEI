package model.pojo;

import java.util.ArrayList;

public class ProjectStudentResponse {
    private int responseCode;
    private ArrayList<ProjectStudent> projectStudents;

    public ProjectStudentResponse() {
    }

    public ProjectStudentResponse(int responseCode, ArrayList<ProjectStudent> projectStudents) {
        this.responseCode = responseCode;
        this.projectStudents = projectStudents;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<ProjectStudent> getProjectStudents() {
        return projectStudents;
    }

    public void setProjectStudents(ArrayList<ProjectStudent> projectStudents) {
        this.projectStudents = projectStudents;
    }
}
