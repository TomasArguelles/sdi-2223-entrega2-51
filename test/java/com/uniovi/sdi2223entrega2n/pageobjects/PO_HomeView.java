package com.uniovi.sdi2223entrega2n.pageobjects;

import com.uniovi.sdi2223entrega2n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class PO_HomeView extends PO_NavView {
    static public void checkWelcomeToPage(WebDriver driver, String kind) {
//Esperamos a que se cargue el saludo de bienvenida en Español
        if (Objects.equals(kind, "standard"))
            SeleniumUtils.waitLoadElementsBy(driver, "text", "Ofertas",
                    getTimeout());
        else
            SeleniumUtils.waitLoadElementsBy(driver, "text", "Usuarios",
                getTimeout());
    }

    static public List<WebElement> getWelcomeMessageText(WebDriver driver, int language) {
//Esperamos a que se cargue el saludo de bienvenida en Español
        return SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("welcome.message", language),
                getTimeout());
    }

    public static String getTitleMessage(WebDriver driver) {
        return driver.findElement(By.name("title")).getText();
    }
}
