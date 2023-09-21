package com.lasvegas;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Util {

    public static void calMoney(List<Ground> grounds, List<Player> players) {
        grounds.forEach((ground -> {
            Map<String, Integer> diceInfo = Ground.getPlacedDiceInfo(ground.getPlacedDice());
            Map<String, Integer> uniqueValues = getUniqueValues(diceInfo);

            List<String> names = sortByValueDescending(uniqueValues);
            System.out.println(ground.getId() + " : " + names);

            for (int i = 0; i < names.size(); i++) {
                int finalI = i;
                Player player = players.stream().filter(v -> v.getName().equals(names.get(finalI))).findFirst().get();
                player.updateTotalMoney(ground.getMoney().get(i));
            }
        }));
        players.sort((a, b) -> b.getTotalMoney() - a.getTotalMoney());
        System.out.println("게임 종료" + players);
    }

    public static Map<String, Integer> getUniqueValues(Map<String, Integer> diceInfo) {
        Map<Integer, Long> valueFrequency = diceInfo.values().stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return diceInfo.entrySet().stream()
                .filter(entry -> valueFrequency.get(entry.getValue()) == 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static List<String> sortByValueDescending(Map<String, Integer> uniqueValues) {
        return new ArrayList<>(uniqueValues.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                )).keySet());
    }
}
