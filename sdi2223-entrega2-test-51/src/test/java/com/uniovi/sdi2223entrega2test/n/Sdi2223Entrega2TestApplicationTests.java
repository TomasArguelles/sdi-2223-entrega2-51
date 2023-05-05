package com.uniovi.sdi2223entrega2test.n;

import com.uniovi.sdi2223entrega2test.n.pageobjects.PO_OfferView;
import com.uniovi.sdi2223entrega2test.n.pageobjects.PO_View;
import com.uniovi.sdi2223entrega2test.n.util.DatabaseUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2223Entrega2TestApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String BASE_PATH = "http://localhost:8081";

    static String BASE_API_CLIENT_URL = "http://localhost:8081/api/v1.0"; // URL base del API del cliente

    // Kiko
    // static String Geckodriver = "C:\\Users\\kikoc\\Desktop\\SDI\\geckodriver-v0.30.0-win64.exe";
    // Teresa
    static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";

    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8081";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp() {
        //driver.navigate().to(URL);
    }

    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {
    }

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
//Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    // -------------------------------------
    // Parte 1 - Aplicacion Web
    // -------------------------------------

    /**
     * Parte 1 - Aplicacion Web - W6
     * <p>
     * [Prueba 16] Ir al formulario de alta de oferta, rellenarla con datos válidos
     * y pulsar el botón Submit.
     * Comprobar que la oferta sale en el listado de ofertas de dicho usuario.
     */
    @Test
    @Order(16)
    public void PR16() {
        DatabaseUtils.resetOffersCollection();

        // Añadir una oferta
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba", "Descripcion de la oferta de prueba", "10.0");

        // Comprobar que la oferta aparece en el listado de ofertas del usuario
        PO_OfferView.checkAddOffer(driver, "Oferta de prueba");
    }

    /**
     * Parte 1 - Aplicacion Web - W6
     * <p>
     * [Prueba 17] Ir al formulario de alta de oferta, rellenarla con datos
     * inválidos (campo título vacío y precio en negativo) y pulsar el botón
     * Submit.
     * <p>
     * Comprobar que se muestra el mensaje de campo inválido.
     */
    @Test
    @Order(17)
    public void PR17() {
        DatabaseUtils.resetOffersCollection();

        // Añadir una oferta introduciendo datos inválidos. Precio negativo
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba", "Descripcion de la oferta de prueba", "-1");

        // Comprobar que se muestra el mensaje de campo inválido. En este caso,
        // el campo precio debe ser mayor que 0.
        PO_View.checkErrorMessageIsShown(driver, "El precio debe ser mayor que 0");
    }

    /**
     * Parte 1 - Aplicacion Web - W7
     * <p>
     * [Prueba 18] Mostrar el listado de ofertas para dicho usuario y
     * comprobar que se muestran todas las que existen para este usuario.
     */
    @Test
    @Order(18)
    public void PR18() {
        DatabaseUtils.resetOffersCollection();

        // Añadir dos ofertas de prueba
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 1", "Descripcion de la oferta de prueba 1", "10");
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 2", "Descripcion de la oferta de prueba 2", "3");

        // *Nota: Se redirecciona al listado de ofertas propias del usuario depués
        // de añadir una oferta.

        // Acceder al listado de ofertas. Por defecto, si el login es satisfactorio
        // se redirige a la vista de listado de ofertas.
        PO_OfferView.checkOfferListingContainsOffers(driver, 2);
    }

    /**
     * Parte 1 - Aplicacion Web - W8
     * <p>
     * [Prueba 19] Ir a la lista de ofertas, borrar la primera oferta de la
     * lista, comprobar que la lista se actualiza y que la oferta desaparece.
     */
    @Test
    @Order(19)
    public void PR19() {
        DatabaseUtils.resetOffersCollection();

        // Añadir dos ofertas de prueba
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 1", "Descripcion de la oferta de prueba 1", "10");
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 2", "Descripcion de la oferta de prueba 2", "3");
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 3", "Descripcion de la oferta de prueba 3", "5");

        // Obtener la primera oferta de la lista y borrarla
        PO_OfferView.deleteOfferFromUserOffersList(driver, 1);

        // Comprobar que la oferta desaparece. Para ello, comprobar que no
        // aparece el título de la oferta en la lista de ofertas.
        PO_OfferView.checkOfferNotAppearOnList(driver, 1, "Oferta de prueba 1");
        PO_OfferView.checkOfferNotAppearOnList(driver, 2, "Oferta de prueba 1");
    }

    /**
     * Parte 1 - Aplicacion Web - W8
     * <p>
     * [Prueba 20] Ir a la lista de ofertas, borrar la última oferta de la
     * lista, comprobar que la lista se actualiza y que la oferta desaparece.
     */
    @Test
    @Order(20)
    public void PR20() {
        DatabaseUtils.resetOffersCollection();

        // Añadir dos ofertas de prueba
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 1", "Descripcion de la oferta de prueba 1", "10");
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 2", "Descripcion de la oferta de prueba 2", "3");

        // Obtener la ultima oferta de la lista y borrarla
        PO_OfferView.deleteOfferFromUserOffersList(driver, 3);

        // Comprobar que la oferta desaparece. Para ello, comprobar que no
        // aparece el título de la oferta en la lista de ofertas.
        PO_OfferView.checkOfferNotAppearOnList(driver, 2, "Oferta de prueba 3");
        PO_OfferView.checkOfferNotAppearOnList(driver, 1, "Oferta de prueba 3");
    }

    /**
     * Parte 1 - Aplicacion Web - W8
     * <p>
     * [Prueba 21] Ir a la lista de ofertas, borrar una oferta de otro usuario,
     * comprobar que la oferta no se borra.
     */
    @Test
    @Order(21)
    public void PR21() {
        DatabaseUtils.resetOffersCollection();

        // Añadir dos ofertas de prueba
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 1", "Descripcion de la oferta de prueba 1", "10");
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 2", "Descripcion de la oferta de prueba 2", "3");

        // Borrar una oferta de otro usuario
        PO_OfferView.deleteOfferFromUserOffersList(driver, 3);

        // Comprobar que la oferta desaparece. Para ello, comprobar que no
        // aparece el título de la oferta en la lista de ofertas.
        PO_OfferView.checkOfferNotAppearOnList(driver, 2, "Oferta de prueba 3");
        PO_OfferView.checkOfferNotAppearOnList(driver, 1, "Oferta de prueba 3");
    }

//    /**
//     * Parte 1 - Aplicacion Web - W8
//     * <p>
//     * [Prueba 22] Ir a la lista de ofertas, borrar una oferta propia que ha
//     * sido vendida, comprobar que la oferta no se borra.
//     */
//    @Test
//    @Order(22)
//    public void PR22() {
//        // Iniciar sesion
//        PO_LoginView.login(driver, "prueba2@prueba2.com", "prueba2", "");
//
//        // Acceder al listado de ofertas.
//
//        // Obtener una oferta propia que ha sido vendida
//
//        // Hacer click en el botón de borrar oferta
//
//        // Comprobar que la oferta no se borra
//    }

    /**
     * [Prueba23] Hacer una búsqueda con el campo vacío
     * y comprobar que se muestra la página que corresponde con el listado de las ofertas existentes
     * en el sistema
     */
    @Test
    @Order(23)
    public void PR023() {
        // Iniciar sesión
        // PO_LoginView.simulateLogin(driver, "prueba2@prueba2.com", "prueba2");

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda con el campo vacío
        // y comprobar que se muentra el listado de todas las ofertas existentes en el sistema

        // Cerrar sesión
        // PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    /**
     * [Prueba24] Hacer una búsqueda escribiendo en el campo un texto que no exista
     * y comprobar que se muestra la página que corresponde, con la lista de ofertas vacía.
     */
    @Test
    @Order(24)
    public void PR024() {
        // Iniciar sesión

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título que no corresponda con ninguna oferta
        // y comprobar que no se muentra ninguna oferta

        // Cerrar sesión
    }

    /**
     * [Prueba25] Hacer una búsqueda escribiendo en el campo un texto en minúscula o mayúscula
     * y comprobar que se muestra la página que corresponde, con la lista de ofertas que contengan
     * dicho texto, independientemente que el título esté almacenado en minúsculas o mayúscula
     */
    @Test
    @Order(25)
    public void PR025() {
        // Iniciar sesión

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título existente, pero en mayúsculas
        // y comprobar que se muentra la oferta correspondiente aunque su título esté en minúsculas

        // Cerrar sesión
    }

    /**
     * [Prueba26] Sobre una búsqueda determinada,
     * comprar una oferta que deja un saldo positivo en el contador del comprobador.
     * Y comprobar que el contador se actualiza correctamente en la vista del comprador.
     */
    @Test
    @Order(26)
    public void PR026() {
        // Iniciar sesión

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título existente
        // y comprar dicha oferta dejando positivo el saldo del comprador
        // Comprobar que el contador se actualiza correctamente en la vista del comprador

        // Cerrar sesión
    }

    /**
     * [Prueba27] Sobre una búsqueda determinada,
     * comprar una oferta que deja un saldo 0 en el contador del comprobador.
     * Y comprobar que el contador se actualiza correctamente en la vista del comprador.
     */
    @Test
    @Order(27)
    public void PR027() {
        // Iniciar sesión

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título existente
        // y comprar dicha oferta dejando nulo el saldo del comprador
        // Comprobar que el contador se actualiza correctamente en la vista del comprador

        // Cerrar sesión
    }

    /**
     * [Prueba28] Sobre una búsqueda determinada,
     * intentar comprar una oferta que esté por encima de saldo disponible del comprador.
     * Y comprobar que se muestra el mensaje de saldo no suficiente.
     */
    @Test
    @Order(28)
    public void PR028() {
        // Iniciar sesión

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título existente
        // e intentar comprar dicha oferta con precio mayor al saldo del comprador
        // Comprobar que se muestra el mensaje de saldo insuficiente

        // Cerrar sesión
    }

    /**
     * [Prueba29] Ir a la opción de ofertas compradas del usuario y mostrar la lista.
     * Comprobar que aparecen las ofertas que deben aparecer.
     */
    @Test
    @Order(29)
    public void PR029() {
        // Iniciar sesión

        // Acceder a la vista del listado de ofertas compradas por el usuario
        // Comprobar que se muestra la lista de ofertas correspondientes

        // Cerrar sesión
    }

//    // -------------------------------------
//    // Parte 2B - Cliente ligero JQuery/AJAX
//    // -------------------------------------
//
//    /**
//     * Cliente ligero JQuery/AJAX
//     * <p>
//     * [Prueba 48] Inicio de sesion con datos válidos.
//     */
//    @Test
//    @Order(48)
//    public void PR48() {
//        // Acceder a la página de login
//        driver.get(BASE_API_CLIENT_URL + "/client.html?w=login");
//
//        PO_LoginView.fillLoginForm(driver, "prueba2@prueba2.com", "prueba2");
//
//        // Comprobar que se ha iniciado sesión y se redirecciona a la página
//        // que contiene el listado de ofertas disponibles.
//        Assertions.assertEquals(BASE_API_CLIENT_URL + "/client.html?w=offers", driver.getCurrentUrl());
//    }
//
//    /**
//     * Cliente ligero JQuery/AJAX
//     * <p>
//     * [Prueba 49] Inicio de sesión con datos inválidos (email existente, pero contraseña incorrecta)
//     */
//    @Test
//    @Order(49)
//    public void PR49() {
//        // Acceder a la página de login
//        driver.get(BASE_API_CLIENT_URL + "/client.html?w=login");
//
//        // Rellenar el formulario, introduciendo una contraseña incorrecta
//        PO_LoginView.fillLoginForm(driver, "prueba2@prueba2.com", "1234");
//
//        // Comprobar que se muestra un mensaje de error en la vista
//        List<WebElement> errors = PO_View.checkElementBy(driver, "text", "");
//        Assertions.assertEquals(1, errors.size());
//    }
//
//    /**
//     * Cliente ligero JQuery/AJAX
//     * <p>
//     * [Prueba 50] Inicio de sesión con datos inválidos (campo email o contraseña vacíos).
//     */
//    @Test
//    @Order(50)
//    public void PR50() {
//        // Acceder a la página de login
//        driver.get(BASE_API_CLIENT_URL + "/client.html?w=login");
//
//        // Rellenar el formulario, introduciendo solo la contraseña, sin email
//        PO_LoginView.fillLoginForm(driver, "", "prueba2");
//
//        // Comprobar que se muestra un mensaje de error en la vista
//        // indicando que faltan campos por completar
//        List<WebElement> errors = PO_View.checkElementBy(driver, "text", "");
//        Assertions.assertEquals(1, errors.size());
//    }
//    @Test
//    @Order(7)
//    public void PR07() {
//        Assertions.assertTrue(false, "PR07 sin hacer");
//    }
//
//    @Test
//    @Order(8)
//    public void PR08() {
//        Assertions.assertTrue(false, "PR08 sin hacer");
//    }
//
//    @Test
//    @Order(9)
//    public void PR09() {
//        Assertions.assertTrue(false, "PR09 sin hacer");
//    }
//
//    @Test
//    @Order(10)
//    public void PR10() {
//        Assertions.assertTrue(false, "PR10 sin hacer");
//    }
//
//
//    /* Ejemplos de pruebas de llamada a una API-REST */
//    /* ---- Probamos a obtener lista de canciones sin token ---- */
//    @Test
//    @Order(11)
//    public void PR11() {
//        final String RestAssuredURL = "http://localhost:8081/api/v1.0/songs";
//        Response response = RestAssured.get(RestAssuredURL);
//        Assertions.assertEquals(403, response.getStatusCode());
//    }
//
//    @Test
//    @Order(38)
//    public void PR38() {
//        final String RestAssuredURL = "http://localhost:8081/api/v1.0/users/login";
//        //2. Preparamos el parámetro en formato JSON
//        RequestSpecification request = RestAssured.given();
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("email", "prueba1@prueba1.com");
//        requestParams.put("password", "prueba1");
//        request.header("Content-Type", "application/json");
//        request.body(requestParams.toJSONString());
//        //3. Hacemos la petición
//        Response response = request.post(RestAssuredURL);
//        //4. Comprobamos que el servicio ha tenido exito
//        Assertions.assertEquals(200, response.getStatusCode());
//    }
}
