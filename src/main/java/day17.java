import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class day17 {
    public static void main(String[] args) {
        int xmin, xmax, ymin, ymax;

        xmin = 248;
        xmax = 285;
        ymin = -56;
        ymax = -85;

        //Test Set
        //xmin = 20;
        //xmax = 30;
        //ymin = -5;
        //ymax = -10;

        //Keep map with Key = number of steps to target, Value = List of starting velocities that hit target for given step count
        HashMap<Integer, List<Integer>> xStepMap = new HashMap<>();
        HashMap<Integer, List<Integer>> yStepMap = new HashMap<>();


        //x velocities decrease until x-velocity = 0
        //xmax is the velocity beyond which the probe passes the target in one step
        for (int vel0 = 1; vel0 <= xmax; vel0++) {
            int count = 0;
            int x = 0;
            int dx = vel0;

            //cannot stop at dx = 0 because the probe can still hit the target if moving straight down.  200 is just an
            //arbitrary large number.  IMPROVE:  process y first and then use maximum steps here
            while (count < 200) {
                count++;
                x += dx;
                if (x >= xmin && x <= xmax) {
                    List<Integer> tempList = xStepMap.getOrDefault(count, new ArrayList<>());
                    tempList.add(vel0);
                    xStepMap.put(count, tempList);
                }
                dx = dx == 0 ? 0 : dx-1;
            }
        }

        //y-velocities keep decreasing always.
        //y-velocity min is velocity where probe would miss target in one step
        //y-velocity max is absolute value of that (as probe is coming down, it will cross y=0 at negative start velocity
        for (int vel0 = ymax; vel0 < Math.abs(ymax); vel0++) {
            int count = 0;
            int y = 0;
            int dy = vel0;

            //stop once the probe has travelled beyond the target bounds
            while (y >= ymax) {
                count++;
                y += dy;
                if (y <= ymin && y >= ymax) {
                    List<Integer> tempList = yStepMap.getOrDefault(count, new ArrayList<>());
                    tempList.add(vel0);
                    yStepMap.put(count, tempList);
                }
                dy--;
            }
        }

        //initial velocities must be unique so add all combinations to HashSet to ensure uniqueness
        HashSet<List<Integer>> resultSet = new HashSet<>();

        yStepMap.forEach((key, value) -> value.forEach(
                yvel -> xStepMap.getOrDefault(key, new ArrayList<>()).forEach(
                        xvel -> resultSet.add(List.of(xvel, yvel))
                )
        ));

        //int count = xStepMap.entrySet().stream()
        //        .map(x -> x.getValue().size() * yStepMap.getOrDefault(x.getKey(), new ArrayList<>()).size())
        //        .reduce(0, Integer::sum);
        //
        //int count2 = yStepMap.entrySet().stream()
        //        .map(y -> y.getValue().size() * xStepMap.getOrDefault(y.getKey(), new ArrayList<>()).size())
        //        .reduce(0, Integer::sum);

        //System.out.println(count);
        //System.out.println(count2);
        //System.out.println(xStepMap);
        //System.out.println(yStepMap);

        System.out.println(resultSet.size());



        //176 - low
        //203 - low
        //1826 - low
        //1966
    }
}
