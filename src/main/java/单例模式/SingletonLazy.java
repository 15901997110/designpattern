package 单例模式;

/**
 * 懒汉式单例，需要此对象时，内存中才加载此对象
 * 非线程安全
 */
public class SingletonLazy {
    private SingletonLazy() {
    }

    private static SingletonLazy lazy;

    public static SingletonLazy getInstance() {
        if (lazy == null) {//可能有多个线程同时执行lazy==null,造成创建多个实例
            lazy = new SingletonLazy();
        }
        return lazy;
    }

    /**
     * 懒汉式单例线程安全解决方案,加上同步代码块
     */
    public static SingletonLazy getInstanceSafe() {
        synchronized (SingletonLazy.class) {
            if (lazy == null) {//可能有多个线程同时执行lazy==null,造成创建多个实例
                lazy = new SingletonLazy();
            }
            return lazy;
        }
    }

    public static void main(String[] args) {
        /*        *//**
         * 单线程调用懒汉单例，安全
         *//*
        SingletonLazy lazy1=SingletonLazy.getInstance();
        SingletonLazy lazy2=SingletonLazy.getInstance();
        System.out.println(lazy1==lazy2);*/

        /**
         * 多线程调用懒汉单例，非线程安全
         */
        ThreadTest1[] threadTest1s = new ThreadTest1[100];
        for (int i = 0; i < threadTest1s.length; i++) {
            threadTest1s[i] = new ThreadTest1();
        }
        for (int i = 0; i < threadTest1s.length; i++) {
            threadTest1s[i].start();
        }



    }
}

class ThreadTest1 extends Thread {

    @Override
    public void run() {
        int hashCode = SingletonLazy.getInstance().hashCode();
        System.out.println(hashCode);
    }
}

class ThreadTest2 implements Runnable{

    @Override
    public void run() {
        int hashCode=SingletonLazy.getInstanceSafe().hashCode();
        System.out.println(hashCode);
    }
}
