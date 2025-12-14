// src/test/java/ru/netology/GameTest.java
package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
    Game game;
    Player player1 = new Player(1, "Alex", 100);
    Player player2 = new Player(2, "Ivan", 90);
    Player player3 = new Player(3, "Masha", 100);
    Player player4 = new Player(4, "Petr", 110);

    // Игрок с другим именем (для покрытия неравенства по name)
    Player player5 = new Player(1, "Alexey", 100);

    // НОВЫЕ: Игрок с другим ID (для покрытия неравенства по id)
    Player player6 = new Player(99, "Alex", 100);

    // НОВЫЕ: Игрок с другой силой (для покрытия неравенства по strength)
    Player player7 = new Player(1, "Alex", 999);

    @BeforeEach
    public void setup() {
        game = new Game();
        // Регистрируем основных игроков
        game.register(player1);
        game.register(player2);
        game.register(player3);
    }

    // --- Тесты для round() (покрывают ветвления в round) ---

    @Test
    public void shouldWinPlayer1() {
        int expected = 1;
        int actual = game.round("Alex", "Ivan");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldWinPlayer2() {
        int expected = 2;
        int actual = game.round("Ivan", "Alex");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldBeDraw() {
        int expected = 0;
        int actual = game.round("Alex", "Masha");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowExceptionIfPlayer1NotRegistered() {
        Assertions.assertThrows(NotRegisteredException.class, () -> {
            game.round("Petr", "Alex");
        });
    }

    @Test
    public void shouldThrowExceptionIfPlayer2NotRegistered() {
        Assertions.assertThrows(NotRegisteredException.class, () -> {
            game.round("Alex", "Petr");
        });
    }

    @Test
    public void shouldThrowExceptionIfNoOneRegistered() {
        Assertions.assertThrows(NotRegisteredException.class, () -> {
            game.round("Petr", "UnregisteredGuy");
        });
    }

    // --- Тест для 100% покрытия Player.equals/hashCode ---

    @Test
    public void testEqualsAndHashCodeCoverage() {
        Player equalPlayer = new Player(1, "Alex", 100); // Идентичен player1

        // 1. Проверка this == o
        Assertions.assertTrue(player1.equals(player1));

        // 2. Проверка o == null
        Assertions.assertFalse(player1.equals(null));

        // 3. Проверка getClass() != o.getClass()
        Assertions.assertFalse(player1.equals(new String("test")));

        // 4. Проверка равенства объектов
        Assertions.assertTrue(player1.equals(equalPlayer));

        // 5. Проверка неравенства (по name)
        Assertions.assertFalse(player1.equals(player5));

        // 6. Проверка неравенства (только по id) - НОВЫЙ БРАНЧ
        Assertions.assertFalse(player1.equals(player6));

        // 7. Проверка неравенства (только по strength) - НОВЫЙ БРАНЧ
        Assertions.assertFalse(player1.equals(player7));

        // 8. Проверка hashCode для равных объектов
        Assertions.assertEquals(player1.hashCode(), equalPlayer.hashCode());

        // 9. Проверка hashCode для неравных объектов (разные id, name, strength)
        Assertions.assertNotEquals(player1.hashCode(), player2.hashCode());

        // 10. Проверка hashCode для player6 и player7 (для покрытия всех бранчей hashCode)
        Assertions.assertNotEquals(player1.hashCode(), player6.hashCode());
        Assertions.assertNotEquals(player1.hashCode(), player7.hashCode());
    }
}