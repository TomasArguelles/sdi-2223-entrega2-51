package com.uniovi.sdi2223entrega1n;

import com.uniovi.sdi2223entrega1n.pageobjects.PO_LoginView;
import com.uniovi.sdi2223entrega1n.pageobjects.PO_View;
import com.uniovi.sdi2223entrega1n.repositories.OffersRepository;
import com.uniovi.sdi2223entrega1n.services.UsersService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2223Entrega1NApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";


    static String Geckodriver = "C:\\Users\\Usuario\\Desktop\\SDI\\sesion5\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";

    // Kiko
    static String Geckodriver = "\"C:\\Users\\kikoc\\Desktop\\SDI\\geckodriver-v0.30.0-win64.exe\"";


    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String BASE_HTTP_URL = "http://localhost:8081";

    // Url por defecto para el cliente HTTP (REST)
    static String BASE_API_CLIENT_URL = BASE_HTTP_URL + "/apiclient";

    // Endpoint para mostrar el listado de usuarios (Ver UserController)
    static final String USER_LIST_ENDPOINT = BASE_HTTP_URL + "/user/list";

    // Endpoint para mostrar la vista de conversaciones de un usuario.
    static final String CONVERSATION_LIST_ENDPOINT = BASE_HTTP_URL + "/conversation/list";


    // Enpoint para mostrar la vista de login
    static final String LOGIN_ENDPOINT = BASE_HTTP_URL + "/login";

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private UsersService userService;

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp() {
        driver.navigate().to(BASE_ENDPOINT);
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
        // Iniciar sesion
        PO_LoginView.simulateLogin("prueba2@prueba2.com", "prueba2");

        // Dentro de la vista del listado de ofertas del usuario, pulsar
        // el botón de añadir oferta

        // Rellenar el formulario con datos válidos

        // Comprobar que la oferta aparece en el listado de ofertas del usuario
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
        // Iniciar sesion
        PO_LoginView.simulateLogin("prueba2@prueba2.com", "prueba2");

        // Dentro de la vista del listado de ofertas del usuario, pulsar
        // el botón de añadir oferta

        // Rellenar el formulario con datos inválidos. Para ello, introducir
        // un precio negativo

        // Comprobar que se muestra el mensaje de campo inválido. En este caso,
        // el campo precio debe ser mayor que 0.
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
        // Iniciar sesion
        PO_LoginView.simulateLogin("prueba2@prueba2.com", "prueba2");

        // Acceder al listado de ofertas. Por defecto, si el login es satisfactorio
        // se redirige a la vista de listado de ofertas.

        // Comprobar que se muestran todas las ofertas publicada
        // por el usuario en sesion.
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
        // Iniciar sesion
        PO_LoginView.simulateLogin("prueba2@prueba2.com", "prueba2");

        // Acceder al listado de ofertas.

        // Obtener la primera oferta de la lista

        // Hacer click en el botón de borrar oferta

        // Comprobar que la oferta desaparece. Para ello, comprobar que no
        // aparece el título de la oferta en la lista de ofertas.
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
        // Iniciar sesion
        PO_LoginView.simulateLogin("prueba2@prueba2.com", "prueba2");

        // Acceder al listado de ofertas.

        // Obtener la primera oferta de la lista

        // Hacer click en el botón de borrar oferta

        // Comprobar que la oferta desaparece. Para ello, comprobar que no
        // aparece el título de la oferta en la lista de ofertas.
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
        // Iniciar sesion
        PO_LoginView.simulateLogin("prueba2@prueba2.com", "prueba2");

        // Acceder al listado de ofertas.

        // Obtener la ÚLTIMA oferta de la lista

        // Hacer click en el botón de borrar oferta

        // Comprobar que la oferta desaparece.
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
        // Iniciar sesion
        PO_LoginView.simulateLogin("prueba2@prueba2.com", "prueba2");

        // Acceder al listado de ofertas.

        // Obtener una oferta que no pertenece al usuario en sesion

        // Hacer click en el botón de borrar oferta

        // Comprobar que la oferta no se borra. Para ello, comprobar que
        // sigue apareciendo en el listado de ofertas.
    }

    /**
     * Parte 1 - Aplicacion Web - W8
     * <p>
     * [Prueba 22] Ir a la lista de ofertas, borrar una oferta propia que ha
     * sido vendida, comprobar que la oferta no se borra.
     */
    @Test
    @Order(22)
    public void PR22() {
        // Iniciar sesion
        PO_LoginView.simulateLogin("prueba2@prueba2.com", "prueba2");

        // Acceder al listado de ofertas.

        // Obtener una oferta propia que ha sido vendida

        // Hacer click en el botón de borrar oferta

        // Comprobar que la oferta no se borra
    }

    // -------------------------------------
    // Parte 2B - Cliente ligero JQuery/AJAX
    // -------------------------------------

    /**
     * Cliente ligero JQuery/AJAX
     * <p>
     * [Prueba 48] Inicio de sesion con datos válidos.
     */
    @Test
    @Order(48)
    public void PR48() {
        // Acceder a la página de login
        driver.get(BASE_API_CLIENT_URL + "/client.html?w=login");

        PO_LoginView.fillForm(driver, "prueba2@prueba2.com", "prueba2");

        // Comprobar que se ha iniciado sesión y se redirecciona a la página
        // que contiene el listado de ofertas disponibles.
        Assertions.assertEquals(BASE_API_CLIENT_URL + "/client.html?w=offers", driver.getCurrentUrl());
    }

    /**
     * Cliente ligero JQuery/AJAX
     * <p>
     * [Prueba 49] Inicio de sesión con datos inválidos (email existente, pero contraseña incorrecta)
     */
    @Test
    @Order(49)
    public void PR49() {
        // Acceder a la página de login
        driver.get(BASE_API_CLIENT_URL + "/client.html?w=login");

        // Rellenar el formulario, introduciendo una contraseña incorrecta
        PO_LoginView.fillForm(driver, "prueba2@prueba2.com", "1234");

        // Comprobar que se muestra un mensaje de error en la vista
        List<WebElement> errors = PO_View.checkElementBy(driver, "text", "");
        Assertions.assertEquals(1, errors.size());
    }

    /**
     * Cliente ligero JQuery/AJAX
     * <p>
     * [Prueba 50] Inicio de sesión con datos inválidos (campo email o contraseña vacíos).
     */
    @Test
    @Order(50)
    public void PR50() {
        // Acceder a la página de login
        driver.get(BASE_API_CLIENT_URL + "/client.html?w=login");

        // Rellenar el formulario, introduciendo solo la contraseña, sin email
        PO_LoginView.fillForm(driver, "", "prueba2");

        // Comprobar que se muestra un mensaje de error en la vista
        // indicando que faltan campos por completar
        List<WebElement> errors = PO_View.checkElementBy(driver, "text", "");
        Assertions.assertEquals(1, errors.size());
    }
}
