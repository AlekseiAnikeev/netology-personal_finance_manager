package ru.agentche.personal_finance_manager.treatment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.agentche.personal_finance_manager.entity.Purchase;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 12.10.2022
 */
public class MiscalculationTest {

    static ObjectMapper mapper = new ObjectMapper();
    static Purchase purchase2022_02_08;
    static Purchase purchase2021_02_08;
    static Purchase purchase2022_02_15;

    String testLine = "{\"maxCategory\": {\"category\": \"еда\",\"sum\": 200},\"maxYearCategory\": {\"category\": \"еда\",\"sum\": 200},\"maxMonthCategory\": {\"category\": \"еда\",\"sum\": 200},\"maxDayCategory\": {\"category\": \"еда\",\"sum\": 200},}";

    Miscalculation miscalculation = new Miscalculation();
    Request request = new Request();

    @BeforeAll
    static void preLoad() {
        File file = new File("src/main/resources/data.bin");
        if (file.exists()) {
            file.delete();
        }
        try {
            purchase2022_02_08 = mapper.readValue(new FileReader("src/test/resources/test.json"), new TypeReference<>() {
            });
            purchase2021_02_08 = mapper.readValue(new FileReader("src/test/resources/test2.json"), new TypeReference<>() {
            });
            purchase2022_02_15 = mapper.readValue(new FileReader("src/test/resources/test3.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Проверка что вернулась строка в формате json")
    @Test
    void shouldBeInJsonFormat() {
        request.addPurchase(purchase2022_02_08);
        assertEquals(testLine, miscalculation.getStatistic(request.getCategories(), purchase2022_02_08));
    }

    @DisplayName("Проверка суммы категорий")
    @Test
    void shouldBeCorrectSum() {
        request.getCategories().getListOfAcquisitions().get("еда").add(purchase2022_02_08);
        request.getCategories().getListOfAcquisitions().get("одежда").add(purchase2022_02_15);
        request.getCategories().getListOfAcquisitions().get("еда").add(purchase2022_02_08);
        request.getCategories().getListOfAcquisitions().get("еда").add(purchase2022_02_08);
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        assertEquals("\"maxCategory\": {\"category\": \"еда\",\"sum\": 600},", miscalculation.getMaxCategoryAllTime(request.getCategories()));


    }

    @DisplayName("Проверка суммы по году")
    @Test
    void shouldBeCorrectSumYears() {
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("одежда").add(purchase2022_02_15);
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("одежда").add(purchase2022_02_15);
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("еда").add(purchase2022_02_08);
        assertEquals("\"maxYearCategory\": {\"category\": \"одежда\",\"sum\": 400},", miscalculation.getMaxYearCategory(request.getCategories(), purchase2022_02_08));
        assertEquals("\"maxYearCategory\": {\"category\": \"другое\",\"sum\": 1200},", miscalculation.getMaxYearCategory(request.getCategories(), purchase2021_02_08));
    }

    @DisplayName("Проверка суммы по месяцу")
    @Test
    void shouldBeCorrectSumMount() {
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("одежда").add(purchase2022_02_15);
        request.getCategories().getListOfAcquisitions().get("одежда").add(purchase2022_02_15);
        request.getCategories().getListOfAcquisitions().get("еда").add(purchase2022_02_08);
        assertEquals("\"maxMonthCategory\": {\"category\": \"одежда\",\"sum\": 400},", miscalculation.getMaxMonthCategory(request.getCategories(), purchase2022_02_08));
        assertEquals("\"maxMonthCategory\": {\"category\": \"другое\",\"sum\": 900},", miscalculation.getMaxMonthCategory(request.getCategories(), purchase2021_02_08));
    }

    @DisplayName("Проверка суммы по дню")
    @Test
    void shouldBeCorrectSumDay() {

        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("другое").add(purchase2021_02_08);
        request.getCategories().getListOfAcquisitions().get("еда").add(purchase2022_02_08);
        request.getCategories().getListOfAcquisitions().get("одежда").add(purchase2022_02_15);
        request.getCategories().getListOfAcquisitions().get("одежда").add(purchase2022_02_15);
        assertEquals("\"maxDayCategory\": {\"category\": \"еда\",\"sum\": 400},", miscalculation.getMaxDayCategory(request.getCategories(), purchase2022_02_08));
        assertEquals("\"maxDayCategory\": {\"category\": \"другое\",\"sum\": 900},", miscalculation.getMaxDayCategory(request.getCategories(), purchase2021_02_08));
    }

}
