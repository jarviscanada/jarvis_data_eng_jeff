package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.lang.Math;

public class LambdaStreamExc {

  /**
   * Create a String stream from array
   *
   * note: arbitrary number of value will be stored in an array
   *
   * @param strings the strings to combine into one String stream
   * @return a String that contains the whole string from the array
   */
  Stream<String> createStrStream(String[]  strings){
    return Arrays.stream(strings);
  }

  /**
   * Convert all strings to uppercase
   * please use createStrStream
   *
   * @param strings a list of strings
   * @return the stream of Strings in uppercase
   */
  Stream<String> toUpperCase(String[] strings){
    return createStrStream(strings).map(String::toUpperCase);
  }

  /**
   * filter strings that contains the pattern
   * e.g.
   * filter(stringStream, "a") will return another stream which no element contains a
   *
   *
   * @param stringStream strings to be matched with
   * @param pattern a pattern to match for each string
   * @return filter the strings returns the ones that does not the pattern
   */
  Stream<String> filter(Stream<String> stringStream, String pattern){
    return stringStream.filter(e -> !(e.contains(pattern)));
  }

  /**
   * Create a intStream from a arr[]
   * @param arr array of int
   * @return converts an array of int to an IntStream
   */
  IntStream createIntStream(int[] arr){
    return Arrays.stream(arr);
  }

  /**
   * Convert a stream to list
   *
   * @param stream a stream of element E
   * @param <E> any element
   * @return a list of that element
   */
  <E> List<E> toList(Stream<E> stream){
    return stream.collect(Collectors.toList());
  }

  /**
   * Convert a intStream to list
   * @param intStream a stream of int
   * @return a list of Integers
   */
  List<Integer> toList(IntStream intStream){
    return intStream.boxed().collect(Collectors.toList());
  }

  /**
   * Create a IntStream range from start to end inclusive
   * @param start an integer number to start at
   * @param end the integer to end on
   * @return a IntStream with numbers from start to end
   */
  IntStream createIntStream(int start, int end) {
    return IntStream.rangeClosed(start,end);
  }
  /**
   * Convert a intStream to a doubleStream
   * and compute square root of each element
   * @param intStream a string of Int
   * @return a stream of doubles where every element is the square root of the intStream
   */
  DoubleStream squareRootIntStream(IntStream intStream){
    return intStream.asDoubleStream()
            .map(Math::sqrt);
  }

  /**
   * filter all even number and return odd numbers from a intStream
   * @param intStream an IntStream
   * @return an IntStream without even numbers
   */
  IntStream getOdd(IntStream intStream){
    return intStream.filter(e -> e % 2 == 1);
  }

  /**
   * Return a lambda function that prints a message with a prefix and suffix
   * This lambda can be useful to format logs
   *
   * You will learn:
   *   - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig
   *   - lambda syntax
   *
   * e.g.
   * LambdaStreamExc lse = new LambdaStreamImp();
   * Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
   * printer.accept("Message body");
   *
   * sout:
   * start>Message body<end
   *
   * @param prefix prefix str
   * @param suffix suffix str
   * @return a Consumer string that will add prefix and suffix the beginning and end of the input
   * for the consumer
   */
  Consumer<String> getLambdaPrinter(String prefix, String suffix){
    Consumer<String> stringConsumer = input -> System.out.println(prefix + input + suffix);
    return stringConsumer;
  }

  /**
   * Print each message with a given printer
   *
   * e.g.
   * String[] messages = {"a","b", "c"};
   * lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!") );
   *
   * sout:
   * msg:a!
   * msg:b!
   * msg:c!
   *
   * @param messages is the array of messages to be printed
   * @param printer is the Consumer that will add the prefix and suffix
   */
  void printMessages(String[] messages, Consumer<String> printer){
    for (String message : messages){
      printer.accept(message);
    }
  }

  /**
   * Print all odd number from a intStream.
   * Please use `createIntStream` and `getLambdaPrinter` methods
   *
   * e.g.
   * lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
   *
   * sout:
   * odd number:1!
   * odd number:3!
   * odd number:5!
   *
   * @param intStream an IntStream
   * @param printer
   */
  void printOdd(IntStream intStream, Consumer<String> printer){
    IntStream oddStream = getOdd(intStream);
    oddStream.forEach(x -> printer.accept(String.valueOf(x)));
  }

  /**
   * Square each number from the input.
   * Please write two solutions and compare difference
   *   - using flatMap
   *
   * @param ints a nested stream of ints
   * @return flatten out the stream as well as have every number be squared
   */
  Stream<Integer> flatNestedInt(Stream<List<Integer>> ints){
    Stream<Integer> res =  ints.flatMap(Collection::stream)
                                .map(x -> x^2);
    return res;
  }
}