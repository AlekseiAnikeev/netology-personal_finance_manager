package ru.agentche.personal_finance_manager.treatment;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.agentche.personal_finance_manager.entity.Category;
import ru.agentche.personal_finance_manager.entity.Purchase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 11.10.2022
 */
public class Request {
    private final String FILE_DATA_PATH = "src/main/resources/data.bin";
    private final Category categories;
    public Request() {
        categories = initializationCategory();
    }
    public void addPurchase(BufferedReader in) {
        ObjectMapper mapper = new ObjectMapper();
        Purchase purchase;
        try {
            purchase = mapper.readValue(in.readLine(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String categoriesName = categories.getCategoriesMap().get(purchase.getTitle());
        if (!categories.getListOfAcquisitions().containsKey(categoriesName)) {
            categoriesName = "другое";
        }
        categories.getListOfAcquisitions()
                .put(categoriesName, categories.getListOfAcquisitions()
                        .get(categoriesName) + purchase.getSum());
        categories.saveBin(new File(FILE_DATA_PATH));
    }
    public Category getCategories() {
        return categories;
    }

    private Category initializationCategory() {
        File file = new File(FILE_DATA_PATH);
        Category category;
        if (file.exists()) {
            category = Category.loadFromBinFile(file);
        } else {
            category = new Category();
        }
        return category;
    }
}
