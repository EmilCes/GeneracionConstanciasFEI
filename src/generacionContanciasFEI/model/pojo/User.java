package model.pojo;

public class User {
    protected int idUser;
    protected String username;
    protected String password;
    protected int idTeacher;
    protected int idAdministrativeStaff;
    protected int idUserType;
    protected int responseCode;

    public User() {
    }

    public User(int idUser, String username, String password, int idTeacher, int idAdministrativeStaff, int idUserType, int responseCode) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.idTeacher = idTeacher;
        this.idAdministrativeStaff = idAdministrativeStaff;
        this.idUserType = idUserType;
        this.responseCode = responseCode;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }

    public int getIdAdministrativeStaff() {
        return idAdministrativeStaff;
    }

    public void setIdAdministrativeStaff(int idAdministrativeStaff) {
        this.idAdministrativeStaff = idAdministrativeStaff;
    }

    public int getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(int idUserType) {
        this.idUserType = idUserType;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
