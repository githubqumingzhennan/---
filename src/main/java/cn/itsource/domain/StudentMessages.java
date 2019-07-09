package cn.itsource.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@ExcelTarget("16")
public class StudentMessages implements java.io.Serializable{
    @Excel(name = "序号", orderNum = "1", width=30)
    private Integer id;
    @Excel(name = "姓名", orderNum = "2", width=30)
    private String name;
    @Excel(name = "毕业学校", orderNum = "3", width=30)
    private String schoolName;
    @Excel(name = "学历", orderNum = "4", width=30)
    private String degree;
    @Excel(name = "公司名称", orderNum = "5", width=30)
    private String companyName;
    @Excel(name = "入职时间", orderNum = "6", width=30,exportFormat="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @Excel(name = "工作地点", orderNum = "7", width=30)
    private String address;
    @Excel(name = "薪资", orderNum = "8", width=30)
    private String salary;
    @Excel(name = "福利待遇", orderNum = "9", width=30)
    private String fl;
    @Excel(name = "福利明细", orderNum = "10", width=30)
    private String flmx;
    @Excel(name = "年终奖", orderNum = "11", width=30)
    private Double year_end_bonus;
    @Excel(name = "社保公积金", orderNum = "12", width=30)
    private String elses;
    @Excel(name = "薪资明细", orderNum = "13", width=30)
    private String salary_detail;
    @Excel(name = "薪资总额", orderNum = "14", width=30)
    private String salary_total;
    @Excel(name = "星级", orderNum = "15", width=30)
    private String grade;

    public String getSalary_detail() {
        return salary_detail;
    }

    public void setSalary_detail(String salary_detail) {
        this.salary_detail = salary_detail;
    }

    public String getSalary_total() {
        return salary_total;
    }

    public void setSalary_total(String salary_total) {
        this.salary_total = salary_total;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public StudentMessages(Integer id, String name, String schoolName, String degree, String companyName, Date date, String address) {
        this.id = id;
        this.name = name;
        this.schoolName = schoolName;
        this.degree = degree;
        this.companyName = companyName;
        this.date = date;
        this.address = address;
    }

    public StudentMessages() {
    }

    public Integer getId() {
        this.getClass().getSimpleName();
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getFlmx() {
        return flmx;
    }

    public void setFlmx(String flmx) {
        this.flmx = flmx;
    }

    public Double getYear_end_bonus() {
        return year_end_bonus;
    }

    public void setYear_end_bonus(Double year_end_bonus) {
        this.year_end_bonus = year_end_bonus;
    }

    public String getElses() {
        return elses;
    }

    public void setElses(String elses) {
        this.elses = elses;
    }

    @Override
    public String toString() {
        return "StudentMessages{" +
                "name='" + name + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", degree='" + degree + '\'' +
                ", companyName='" + companyName + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", fl='" + fl + '\'' +
                ", flmx='" + flmx + '\'' +
                ", year_end_bonus=" + year_end_bonus +
                ", elses=" + elses +
                '}';
    }
}
