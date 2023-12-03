package model.pojo;

public class AdministrativeStaff extends User{
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String institutionalEmail;
    private String personalNumber;

    public AdministrativeStaff() {
    }

    public AdministrativeStaff(int idUser, String username, String password, int idTeacher, int idAdministrativeStaff,
                               int userType, int responseCode, String name, String firstLastName, String secondLastName,
                               String institutionalEmail, String personalNumber) {
        super(idUser, username, password, idTeacher, idAdministrativeStaff, userType, responseCode);
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.institutionalEmail = institutionalEmail;
        this.personalNumber = personalNumber;
    }

    public AdministrativeStaff(int idAdministrative, String name, String firstLastName, String secondLastName, String institutionalEmail) {
        this.idAdministrativeStaff = idAdministrative;
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.institutionalEmail = institutionalEmail;
        this.personalNumber = personalNumber;
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

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getFullName(){
        return name + " " + firstLastName + " " + secondLastName;
    }
}
