package cn.itsource.test;

public class Thread2 extends Thread {
    @Override
    public void run(){
        System.out.println(TherdLocalDemo.getInstance());
    }
}
