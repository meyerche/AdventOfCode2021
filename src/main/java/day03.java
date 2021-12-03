import shared.loadInputFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day03 {
    public static void main(String[] args) {
        List<String> data = loadInputFile.ReadFileString("day03_input.txt");

        List<Integer> digitCount = data.stream()
                .map(day03::MapToIntList)
                .reduce(new ArrayList<>(Collections.nCopies(12, 0)), day03::SumList);

        Double gamma = GetGamma(digitCount, data.size());
        Double epsilon = GetEpsilon(digitCount, data.size());

        System.out.println(gamma * epsilon);
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
