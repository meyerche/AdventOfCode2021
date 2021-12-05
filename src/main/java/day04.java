import models.bingoCard;
import shared.loadInputFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day04 {
    public static int count;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(Objects.requireNonNull(loadInputFile.class.getClassLoader().getResourceAsStream("day04_input.txt")));

        //Get first line for bingo inputs
        String[] bingoArray = scanner.nextLine().split(",");
        List<Integer> bingoList = Arrays.stream(bingoArray).sequential()
                .map(Integer::parseInt)
                .collect(Collectors.toList());


        List<bingoCard> bingoCards = new ArrayList<>();
        bingoCards.add(new bingoCard());  //add first card

        int cardValue;
        boolean isCardFinished;

        //build bingo cards line by line
        while (scanner.hasNextInt()) {
            cardValue = scanner.nextInt();
            isCardFinished = bingoCards.get(bingoCards.size() - 1).buildCard(cardValue);
            //if card is completed and there are more lines, then add a new blank card
            if (scanner.hasNextInt() && isCardFinished) {
                bingoCards.add(new bingoCard());
            }
        }

        count = 0;
        Arrays.stream(bingoArray).sequential().forEach(n -> PlayNumber(Integer.parseInt(n), bingoCards));
        Arrays.stream(bingoArray).sequential().forEach(n -> GetLastWinner(Integer.parseInt(n), bingoCards));
    }

    private static void PlayNumber(int n, List<bingoCard> bingoCards) {
        Optional<Integer> ans = bingoCards.stream().map(c -> c.getBingoWinner(n)).filter(p -> p > 0).findFirst();
        if (ans.isPresent()) {
            count++;
            System.out.println(count + " " + n + " " + ans.get());
        }
    }

    private static void GetLastWinner(int n, List<bingoCard> bingoCards) {
        //System.out.println("Begin function:  " + bingoCards.size());
        bingoCards = bingoCards.stream()
                .filter(c -> c.getSumCard() == 0)
                .map(c -> c.playBingoNumber(n))
                .filter(c -> c.getSumCard() > 0)
                .collect(Collectors.toList());

        count++;
        System.out.println(count + " " + n  + " " + bingoCards.size());
        if (bingoCards.size() == 1) System.out.println(bingoCards.get(0).getSumCard() * n);
    }

    public static bingoCard PlayCard(bingoCard card, List<Integer> plays) {
        plays.forEach(card::playBingoNumber);
        return card;
    }
}
