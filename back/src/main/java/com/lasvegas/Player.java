package com.lasvegas;

import lombok.Data;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Player {

    static int num = 1;

    private int id;
    private String name;
    private int diceCnt;
    private int totalMoney;

    public Player(String name) {
        this.name = name;
        this.id = num++;
        this.diceCnt = 5;
    }

    public void sendDice(int cnt) {
        this.diceCnt -= cnt;
    }

    public void updateTotalMoney(int money) {
        this.totalMoney += money;
    }

    public static void rollDice(List<Player> players, List<Ground> grounds) {
        Scanner sc = new Scanner(System.in);
        players.forEach((player) -> {
            if (player.getDiceCnt() > 0) {
                System.out.println(player.getName() + "님이 주사위를 굴립니다.");

                List<Dice> diceList = IntStream.range(0, player.getDiceCnt())
                        .mapToObj(i -> new Dice(player.getName())).sorted(Comparator.comparingInt(Dice::getNumber)).collect(Collectors.toList());

                List<Integer> collect = diceList.stream().map((Dice::getNumber)).collect(Collectors.toList());

                System.out.println(collect + " 가 나왔습니다.");
                Ground.getInfo(grounds);
                HashSet<Integer> diceNum = new HashSet<>(collect);

                int choice;
                boolean isValidChoice = false;
                while (!isValidChoice) {
                    System.out.println("놓을 수 있는 땅은 " + diceNum + " 몇 번 땅에 놓으실 건가요?");
                    choice = sc.nextInt();
                    final int finalChoice = choice;
                    if (diceNum.contains(finalChoice)) {
                        Ground ground = grounds.stream().filter((v) -> v.getId() == finalChoice).findFirst().get();
                        List<Dice> choiceDice = diceList.stream().filter((v) -> v.getNumber() == finalChoice).collect(Collectors.toList());
                        ground.updatePlacedDice(choiceDice);
                        player.sendDice(choiceDice.size());
                        isValidChoice = true;
                    } else {
                        System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
                    }
                }
            }
        });
    }
}
