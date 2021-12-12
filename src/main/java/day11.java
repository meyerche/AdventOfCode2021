import java.util.*;
import java.util.stream.IntStream;

import shared.loadInputFile;

public class day11 {
    private static final int STEPS = 100;
    private static HashMap<List<Integer>, Integer> octopuses;
    //private static HashMap<Integer[], Boolean> flashSet;

    public static void main(String[] args) {
        //List<String> data = loadInputFile.ReadFileString("day11_input_test.txt");
        Scanner scanner = new Scanner(Objects.requireNonNull(loadInputFile.class.getClassLoader().getResourceAsStream("day11_input.txt")));
        octopuses = new HashMap<>();

        int row = 0;
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split("");
            for (int i = 0; i < line.length; i++) {
                octopuses.put(Arrays.asList(row, i), Integer.parseInt(line[i]));
            }
            row++;
        }

        int flashCount = Part1();
        System.out.println(flashCount);
    }

    private static int Part1() {
        int step = 0;
        int flashCount = 0;
        Stack<List<Integer>> flashes = new Stack<>();

        //for part 1, change condition to step < STEPS
        while (true) {
            octopuses.entrySet().stream().forEach(o -> IncrementOctopus(o.getKey(), o.getValue(), true));
            octopuses.entrySet().stream()
                    .filter(oct -> oct.getValue() == 10)
                    .map(oct -> oct.getKey())
                    .forEach(flashes::push);

            while (flashes.size() > 0) {
                List<Integer> octopus = flashes.pop();
                Flash(octopus, flashes);
            }

            Long newFlashes = octopuses.entrySet().stream().filter(o -> o.getValue() == 0).count();
            flashCount += newFlashes;
            step++;

            if (newFlashes == 100L) {
                System.out.println("Step: " + step);
                break; //break out of endless loop...you're done.
            }

        }

        return flashCount;
    }

    private static void PrintBoard(int step) {
        System.out.println("Step " + step);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(String.format("%1$4s", octopuses.get(List.of(i,j))));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void Flash(List<Integer> octopus, Stack<List<Integer>> flashes) {
        List<Integer> up_left = Arrays.asList(octopus.get(0)-1, octopus.get(1)-1);
        List<Integer> up = Arrays.asList(octopus.get(0)-1, octopus.get(1));
        List<Integer> up_right = Arrays.asList(octopus.get(0)-1, octopus.get(1)+1);
        List<Integer> right = Arrays.asList(octopus.get(0), octopus.get(1)+1);
        List<Integer> down_right = Arrays.asList(octopus.get(0)+1, octopus.get(1)+1);
        List<Integer> down = Arrays.asList(octopus.get(0)+1, octopus.get(1));
        List<Integer> down_left = Arrays.asList(octopus.get(0)+1, octopus.get(1)-1);
        List<Integer> left = Arrays.asList(octopus.get(0), octopus.get(1)-1);

        if (NeighborOctopus(up_left)) flashes.push(up_left);
        if (NeighborOctopus(up)) flashes.push(up);
        if (NeighborOctopus(up_right)) flashes.push(up_right);
        if (NeighborOctopus(right)) flashes.push(right);
        if (NeighborOctopus(down_right)) flashes.push(down_right);
        if (NeighborOctopus(down)) flashes.push(down);
        if (NeighborOctopus(down_left)) flashes.push(down_left);
        if (NeighborOctopus(left)) flashes.push(left);

        octopuses.put(octopus, 0);
    }

    private static boolean NeighborOctopus(List<Integer> octopus) {
        boolean isNewFlash = false;
        if (octopuses.containsKey(octopus)) {
            IncrementOctopus(octopus, octopuses.get(octopus), false);
            isNewFlash = octopuses.get(octopus) == 10;
        }
        return isNewFlash;
    }

    private static void IncrementOctopus(List<Integer> octopus, int curEnergy, boolean isNewStep) {
        if (isNewStep || (curEnergy != 0)) {
            octopuses.put(octopus, curEnergy + 1);
        }
    }

}
