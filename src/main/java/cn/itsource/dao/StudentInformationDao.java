package cn.itsource.dao;

import cn.itsource.domain.Account;
import cn.itsource.domain.Query;
import cn.itsource.domain.StudentInformation;
import cn.itsource.domain.StudentMessages;

import java.util.List;

public interface StudentInformationDao {
    public void insertStudentInformation(StudentInformation studentInformation);
    public void deleteByPrimaryKey(Integer id);
    public void updateByPrimaryKey(StudentInformation studentInformation);
    public List<StudentInformation> search(Query query);
    public void insertList(List<StudentInformation> list);
    public Integer getSize(Query query);
}
