package cn.itsource.test;

public class Thread1 extends Thread{
    @Override
    public void run(){
        System.out.println(TherdLocalDemo.getInstance());
    }

}
