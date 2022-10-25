package ru.agentche.personal_finance_manager.treatment;

import ru.agentche.personal_finance_manager.entity.CategoryManager;
import ru.agentche.personal_finance_manager.entity.Purchase;

import java.io.File;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 11.10.2022
 */
public class Request {
    private final String FILE_DATA_PATH = "src/main/resources/data.bin";
    private final String FILE_CATEGORY_PATH = "src/main/resources/categories.tsv";
    private final CategoryManager categories;

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

    public CategoryManager getCategories() {
        return categories;
    }

    private CategoryManager initializationCategory() {
        File file = new File(FILE_DATA_PATH);
        CategoryManager categoryManager;
        if (file.exists()) {
            categoryManager = CategoryManager.loadFromBinFile(file);
        } else {
            categoryManager = new CategoryManager(FILE_CATEGORY_PATH);
        }
        return categoryManager;
    }
}
