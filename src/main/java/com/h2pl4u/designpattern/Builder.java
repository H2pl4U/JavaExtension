package com.h2pl4u.designpattern;

/**
 * 建造者模式也称为生成器模式（Builder Pattern）
 * 将复杂对象的建造过程抽象出来（抽象类别），使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。
 * 简单来说就是，相同的过程可以创建不同的产品。
 * 将复杂对象的建造过程抽象出来（抽象类别），使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。
 * 简单来说就是，相同的过程可以创建不同的产品。
 * 适用于：
 * 一个对象有非常复杂的内部结构（很多属性）
 * 想将复
 * 杂对象的创建和使用分离。
 * 优点：
 * 封装性好，创建和使用分离
 * 拓展性好，建造类之间独立，一定程度上解耦。
 * 缺点：
 * 产生多余的Builder对象；
 * 产品内部发生变化，建造者需要更改，成本较大。
 * Created by Liuwei on 2020/9/29 11:52
 */
public class Builder {
    static class Shop {
        private String name;
        private String location;
        private String type;

        public Shop(ShopBuilder builder) {
            this.name = builder.name;
            this.location = builder.location;
            this.type = builder.type;
        }

        @Override
        public String toString() {
            return "Shop{" +
                    "name='" + name + '\'' +
                    ", location='" + location + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        static class ShopBuilder {
            private String name;
            private String location;
            private String type;

            public ShopBuilder name(String name) {
                this.name = name;
                return this;
            }

            private ShopBuilder location(String location) {
                this.location = location;
                return this;
            }

            private ShopBuilder type(String type) {
                this.type = type;
                return this;
            }

            public Shop build() {
                return new Shop(this);
            }
        }
    }

    static class Animal {
        private Integer id;
        private String name;
        private String sex;
        private Integer age;

        public Animal(AnimalBuilder animalBuilder) {
            this.id = animalBuilder.id;
            this.name = animalBuilder.name;
            this.sex = animalBuilder.sex;
            this.age = animalBuilder.age;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Animal{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age=" + age +
                    '}';
        }

        public static class AnimalBuilder {
            private Integer id;
            private String name;
            private String sex;
            private Integer age;

            public AnimalBuilder id(Integer id) {
                this.id = id;
                return this;
            }

            public AnimalBuilder name(String name) {
                this.name = name;
                return this;
            }

            public AnimalBuilder sex(String sex) {
                this.sex = sex;
                return this;
            }

            public AnimalBuilder age(Integer age) {
                this.age = age;
                return this;
            }

            public Animal build() {
                return new Animal(this);
            }
        }
    }

    public static void main(String[] args) {
        Shop shop = new Shop.ShopBuilder()
                .name("鲜果园")
                .location("上海闵行区")
                .type("水果店")
                .build();
        System.out.println(shop.toString());

        Animal animal = new Animal.AnimalBuilder().id(1).name("老虎").sex("雄").age(10).build();
        System.out.println(animal.toString());
    }
}
