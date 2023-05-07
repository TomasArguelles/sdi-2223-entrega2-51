package com.uniovi.sdi2223entrega2test.n.pageobjects;

import com.uniovi.sdi2223entrega2test.n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_SignUpView extends PO_NavView {
    static public void fillForm(WebDriver driver, String emailp, String namep, String surnamep, String datep, String
            passwordp, String passwordconfp) {
        WebElement email = driver.findElement(By.name("email"));
        email.click();
        email.clear();
        email.sendKeys(emailp);
        WebElement name = driver.findElement(By.name("name"));
        name.click();
        name.clear();
        name.sendKeys(namep);
        WebElement surname = driver.findElement(By.name("surname"));
        surname.click();
        surname.clear();
        surname.sendKeys(surnamep);
        WebElement date = driver.findElement(By.name("date"));
        date.click();
//        date.clear();
        date.sendKeys("2023-05-22");
        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);
        WebElement passwordConfirm = driver.findElement(By.name("passwordConfirm"));
        passwordConfirm.click();
        passwordConfirm.clear();
        passwordConfirm.sendKeys(passwordconfp);
        // Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    public static void registerUsersForTesting(WebDriver driver) {
        // user01
        registerOneUser(driver, "user01@email.com", "user01",
                "user01", "2023-05-22", "user01", "user01");

        // user02
        registerOneUser(driver, "user02@email.com", "user02",
                "user02", "2023-05-22", "user02", "user02");

        // user03
        registerOneUser(driver, "user03@email.com", "user03",
                "user03", "2023-05-22", "user03", "user03");

        //user04
        registerOneUser(driver, "user04@email.com", "user04",
                "user04", "2023-05-22", "user04", "user04");

        //user05
        registerOneUser(driver, "user05@email.com", "user05",
                "user05", "2023-05-22", "user05", "user05");

        //user06
        registerOneUser(driver, "user06@email.com", "user06",
                "user06", "2023-05-22", "user06", "user06");

        //user07
        registerOneUser(driver, "user07@email.com", "user07",
                "user07", "2023-05-22", "user07", "user07");

        //user08
        registerOneUser(driver, "user08@email.com", "user08",
                "user08", "2023-05-22", "user08", "user08");

        //user09
        registerOneUser(driver, "user09@email.com", "user09",
                "user09", "2023-05-22", "user09", "user09");

        //user10
        registerOneUser(driver, "user10@email.com", "user10",
                "user10", "2023-05-22", "user10", "user10");

        //user11
        registerOneUser(driver, "user11@email.com", "user11",
                "user11", "2023-05-22", "user11", "user11");

        //user12
        registerOneUser(driver, "user12@email.com", "user12",
                "user12", "2023-05-22", "user12", "user12");

        //user13
        registerOneUser(driver, "user13@email.com", "user13",
                "user13", "2023-05-22", "user13", "user13");

        //user14
        registerOneUser(driver, "user14@email.com", "user14",
                "user14", "2023-05-22", "user14", "user14");

        //user15
        registerOneUser(driver, "user15@email.com", "user15",
                "user15", "2023-05-22", "user15", "user15");
    }

    private static void registerOneUser(WebDriver driver, String email, String name, String surname, String date, String password, String passwordConfirm) {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        fillForm(driver, email, name, surname, date, password, passwordConfirm);
    }

    static public void checkSignUpPage(WebDriver driver) {
        // Esperamos a que se cargue el saludo de bienvenida en Español
        SeleniumUtils.waitLoadElementsBy(driver, "text", "Registrar usuario",
                getTimeout());
    }

}
