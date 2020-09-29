package com.h2pl4u.designpattern;

/**
 * 外观模式
 * 外观模式又叫门面模式，提供了统一得接口，用来访问子系统中的一群接口。
 * 适用于：
 * - 子系统越来越复杂，增加外观模式提供简单接口调用；
 * - 构建多层系统结构，利用外观对象作为每层的入口，简化层间调用。
 * 优点：
 * - 简化了调用过程，无需了解深入子系统；
 * - 减低耦合度；
 * - 更好的层次划分；
 * - 符合迪米特法则。
 * 缺点：
 * - 增加子系统，拓展子系统行为容易引入风险；
 * - 不符合开闭原则。
 * Created by Liuwei on 2020/9/29 15:38
 */
public class Facade {

    static class Takeaway {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class OrderService {
        public boolean placeAndOrder(Takeaway takeaway) {
            System.out.println(takeaway.getName() + "创建了订单");
            return true;
        }
    }

    static class PayService {
        public boolean pay(Takeaway takeaway) {
            System.out.println(takeaway.getName() + "完成了支付");
            return true;
        }
    }

    static class DeliveryService {
        public void delivery(Takeaway takeaway) {
            System.out.println(takeaway.getName() + "已由骑手冯曾科接单，订单派送中");
        }
    }

    static class TakeawayService {
        OrderService orderService = new OrderService();
        PayService payService = new PayService();
        DeliveryService deliveryService = new DeliveryService();

        public void takeOrder(Takeaway takeaway) {
            if (orderService.placeAndOrder(takeaway)) {
                if (payService.pay(takeaway)) {
                    deliveryService.delivery(takeaway);
                }
            }
        }
    }

    public static void main(String[] args) {
        Takeaway takeaway = new Takeaway();
        takeaway.setName("🐔🐔✈");
        TakeawayService takeawayService = new TakeawayService();
        takeawayService.takeOrder(takeaway);
    }

}
