package com.h2pl4u.designpattern;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略模式
 * 策略模式定义了算法家族，分别封装起来，让它们之间可以互相替换。此模式让算法的变化不
 * 会影响到使用算法的用户。策略模式常用于消除大量的if else代码。
 * 适用场景：
 * 系统有很多类，它们的区别仅仅在于行为不同；
 * 一个系统需要动态地在几种算法中选择一种；
 * Created by Liuwei on 2020/9/30 14:32
 */
public class Strategy {
    interface PromotionStrategy {
        void promotion();
    }

    public static class FullReductionPromotionStrategy implements PromotionStrategy {
        @Override
        public void promotion() {
            System.out.println("满1000立减50");
        }
    }

    public static class DiscountPromotionStrategy implements PromotionStrategy {
        @Override
        public void promotion() {
            System.out.println("全场9.8折优惠");
        }
    }

    //策略模式常结合工厂模式来消除大量的if else代码，新建一个促销策略的创建工厂：
    static class PromotionStrategyFactory {
        private static final Map<String, PromotionStrategy> PROMOTION_STRATEGY_MAP = new HashMap<>();
        private static final PromotionStrategy NON_PROMOTION = () -> System.out.println("无促销活动");

        static {
            PROMOTION_STRATEGY_MAP.put("fr", new FullReductionPromotionStrategy());
            PROMOTION_STRATEGY_MAP.put("ds", new DiscountPromotionStrategy());
        }

        private PromotionStrategyFactory() {

        }

        private static PromotionStrategy getPromotionStrategy(String promotionKey) {
            PromotionStrategy strategy = PROMOTION_STRATEGY_MAP.get(promotionKey);
            return strategy == null ? NON_PROMOTION : strategy;
        }
    }


    public static void main(String[] args) {
        //模拟客户端传递的促销策略key
        String promotionKey = "fr";
//        PromotionStrategy strategy;
//        if ("fr".equals(promotionKey)) {
//            strategy = new FullReductionPromotionStrategy();
//        } else if ("ds".equals(promotionKey)) {
//            strategy = new DiscountPromotionStrategy();
//        } else {
//            throw new RuntimeException();
//        }
//        strategy.promotion();

        // 通过Map来装载促销策略，这样可以减少对象的重复创建。
        // 如果不希望在static块中一次性初始化所有促销策略，可以结合享元模式来推迟对象创建过程
        PromotionStrategy promotionStrategy = PromotionStrategyFactory.getPromotionStrategy(promotionKey);
        promotionStrategy.promotion();
    }
}
