package cn.itsource.test;

public class jkkj {
    public static void main(String[] args) {
        int i=0;
        while (i<100) {
            new Thread1().start();
            new Thread2().start();
            new Thread3().start();
            new Thread1().start();
            i++;
        }
    }
}
