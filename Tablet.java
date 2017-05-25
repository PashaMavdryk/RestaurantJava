package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet{
    protected final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder() {
        Order order = null;
        try {
            order = new Order(this);
            if (!order.isEmpty()) {
                AdvertisementManager manager = new AdvertisementManager(order.getTotalCookingTime() * 60);
                ConsoleHelper.writeMessage(order.toString());
                try {
                    manager.processVideos();
                } catch (NoVideoAvailableException e) {
                    logger.log(Level.INFO, "No video is available for the order " + order);
                }
                queue.add(order);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
        return order;
    }

    private void insideOrder(Order newOrder) throws IOException {
        if (newOrder.isEmpty()) return;
        ConsoleHelper.writeMessage(newOrder.toString());
        queue.add(newOrder);
        try {
            new AdvertisementManager(newOrder.getTotalCookingTime() * 60).processVideos();
        } catch (NoVideoAvailableException e) {
            StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(newOrder.getTotalCookingTime() * 60));
            logger.log(Level.INFO, "No video is available for the order " + newOrder);
        }
    }

    public void createTestOrder() {
        try {
            final Order newOrder = new TestOrder(this);
            insideOrder(newOrder);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            return;
        }
    }


    @Override
    public String toString() {
        return "Tablet{number=" + number + "}";
    }

    public int getNumber() {
        return number;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }
}