package model.pojo;

public class DigitalSign {
    private int responseCode;
    private int idDigitalSign;
    private String sign;
    private String expirationDate;

    public DigitalSign() {
    }

    public DigitalSign(int responseCode, int idDigitalSign, String sign, String expirationDate) {
        this.responseCode = responseCode;
        this.idDigitalSign = idDigitalSign;
        this.sign = sign;
        this.expirationDate = expirationDate;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getIdDigitalSign() {
        return idDigitalSign;
    }

    public void setIdDigitalSign(int idDigitalSign) {
        this.idDigitalSign = idDigitalSign;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
