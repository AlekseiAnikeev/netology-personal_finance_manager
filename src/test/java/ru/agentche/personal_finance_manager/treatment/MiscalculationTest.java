package ru.agentche.personal_finance_manager.treatment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 12.10.2022
 */
public class MiscalculationTest {
    BufferedReader bf = new BufferedReader(new FileReader("src/test/resources/test.json"));
    Miscalculation miscalculation = new Miscalculation();
    Request request = new Request();

    public MiscalculationTest() throws FileNotFoundException {
        request.addPurchase(bf);
    }

    @DisplayName("Проверка что вернулась строка в формате json")
    @Test
    void shouldBeInJsonFormat() {
        assertEquals("{\"maxCategory\": {\"category\": \"еда\",\"sum\": 200}}", miscalculation.getStatistic(request.getCategories()));
    }

    @DisplayName("Проверка суммы категорий")
    @Test
    void shouldBeCorrectSum() {
        request.getCategories().getListOfAcquisitions().put("еда", request.getCategories().getListOfAcquisitions()
                .get("еда") + 200);
        assertEquals(400, request.getCategories().getListOfAcquisitions().get("еда"));
    }

    @DisplayName("Проверка корректности выбора категорий")
    @Test
    void shouldBeCorrectCategory() {
        request.getCategories().getListOfAcquisitions().put("другое", 900);
        String maxCategory = Objects.requireNonNull(request.getCategories().getListOfAcquisitions()
                .entrySet()
                .stream()
                .max((Comparator.comparingInt(Map.Entry::getValue))).orElse(null)).getKey();
        assertEquals("другое", maxCategory);
    }
}
