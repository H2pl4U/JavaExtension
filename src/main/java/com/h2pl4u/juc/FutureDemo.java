package com.h2pl4u.juc;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2021/4/12 13:29
 *
 * @Author Liuwei
 */
public class FutureDemo {
    interface Data {
        String getResult();
    }

    static class RealData implements Data {
        protected final String result;

        public RealData(String para) {
            //假设这里很慢很慢，构造RealData不是一个容易的事
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
            }
            result = para;
        }

        @Override
        public String getResult() {
            return result;
        }
    }

    static class FutureData implements Data {
        // 内部需要维护RealData
        protected RealData realdata = null;
        protected boolean isReady = false;

        public synchronized void setRealData(RealData realdata) {
            if (isReady) {
                return;
            }
            this.realdata = realdata;
            isReady = true;
            //RealData已经被注入，通知getResult()
            notifyAll();
        }

        //会等待RealData构造完成
        @Override
        public synchronized String getResult() {
            while (!isReady) {
                try {
                    //一直等待，直到RealData被注入
                    wait();
                } catch (InterruptedException e) {
                }
            }
            //真正需要的数据从RealData获取
            return realdata.result;
        }
    }

    static class Client {
        //这是一个异步方法，返回的Data接口是一个Future
        public Data request(final String queryStr) {
            final FutureData future = new FutureData();
            new Thread() {
                @Override
                public void run() {
                    // RealData的构建很慢，所以在单独的线程中进行
                    RealData realdata = new RealData(queryStr);
                    //setRealData()的时候会notify()等待在这个future上的对象
                    future.setRealData(realdata);
                }
            }.start();
            // FutureData会被立即返回，不会等待RealData被构造完
            return future;
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕");
        try {
            //这里可以用一个sleep代替了对其他业务逻辑的处理
            //在处理这些业务逻辑的过程中，RealData被创建，从而充分利用了等待时间
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        //使用真实的数据，如果到这里数据还没有准备好，getResult()会等待数据准备完，再返回
        System.out.println("数据 = " + data.getResult());
    }
}
