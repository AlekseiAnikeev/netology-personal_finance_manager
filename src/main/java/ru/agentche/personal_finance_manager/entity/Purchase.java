package ru.agentche.personal_finance_manager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 10.10.2022
 */
public class Purchase {
    private final String title;
    private final LocalDate date;
    private final int sum;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public Purchase(@JsonProperty("title") String title,
                    @JsonProperty("date") String date,
                    @JsonProperty("sum") int sum) {
        this.title = title;
        this.date = LocalDate.parse(date, dateTimeFormatter);
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "\"title\"" + ":" + " " + title + ", " +
                "\"date\"" + ":" + " " + date.format(dateTimeFormatter) + ", " +
                "\"sum\"" + ":" + " " + sum;
    }
}
