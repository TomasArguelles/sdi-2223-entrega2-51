package com.uniovi.sdi2223entrega2n.pageobjects;

import com.uniovi.sdi2223entrega2n.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_OfferView extends PO_NavView {

    static String BASE_PATH = "http://localhost:8081";

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
     * Simula el proceso de añadir una nueva oferta.
     *
     * @param driver
     * @param titlep
     * @param descriptionp
     * @param pricep
     */
    public static void simulateAddNewOffer(WebDriver driver, String titlep, String descriptionp, String pricep, boolean featured) {
        // Iniciar sesion
        driver.navigate().to(BASE_PATH + "/users/login");
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        // Pulsar en la opción de menu de agregar oferta:
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");

        // Rellenar el formulario con datos válidos
        fillAddNewOfferForm(driver, titlep, descriptionp, pricep, featured);
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
     * @param offerTitle       Titulo de la oferta
     * @param offerDescription Descripcion de la oferta
     * @param offerPrice       Precio de la oferta
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
        fillAddNewOfferForm(driver, titlep, descriptionp, pricep, false);
    }

    /**
     * Rellena el formulario de alta de oferta
     *
     * @param driver
     * @param titlep       Titulo de la oferta
     * @param descriptionp Detalle de la oferta
     * @param pricep       Precio de la oferta
     */
    public static void fillAddNewOfferForm(WebDriver driver, String titlep, String descriptionp, String pricep, boolean featured) {
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

        // Destacar nueva oferta
        if (featured)
            driver.findElement(By.name("destacar")).click();

        // Pulsar el boton de Alta.
        By addOfferButton = By.xpath("/html/body/div/form/div[5]/div/button");
        driver.findElement(addOfferButton).click();
    }


    /**
     * Comprueba que la lista de ofertas contiene el número de ofertas determinado.
     *
     * @param driver
     * @param numberOfOffersExpected Número de ofertas esperado en la lista.
     */
    public static void checkOfferListingContainsOffers(WebDriver driver, int numberOfOffersExpected) {
        boolean isOffers = driver.findElements(By.xpath("/html/body/div/div[2]/table/tbody/tr")).size() == numberOfOffersExpected;
        Assertions.assertTrue(isOffers);
    }

    public static void checkOfferListingContainsOffersDest(WebDriver driver, int numberOfOffersExpected) {
        boolean isOffers = driver.findElements(By.xpath("/html/body/div/div[2]/table/tbody/tr")).size() == numberOfOffersExpected;
        Assertions.assertFalse(isOffers);
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
        PO_View.clickOnButton(driver, "/html/body/div/div[2]/table/tbody/tr[" + row + "]/td[5]/a");
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
        WebElement element = driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr[" + row + "]/td[1]"));
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
        Assertions.assertFalse(element.getText().contains(offerTitle));
    }

    /**
     * Realiza una búsqueda (usando el buscador)
     * @param driver
     * @param search, lo que se busca (título de la oferta o campo vacío)
     */
    public static void Search(WebDriver driver, String search) {
        WebElement element = driver.findElement(By.name("search"));
        element.click();
        element.clear();
        element.sendKeys(search);
        driver.findElement(By.name("btSearch")).click();
    }

    /**
     * Realiza la compra de una oferta
     * @param driver
     */
    public static void buyOffer(WebDriver driver) {
        driver.findElement(By.id("buy")).click();
    }
}
