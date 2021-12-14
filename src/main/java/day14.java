import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;


public class day14 {
    public static void main(String[] args) {
        InputStream is = day14.class.getClassLoader().getResourceAsStream("day14_input_test.txt");
        assert is != null;
        Scanner scanner = new Scanner(is);

        HashMap<List<String>, String> rules = new HashMap<>();

        String template = scanner.nextLine();

        while (scanner.hasNext()) {
            String insertionRule = scanner.nextLine();

            if (!insertionRule.isBlank()) {
                List<String> pair = Arrays.asList(insertionRule.split(" -> ")[0].split(""));
                String monomer = insertionRule.split(" -> ")[1];
                rules.put(pair, monomer);
            }
        }

        System.out.println(template);
        System.out.println(rules);

        String polymer = Polymerize(template, rules, 10);
        int mostCommont = FindMonomer(polymer, Integer::max);
        int leastCommont = FindMonomer(polymer, Integer::min);
    }

    private static String Polymerize(String template, HashMap<List<String>, String> rules, int steps) {
        String newTemplate = template;
        for (int i = 0; i < steps; i++) {
            IntStream.range(0,newTemplate.length())
                    .forEach(j -> {
                        Arrays.asList(template.split()).
                    });
        }
    }
}
