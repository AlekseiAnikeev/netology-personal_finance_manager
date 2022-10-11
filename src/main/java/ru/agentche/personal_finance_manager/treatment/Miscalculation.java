package ru.agentche.personal_finance_manager.treatment;

import ru.agentche.personal_finance_manager.entity.Category;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;


/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 11.10.2022
 */
public class Miscalculation {

    public String getStatistic(Category categories) {
        String maxCategory;
        int categorySum;
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"maxCategory\": {");

        maxCategory = Objects.requireNonNull(categories.getListOfAcquisitions()
                .entrySet()
                .stream()
                .max((Comparator.comparingInt(Map.Entry::getValue))).orElse(null)).getKey();

        sb.append("\"category\": \"")
                .append(maxCategory)
                .append("\",");

        categorySum = categories.getListOfAcquisitions().get(maxCategory);
        sb.append("\"sum\": ")
                .append(categorySum)
                .append("}")
                .append("}");
        return sb.toString();
    }
}
