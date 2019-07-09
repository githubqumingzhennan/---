package cn.itsource.test;

public class TherdLocalDemo {
    private TherdLocalDemo() {}
    private static int i;
    private static int test(){
        return i++;
    }
    //相当于是一个容器
    private static ThreadLocal<Integer> local = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            int y=test();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return y++;
        };
    };


    public static Integer getInstance() {
        return local.get();
    }
}
