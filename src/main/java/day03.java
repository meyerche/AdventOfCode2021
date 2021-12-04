import shared.loadInputFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class day03 {
    public static void main(String[] args) {
        List<String> data = loadInputFile.ReadFileString("day03_input.txt");


        List<Integer> digitCount = GetMostCommon(data);

        Double gamma = GetGamma(digitCount, data.size());
        Double epsilon = GetEpsilon(digitCount, data.size());

        Integer oxygen = filterOxygen(data.stream(), 0);
        Integer co2 = filterC02(data.stream(), 0);

        System.out.println(gamma + " " + epsilon + " " + gamma * epsilon);
        System.out.println(oxygen);
        System.out.println(co2);
        System.out.println(oxygen * co2);
    }

    private static List<Integer> GetMostCommon(List<String> data) {
        return data.stream()
                .map(day03::MapToIntList)
                .reduce(new ArrayList<>(Collections.nCopies(12, 0)), day03::SumList);
    }

    private static Integer filterC02(Stream<String> stream, int pos) {
        List<String> lst = stream.collect(Collectors.toList());

        if (lst.size() == 1) {
            return Integer.parseInt(lst.get(0), 2);
        }

        List<Integer> digitCount = GetMostCommon(lst);

        int filter;
//        filter = digitCount.get(pos) >= Math.ceil(lst.size()/2.0) ? 0 : 1;
        filter = digitCount.get(pos) > lst.size()/2.0 ? 0 : 1;

        return filterC02(lst.stream().filter(s -> s.charAt(pos) % 48 == filter), pos + 1);
    }

    private static Integer filterOxygen(Stream<String> stream, int pos) {
        List<String> lst = stream.collect(Collectors.toList());

        if (lst.size() == 1) {
            return Integer.parseInt(lst.get(0), 2);
            //return lst.stream().map(x -> Integer.parseInt(x,2)).reduce(0, (x,y) -> y);
        }

        List<Integer> digitCount = GetMostCommon(lst);

        //int filter = digitCount.get(pos) >= Math.ceil(lst.size()/2.0) ? 1 : 0;
        int filter = digitCount.get(pos) > lst.size()/2.0 ? 1 : 0;

        return filterOxygen(lst.stream().filter(s -> s.charAt(pos) % 48 == filter), pos + 1);
    }

    private static Double GetEpsilon(List<Integer> digitCount, int size) {
        return IntStream
                .range(0, digitCount.size())
                .mapToObj(i -> (digitCount.get(i) > size/2 ? 0 : 1) * Math.pow(2, (digitCount.size()-i-1)))
                .reduce(0.0, Double::sum);
    }

    private static Double GetGamma(List<Integer> digitCount, int size) {
        return IntStream
                .range(0, digitCount.size())
                .mapToObj(i -> (digitCount.get(i) > size/2 ? 1 : 0) * Math.pow(2, (digitCount.size()-i-1)))
                .reduce(0.0, Double::sum);
    }

    private static List<Integer> MapToIntList(String binString) {
        return binString.chars().mapToObj(x -> x % 48).collect(Collectors.toList());
    }

    private static List<Integer> SumList(List<Integer> x, List<Integer> y) {
        return IntStream.range(0,x.size()).mapToObj(i -> x.get(i) + y.get(i)).collect(Collectors.toList());
    }
}
