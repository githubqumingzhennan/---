package cn.itsource.dao;

import cn.itsource.domain.Audio;
import cn.itsource.domain.StudentMessages;

import java.util.List;

public interface AudioDao {
    public Audio queryByPrimaryKey(Integer id);
    public void insertUser(Audio audio);
    public void updateByPrimaryKey(Audio audio);
    public void deleteByPrimaryKey(Integer id);
    public List<Audio> queryAll();
    public void insertList(List<Audio> list);
}
