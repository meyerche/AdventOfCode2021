import shared.loadInputFile;

import java.util.*;
import java.util.stream.Collectors;

public class day06 {
    public static List<Integer> fish;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                Objects.requireNonNull(
                        loadInputFile.class.getClassLoader().getResourceAsStream("day06_input.txt")
                )
        );

        //initialize fish list
        fish = Arrays.stream(scanner.nextLine().split(",")).map(Integer::parseInt).collect(Collectors.toList());
        //fish = Arrays.stream("3,4,3,1,2".split(",")).map(Integer::parseInt).collect(Collectors.toList());

        int days = 256;
        Long numOfNewFish;

        List<Long> numOfFish = new ArrayList<>(Collections.nCopies(9, 0L));
        fish.forEach(x -> numOfFish.set(x, numOfFish.get(x)+1L));

        for (int i = 0; i < days; i++) {
            numOfNewFish = numOfFish.get(0);
            Collections.rotate(numOfFish, -1);
            numOfFish.set(6, numOfFish.get(6) + numOfNewFish);

            System.out.println(i + " " + numOfFish.stream().reduce(0L, Long::sum)) ;
        }
    }

//    private static void addFish(Integer i) {
//        if (fish.get(i) == -1) {
//            fish.add(8);
//            fish.set(i, 6);
//        }
//    }
//
//    private static void addNFish(Long n) {
//
//        List<Integer> newFish = new ArrayList<>(Collections.nCopies(n.intValue(), 8));
//        fish.addAll(newFish);
//    }

    //part2 guess...
    //1619067452340 (high)
    //1589590444365 (correct)
}
