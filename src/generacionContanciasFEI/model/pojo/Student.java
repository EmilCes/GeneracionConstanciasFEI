package model.pojo;

public class Student {
    private int codeResponse;
    private int idEstudiante;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String tuition;

    public Student() {
    }

    public Student(int codeResponse, int idEstudiante, String name, String firstLastName, String secondLastName, String tuition) {
        this.codeResponse = codeResponse;
        this.idEstudiante = idEstudiante;
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.tuition = tuition;
    }

    public Student(String name, String firstLastName, String secondLastName, String tuition) {
        this.codeResponse = codeResponse;
        this.idEstudiante = idEstudiante;
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.tuition = tuition;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getTuition() {
        return tuition;
    }

    public void setTuition(String tuition) {
        this.tuition = tuition;
    }

    public int getCodeResponse() {
        return codeResponse;
    }

    public void setCodeResponse(int codeResponse) {
        this.codeResponse = codeResponse;
    }

    public String getFullName(){
        return name + " " + firstLastName + " " + secondLastName;
    }

    @Override
    public String toString() {
        return name + " " + firstLastName + " " + secondLastName;
    }
}
