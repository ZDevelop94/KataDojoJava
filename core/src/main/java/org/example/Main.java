package org.example;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        callFizzBuzz();
    }

    public static void callFizzBuzz(){
        System.out.println("\n----Hello and welcome to Fizz Buzz!----\n");
        FizzBuzzService service = new FizzBuzzService();
        var outputFizzBuzzs = IntStream.rangeClosed(1, 100).mapToObj(service::isFizOrBuzz);
        outputFizzBuzzs.forEach(System.out::println);
    }
}