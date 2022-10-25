package ru.agentche.personal_finance_manager.entity;

import java.io.*;
import java.util.*;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 10.10.2022
 */
public class CategoryManager implements Serializable {
    private final Map<String, String> categoriesTemplate = new HashMap<>();
    private final Map<String, List<Purchase>> listOfAcquisitions = new HashMap<>();

    public CategoryManager(String fileName) {
        intiCategories(fileName);
        initListOfAcquisitions();
    }

    private void initListOfAcquisitions() {
        listOfAcquisitions.put("другое", new ArrayList<>());
        for (Map.Entry<String, String> entry : categoriesTemplate.entrySet()) {
            if (listOfAcquisitions.containsKey(entry.getValue())) {
                continue;
            }
            listOfAcquisitions.put(entry.getValue(), new ArrayList<>());
        }
    }

    private void intiCategories(String fileName) {
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

    public Map<String, List<Purchase>> getListOfAcquisitions() {
        return listOfAcquisitions;
    }

    public void saveBin(File file) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
            os.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CategoryManager loadFromBinFile(File file) {
        CategoryManager categoryManager;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            categoryManager = (CategoryManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return categoryManager;
    }
}