// src/main/java/ru/netology/Game.java
package ru.netology;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> registeredPlayers = new ArrayList<>();

    public void register(Player player) {
        registeredPlayers.add(player);
    }

    // Вспомогательный метод для поиска игрока по имени
    public Player findByName(String name) {
        for (Player player : registeredPlayers) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Проводит соревнование между двумя игроками.
     *
     * @param playerName1 Имя первого игрока
     * @param playerName2 Имя второго игрока
     * @return 0 - ничья, 1 - победа первого игрока, 2 - победа второго игрока
     * @throws NotRegisteredException Если хотя бы один игрок не зарегистрирован
     */
    public int round(String playerName1, String playerName2) {
        Player player1 = findByName(playerName1);
        Player player2 = findByName(playerName2);

        // Бранч 1: Проверка регистрации первого игрока
        if (player1 == null) {
            throw new NotRegisteredException(playerName1);
        }

        // Бранч 2: Проверка регистрации второго игрока
        if (player2 == null) {
            throw new NotRegisteredException(playerName2);
        }

        // Бранч 3: Победа первого игрока (strength >)
        if (player1.getStrength() > player2.getStrength()) {
            return 1;
            // Бранч 4: Победа второго игрока (strength <)
        } else if (player1.getStrength() < player2.getStrength()) {
            return 2;
        } else {
            // Бранч 5: Ничья (strength ==)
            return 0;
        }
    }
}