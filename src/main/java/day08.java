import shared.loadInputFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class day08 {
    public static void main(String[] args) {
        List<String> data = loadInputFile.ReadFileString("day08_input.txt");

        Integer countUniqueDigits = data.stream().map(day08::countInEntry).reduce(0, Integer::sum);

        Integer outputSum = data.stream().map(day08::getOutputValue).reduce(0, Integer::sum);

        System.out.println(countUniqueDigits);
    }



    //part 1
    private static Integer countInEntry(String s) {
        String[] digits = s.substring(s.indexOf("|") + 2).split(" ");
        return Math.toIntExact(Arrays.stream(digits).filter(day08::uniqueLength).count());
    }

    private static boolean uniqueLength(String s) {
        int l = s.length();
        return l == 2 || l == 4 || l == 3 || l == 7;
    }

    //part 2
    private static Integer getOutputValue(String s) {
        String[] digits = s.substring(s.indexOf("|") + 2).split(" ");
        String[] signal = s.substring(0, s.indexOf("|") - 1).split(" ");

        String one = Arrays.stream(signal).filter(x -> x.length() == 1).toString();
        String four = Arrays.stream(signal).filter(x -> x.length() == 4).toString();
        String seven = Arrays.stream(signal).filter(x -> x.length() == 3).toString();

        List<Integer> numDigits = Arrays.stream(digits).sequential()
                .map(d -> getDigitValue(d, one, four, seven))
                .collect(Collectors.toList());

        return 0;
    }

    private static Integer getDigitValue(String d, String one, String four, String seven) {
        int digitValue;
        String missingLetterFromOne = ""; //six is missing one of the letters from one
        if (d.length() == 5 || d.length() == 6) {
        }

        switch ( d.length()) {
            case 1:
                digitValue = 1;
                break;
            case 4:
                digitValue = 4;
                break;
            case 3:
                digitValue = 7;
                break;
            case 7:
                digitValue = 8;
                break;
            case 5:
            case 6:
                missingLetterFromOne = isSixNineZero(d, one, four, seven);

                if (missingLetterFromOne == "9" || missingLetterFromOne == "0") {
                    digitValue = Integer.parseInt(d);
                } else {
                    digitValue = 6;
                }
                break;
        }

        return 0;
    }

    private static String isSixNineZero(String d, String one, String four, String seven) {

        return "";
    }

}
// 1,4,7,8,3,6,5,2,
// 0:  6 letters, last one
// 1:  2 letters
// 2:  5 letters, 5 letter sequence that is not #5
// 3:  5 letters and both from #1
// 4:  4 letters
// 5:  5 letters, also has letter from 1 that is in #6
// 6:  6 letters and only one from #1
// 7:  3 letters
// 8:  7 letters
// 9:  6 letters, contains all letters from 4
