package model.pojo;

import java.util.List;

public class Project {
    private int responseCode;
    private int idProject;
    private String projectDone;
    private String duration;
    private String projectPlace;
    private String impactObtained;
    private int idTeacher;
    private List<ProjectStudent> projectStudents;

    public Project() {
    }

    public Project(int respondeCode, int idProject, String projectDone, String duration, String projectPlace, String impactObtained, int idTeacher) {
        this.responseCode = respondeCode;
        this.idProject = idProject;
        this.projectDone = projectDone;
        this.duration = duration;
        this.projectPlace = projectPlace;
        this.impactObtained = impactObtained;
        this.idTeacher = idTeacher;
    }

    public Project(String projectDone, String duration, String projectPlace, String impactObtained, int idTeacher) {
        this.idProject = idProject;
        this.projectDone = projectDone;
        this.duration = duration;
        this.projectPlace = projectPlace;
        this.impactObtained = impactObtained;
        this.idTeacher = idTeacher;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getProjectDone() {
        return projectDone;
    }

    public void setProjectDone(String projectDone) {
        this.projectDone = projectDone;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getProjectPlace() {
        return projectPlace;
    }

    public void setProjectPlace(String projectPlace) {
        this.projectPlace = projectPlace;
    }

    public String getImpactObtained() {
        return impactObtained;
    }

    public void setImpactObtained(String impactObtained) {
        this.impactObtained = impactObtained;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
