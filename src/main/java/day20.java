import shared.loadInputFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day20 {
    public static final int[] dx = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
    public static final int[] dy = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
    private static final int RUN_TIMES = 50;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(Objects.requireNonNull(loadInputFile.class.getClassLoader().getResourceAsStream("day20_input.txt")));

        //first line is the image enhancement algorithm
        String algo = scanner.nextLine();

        //initialize input map
        HashMap<List<Integer>, Integer> imageMap = new HashMap<>();
        int y = 0; //line count
        while (scanner.hasNext()) {
            String imageLine = scanner.nextLine();

            if (imageLine.isBlank()) continue;  //blank line between algorithm and image

            int lineLength = imageLine.length();

            for (int x = 0; x < lineLength; x++) {
                List<Integer> coord = List.of(x,y);
                Integer pixValue = imageLine.charAt(x) == '#' ? 1 : 0;

                imageMap.put(coord, pixValue);
            }

            y++;
        }

        //if the first character inthe algorithm is '#' all the infinite pixels will turn light on the first pass
        //if the last character in the algorithm is '.' all the infinite pixels will turn dark again on the second pass
        //so the default value of the "infinite" pixels needs to change depending on the first and last algorithm value
        int defaultVal = 0;
        for (int i = 0; i < RUN_TIMES; i++) {
            if (algo.charAt(0) == '.') defaultVal = 0;
            if (algo.charAt(0) == '#' && algo.charAt(algo.length()-1) == '.') defaultVal = i%2;
            if (algo.charAt(0) == '#' && algo.charAt(algo.length()-1) == '#') defaultVal = i > 0 ? 1 : 0;

            imageMap = Part1(imageMap, algo, defaultVal);
        }

        //PrintImage(imageMap, y);

        int countPixels = imageMap.values().stream().reduce(0, Integer::sum);
        System.out.println(countPixels);
    }

    private static void PrintImage(HashMap<List<Integer>, Integer> imageMap, int size) {
        for (int j = 0; j < size+1; j++) {
            for (int i = 0; i < size+1; i++) {
                System.out.print(imageMap.getOrDefault(List.of(i,j), 0) == 1 ? "#" : ".");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static HashMap<List<Integer>, Integer> Part1(HashMap<List<Integer>, Integer> imageMap, String algo, int defaultVal) {
        //create output image
        HashMap<List<Integer>, Integer> outputMap = new HashMap<>(imageMap);

        //add "infinite" lines and columns


        int imageWidth = FindDimension(imageMap, 0);
        int imageHeight = FindDimension(imageMap, 1);
        int algoIndex, outputValue;

        for (int j = -2; j < imageHeight+2 ; j++) {
            for (int i = -2; i < imageWidth+2; i++) {
                List<Integer> inputCoord = List.of(i,j);
                List<Integer> outputCoord = List.of(i+1,j+1);
                algoIndex = GetIndex(imageMap, inputCoord, defaultVal);
                outputValue = algo.charAt(algoIndex) == '#' ? 1 : 0;
                outputMap.put(List.of(i+2,j+2), outputValue);
            }
        }

        return outputMap;
    }

    private static int GetIndex(HashMap<List<Integer>, Integer> imageMap, List<Integer> inputCoord, Integer defaultVal) {
        String binString = IntStream.range(0,9)
                .map(i -> imageMap.getOrDefault(List.of(inputCoord.get(0) + dx[i], inputCoord.get(1) +dy[i]), defaultVal))
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(""));

        return Integer.parseInt(binString, 2);
    }

    private static int FindDimension(HashMap<List<Integer>, Integer> imageMap, int dim) {
        return imageMap.keySet().stream().map(coord -> coord.get(dim)).max(Integer::compare).get() + 1;
    }

    //1919 - low
    //5406 - high
}
