package kr.ngii.pilot.sdmc.sample.model;

public class User {

    private String id;
    private String userName;
    private String passWord;
    private String phonNo;
    private String addr;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String toString() {
        return id + ":" + userName + ":" + passWord;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getPhonNo() {
        return phonNo;
    }
    public void setPhonNo(String phonNo) {
        this.phonNo = phonNo;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }

}
