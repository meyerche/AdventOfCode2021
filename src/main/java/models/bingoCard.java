package models;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class bingoCard {
    private List<Integer> card;
    private List<Integer> cardHits;
    private Integer sumCard;
    private Integer winningTurn;
    private Integer winningNumber;

    public bingoCard(bingoCard b) {
        this.card = b.card;
        this.cardHits = b.cardHits;
        this.sumCard = b.sumCard;
        this.winningTurn = b.winningTurn;  //lower is better so start at the worst outcome
        this.winningNumber = b.winningNumber;
    }
    public bingoCard() {
        card = new ArrayList<>();
        cardHits = new ArrayList<>(Collections.nCopies(25, 1));
        sumCard = 0;
        winningTurn = Integer.MAX_VALUE;  //lower is better so start at the worst outcome
        winningNumber = 0;
    }

    public void setCard(List<Integer> card) {
        this.card = card;
    }

    public List<Integer> getCard() {
        return card;
    }

    public List<Integer> getCardHits() {
        return cardHits;
    }

    public Integer getSumCard() {
        return sumCard;
    }

    public Integer getWinningTurn() {
        return winningTurn;
    }

    public Integer getWinningNumber() {
        return winningNumber;
    }

    public boolean buildCard(String cardLine) {
        if (cardLine.length() != 0 && !(this.card.size() > 25)) {
            Arrays.stream(cardLine.split(" +")).sequential()
                    .map(c -> Integer.parseInt(c.trim()))
                    .peek(System.out::println)
                    .forEach(this.card::add);
        }

        return this.card.size() == 25;
    }

    public boolean buildCard(Integer cardValue) {
        this.card.add(cardValue);
        return this.card.size() == 25;
    }

    public bingoCard playBingoNumber(Integer n) {
        if (this.sumCard > 0) return this;  //card is already finished so don't play the number

        if (card.contains(n)) {
            cardHits.set(card.indexOf(n), 0);
        }

        if (isWinner()) {
            this.winningTurn = 25 - this.cardHits.stream().reduce(Integer::sum).get();
            this.winningNumber = n;
            this.sumCard = IntStream.range(0,25).map(i -> this.card.get(i) * this.cardHits.get(i)).sum();
        }

        return this;
    }

    public Integer getBingoWinner(Integer n) {
        //play number return winning sum or 0 if not winner.
        if (this.sumCard > 0) return 0;  //card is already finished so don't play the number

        if (card.contains(n)) {
            cardHits.set(card.indexOf(n), 0);
        }

        if (isWinner()) {
            //this.winningTurn = 25 - this.cardHits.stream().reduce(Integer::sum).get();
            this.winningNumber = n;
            this.sumCard = IntStream.range(0,25).map(i -> this.card.get(i) * this.cardHits.get(i)).sum();
        }

        return this.sumCard * n;
    }

    public boolean isWinner() {
        boolean win;

        for (int i = 0; i < 5; i++) {
            //check row i
            win = IntStream.range(i*5, i*5+5).map(j -> cardHits.get(j)).sum() == 0;
            if (win) return true;

            //check column i
            int finalI = i;
            win = IntStream.range(0,25).filter(j -> j % 5 == finalI).map(k -> cardHits.get(k)).sum() == 0;
            if (win) return true;
        }

        return false;
    }
}
