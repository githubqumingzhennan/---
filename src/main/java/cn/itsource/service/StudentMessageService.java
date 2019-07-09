package cn.itsource.service;

import cn.itsource.dao.StudentMessagesDao;
import cn.itsource.domain.StudentMessages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentMessageService {
    @Resource
    private StudentMessagesDao studentMessagesDao;

    public StudentMessages getUserById(Integer Id) {
        return studentMessagesDao.queryByPrimaryKey(Id);
    }
}
