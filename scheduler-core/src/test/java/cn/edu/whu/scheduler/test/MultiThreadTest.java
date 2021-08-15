package cn.edu.whu.scheduler.test;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/15 14:35
 **/
public class MultiThreadTest {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("sb");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(sb);
            }
        }).start();
    }
}
