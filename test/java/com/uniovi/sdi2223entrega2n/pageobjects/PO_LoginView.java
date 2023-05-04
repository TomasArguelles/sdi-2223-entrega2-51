package com.uniovi.sdi2223entrega2n.pageobjects;

import com.uniovi.sdi2223entrega2n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {

    static String LOGIN_BUTTON_TEXT = "Aceptar";

    static public void fillForm(WebDriver driver, String emailp, String passwordp) {
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

    static public void checkLoginPage(WebDriver driver) {
//Esperamos a que se cargue el saludo de bienvenida en Español
        SeleniumUtils.waitLoadElementsBy(driver, "text", "Identificación de usuario",
                getTimeout());
    }

    /**
     * Acción de rellenar el formulario de login con credenciales válidas
     * y enviarlo.
     */
    static public void simulateLogin(WebDriver driver, String emailp, String passwordp) {
        fillForm(driver, emailp, passwordp);
        PO_View.checkElementBy(driver, "text", LOGIN_BUTTON_TEXT);
    }
}
