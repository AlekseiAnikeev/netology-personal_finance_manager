package ru.agentche.personal_finance_manager.treatment;

import ru.agentche.personal_finance_manager.entity.Category;
import ru.agentche.personal_finance_manager.entity.Purchase;

import java.util.*;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 11.10.2022
 */
public class Miscalculation {
    private final Map<String, Integer> maxCategoryAllTime = new HashMap<>();

    public String getStatistic(Category categories, Purchase purchase) {
        return "{" +
                getMaxCategoryAllTime(categories) +
                getMaxYearCategory(categories, purchase) +
                getMaxMonthCategory(categories, purchase) +
                getMaxDayCategory(categories, purchase) +
                "}";
    }

    public String getMaxCategoryAllTime(Category categories) {
        int categorySum;
        String maxCategory;
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<Purchase>> entry : categories.getListOfAcquisitions().entrySet()) {
            maxCategoryAllTime.put(entry.getKey(), entry.getValue().stream().mapToInt(Purchase::getSum).sum());
        }
        sb.append("\"maxCategory\": {");

        maxCategory = Objects.requireNonNull(maxCategoryAllTime
                .entrySet()
                .stream()
                .max((Comparator.comparingInt(Map.Entry::getValue))).orElse(null)).getKey();

        sb.append("\"category\": \"")
                .append(maxCategory)
                .append("\",");

        categorySum = maxCategoryAllTime.get(maxCategory);
        sb.append("\"sum\": ")
                .append(categorySum)
                .append("}")
                .append(",");
        return sb.toString();
    }

    public String getMaxYearCategory(Category categories, Purchase purchase) {
        int categorySum;
        String maxCategory;
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<Purchase>> entry : categories.getListOfAcquisitions().entrySet()) {
            maxCategoryAllTime.put(entry.getKey(), entry.getValue().stream()
                    .filter(date -> date.getDate().getYear() == (purchase.getDate().getYear()))
                    .mapToInt(Purchase::getSum).sum());
        }

        sb.append("\"maxYearCategory\": {");

        maxCategory = Objects.requireNonNull(maxCategoryAllTime
                .entrySet()
                .stream()
                .max((Comparator.comparingInt(Map.Entry::getValue))).orElse(null)).getKey();

        sb.append("\"category\": \"")
                .append(maxCategory)
                .append("\",");

        categorySum = maxCategoryAllTime.get(maxCategory);
        sb.append("\"sum\": ")
                .append(categorySum)
                .append("}")
                .append(",");
        return sb.toString();
    }

    public String getMaxMonthCategory(Category categories, Purchase purchase) {
        int categorySum;
        String maxCategory;
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<Purchase>> entry : categories.getListOfAcquisitions().entrySet()) {
            maxCategoryAllTime.put(entry.getKey(), entry.getValue().stream()
                    .filter(date -> date.getDate().getMonth() == (purchase.getDate().getMonth())
                            && date.getDate().getYear() == (purchase.getDate().getYear()))
                    .mapToInt(Purchase::getSum).sum());
        }

        sb.append("\"maxMonthCategory\": {");

        maxCategory = Objects.requireNonNull(maxCategoryAllTime
                .entrySet()
                .stream()
                .max((Comparator.comparingInt(Map.Entry::getValue))).orElse(null)).getKey();

        sb.append("\"category\": \"")
                .append(maxCategory)
                .append("\",");

        categorySum = maxCategoryAllTime.get(maxCategory);
        sb.append("\"sum\": ")
                .append(categorySum)
                .append("}")
                .append(",");
        return sb.toString();
    }

    public String getMaxDayCategory(Category categories, Purchase purchase) {
        int categorySum;
        String maxCategory;
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<Purchase>> entry : categories.getListOfAcquisitions().entrySet()) {
            maxCategoryAllTime.put(entry.getKey(), entry.getValue().stream()
                    .filter(date -> date.getDate().equals(purchase.getDate()))
                    .mapToInt(Purchase::getSum).sum());
        }

        sb.append("\"maxDayCategory\": {");

        maxCategory = Objects.requireNonNull(maxCategoryAllTime
                .entrySet()
                .stream()
                .max((Comparator.comparingInt(Map.Entry::getValue))).orElse(null)).getKey();

        sb.append("\"category\": \"")
                .append(maxCategory)
                .append("\",");

        categorySum = maxCategoryAllTime.get(maxCategory);
        sb.append("\"sum\": ")
                .append(categorySum)
                .append("}")
                .append(",");
        return sb.toString();
    }

}
