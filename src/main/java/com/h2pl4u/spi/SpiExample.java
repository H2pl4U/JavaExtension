package com.h2pl4u.spi;

import java.util.ServiceLoader;

public class SpiExample {
    public static void main(String[] args) {
         ServiceLoader<SpiService> serviceLoader = ServiceLoader.load(SpiService.class);
         for (SpiService spiService : serviceLoader) {
             spiService.execute();
         }
    }
}
