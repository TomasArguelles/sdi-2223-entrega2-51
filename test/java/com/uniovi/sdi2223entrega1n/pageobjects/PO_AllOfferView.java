package com.uniovi.sdi2223entrega1n.pageobjects;

import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_AllOfferView extends PO_NavView{


    public static void SearchInvalid(WebDriver driver, String invalid) {

        PO_NavView.selectDropdownById(driver,"gestionOfertasMenu","gestionOfertasDropdow","listAllOfferMenu");

        PO_AllOfferView.fillForm(driver,invalid);

    }

    public static void fillForm(WebDriver driver, String invalid){

        //Ponemos el texto vacio
        WebElement seeker = driver.findElement(By.name("searchText"));
        seeker.click();
        seeker.clear();
        seeker.sendKeys(invalid);
        //Damos al boton buscar
        By boton = By.name("search");
        driver.findElement(boton).click();

    }

    public static void buyOffer(WebDriver driver,String nameButton) {
        PO_NavView.selectDropdownById(driver,"gestionOfertasMenu","gestionOfertasDropdow","listAllOfferMenu");
        By boton = By.id(nameButton);
        driver.findElement(boton).click();
    }

    public static String seeWallet(WebDriver driver) {

        return driver.findElement(By.id("wallet")).getText();

    }

    public static String getTitleMessage(WebDriver driver) {
        return driver.findElement(By.name("title")).getText();
    }
}
