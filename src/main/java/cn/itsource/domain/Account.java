package cn.itsource.domain;

public class Account {
    private Integer id;
    private String num;//账号
    private String password;
    private String dept;//部门名称
    private String jurisdiction;//权限
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", password='" + password + '\'' +
                ", dept='" + dept + '\'' +
                ", jurisdiction='" + jurisdiction + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Account(String num, String password) {
        this.num = num;
        this.password = password;
    }

    public Account() {
    }
}
