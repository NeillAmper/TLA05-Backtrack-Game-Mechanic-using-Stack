package com.mycompany.turnbasedstack;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class TurnBasedStack {

    static boolean isOddOrEven(int n) {
        return n % 2 == 0;
    }

    public static void main(String[] args) {
        Stack<Integer> lastPlayerHP = new Stack<>();
        Stack<Integer> lastBotHP = new Stack<>();

        int GameTurn = 1;
        Scanner myObj = new Scanner(System.in);
        Random random = new Random();

        int playerHP = 100;
        int botHP = 100;
        int playerMaxDmg = 10;
        int playerMinDmg = 5;
        int botMaxDmg = 10;
        int botMinDmg = 5;
        int botStunTurns = 0;

        System.out.println("==========================================");
        System.out.println("   WELCOME TO THE TURN-BASED BATTLE GAME  ");
        System.out.println("==========================================");
        System.out.print("Please enter your name: ");
        String playerName = myObj.nextLine();
        if (playerName.trim().isEmpty()) {
            playerName = "Player";
        }
        System.out.print("Please enter the monster's name: ");
        String monsterName = myObj.nextLine();
        if (monsterName.trim().isEmpty()) {
            monsterName = "Monster";
        }
        System.out.println("Welcome, Player " + playerName + "!\n");
        System.out.println("Instructions:");
        System.out.println("Type 'Attack' to attack, 'Stun' to stun your opponent, 'Skip' to skip your turn, or 'Undo' to undo your last action.");

        while (playerHP > 0 && botHP > 0) {
            System.out.println("\n------------------------------------------");
            System.out.println("                 TURN " + GameTurn);
            System.out.println("------------------------------------------");
            if (!isOddOrEven(GameTurn)) {
                lastPlayerHP.push(playerHP);
                lastBotHP.push(botHP);

                System.out.println(playerName + "'s Turn");
                System.out.println(playerName + "'s HP: " + playerHP + "   |   " + monsterName + "'s HP: " + botHP);
                System.out.println("------------------------------------------");
                System.out.println("What would you like to do?");
                System.out.println(">>Attack");
                System.out.println(">>Stun");
                System.out.println(">>Skip Turn");
                System.out.println(">>Undo");
                System.out.print("> ");

                String input = myObj.nextLine();

                if (input.equalsIgnoreCase("attack")) {
                    int playerDmg = playerMinDmg + random.nextInt(playerMaxDmg - playerMinDmg + 1);
                    botHP = botHP - playerDmg;
                    System.out.println(playerName + " dealt " + playerDmg + " damage to the enemy.");
                    System.out.println("The enemy has " + Math.max(botHP, 0) + " HP remaining.");
                } else if (input.equalsIgnoreCase("stun")) {
                    System.out.println("You attempted to stun the " + monsterName + ".");
                    System.out.println("The " + monsterName + " is stunned and will skip its next turn!");
                    botStunTurns = 1;
                } else if (input.equalsIgnoreCase("undo")) {
                    if (!lastPlayerHP.isEmpty() && !lastBotHP.isEmpty()) {
                        playerHP = lastPlayerHP.pop();
                        botHP = lastBotHP.pop();
                        System.out.println("Undid your last action.");
                        continue;
                    } else {
                        System.out.println("Nothing to undo!");
                        continue;
                    }
                } else {
                    System.out.println("You did nothing and skipped a turn.");
                }
                ++GameTurn;
            } else {
                System.out.println(monsterName + "'s Turn");
                if (botStunTurns > 0) {
                    System.out.println("The " + monsterName + " is stunned and cannot move!");
                    botStunTurns--;
                } else {
                    int monsterDmg = botMinDmg + random.nextInt(botMaxDmg - botMinDmg + 1);
                    playerHP = playerHP - monsterDmg;
                    System.out.println("The " + monsterName + " attacks " + playerName + "!");
                    System.out.println(playerName + " received " + monsterDmg + " damage.");
                    System.out.println(playerName + " has " + Math.max(playerHP, 0) + " HP left.");

                    int chance = random.nextInt(4) + 1;
                    System.out.println("The chance is " + chance);
                    if (chance == 4) {
                        if (!lastBotHP.isEmpty()) {
                            botHP = lastBotHP.peek();
                            System.out.println("Passive triggered! " + monsterName + " restored its previous HP: " + botHP);
                        } else {
                            System.out.println("Passive triggered! But there is no previous HP to restore.");
                        }
                    }
                    if (playerHP < 0) {
                        System.out.println("The player is defeated");
                    }
                }
                ++GameTurn;
            }
        }

        System.out.println("\n==========================================");
        if (playerHP <= 0 && botHP <= 0) {
            System.out.println("It's a draw! Both " + playerName + " and the " + monsterName + " have fallen.");
        } else if (playerHP <= 0) {
            System.out.println("Defeat! " + playerName + " has been slain!");
        } else if (botHP <= 0) {
            System.out.println("Victory! The " + monsterName + " has been defeated by " + playerName + "!");
        }
        System.out.println("==========================================");
    }
}
