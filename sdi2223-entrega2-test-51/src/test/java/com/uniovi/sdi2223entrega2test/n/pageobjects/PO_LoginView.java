package com.uniovi.sdi2223entrega2test.n.pageobjects;

import com.uniovi.sdi2223entrega2test.n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {

    static public void fillLoginForm(WebDriver driver, String dnip, String passwordp) {
        WebElement dni = driver.findElement(By.name("email"));
        dni.click();
        dni.clear();
        dni.sendKeys(dnip);
        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);
        //Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void login(WebDriver driver, String user, String password, String checkText) {
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
