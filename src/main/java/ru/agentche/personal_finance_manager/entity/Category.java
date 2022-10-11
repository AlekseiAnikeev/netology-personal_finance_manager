package ru.agentche.personal_finance_manager.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 10.10.2022
 */
public class Category {
    private final Map<String, String> categoriesTemplate = new HashMap<>();
    private final Map<String, Integer> listOfAcquisitions = new HashMap<>();

    public Category() {
        intiCategories();
        initListOfAcquisitions();
    }

    private void initListOfAcquisitions() {
        listOfAcquisitions.put("другое", 0);
        for (Map.Entry<String, String> entry : categoriesTemplate.entrySet()) {
            if (listOfAcquisitions.containsKey(entry.getValue())) {
                continue;
            }
            listOfAcquisitions.put(entry.getValue(), 0);
        }
    }

    private void intiCategories() {
        String fileName = "src/main/resources/categories.tsv";
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            while (bufferedReader.ready()) {
                String[] meaning = bufferedReader.readLine().split("\t");
                categoriesTemplate.put(meaning[0], meaning[1]);
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getCategoriesMap() {
        return categoriesTemplate;
    }

    public Map<String, Integer> getListOfAcquisitions() {
        return listOfAcquisitions;
    }
}
