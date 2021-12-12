import shared.loadInputFile;

import java.util.*;
import java.util.stream.Collectors;

public class day10 {
    private static HashMap<String, Integer> errorScore;
    private static HashMap<String, Integer> completionScore;
    private static HashMap<String, String> chunkSet;

    public static void main(String[] args) {
        List<String> data = loadInputFile.ReadFileString("day10_input.txt");

        errorScore = new HashMap<>();
        errorScore.put(")", 3);
        errorScore.put("]", 57);
        errorScore.put("}", 1197);
        errorScore.put(">", 25137);
        errorScore.put("no error", 0);

        completionScore = new HashMap<>();
        completionScore.put("(", 1);
        completionScore.put("[", 2);
        completionScore.put("{", 3);
        completionScore.put("<", 4);

        chunkSet = new HashMap<>();
        chunkSet.put(")", "(");
        chunkSet.put("]", "[");
        chunkSet.put("}", "{");
        chunkSet.put(">", "<");

        Integer totalScore = data.stream().map(day10::FindErrorScore).reduce(0, Integer::sum);
        List<Long> lstCompScores = data.stream()
                .filter(s -> FindErrorScore(s) == 0)
                .map(day10::FindCompScore)
                .sorted()
                .collect(Collectors.toList());

        System.out.println(totalScore);

        System.out.println(lstCompScores.get(lstCompScores.size()/2));
    }

    private static Long FindCompScore(String s) {
        Stack<String> seq = new Stack<>();
        List<String> input = Arrays.stream(s.split("")).collect(Collectors.toList());
        String lastInput = "";
        String nextInput = "";
        Long compTotal = 0L;

        while (input.size() > 0) {
            nextInput = input.remove(0);
            if (chunkSet.containsKey(nextInput)) {
                lastInput = seq.pop();
            }
            else {
                //add to top of stack
                seq.push(nextInput);
            }
        }

        while (seq.size() > 0) {
            compTotal = 5*compTotal + completionScore.get(seq.pop());
        }

        return compTotal;
    }

    private static Integer FindErrorScore(String s) {
        Stack<String> seq = new Stack<>();
        List<String> input = Arrays.stream(s.split("")).collect(Collectors.toList());
        boolean foundError = false;

        String lastInput = "";
        String nextInput = "";

        while (!foundError) {
            if (input.size() == 0) {
                nextInput = "no error";
                break;
            }

            nextInput = input.remove(0);
            if (chunkSet.containsKey(nextInput)) {
                //pop the stack and check if they're equal
                lastInput = seq.pop();

                //not equal = error
                if (!lastInput.equals(chunkSet.get(nextInput))) {
                    foundError = true;
                }
            }
            else {
                //add to top of stack
                seq.push(nextInput);
            }
        }

        return errorScore.get(nextInput);
    }

    //part 1
    //653994 too high

    //part 2
    //365176497 too low
}
