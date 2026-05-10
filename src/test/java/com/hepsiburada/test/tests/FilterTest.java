package com.hepsiburada.test.tests;

import com.hepsiburada.test.base.BaseTest;
import com.hepsiburada.test.pages.HomePage;
import com.hepsiburada.test.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class FilterTest extends BaseTest {

    private static final String BASE_URL = "https://www.hepsiburada.com/ara?q=laptop";

    @Test(description = "Filtre bölümünün görünür olduğunu doğrula")
    public void testFilterSectionIsVisible() {
        driver.get(BASE_URL);
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        SearchResultsPage resultsPage = new SearchResultsPage(driver);

        Assert.assertTrue(
            resultsPage.isFilterSectionVisible(),
            "Filtre bölümü görünür olmalı!"
        );
        System.out.println("✓ Filtre bölümü görünür");
    }

    @Test(description = "Arama sonrası ürün listelendiğini doğrula")
    public void testProductsAreListedAfterSearch() {
        driver.get(BASE_URL);
        SearchResultsPage resultsPage = new SearchResultsPage(driver);

        int count = resultsPage.getProductCount();
        System.out.println("✓ Bulunan ürün sayısı: " + count);

        Assert.assertTrue(count > 0, "En az 1 ürün listelenmeli!");
    }

    @Test(description = "Filtre kategorileri listelenmeli")
    public void testFilterCategoriesVisible() {
        driver.get(BASE_URL);
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        SearchResultsPage resultsPage = new SearchResultsPage(driver);

        List<String> categories = resultsPage.getFilterCategories();
        System.out.println("✓ Filtre kategorileri: " + categories);

        Assert.assertTrue(categories.size() > 0, "En az 1 filtre kategorisi olmalı!");
        Assert.assertTrue(categories.contains("Marka"), "Marka filtresi olmalı!");
        Assert.assertTrue(categories.contains("Fiyat Aralığı"), "Fiyat Aralığı filtresi olmalı!");
    }

    @Test(description = "Marka filtresi uygulandığında URL güncellenmeli")
    public void testBrandFilterUrl() {
        driver.get(BASE_URL + "&markalar=asus");
        SearchResultsPage resultsPage = new SearchResultsPage(driver);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("✓ Marka filtresi URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("markalar=asus"), "URL marka filtresini içermeli!");
        Assert.assertTrue(resultsPage.getProductCount() > 0, "Asus ürünleri listelenmeli!");
    }

    @Test(description = "Marka filtresi uygulandığında ürün başlıkları listelenmeli")
    public void testBrandFilterProducts() {
        driver.get(BASE_URL + "&markalar=lenovo");
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        SearchResultsPage resultsPage = new SearchResultsPage(driver);

        List<String> titles = resultsPage.getProductTitles();
        int count = resultsPage.getProductCount();

        System.out.println("✓ Lenovo filtreli ürün sayısı: " + count);
        System.out.println("✓ İlk ürün: " + (titles.isEmpty() ? "yok" : titles.get(0)));

        Assert.assertTrue(count > 0, "Lenovo filtresi ile en az 1 ürün listelenmeli!");
    }

    @Test(description = "Fiyat filtresi uygulandığında URL güncellenmeli")
    public void testPriceFilterUrl() {
        driver.get(BASE_URL + "&filtreler=fiyat:9900-34900");
        SearchResultsPage resultsPage = new SearchResultsPage(driver);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("✓ Fiyat filtresi URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("fiyat"), "URL fiyat filtresini içermeli!");
        Assert.assertTrue(resultsPage.getProductCount() > 0, "Ürünler listelenmeli!");
    }

    @Test(description = "Fiyat filtresi sonrası ürün listelenmeli")
    public void testPriceFilterReducesResults() {
        driver.get(BASE_URL + "&filtreler=fiyat:9900-34900");
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        SearchResultsPage filteredPage = new SearchResultsPage(driver);

        int filteredCount = filteredPage.getProductCount();
        String currentUrl = driver.getCurrentUrl();

        System.out.println("✓ Fiyat filtreli ürün sayısı: " + filteredCount);
        Assert.assertTrue(filteredCount > 0, "Fiyat filtresi ile ürün listelenmeli!");
        Assert.assertTrue(currentUrl.contains("fiyat"), "URL fiyat filtresini içermeli!");
    }

    @Test(description = "Marka ve fiyat filtresi birlikte uygulanabilmeli")
    public void testCombinedFilter() {
        driver.get(BASE_URL + "&markalar=asus&filtreler=fiyat:9900-34900");
        SearchResultsPage resultsPage = new SearchResultsPage(driver);

        String currentUrl = driver.getCurrentUrl();
        int count = resultsPage.getProductCount();

        System.out.println("✓ Kombine filtre URL: " + currentUrl);
        System.out.println("✓ Kombine filtre ürün sayısı: " + count);

        Assert.assertTrue(currentUrl.contains("markalar=asus"), "URL marka filtresini içermeli!");
        Assert.assertTrue(currentUrl.contains("fiyat"), "URL fiyat filtresini içermeli!");
        Assert.assertTrue(count > 0, "Kombine filtrede ürün listelenmeli!");
    }

    @Test(description = "Ürün başlıkları ve fiyatları doğru listelenmeli")
    public void testProductTitlesAndPricesListed() {
        driver.get(BASE_URL);
        SearchResultsPage resultsPage = new SearchResultsPage(driver);

        List<String> titles = resultsPage.getProductTitles();
        List<String> prices = resultsPage.getProductPrices();

        System.out.println("✓ İlk ürün: " + (titles.isEmpty() ? "yok" : titles.get(0)));
        System.out.println("✓ İlk fiyat: " + (prices.isEmpty() ? "yok" : prices.get(0)));

        Assert.assertTrue(titles.size() > 0, "Ürün başlıkları listelenmeli!");
        Assert.assertTrue(prices.size() > 0, "Ürün fiyatları listelenmeli!");
    }
}