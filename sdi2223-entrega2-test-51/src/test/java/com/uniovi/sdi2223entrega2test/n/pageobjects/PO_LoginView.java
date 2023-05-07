package com.uniovi.sdi2223entrega2test.n.pageobjects;

import com.uniovi.sdi2223entrega2test.n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {

    static public void fillLoginForm(WebDriver driver, String emailp, String passwordp) {
        WebElement email = driver.findElement(By.name("email"));
        email.click();
        email.clear();
        email.sendKeys(emailp);
        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);
        //Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    public static void fillLoginFormApi(WebDriver driver, String emailp, String passwordp) {
        WebElement email = driver.findElement(By.name("email"));
        email.click();
        email.clear();
        email.sendKeys(emailp);
        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);
        //Pulsar el boton de Alta.
        By boton = By.xpath("//*[@id=\"boton-login\"]");
        driver.findElement(boton).click();
    }

    /**
     * Simulación de login de usuario con datos válidos.
     * <p>
     *
     * @param driver
     * @param user
     * @param password
     */
    static public void simulateLogin(WebDriver driver, String user, String password) {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        fillLoginForm(driver, user, password);
    }

    /**
     * Simula el proceso de cerrar sesión de un usuario.
     *
     * @param driver
     */
    static public void logout(WebDriver driver) {
        PO_NavView.clickOption(driver, "logout", "text", "Desconectate");
    }

    static public void checkLoginPage(WebDriver driver) {
        // Esperamos a que se cargue el saludo de bienvenida en Español
        SeleniumUtils.waitLoadElementsBy(driver, "text", "Identificación de usuario",
                getTimeout());
    }
}
