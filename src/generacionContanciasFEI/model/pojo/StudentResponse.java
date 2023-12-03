package model.pojo;

import java.util.ArrayList;

public class StudentResponse {
    private int responseCode;
    private ArrayList<Student> students;

    public StudentResponse() {
    }

    public StudentResponse(int responseCode, ArrayList<Student> teachers) {
        this.responseCode = responseCode;
        this.students = teachers;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
