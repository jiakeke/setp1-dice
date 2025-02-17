public class Dice {
    private int die1 = 0;
    private int die2 = -1;
    private int counter = 0;

    public void roll() {
        die1 = (int) (Math.random() * 6) + 1;
        die2 = (int) (Math.random() * 6) + 1;
    }

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }

    public boolean isDouble() {
        return getDie1() == getDie2();
    }

    public void setCounter() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        Dice dice = new Dice();
        while (true) {
            System.out.println("Rolling the dice...");
            dice.roll();
            System.out.println("Die 1: " + dice.getDie1());
            System.out.println("Die 2: " + dice.getDie2());
            if (dice.isDouble()) {
                System.out.println("You rolled a double!");
                System.out.println("After " + dice.getCounter() + " try both reach the same value!");
                break;
            } else {
                System.out.println("Try again.");
                dice.setCounter();
            }
        }
    }

}
