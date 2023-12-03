package model.pojo;

import java.util.ArrayList;

public class AdministrativeStaffResponse {
    private int responseCode;
    private ArrayList<AdministrativeStaff> administrativeStaffs;

    public AdministrativeStaffResponse() {
    }

    public AdministrativeStaffResponse(int responseCode, ArrayList<AdministrativeStaff> administrativeStaffs) {
        this.responseCode = responseCode;
        this.administrativeStaffs = administrativeStaffs;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<AdministrativeStaff> getAdministrativeStaffs() {
        return administrativeStaffs;
    }

    public void setAdministrativeStaffs(ArrayList<AdministrativeStaff> administrativeStaffs) {
        this.administrativeStaffs = administrativeStaffs;
    }
}
