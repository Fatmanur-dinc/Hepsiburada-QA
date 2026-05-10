package com.hepsiburada.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By filterContainer = By.cssSelector("[data-test-id='collapse-container']");
    private By collapseTitle   = By.cssSelector("[data-test-id='collapse-title']");
    private By productTitles   = By.cssSelector("[data-test-id^='title-']");
    private By productPrices   = By.cssSelector("[data-test-id^='final-price-']");

    public SearchResultsPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
    }

    public boolean isFilterSectionVisible() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(filterContainer));
            return driver.findElement(filterContainer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getProductCount() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(productTitles));
            return driver.findElements(productTitles).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getFilterCategories() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(collapseTitle));
            return driver.findElements(collapseTitle)
                         .stream()
                         .map(WebElement::getText)
                         .filter(t -> !t.isEmpty())
                         .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<String> getProductTitles() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(productTitles));
            return driver.findElements(productTitles)
                         .stream()
                         .map(WebElement::getText)
                         .filter(t -> !t.isEmpty())
                         .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<String> getProductPrices() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(productPrices));
            return driver.findElements(productPrices)
                         .stream()
                         .map(WebElement::getText)
                         .filter(t -> !t.isEmpty())
                         .toList();
        } catch (Exception e) {
            return List.of();
        }
    }
}