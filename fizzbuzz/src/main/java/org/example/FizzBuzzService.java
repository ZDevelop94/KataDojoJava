package org.example;

public class FizzBuzzService {

    @FunctionalInterface
    private interface Operation {
        Boolean isFizzOrBuzz(int a);
    }

    public String isFizOrBuzz(int number) {

        Object number2 = number;
        Operation isFizz = (x) -> x % 3 == 0;
        Operation isBuzz = (x) -> x % 5 == 0;

        return switch (number2) {
            case Integer n when isFizz.isFizzOrBuzz(n) -> new Fizz().value();
            case Integer n when isBuzz.isFizzOrBuzz(n) -> new Buzz().value();
            default -> String.valueOf(number2);
        };
    }
}

interface FizzBuzz {
    String value();
}

class Fizz implements FizzBuzz {
    public String value() {
        return "Fizz";
    }
}

class Buzz implements FizzBuzz {
    public String value() {
        return "Buzz";
    }
}


