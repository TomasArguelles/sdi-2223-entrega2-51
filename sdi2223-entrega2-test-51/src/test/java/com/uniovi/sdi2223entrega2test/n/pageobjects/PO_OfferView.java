package com.uniovi.sdi2223entrega2test.n.pageobjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_OfferView {

    static String BASE_PATH = "http://localhost:8081";

    /**
     * Rellena el formulario de alta de oferta
     *
     * @param driver
     * @param titlep       Titulo de la oferta
     * @param descriptionp Detalle de la oferta
     * @param pricep       Precio de la oferta
     */
    public static void fillAddNewOfferForm(WebDriver driver, String titlep, String descriptionp, String pricep) {
        // Rellenamos el campo de título
        WebElement offerTitle = driver.findElement(By.name("title"));
        offerTitle.click();
        offerTitle.clear();
        offerTitle.sendKeys(titlep);

        // Rellenamos el campo de descripción
        WebElement offerDescription = driver.findElement(By.name("description"));
        offerDescription.click();
        offerDescription.clear();
        offerDescription.sendKeys(descriptionp);

        // Rellenamos el campo de precio
        WebElement offerPrice = driver.findElement(By.name("price"));
        offerPrice.click();
        offerPrice.clear();
        offerPrice.sendKeys(pricep);

        // Pulsar el boton de Alta.
        By addOfferButton = By.xpath("/html/body/div/form/div[5]/div/button");
        driver.findElement(addOfferButton).click();
    }

    /**
     * Comprobar que la oferta se ha añadido correctamente.
     *
     * @param driver
     * @param titlep
     */
    public static void checkAddOffer(WebDriver driver, String titlep) {
        // Esperamos a que se cargue el saludo de bienvenida en Español
        WebElement element = driver.findElement(By.xpath("//*[contains(text(),'" + titlep + "')]"));
        Assertions.assertNotNull(element);
    }

    /**
     * Simula el proceso de añadir una nueva oferta.
     *
     * @param driver
     * @param titlep
     * @param descriptionp
     * @param pricep
     */
    public static void simulateAddNewOffer(WebDriver driver, String titlep, String descriptionp, String pricep) {
        // Iniciar sesion
        driver.navigate().to(BASE_PATH + "/users/login");
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        // Pulsar en la opción de menu de agregar oferta:
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");

        // Rellenar el formulario con datos válidos
        fillAddNewOfferForm(driver, titlep, descriptionp, pricep);
    }

    /**
     * Simula el proceso de añadir una nueva oferta. Método sobrecargado para
     * permitir el inicio de sesión con un usuario determinado.
     * <p>
     *
     * @param driver
     * @param userEmail
     * @param userPassword
     * @param titlep
     * @param descriptionp
     * @param pricep
     */
    public static void simulateAddNewOffer(WebDriver driver, String userEmail, String userPassword, String titlep, String descriptionp, String pricep) {
        // Iniciar sesion
        driver.navigate().to(BASE_PATH + "/users/login");
        PO_LoginView.fillLoginForm(driver, userEmail, userPassword);

        // Pulsar en la opción de menu de agregar oferta:
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");

        // Rellenar el formulario con datos válidos
        fillAddNewOfferForm(driver, titlep, descriptionp, pricep);
    }

    /**
     * Comprueba que la lista de ofertas contiene el número de ofertas determinado.
     *
     * @param driver
     * @param numberOfOffersExpected Número de ofertas esperado en la lista.
     */
    public static void checkOfferListingContainsOffers(WebDriver driver, int numberOfOffersExpected) {
        boolean isOffers = driver.findElements(By.xpath("/html/body/div/table/tbody/tr")).size() == numberOfOffersExpected;
        Assertions.assertTrue(isOffers);
    }

    /**
     * Eliminar la oferta de la fila <code>row</code> indicada.
     * <p>
     * Acceder al listado de ofertas propias.
     *
     * @param driver
     * @param row    Número de fila de la oferta a eliminar.
     */
    public static void deleteOfferFromUserOffersList(WebDriver driver, int row) {
        // Pulsar en la opción de menu de listar ofertas propias
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listOfferMenu");

        // Dentro de la vista del listado de ofertas del usuario, pulsar
        // el botón de eliminar oferta
        PO_View.clickOnButton(driver, "/html/body/div/table/tbody/tr[" + row + "]/td[5]/a");
    }

    /**
     * Eliminar la oferta de la fila <code>row</code> indicada.
     * <p>
     * Acceder al listado de ofertas disponibles. Que no pertenecen al usuario.
     *
     * @param driver
     * @param row
     */
    public static void deleteOfferFromAllAvailableOfferList(WebDriver driver, int row) {
        // Pulsar en la opción de menu de listar ofertas para comprar
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listAllOffersMenu");

        // Dentro de la vista del listado de ofertas del usuario, pulsar
        // el botón de eliminar oferta
        PO_View.clickOnButton(driver, "/html/body/div/div[2]/table/tbody/tr[" + row + "]/td[5]/a");
    }

    /**
     * Comprueba que la oferta no aparece en la lista de ofertas del usuario.
     * Para obtener la oferta, se utiliza el título de la oferta.
     *
     * @param driver
     * @param row        Número de fila de la oferta a eliminar.
     * @param offerTitle Título de la oferta a comprobar.
     */
    public static void checkOfferNotAppearOnList(WebDriver driver, int row, String offerTitle) {
        WebElement element = driver.findElement(By.xpath("/html/body/div/table/tbody/tr[" + row + "]/td[1]"));
        Assertions.assertFalse(element.getText().contains(offerTitle));
    }

    /**
     * Comprueba que la oferta aparece en la lista de ofertas del usuario.
     * Para obtener la oferta, se utiliza el título de la oferta.
     *
     * @param driver
     * @param row        Número de fila de la oferta a eliminar.
     * @param offerTitle Título de la oferta a comprobar.
     */
    public static void checkOfferAppearOnList(WebDriver driver, int row, String offerTitle) {
        WebElement element = driver.findElement(By.xpath("/html/body/div/table/tbody/tr[" + row + "]/td[1]"));
        Assertions.assertTrue(element.getText().contains(offerTitle));
    }

    /**
     * Comprueba que la oferta aparece en la lista de ofertas disponibles.
     * Para obtener la oferta, se utiliza el título de la oferta.
     * <p>
     *
     * @param driver
     * @param row
     * @param offerTitle
     */
    public static void checkOfferAppearOnAllAvailableOfferList(WebDriver driver, int row, String offerTitle) {
        WebElement element = driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr[" + row + "]/td[1]"));
        Assertions.assertTrue(element.getText().contains(offerTitle));
    }
}
