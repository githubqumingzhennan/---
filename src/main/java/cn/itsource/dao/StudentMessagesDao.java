package cn.itsource.dao;

import cn.itsource.domain.Query;
import cn.itsource.domain.StudentMessages;
import org.springframework.stereotype.Component;

import java.util.List;


public interface StudentMessagesDao {
    public StudentMessages queryByPrimaryKey(Integer id);
    public void insertUser(StudentMessages studentMessages);
    public void updateByPrimaryKey(StudentMessages studentMessages);
    public void deleteByPrimaryKey(Integer id);
    public List<StudentMessages> queryAll();
    public void insertList(List<StudentMessages> list);
    public List<StudentMessages> search(Query query);
    public List<StudentMessages> searchQuery(Query query);
    public Integer getSize(Query query);
    public void deleteAll();
}
