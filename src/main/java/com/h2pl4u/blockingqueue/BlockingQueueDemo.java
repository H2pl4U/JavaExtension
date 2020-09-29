package com.h2pl4u.blockingqueue;

import java.util.concurrent.*;

/**
 * BlockingQueue即阻塞队列
 * 线程1往阻塞队列中添加元素，线程2从阻塞队列中移出元素。当阻塞队列是空的时候，
 * 从队列中获取元素的操作将会被阻塞；当阻塞队列是满的时候，往队列中添加元素的
 * 操作将会被阻塞。使用BlockingQueue的好处是，我们不需要关心什么时候需要阻
 * 塞线程，什么时候需要唤醒线程，这些都由BlockingQueue自定完成。
 * Created by Liuwei on 2020/9/29 9:57
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        //ArrayBlockingQueue是一个用数组实现的有界阻塞队列。此队列按照先进先出（FIFO）的原则对元素进行排序。
        // 默认情况下不保证访问者公平的访问队列，所谓公平访问队列是指阻塞的所有生产者线程或消费者线程，当队列可用时，
        // 可以按照阻塞的先后顺序访问队列，即先阻塞的生产者线程，可以先往队列里插入元素，先阻塞的消费者线程，可以先从队列里获取元素
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(5);

        //LinkedBlockingQueue是一个用链表实现的有界阻塞队列。此队列按照先进先出的原则对元素进行排序。
        // 此队列的默认和最大长度为Integer.MAX_VALUE,所以推荐的做法是不要使用无参构造器，而是通过有
        // 参构造器指定容器的初始大小。
        LinkedBlockingQueue<Object> linkedBlockingQueue = new LinkedBlockingQueue<>(5);

        //PriorityBlockingQueue是一个支持优先级的无界队列。默认情况下元素采取自然顺序排列，
        // 也可以通过比较器comparator来指定元素的排序规则。元素按照升序排列。
        PriorityBlockingQueue<Object> priorityBlockingQueue = new PriorityBlockingQueue<>();

        //DelayQueue也是一个无界阻塞队列，只有在延迟期满时才能从中提取元素。DelayQueue的所有方法只能操作“到期的元素“，
        // 例如，poll()、remove()、size()等方法，都会忽略掉未到期的元素
        DelayQueue delayQueue = new DelayQueue();

        //SynchronousQueue是一个不存储元素的阻塞队列。每一个put操作必须等待一个take操作，否则不能继续添加元素。
        // SynchronousQueue可以看成是一个传球手，负责把生产者线程处理的数据直接传递给消费者线程。队列本身并不存
        // 储任何元素，非常适合于传递性场景,比如在一个线程中使用的数据，传递给另外一个线程使用
        SynchronousQueue synchronousQueue = new SynchronousQueue();

        //LinkedTransferQueue是一个由链表结构组成的无界阻塞TransferQueue队列。
        // 相对于其他阻塞队列LinkedTransferQueue多了tryTransfer和transfer方法。
        LinkedTransferQueue<Object> linkedTransferQueue = new LinkedTransferQueue<>();

        //LinkedBlockingDeque是一个由链表结构组成的双向阻塞队列。所谓双向队列指的你可以从队列的两端插入和移出元素。
        // 双端队列因为多了一个操作队列的入口，在多线程同时入队时，也就减少了一半的竞争。
        // 相比其他的阻塞队列，LinkedBlockingDeque多了一些首尾元素的操作方法
        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<>();

    }
}
