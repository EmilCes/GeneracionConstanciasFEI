package model.pojo;

import java.util.ArrayList;

public class TeacherResponse {
    private int responseCode;
    private ArrayList<Teacher> teachers;

    public TeacherResponse() {
    }

    public TeacherResponse(int responseCode, ArrayList<Teacher> teachers) {
        this.responseCode = responseCode;
        this.teachers = teachers;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }
}
