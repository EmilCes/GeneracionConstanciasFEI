package model.pojo;

public class Teacher extends  User{
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String institutionalEmail;
    private int personalNumber;
    private String alternativeEmail;
    private String birthdate;

    public Teacher() {
    }

    public Teacher(String name, String firstLastName, String secondLastName, String institutionalEmail, int personalNumber, String alternativeEmail, String birthdate) {
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.institutionalEmail = institutionalEmail;
        this.personalNumber = personalNumber;
        this.alternativeEmail = alternativeEmail;
        this.birthdate = birthdate;
    }

    public Teacher(int idUser, String username, String password, int idTeacher, int idAdministrativeStaff,
                   int userType, int responseCode, String name, String firstLastName, String secondLastName,
                   String institutionalEmail, int personalNumber, String alternativeEmail, String birthdate) {

        super(idUser, username, password, idTeacher, idAdministrativeStaff, userType, responseCode);
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.institutionalEmail = institutionalEmail;
        this.personalNumber = personalNumber;
        this.alternativeEmail = alternativeEmail;
        this.birthdate = birthdate;
    }

    public Teacher(int idTeacher, String name, String firstLastName, String secondLastName, String institutionalEmail,
                   int personalNumber, String alternativeEmail, String birthdate) {
        this.idTeacher = idTeacher;
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.institutionalEmail = institutionalEmail;
        this.personalNumber = personalNumber;
        this.alternativeEmail = alternativeEmail;
        this.birthdate = birthdate;
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

    public String getInstitutionalEmail() {
        return institutionalEmail;
    }

    public void setInstitutionalEmail(String institutionalEmail) {
        this.institutionalEmail = institutionalEmail;
    }

    public int getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(int personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getAlternativeEmail() {
        return alternativeEmail;
    }

    public void setAlternativeEmail(String alternativeEmail) {
        this.alternativeEmail = alternativeEmail;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getFullName(){
        return this.name + " " + this.firstLastName + " " + this.secondLastName;
    }
}
