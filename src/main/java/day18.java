import shared.loadInputFile;

import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class day18 {
    public static void main(String[] args) {
        PartOne();
        PartTwo();
    }

    private static void PartTwo() {
        List<String> data = loadInputFile.ReadFileString("day18_input.txt");

        int max = data.stream()
                .map(s1 -> data.stream()
                        .map(s2 -> GetMagnitude(GetSum(s1, s2)))
                        .reduce(0, Integer::max))
                .reduce(0, Integer::max);

        System.out.println(max);
    }

    private static void PartOne() {
        Scanner scanner = new Scanner(Objects.requireNonNull(loadInputFile.class.getClassLoader().getResourceAsStream("day18_input.txt")));

        String str1;
        String str2 = "";

        while (scanner.hasNext()) {
            str1 = str2;
            str2 = scanner.nextLine();
            if (!str1.isBlank() && !str2.isBlank()) {
                str2 = GetSum(str1, str2);
            }
        }

        int magnitude = GetMagnitude(str2);
        System.out.println(magnitude);
    }

    private static String GetSum(String str1, String str2) {
        //add strings
        str2 = AddStrings(str1, str2);
        //System.out.print("After Add:  ");
        //System.out.println(str2);

        //explode string
        boolean didExplode;
        boolean didSplit = true;

        String tempStr;
        while (didSplit) {
            //explode snailfish number
            tempStr = str2;
            str2 = ExplodeSnailfishNumber(str2);
            didExplode = !str2.equals(tempStr);  //if the strings are equal, there was no explosion
            //if (didExplode){
            //    System.out.print("After explode:  ");
            //    System.out.println(str2);
            //}


            if (!didExplode) {
                //split snailfish number
                tempStr = str2;
                str2 = SplitSnailfishNumber(str2);
                didSplit = !str2.equals(tempStr); //if the strings are equal, there was no split
                //if (didSplit) {
                //    System.out.print("After split:  ");
                //    System.out.println(str2);
                //}
            }
        }

        return str2;
    }

    private static int GetMagnitude(String str) {
        StringCharacterIterator itr = new StringCharacterIterator(str);
        int mag;

        while (itr.current() != StringCharacterIterator.DONE) {
            if (itr.current() == ','
                    && Character.isDigit(str.charAt(itr.getIndex()-1))
                    && Character.isDigit(str.charAt(itr.getIndex()+1))) {

                int curIndex = itr.getIndex(); //save location of middle comma
                int leftValue = GetLeftValue(itr);
                int replaceIndex = itr.getIndex();  //save location of opening bracket
                itr.setIndex(curIndex); //reset location to middle comma
                int rightValue = GetRightValue(itr);

                //multiply and replace
                mag = leftValue*3 + rightValue*2;
                str = ReplaceMagnitudeOfPair(str, replaceIndex, mag);
                //System.out.println(str);
                itr.setText(str);
            } else {
                itr.next();
            }
        }

        return Integer.parseInt(str);
    }

    private static int GetRightValue(StringCharacterIterator itr) {
        int rightValue = 0;
        while(itr.next() != ']') {
            rightValue = rightValue * 10 + Character.getNumericValue(itr.current());
        }
        return rightValue;
    }

    private static int GetLeftValue(StringCharacterIterator itr) {
        int leftValue = 0;
        int leftDigitCount = 0;
        while(itr.previous() != '[') {
            leftValue = MultipleOfTen(leftDigitCount) * Character.getNumericValue(itr.current()) + leftValue;
            leftDigitCount++;
        }
        return leftValue;
    }

    private static int MultipleOfTen(int leftDigitCount) {
        return (int) Math.pow(10.0, leftDigitCount);
    }

    private static String ReplaceMagnitudeOfPair(String str, int startIdx, int mag) {
        StringCharacterIterator itr = new StringCharacterIterator(str, startIdx);
        while (itr.current() != ']') itr.next();

        StringBuilder sb = new StringBuilder(str);
        sb.replace(startIdx, itr.getIndex()+1, String.valueOf(mag));

        return sb.toString();
    }

    private static String SplitSnailfishNumber(String str) {
        StringCharacterIterator itr = new StringCharacterIterator(str);
        //find 1, then check if it's a double digit
        while (itr.current() != StringCharacterIterator.DONE) {
            if (Character.isDigit(itr.current()) && Character.isDigit(itr.next())) {
                int largeVal = Character.getNumericValue(itr.current())
                                + (Character.getNumericValue(itr.previous()) * 10);
                String strInsert = AddStrings(
                        String.valueOf(largeVal/2),
                        String.valueOf(largeVal - largeVal/2));

                StringBuilder sb = new StringBuilder(str);
                sb.replace(itr.getIndex(), itr.getIndex()+2, strInsert);
                str = sb.toString();
                break;
            }
            itr.next();
        }

        return str;
    }

    private static String ExplodeSnailfishNumber(String str) {
        StringCharacterIterator itr = new StringCharacterIterator(str);
        int pairIdx;
        char prevChar;

        FindExplodingPair(itr);
        if (itr.current() == StringCharacterIterator.DONE) return str; //did not find exploding pair
        pairIdx = itr.getIndex();

        //get left and right values
        int leftValue, rightValue;
        while (itr.current() != ',') itr.next(); //find the middle comma
        leftValue = GetLeftValue(itr);
        itr.setIndex(pairIdx);
        while (itr.current() != ',') itr.next(); //return to the middle comma
        rightValue = GetRightValue(itr);

        //explode pair by replacing with 0
        itr.setIndex(pairIdx);  //return to the opening bracket
        str = ExplodePair(str, itr);  //itr ends pointing to zero
        itr.setIndex(pairIdx);

        //replace right
        itr.next();
        while (!Character.isDigit(itr.current()) && itr.current() != StringCharacterIterator.DONE) itr.next();
        if (itr.current() != StringCharacterIterator.DONE) {
            prevChar = itr.previous();
            if (prevChar == ',') {
                str = AddDigitInString(str, itr, rightValue, 'R');
            }
            else if(prevChar == '[') {
                while(itr.current() != ',') itr.next();
                str = AddDigitInString(str, itr, rightValue, 'L');
            }
        }
        itr.setIndex(pairIdx); //reset iterator to the zero

        //replace left
        itr.previous();
        while (!Character.isDigit(itr.current()) && itr.getIndex() != 0)  itr.previous();
        if (itr.getIndex() != 0) {
            prevChar = itr.next();
            if (prevChar == ',') {
                str = AddDigitInString(str, itr, leftValue, 'L');
            }
            else if(prevChar == ']') {
                while (itr.current() != ',') itr.previous();
                str = AddDigitInString(str, itr, leftValue, 'R');
            }
        }

        return str;
    }

    private static void FindExplodingPair(StringCharacterIterator itr) {
        //itr ends up pointing to opening bracket of exploding pair
        int openBracketCount = 0;
        while (itr.current() != StringCharacterIterator.DONE ) {
            //count brackets - when the 5th open bracket will be a pair to explode
            if (itr.current() == '[') {
                openBracketCount++;
            } else if (itr.current() == ']') {
                openBracketCount--;
            }

            if (openBracketCount == 5) break;

            itr.next();
        }

    }

    private static String ExplodePair(String str, StringCharacterIterator itr) {
        int startIdx = itr.getIndex();
        int endIdx;

        while (itr.current() != ']') itr.next();
        endIdx = itr.getIndex() + 1;

        StringBuilder sb = new StringBuilder(str);
        str = sb.replace(startIdx, endIdx, "0").toString();
        itr.setText(str);
        itr.setIndex(startIdx);

        return str;
    }

    private static String AddDigitInString(String str, StringCharacterIterator itr, int val, char dir) {
        int curIndex = itr.getIndex();  //index of middle comma
        int newIndex = 0;
        int curVal;
        StringBuilder sb = new StringBuilder(str);

        if (dir == 'L') {
            curVal = GetLeftValue(itr);
            newIndex = itr.getIndex();
            sb.replace(newIndex+1, curIndex, String.valueOf(val + curVal));
        }
        else if (dir == 'R') {
            curVal = GetRightValue(itr);
            newIndex = itr.getIndex();
            sb.replace(curIndex+1, newIndex, String.valueOf(val + curVal));
        }

        str = sb.toString();
        itr.setText(str);
        itr.setIndex(newIndex);
        return sb.toString();
    }

    private static String AddStrings(String str1, String str2) {
        return "[" + str1 + "," + str2 + "]";
    }
}
