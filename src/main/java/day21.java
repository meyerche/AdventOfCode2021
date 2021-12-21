public class day21 {
    private static final int PLAYER1_START = 7;
    private static final int PLAYER2_START = 1;
    private static final int DIE_SIDES = 100;
    private static final int ROLLS_PER_TURN = 3;
    private static final int GAME_BOARD_SIZE = 10;
    private static final int WINNING_SCORE = 1000;

    public static void main(String[] args) {
        DicePlayer player1 = new DicePlayer(PLAYER1_START);
        DicePlayer player2 = new DicePlayer(PLAYER2_START);
        DeterministicDie die = new DeterministicDie(DIE_SIDES);

        int turn = 0;
        DicePlayer loser = null;

        while (loser == null) {
            turn++;
            if (turn % 2 == 1) {
                player1.takeTurn(die.rollNTimes(ROLLS_PER_TURN));
                if (player1.score >= WINNING_SCORE) loser = player2;
            } else {
                player2.takeTurn(die.rollNTimes(ROLLS_PER_TURN));
                if (player2.score >= WINNING_SCORE) loser = player1;
            }
        }

        System.out.println(die.rollCount);
        System.out.println(loser.getScore());
        System.out.println(loser.getScore() * die.rollCount);


    }

    static class DicePlayer {
        private int score;
        private int position;

        public DicePlayer(int position) {
            this.score = 0;
            this.position = position;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void takeTurn(int move) {
            this.position = (this.position + move) % GAME_BOARD_SIZE;
            this.score += this.position == 0 ? GAME_BOARD_SIZE : this.position;
        }
    }

    static class DeterministicDie {
        private int rollCount;
        private int lastRoll;
        private final int numberOfSides;

        public DeterministicDie(int numberOfSides) {
            this.rollCount = 0;
            this.lastRoll = 0;
            this.numberOfSides = numberOfSides;
        }

        public int getRollCount() {
            return rollCount;
        }

        public int getLastRoll() {
            return lastRoll;
        }

        public int getNumberOfSides() {
            return numberOfSides;
        }

        public int roll () {
            this.rollCount++;
            this.lastRoll = (this.lastRoll + 1) % this.numberOfSides;
            if (this.lastRoll == 0) this.lastRoll = this.numberOfSides;

            return this.lastRoll;
        }

        public int rollNTimes(int rolls) {
            int total = 0;
            for (int i = 0; i < rolls; i++) {
                total += this.roll();
            }
            return total;
        }

    }

    //18900 - low
}
