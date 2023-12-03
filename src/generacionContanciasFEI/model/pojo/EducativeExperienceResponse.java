package model.pojo;

import java.util.ArrayList;

public class EducativeExperienceResponse {
    private int responseCode;
    private ArrayList<EducativeExperience> educativeExperiences;

    public EducativeExperienceResponse() {
    }

    public EducativeExperienceResponse(int responseCode, ArrayList<EducativeExperience> educativeExperiences) {
        this.responseCode = responseCode;
        this.educativeExperiences = educativeExperiences;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<EducativeExperience> getEducativeExperiences() {
        return educativeExperiences;
    }

    public void setEducativeExperiences(ArrayList<EducativeExperience> educativeExperiences) {
        this.educativeExperiences = educativeExperiences;
    }
}
