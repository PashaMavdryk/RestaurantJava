package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException{
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException{
        String s;
        List<Dish> dishes = new ArrayList<>();
        while (!(s = readString()).equalsIgnoreCase("exit")) {
            try {
                dishes.add(Dish.valueOf(s));
            } catch (IllegalArgumentException e) {
                ConsoleHelper.writeMessage(s + " is not detected");
            }
        }
        return dishes;
    }
}
