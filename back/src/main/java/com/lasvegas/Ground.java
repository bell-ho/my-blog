package com.lasvegas;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Ground {
    static int num = 1;
    private static int[] dollar = {10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000};

    private int id;
    private List<Integer> money = new ArrayList<>();
    private List<Dice> placedDice = new ArrayList<>();

    public Ground() {
        this.id = num++;
        this.money = betting();
    }

    private List<Integer> betting() {
        int total = 0;
        List<Integer> bill = new ArrayList<>();

        while (total < 70000) {
            int money = dollar[(int) (Math.random() * 8)];
            total += money;
            bill.add(money);
        }
        bill.sort((a, b) -> b - a);
        return bill;
    }

    public static void getInfo(List<Ground> grounds) {
        grounds.forEach((v) -> {
            Map<String, Integer> placedDice = getPlacedDiceInfo(v.getPlacedDice());
            System.out.println(v.getId() + "번 땅에 " + v.getMoney() + "의 돈이 있고 배치된 주사위는 " + placedDice);
        });
    }

    public static Map<String, Integer> getPlacedDiceInfo(List<Dice> placedDice) {
        Map<String, Integer> result = new HashMap<>();

        for (Dice dice : placedDice) {
            String owner = dice.getOwner();
            result.put(owner, result.getOrDefault(owner, 0) + 1);
        }
        return result;
    }

    public void updatePlacedDice(List<Dice> choiceDice) {
        this.placedDice.addAll(choiceDice);
    }
}
