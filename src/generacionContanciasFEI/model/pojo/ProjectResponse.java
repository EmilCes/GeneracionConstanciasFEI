package model.pojo;

import java.util.ArrayList;

public class ProjectResponse {
    private int responseCode;
    private ArrayList<Project> projects;

    public ProjectResponse() {
    }

    public ProjectResponse(int responseCode, ArrayList<Project> projects) {
        this.responseCode = responseCode;
        this.projects = projects;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
}
