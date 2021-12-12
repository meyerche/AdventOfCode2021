import shared.loadInputFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class day12 {
    public static HashMap<String, TreeSet<String>> connections;
    public static List<Deque<String>> paths;
    public static List<Deque<String>> paths2;

    public static void main(String[] args) {
        List<String> data = loadInputFile.ReadFileString("day12_input.txt");
        connections = new HashMap<>();
        paths = new ArrayList<>();
        paths2 = new ArrayList<>();

        data.forEach(s -> {
            String[] pair = s.split("-");
            AddConnection(pair[0], pair[1]);
            AddConnection(pair[1], pair[0]);
        });

        FindAllPaths();

        //System.out.println(connections);
        System.out.println(paths2.size());
    }

    private static void FindAllPaths() {
        Deque<String> path = new ArrayDeque<>();
        path.addFirst("start");

        //part 1
        //NextStep(path);

        //for part 2, add an indicator
        NextStep2(path);

    }

    private static void NextStep(Deque<String> path) {
        if (!path.isEmpty() && path.peekLast().equals("end")) return;

        for (String next: connections.get(path.peekLast())) {
            if (next.equals("end")) {
                path.addLast(next);
                paths.add(path);  //reached the end...add to path list
                //System.out.println(path);
            }
            else if (Character.isLowerCase(next.charAt(0)) && path.contains(next)) {
                //cannot go to a lower case node if already on the path
                continue;
            }
            else {
                Deque<String> newPath = new ArrayDeque<>(path);
                newPath.addLast(next);
                NextStep(newPath);
            }
        }
    }

    private static void NextStep2(Deque<String> path) {
        if (!path.isEmpty() && path.peekLast().equals("end")) return;  //this path has ended...go home

        for (String next: connections.get(path.peekLast())) {
            if (next.equals("end")) {
                Deque<String> endPath = new ArrayDeque<>(path);
                endPath.addLast(next);
                paths2.add(endPath);  //reached the end...add to path list
                //System.out.println("End: " + endPath);
            }
            else if (next.equals("start")) {
                //cannot go back to start
                continue;
            }
            else if (Character.isLowerCase(next.charAt(0))
                    && hasDuplicates(path.stream().filter(x -> Character.isLowerCase(x.charAt(0))))
                    && path.contains(next)) {
                continue;
            }
            else {
                Deque<String> newPath = new ArrayDeque<>(path);
                newPath.addLast(next);
                NextStep2(newPath);
            }
        }
    }

    private static <T> boolean hasDuplicates(Stream<T> stream) {
        //return true if duplicates are found (i.e. NOT Empty), else false
        Set<T> items = new HashSet<>();
        return !stream.filter(x -> !items.add(x)).collect(Collectors.toSet()).isEmpty();
    }

    private static void AddConnection(String s1, String s2) {
        if (!connections.containsKey(s1)) connections.put(s1, new TreeSet<>());
        TreeSet<String> curSet = connections.get(s1);
        curSet.add(s2);
    }

    //part 2
    //17429 - low
    //18334 - low
}
