package model.pojo;

public class ProjectStudent {
    private int responseCode;
    private int idProjectStudent;
    private int idProject;
    private int idStudent;

    public ProjectStudent() {
    }

    public ProjectStudent(int responseCode, int idProjectStudent, int idProject, int idStudent) {
        this.responseCode = responseCode;
        this.idProjectStudent = idProjectStudent;
        this.idProject = idProject;
        this.idStudent = idStudent;
    }

    public ProjectStudent(int idProjectStudent, int idProject, int idStudent) {
        this.idProjectStudent = idProjectStudent;
        this.idProject = idProject;
        this.idStudent = idStudent;
    }

    public ProjectStudent(int idProject, int idStudent) {
        this.idProject = idProject;
        this.idStudent = idStudent;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getIdProjectStudent() {
        return idProjectStudent;
    }

    public void setIdProjectStudent(int idProjectStudent) {
        this.idProjectStudent = idProjectStudent;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }
}
