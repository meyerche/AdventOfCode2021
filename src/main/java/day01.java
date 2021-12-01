import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class day01 {
    public static int globalCount = 0;

    public static void main(String[] args) {

        InputStream is = day01.class.getClassLoader().getResourceAsStream("day01_input.txt");
        if (is == null) throw new AssertionError();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        List<Integer> in = reader.lines().map(Integer::parseInt).collect(Collectors.toList());

        Integer count = countIncreases(in);
        Integer count2 = countIncreasesByThree(in);
        System.out.println(count + " " + count2);

        //try new aggregate function with stream
        in.stream().reduce(day01::nextIncrease);
        System.out.println("Functional count:  " + globalCount);

    }

    private static Integer countIncreases(List<Integer> in) {
        Integer count = 0;

        ListIterator<Integer> itr = in.listIterator();

        int i = itr.next();
        int j;

        while (itr.hasNext()) {
            j = itr.next();

            if (j > i) {
                count++;
            }

            i = j;
        }

        return count;
    }

    private static Integer countIncreasesByThree(List<Integer> in) {
        Integer count = 0;
        ListIterator<Integer> itr = in.listIterator();

        int i;
        int currentSum = in.get(0) + in.get(1) + in.get(2);
        int nextSum = 0;

        while (itr.hasNext()) {
            itr.next();
            i = itr.nextIndex();
            if ((i+3) < in.size()) {
                nextSum = in.get(i+1) + in.get(i+2) + in.get(i+3);

                if (nextSum > currentSum) {
                    count++;
                }
            }


            currentSum = nextSum;
        }

        return count;
    }

    private static int nextIncrease(int aggregate, int nextNumber) {
        if (nextNumber > aggregate) {
            globalCount++;
        }

        return nextNumber;
    }
}
