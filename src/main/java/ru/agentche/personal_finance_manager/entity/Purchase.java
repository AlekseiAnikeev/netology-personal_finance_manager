package ru.agentche.personal_finance_manager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import static ru.agentche.personal_finance_manager.server.Server.dateTimeFormatter;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 10.10.2022
 */
public class Purchase implements Serializable {
    @Serial
    private static final long serialVersionUID = -2299982128974797230L;
    private final String title;
    private final LocalDate date;
    private final int sum;

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
