import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class day17 {
    public static void main(String[] args) {
        int xmin, xmax, ymin, ymax;

        xmin = 248;
        xmax = 285;
        ymin = -56;
        ymax = -85;

        List<Integer> yHits = new ArrayList<>();
        List<Integer> xHits = new ArrayList<>();

        HashMap<Integer, List<Integer>> xStepMap = new HashMap<>();
        HashMap<Integer, List<Integer>> yStepMap = new HashMap<>();


        for (int vel0 = 1; vel0 <= xmax; vel0++) {
            int count = 0;
            int x = 0;
            xHits = new ArrayList<>();
            int dx = vel0;

            while (dx >= 0) {
                count++;
                x += dx;
                if (x >= xmin && x <= xmax) {
                    List<Integer> tempList = xStepMap.getOrDefault(count, new ArrayList<>());
                    tempList.add(vel0);
                    //xHits.add(vel0);
                    xStepMap.put(count, tempList);
                }
                dx--;
            }
        }

        for (int vel0 = ymax; vel0 < Math.abs(ymax); vel0++) {
            int count = 0;
            int y = 0;
            yHits = new ArrayList<>();
            int dy = vel0;

            while (y >= ymax) {
                count++;
                y += dy;
                if (y <= ymin && y >= ymax) {
                    //yHits.add(vel0);
                    List<Integer> tempList = yStepMap.getOrDefault(count, new ArrayList<>());
                    tempList.add(vel0);
                    yStepMap.put(count, yHits);
                }
                dy--;
            }
        }

        int count = 0;
        count = xStepMap.entrySet().stream()
                .map(x -> x.getValue().size() * yStepMap.getOrDefault(x.getKey(), new ArrayList<>()).size())
                .reduce(0, Integer::sum);

        int count2 = yStepMap.entrySet().stream()
                .map(y -> y.getValue().size() * xStepMap.getOrDefault(y.getKey(), new ArrayList<>()).size())
                .reduce(0, Integer::sum);

        System.out.println(count);
        System.out.println(count2);
        System.out.println(xStepMap);
        System.out.println(yStepMap);



        //176 - low
        //203 - low
    }
}
