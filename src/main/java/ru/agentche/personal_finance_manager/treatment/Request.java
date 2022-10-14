package ru.agentche.personal_finance_manager.treatment;

import ru.agentche.personal_finance_manager.entity.Category;
import ru.agentche.personal_finance_manager.entity.Purchase;

import java.io.File;

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

    public void addPurchase(Purchase purchase) {
        String categoriesName = categories.getCategoriesMap().get(purchase.getTitle());
        if (!categories.getListOfAcquisitions().containsKey(categoriesName)) {
            categoriesName = "другое";
        }
        categories.getListOfAcquisitions().get(categoriesName).add(purchase);
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
