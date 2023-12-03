package model.pojo;

public class EducativeExperience {
    private int responseCode;
    private int idEducativeExperience;
    private int idEducationalProgram;
    private int idTeacher;
    private String name;
    private String block;
    private String section;
    private String credits;

    public EducativeExperience() {
    }

    public EducativeExperience(int responseCode, int idEducativeExperience, int idEducationalProgram, int idTeacher, String name, String block, String section, String credits) {
        this.responseCode = responseCode;
        this.idEducativeExperience = idEducativeExperience;
        this.idEducationalProgram = idEducationalProgram;
        this.idTeacher = idTeacher;
        this.name = name;
        this.block = block;
        this.section = section;
        this.credits = credits;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getIdEducativeExperience() {
        return idEducativeExperience;
    }

    public void setIdEducativeExperience(int idEducativeExperience) {
        this.idEducativeExperience = idEducativeExperience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public int getIdEducationalProgram() {
        return idEducationalProgram;
    }

    public void setIdEducationalProgram(int idEducationalProgram) {
        this.idEducationalProgram = idEducationalProgram;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }
}
