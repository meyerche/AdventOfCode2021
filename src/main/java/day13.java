import shared.loadInputFile;

import java.util.*;
import java.util.stream.Collectors;

public class day13 {
    public static void main(String[] args) {
        List<String> data = loadInputFile.ReadFileString("day13_input.txt");

        //process file...first the points into a set...then the fold instructions
        HashSet<List<Integer>> dots;
        dots = data.stream()
                .filter(x -> Character.isDigit(x.charAt(0)))
                .map(day13::CoordToList)
                .collect(Collectors.toCollection(HashSet::new));

        Deque<String> folds;
        folds = data.stream()
                .filter(x -> Character.isAlphabetic(x.charAt(0)))
                .map(day13::ExtractFold)
                .collect(Collectors.toCollection(ArrayDeque::new));

        //Part 1:  do one fold
        //Fold(dots, folds);
        //System.out.println(dots.size());

        //Part 2:  do all folds and then print result
        FoldAll(dots, folds);
        PrintDots(dots);

    }

    private static void PrintDots(HashSet<List<Integer>> dots) {
        int xmax = dots.stream().map(x -> x.get(0)).max(Integer::compare).get();
        int ymax = dots.stream().map(y -> y.get(1)).max(Integer::compare).get();
        List<Integer> point;

        for (int i = 0; i <= xmax; i++) {
            for (int j = 0; j <= ymax; j++) {
                point = new ArrayList<>();
                point.add(i);
                point.add(j);

                if (dots.contains(point)) {
                    System.out.print("#");
                }
                else System.out.print(".");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void FoldAll(HashSet<List<Integer>> dots, Deque<String> folds) {
        while (!folds.isEmpty()) {
            Fold(dots, folds);
            if (folds.size() == 1) PrintDots(dots);
        }
    }

    private static void Fold(HashSet<List<Integer>> dots, Deque<String> folds) {
        String fold = folds.removeFirst();
        String axis = fold.split("=")[0];
        int foldLine = Integer.parseInt(fold.split("=")[1]);

        HashSet<List<Integer>> copyDots = new HashSet<>(dots);

        copyDots.forEach(d -> {
            dots.remove(d);
            dots.add(TranslateDot(d, axis, foldLine));
        });

        //return dots.size();
    }

    private static List<Integer> TranslateDot(List<Integer> dot, String axis, int foldLine) {
        List<Integer> newDot = new ArrayList<>(dot);

        if (axis.equals("x")) {
            newDot.set(0, foldLine - Math.abs(dot.get(0) - foldLine));
        } else {
            newDot.set(1, foldLine - Math.abs(dot.get(1) - foldLine));
        }

        return newDot;
    }

    private static String ExtractFold(String s) {
        return s.split(" ")[2];
    }

    private static List<Integer> CoordToList(String s) {
        return Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
}
