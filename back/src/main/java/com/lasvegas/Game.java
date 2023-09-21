package com.lasvegas;

import lombok.Data;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("게임을 시작");
        System.out.println("플레이어는 몇명인가요?");
        int personCnt = sc.nextInt();

        List<Player> players = IntStream.range(0, personCnt)
                .mapToObj(i -> {
                    System.out.println("플레이어의 이름을 입력해주세요");
                    return new Player(sc.next());
                }).collect(Collectors.toList());

        System.out.println("그라운드를 생성 하였습니다.");
        List<Ground> grounds = IntStream.range(0, 6)
                .mapToObj(i -> new Ground()).collect(Collectors.toList());

        while (true) {
            int playerDiceCnt = players.stream().map(Player::getDiceCnt).reduce(0, Integer::sum);
            if (playerDiceCnt == 0) {
                System.out.println("플레이어들 주사위 모두 소모");
                Util.calMoney(grounds, players);
                break;
            }

            System.out.println("무엇을 할까요?");
            System.out.println("1. 그라운드 정보 보기");
            System.out.println("2. 주사위 굴리기");
            switch (sc.nextInt()) {
                case 1:
                    Ground.getInfo(grounds);
                    break;
                case 2:
                    Player.rollDice(players, grounds);
                    break;
                default:
                    System.out.println("다시 선택해 주세요.");
            }
            System.out.println();
        }
    }

}
