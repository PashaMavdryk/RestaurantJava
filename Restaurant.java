package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private final static int  ORDER_CREATING_INTERVAL = 100;
    private final  static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();


    public static void main(String[] args) throws InterruptedException {
        Locale.setDefault(Locale.ENGLISH);

        Cook cookVictor = new Cook("Victor Barinov");
        Cook cookMaks = new Cook("Maks Lavrov");
        cookVictor.setQueue(orderQueue);
        cookMaks.setQueue(orderQueue);
        
        List<Tablet> tablets = new ArrayList<>();

        for(int i = 0;i < 5; i++){
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        Waiter waitor = new Waiter();
        cookVictor.addObserver(waitor);
        cookMaks.addObserver(waitor);

        Thread t = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        t.start();
        try
        {
            Thread.sleep(1000);

        }
        catch (InterruptedException e)
        {

        }
        t.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
