package cn.itsource.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ExcelTarget("16")
public class StudentInformation {
    @Excel(name = "班级", orderNum = "1", width=30)
    private String classes;
    @Excel(name = "编号", orderNum = "2", width=30)
    private Integer id;
    @Excel(name = "姓名", orderNum = "3", width=30)
    private String name;
    @Excel(name = "性别", orderNum = "4", width=30)
    private String sex;
    @Excel(name = "年龄", orderNum = "5", width=30)
    private String age;
    @Excel(name = "联系方式", orderNum = "6", width=30)
    private String phoneNumber;
    @Excel(name = "毕业学校", orderNum = "7", width=30)
    private String schoolName;
    @Excel(name = "专业", orderNum = "8", width=30)
    private String major;
    @Excel(name = "学历", orderNum = "9", width=30)
    private String degree;

    @Excel(name = "毕业时间", orderNum = "10", width=30)

    private String graduateTime;

    @Override
    public String toString() {
        return "StudentInformation{" +
                "classes='" + classes + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", major='" + major + '\'' +
                ", degree='" + degree + '\'' +
                ", graduateTime='" + graduateTime + '\'' +
                '}';
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(String graduateTime) {
        this.graduateTime = graduateTime;
    }
}
