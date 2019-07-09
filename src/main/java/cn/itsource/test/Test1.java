package cn.itsource.test;

import cn.itsource.dao.AccountDao;
import cn.itsource.dao.AudioDao;
import cn.itsource.dao.StudentMessagesDao;
import cn.itsource.domain.Account;
import cn.itsource.domain.Audio;
import cn.itsource.domain.Query;
import cn.itsource.domain.StudentMessages;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test1 {
   @Autowired
    private StudentMessagesDao studentMessagesDao;
   @Autowired
   private AccountDao accountDao;
   /*@Autowired
   private AudioDao audioDao;*/
    @org.junit.Test
    public void getUserById() {
   /*     System.out.println(studentMessagesDao.queryByPrimaryKey(1));;
        StudentMessages studentMessages = new StudentMessages(1, "h", "xadad", "kjhj", "kjh", new Date(), "hh");
        studentMessages.setFl("kjj");
        studentMessages.setElses(20000.0);
        studentMessagesDao.updateByPrimaryKey(studentMessages);
        studentMessagesDao.deleteByPrimaryKey(1);
        List<StudentMessages> studentMessages1 = studentMessagesDao.queryAll();
        System.out.println(studentMessages1.size());*/
       /* List<StudentMessages> studentMessages = studentMessagesDao.queryAll();
        System.out.println(studentMessages);*/
       Xiancheng xiancheng=new Xiancheng();
        Thread t1=new Thread(xiancheng);
        Thread t2=new Thread(xiancheng);
        t1.start();
        t2.start();


    }
    @Test
    public void faq(){

        double a=116.32;
        System.out.println(116.32+0.11);
    }
    @org.junit.Test
    public void test2() {
        Audio audio=new Audio();
        audio.setName("01_单体应用的缺点介绍.avi");
       /* audio.setPath("G:\\SSMstudentManage\\target\\employment\\WEB-INF\\upload\\/01_单体应用的缺点介绍.avi");*/
        audio.setCreateTime(new Date());
        Audio audio1=new Audio();
        audio.setName("nhh1");
       // audioDao.insertUser(audio);
    /*    List<Audio> audios=new ArrayList<>();
        audios.add(audio);
        audios.add(audio1);*/
      //  audioDao.insertList(audios);
    /*    for (int i=0;i<11000;i++){
            audio.setName(i+"");
            audioDao.insertUser(audio);
        }*/
    }
    class Xiancheng implements Runnable{
        int a=10;
        Lock lock=new ReentrantLock();
        @Override
        public void run() {
            while (a>0){
                lock.lock();
                try{
                    System.out.println(a);
                    a--;
                }finally {
                    lock.unlock();
                }
            }

        }
    }

}
