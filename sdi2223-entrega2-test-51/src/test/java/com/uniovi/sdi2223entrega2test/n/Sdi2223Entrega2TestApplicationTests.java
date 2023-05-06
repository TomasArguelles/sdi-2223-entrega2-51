package com.uniovi.sdi2223entrega2test.n;

import com.uniovi.sdi2223entrega2test.n.pageobjects.*;
import com.uniovi.sdi2223entrega2test.n.util.DatabaseUtils;
import com.uniovi.sdi2223entrega2test.n.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2223Entrega2TestApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String BASE_PATH = "http://localhost:8081";

    static String BASE_API_CLIENT_URL = "http://localhost:8081/api/v1.0"; // URL base del API del cliente

    // Kiko
    static String Geckodriver = "C:\\Users\\kikoc\\Desktop\\SDI\\geckodriver-v0.30.0-win64.exe";
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
        driver.navigate().to(URL);
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
        // Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    // -------------------------------------
    // Parte 1 - Aplicacion Web
    // -------------------------------------
    //    [Prueba1] Registro de Usuario con datos válidos.
    //    [Prueba1] Registro de Usuario con datos válidos.
    @Test
    @Order(1)
    void PR01() {
        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos VALIDOS
        PO_SignUpView.fillForm(driver, "JoseFo@gmail.com", "Josefo", "Perez", "2023-05-22",
                "77777", "77777");
        //Comprobamos que hemos ido a la pagina de home, confirmando que el registro se ha completado con exito
        PO_HomeView.checkWelcomeToPage(driver);
    }

    //    [Prueba2] Registro de Usuario con datos inválidos (email, nombre, apellidos y fecha de nacimiento vacíos).
    @Test
    @Order(2)
    void PR02() {
        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos INVALIDOS
        PO_SignUpView.fillForm(driver, "", "", "", "", "77777", "77777");
        //Comprobamos que seguimos en la pantalla de registro
        PO_SignUpView.checkSignUpPage(driver);
    }

    //    [Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña inválida).
    @Test
    @Order(3)
    void PR03() {
        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos INVALIDOS
        PO_SignUpView.fillForm(driver, "JoseFo@gmail.com", "Josefo", "2023-05-22", "Perez", "77777", "773777");
        //Comprobamos que seguimos en la pantalla de registro
        PO_SignUpView.checkSignUpPage(driver);
    }

    //    [Prueba4] Registro de Usuario con datos inválidos (email existente).
    @Test
    @Order(4)
    void PR04() {
        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos VALIDOS
        PO_SignUpView.fillForm(driver, "JoseFo1@gmail.com", "Josefo", "Perez", "2023-05-22", "77777", "77777");
        //Comprobamos que hemos ido a la pagina de home, confirmando que el registro se ha completado con exito
        PO_HomeView.checkWelcomeToPage(driver);

        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos INVALIDOS
        PO_SignUpView.fillForm(driver, "JoseFo1@gmail.com", "Josefo", "Perez", "2023-05-22", "77777", "77777");
        //Comprobamos que seguimos en la pantalla de registro
        PO_SignUpView.checkSignUpPage(driver);
    }

    //[Prueba5] Inicio de sesión con datos válidos (administrador).
    @Test
    @Order(5)
    void PR05() {
        //Nos movemos al formulario de inicio de sesión
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos con datos validos del usuario administrador
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        //Comprobamos que hemos ido a la pagina de home, confirmando que el inicio de sesión se ha completado con exito
        PO_HomeView.checkWelcomeToPage(driver);
    }

    //[Prueba6] Inicio de sesión con datos válidos (usuario estándar).
    @Test
    @Order(6)
    void PR06() {
        //Nos movemos al formulario de inicio de sesión
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos con datos validos del usuario estandar
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Comprobamos que hemos ido a la pagina de home, confirmando que el inicio de sesión se ha completado con exito
        PO_HomeView.checkWelcomeToPage(driver);
    }

    //[Prueba7] Inicio de sesión con datos válidos (usuario estándar, email existente, pero contraseña incorrecta)
    @Test
    @Order(7)
    void PR07() {
        // Insertar contraseña incorrecta
        SeleniumUtils.signInIntoAccount(driver, "STANDARD", "user01@email.com", "123");
        PO_LoginView.checkLoginPage(driver);
    }

    //[Prueba8] Inicio de sesión con datos inválidos (usuario estándar, campo contraseña vacío)
    @Test
    @Order(8)
    void PR08() {
        // Insertar contraseña incorrecta
        SeleniumUtils.signInIntoAccount(driver, "STANDARD", "user01@email.com", "");
        //Comprobamos que seguimos en la pantalla de inicio de sesión
        PO_LoginView.checkLoginPage(driver);
    }

    //[Prueba9] Hacer clic en la opción de salir de sesión y comprobar que se redirige a la página de inicio de sesión (Login).
    @Test
    @Order(9)
    void PR09() {
        //Nos movemos al formulario de inicio de sesión
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos con datos validos del usuario estandar
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Comprobamos que hemos ido a la pagina de home, confirmando que el inicio de sesión se ha completado con exito
        PO_HomeView.checkWelcomeToPage(driver);
        //Nos movemos al formulario de inicio de sesión
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        //Comprobamos que estamos en la pantalla de inicio de sesión
        PO_LoginView.checkLoginPage(driver);
    }

    //[Prueba10] Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
    @Test
    @Order(10)
    void PR010() {
        //Buscamos que tenga el tex
        SeleniumUtils.textIsNotPresentOnPage(driver, "Desconectate");
    }


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
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 3", "Descripcion de la oferta de prueba 3", "2");

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
        PO_OfferView.simulateAddNewOffer(driver, "Oferta de prueba 3", "Descripcion de la oferta de prueba 3", "2");

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
//
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
