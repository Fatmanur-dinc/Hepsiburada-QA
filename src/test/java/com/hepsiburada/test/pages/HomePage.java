package com.hepsiburada.test.pages;

import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://www.hepsiburada.com");
    }

    public void acceptCookies() {
        System.out.println("Cookie adimi atlandi.");
    }

    public SearchResultsPage search(String keyword) {
        String searchUrl = "https://www.hepsiburada.com/ara?q=" + keyword.replace(" ", "+");
        driver.get(searchUrl);
        System.out.println("Arama yapildi: " + searchUrl);
        return new SearchResultsPage(driver);
    }
}