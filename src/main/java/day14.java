import java.io.InputStream;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class day14 {
    static HashMap<List<String>, String> rules; //rules for adding a monomer
    static HashMap<String, Long> monomerCounts; //counts of individual monomers
    static HashMap<List<String>, Long> monomerPairs; //monomer pair counts

    private static final int STEPS = 40;

    public static void main(String[] args) {
        InputStream is = day14.class.getClassLoader().getResourceAsStream("day14_input.txt");
        assert is != null;
        Scanner scanner = new Scanner(is);

        rules = new HashMap<>();
        monomerCounts = new HashMap<>();
        monomerPairs = new HashMap<>();

        String template = scanner.nextLine();
        Arrays.stream(template.split("")).forEach(m -> monomerCounts.merge(m, 1L, Long::sum));
        IntStream.range(0,template.length()-1)
                .forEach(m -> monomerPairs.merge(
                        List.of(String.valueOf(template.charAt(m)), String.valueOf(template.charAt(m+1))),
                        1L,
                        Long::sum));

        while (scanner.hasNext()) {
            String insertionRule = scanner.nextLine();

            if (!insertionRule.isBlank()) {
                List<String> pair = Arrays.asList(insertionRule.split(" -> ")[0].split(""));
                String monomer = insertionRule.split(" -> ")[1];
                rules.put(pair, monomer);
            }
        }

        //System.out.println(template);
        //System.out.println(rules);


        //part 1....
        //String polymer = Polymerize(template);
        //int mostCommon = FindMonomer(polymer, "max");
        //int leastCommon = FindMonomer(polymer, "min");

        //System.out.println(mostCommon - leastCommon);

        //part 2 ...
        PolymerizeInSitu();
        Long mostCommon = monomerCounts.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
        Long leastCommon = monomerCounts.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue();
        System.out.println(mostCommon - leastCommon);


    }

    private static void PolymerizeInSitu() {
        // = (HashMap<List<String>, Long>) monomerPairs.clone();
        String newMonomer;
        Long stepSum;

        for (int i = 0; i < STEPS; i++) {
            HashMap<List<String>, Long> pairsAtStep = new HashMap<>();  //create temporary map
            stepSum = monomerCounts.entrySet().stream().map(x -> x.getValue()).reduce(0L, Long::sum);

            for (Map.Entry<List<String>, Long> p : monomerPairs.entrySet()) {
                newMonomer = rules.get(p.getKey());

                pairsAtStep.merge(List.of(p.getKey().get(0), newMonomer), p.getValue(), Long::sum);
                pairsAtStep.merge(List.of(newMonomer, p.getKey().get(1)), p.getValue(), Long::sum);
                monomerCounts.merge(newMonomer, p.getValue(), Long::sum);
            }

            monomerPairs = pairsAtStep;
        }


    }

    private static int FindMonomer(String polymer, String maxOrMin) {
        int result = 0;

        List<String> monomerList = Arrays.asList(polymer.split(""));
        HashMap<String, Integer> freq = new HashMap<>();

        monomerList.forEach(m -> freq.put(m, Collections.frequency(monomerList, m)));
        if (maxOrMin.equals("max")) {
            result = freq.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
        }
        else if (maxOrMin.equals("min")) {
            result = freq.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue();
        }

        return result;

    }

//    private static int FindMonomer(String polymer) {
//        List<String> monomerList = Arrays.asList(polymer.split(""));
//        HashMap<String, Integer> freq = new HashMap<>();
//        Integer res = monomerList.stream().map(m -> freq.put(m, Collections.frequency(monomerList, m))).reduce(0, Integer::max);
//    }

    private static String Polymerize(String template) {
        List<String> templateList = Arrays.stream(template.split("")).collect(Collectors.toList());

        for (int i = 0; i < STEPS; i++) {
            System.out.println(i);
            IntStream.range(0,templateList.size()-1)
                    .boxed()
                    .sorted(Collections.reverseOrder())
                    .forEach(j -> {
                        String newMonomer = rules.get(templateList.subList(j, j+2));
                        templateList.add(j+1, newMonomer);
                        monomerCounts.merge(newMonomer, 1L, Long::sum);
                    });
        }

        return templateList.stream().collect(Collectors.joining(""));
    }
}
