package com.uniovi.sdi2223entrega1n.pageobjects;

import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_OfferView extends PO_NavView {
    static public void fillForm(WebDriver driver, String titleP, String descriptionP, Double priceP, Boolean isFeatured) {

        // Campo titulo de la oferta
        WebElement offerTitle = driver.findElement(By.name("title"));
        offerTitle.click();
        offerTitle.clear();
        offerTitle.sendKeys(titleP);

        // Campo descripcion de la oferta
        WebElement description = driver.findElement(By.name("description"));
        description.click();
        description.clear();
        description.sendKeys(descriptionP);

        // Campo precio (En euros) de la oferta
        WebElement price = driver.findElement(By.name("price"));
        price.click();
        price.clear();
        price.sendKeys(priceP.toString());

        // Checkbox destacar la oferta
        if (isFeatured) {
            WebElement featured = driver.findElement(By.name("featured"));
            featured.click();
        }

        // Pulsar el boton de Añadir.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    /**
     * Eliminar una oferta de la lista.
     *
     * @param driver
     * @param position Número de fila de la tabla donde se encuentra el botón borrar oferta específico.
     */
    static public void clickDeleteButton(WebDriver driver, int position) {
        List<WebElement> markList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());

        // Boton de eliminar de la primera fila
        WebElement btnDeleteOffer = markList.get(position).findElement(By.className("btnBorrarOferta"));

        btnDeleteOffer.click();
    }

    /**
     * Añadir una oferta para probar.
     *
     * @param driver
     * @param offerTitle
     */
    static public void addSampleOffer(WebDriver driver, String offerTitle) {
        // Acceder a la vista de añadir una nueva oferta
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");

        // Rellenar campos del formulario con valores inválidos.
        PO_OfferView.fillForm(driver, offerTitle, "Descripcion de prueba", 100.0, false);
    }

    /**
     * Añadir una oferta para probar.
     *
     * @param driver
     * @param offerTitle Titulo de la oferta
     * @param offerDescription Descripcion de la oferta
     * @param offerPrice Precio de la oferta
     */
    static public void addSampleOfferWithDescriptionAndPrice(WebDriver driver, String offerTitle, String offerDescription, Double offerPrice) {
        // Acceder a la vista de añadir una nueva oferta
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");

        // Rellenar campos del formulario con valores inválidos.
        PO_OfferView.fillForm(driver, offerTitle, "Coche de los años 90", 100.0, false);
    }

    /**
     * Comprueba que se muestra el mensaje de error con clave <code>resourceKey</code>.
     *
     * @param driver
     * @param language
     * @param resourceKey Clave en el fichero de idiomas.
     */
    static public void checkErrorMessage(WebDriver driver, int language, String resourceKey) {
        // Esperamos a que se cargue el saludo de bienvenida en Español
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString(resourceKey, language),
                getTimeout());
    }

    /**
     * Comprueba que la oferta no está en el listado de ofertas.
     *
     * @param driver
     * @param textToSearch
     */
    static public void checkOfferNotExistsOnPage(WebDriver driver, String textToSearch) {
        SeleniumUtils.textIsNotPresentOnPage(driver, textToSearch);
    }

    /**
     * Comprueba que se muestra el mensaje de no tener suficiente dinero para destacar una oferta
     *
     * @param driver
     * @param language
     * @param resourceKey Clave en el fichero de idiomas.
     */
    static public void checkNoMoneyMessage(WebDriver driver, int language, String resourceKey) {
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString(resourceKey, language),
                getTimeout());
    }

    /**
     * Añadir una oferta con imagen para probar.
     *
     * @param driver
     * @param s
     */
    public static void addImageOffer(WebDriver driver, String s) {
        // Acceder a la vista de añadir una nueva oferta
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");

        // Rellenar campos del formulario con valores inválidos.
        PO_OfferView.fillImageForm(driver, s, "Coche de los años 90", 100.0, false);
    }

    private static void fillImageForm(WebDriver driver, String titleP, String descriptionP, Double priceP, Boolean isFeatured) {
        // Campo titulo de la oferta
        WebElement offerTitle = driver.findElement(By.name("title"));
        offerTitle.click();
        offerTitle.clear();
        offerTitle.sendKeys(titleP);

        // Campo descripcion de la oferta
        WebElement description = driver.findElement(By.name("description"));
        description.click();
        description.clear();
        description.sendKeys(descriptionP);

        // Campo precio (En euros) de la oferta
        WebElement price = driver.findElement(By.name("price"));
        price.click();
        price.clear();
        price.sendKeys(priceP.toString());

        //Campo imagen
        WebElement image = driver.findElement(By.name("image"));
        image.click();
        image.clear();
        image.sendKeys("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/iphone-12-finish-select-202207-white?wid=2560&hei=1440&fmt=p-jpg&qlt=80&.v=1662150115751");

        // Checkbox destacar la oferta
        if (isFeatured) {
            WebElement featured = driver.findElement(By.name("featured"));
            featured.click();
        }

        // Pulsar el boton de Añadir.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

}
