package machine;

import java.util.Scanner;

public class CoffeeMachine {

    // Resources
    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    private enum State {
        CHOOSE_ACTION("Write action (buy, fill, take, remaining, exit):"),
        CHOOSE_COFFEE("" +
                "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:"),
        FILL_WATER("Write how many ml of water do you want to add:"),
        FILL_MILK("Write how many ml of milk do you want to add:"),
        FILL_BEANS("Write how many grams of coffee beans do you want to add:"),
        FILL_CUPS("Write how many disposable cups of coffee do you want to add:");

        final String message;

        State(String message) {
            this.message = message;
        }
    }

    private State state = State.CHOOSE_ACTION;

    public String execute(String command) {
        switch (state) {
            case CHOOSE_ACTION:
                switch (command) {
                    case "remaining":
                        return getRemainders() + System.lineSeparator() + state.message;
                    case "take":
                        return take() + System.lineSeparator() + state.message;
                    case "fill":
                        state = State.FILL_WATER;
                        break;
                    case "buy":
                        state = State.CHOOSE_COFFEE;
                        break;
                    case "exit":
                        return "exit";
                    default:
                }
                break;
            case CHOOSE_COFFEE:
                switch (command) {
                    case "back":
                        state = State.CHOOSE_ACTION;
                        break;
                    case "1":
                        state = State.CHOOSE_ACTION;
                        return prepare(250, 0, 16, 4) + System.lineSeparator() + state.message;
                    case "2":
                        state = State.CHOOSE_ACTION;
                        return prepare(350, 75, 20, 7) + System.lineSeparator() + state.message;
                    case "3":
                        state = State.CHOOSE_ACTION;
                        return prepare(200, 100, 12, 6) + System.lineSeparator() + state.message;
                    default:
                }

                break;
            case FILL_WATER:
                water += Integer.parseInt(command);
                state = State.FILL_MILK;
                break;
            case FILL_MILK:
                milk += Integer.parseInt(command);
                state = State.FILL_BEANS;
                break;
            case FILL_BEANS:
                beans += Integer.parseInt(command);
                state = State.FILL_CUPS;
                break;
            case FILL_CUPS:
                cups += Integer.parseInt(command);
                state = State.CHOOSE_ACTION;
                break;
            default:
        }
        return state.message;
    }

    public String hello() {
        return state.message;
    }

    private String getRemainders() {
        return "The coffee machine has:" +
                System.lineSeparator() +
                water +
                " of water" +
                System.lineSeparator() +
                milk +
                " of milk" +
                System.lineSeparator() +
                beans +
                " of coffee beans" +
                System.lineSeparator() +
                cups +
                " of disposable cups" +
                System.lineSeparator() +
                "$" +
                money +
                " of money" +
                System.lineSeparator();
    }

    private String take() {
        String result = "I gave you $" + money + System.lineSeparator();
        money = 0;
        return result;
    }

    private String prepare(int water, int milk, int beans, int cost) {
        if (this.water < water) {
            return "Sorry, not enough water!";
        } else if (this.milk < milk) {
            return "Sorry, not enough milk!";
        } else if (this.beans < beans) {
            return "Sorry, not enough coffee beans!";
        } else if (this.cups < 1) {
            return "Sorry, not enough disposable cups!";
        } else {
            this.water -= water;
            this.milk -= milk;
            this.beans -= beans;
            this.cups--;
            money += cost;
            return "I have enough resources, making you a coffee!";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine machine = new CoffeeMachine();
        System.out.println(machine.hello());
        while (true) {
            String output = machine.execute(scanner.nextLine());
            if (output.equals("exit")) {
                break;
            } else {
                System.out.println(System.lineSeparator() + output);
            }
        }
    }
}
