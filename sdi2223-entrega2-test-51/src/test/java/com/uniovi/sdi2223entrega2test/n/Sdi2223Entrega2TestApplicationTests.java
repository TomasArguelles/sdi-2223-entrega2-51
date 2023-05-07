package com.uniovi.sdi2223entrega2test.n;

import com.uniovi.sdi2223entrega2test.n.pageobjects.*;
import com.uniovi.sdi2223entrega2test.n.util.DatabaseUtils;
import com.uniovi.sdi2223entrega2test.n.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2223Entrega2TestApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String BASE_PATH = "http://localhost:8081";

    static String BASE_API_CLIENT_URL = "http://localhost:8081/api/v1.0"; // URL base del API del cliente

    // Kiko
    static String Geckodriver = "C:\\Users\\kikoc\\Desktop\\SDI\\geckodriver-v0.30.0-win64.exe";

    //static String Geckodriver = "C:\\Users\\Tomás\\Downloads\\OneDrive_1_7-3-2023\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    // Teresa
    //static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";

    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8081";

    // Url de la vista de listado de ofertas para comprar
    static String ALL_AVAILABLE_OFFERS_URL = "http://localhost:8081/offers/all";

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
        // Crear los usuarios de prueba
        driver.navigate().to(URL);
        DatabaseUtils.seedUsers();
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
    @Test
    @Order(1)
    void PR01() {
        DatabaseUtils.resetUsersCollection();
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
        DatabaseUtils.resetUsersCollection();
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
     * Parte 1 - Aplicacion Web - W5
     * <p>
     * W4 Administrador: Listado de usuarios del sistema
     * <p>
     * [Prueba11] Mostrar el listado de usuarios. Comprobar que se muestran todos los que existen en el
     * sistema, contabilizando al menos el número de usuarios.
     */
    @Test
    @Order(11)
    void PR011() {
        // Insertar usuarios de prueba
        DatabaseUtils.seedUsers();

        // Iniciar sesión como administrador
        PO_LoginView.simulateLogin(driver, "admin@email.com", "admin");
        PO_NavView.selectDropdownById(driver, "gestionUsuariosMenu", "gestionUsuariosDropdown", "listAllUsers");

        // Comprobar que se muestran todos los usuarios
        // Consultar primera pagina
        List<WebElement> firstPageUsers = driver.findElements(By.xpath("/html/body/div/div[1]/table/tbody/tr"));
        Assertions.assertTrue(firstPageUsers.size() == 4);

        // Consultar segunda pagina
        driver.findElement(By.xpath("/html/body/div/div[2]/ul/li[3]/a")).click();
        List<WebElement> secondPageUsers = driver.findElements(By.xpath("/html/body/div/div[1]/table/tbody/tr"));
        Assertions.assertTrue(secondPageUsers.size() == 5);

        // Consultar tercera pagina
        driver.findElement(By.xpath("/html/body/div/div[2]/ul/li[4]/a")).click();
        List<WebElement> thirdPageUsers = driver.findElements(By.xpath("/html/body/div/div[1]/table/tbody/tr"));
        Assertions.assertTrue(thirdPageUsers.size() == 5);

        // Consultar cuarta pagina
        driver.findElement(By.xpath("/html/body/div/div[2]/ul/li[5]/a")).click();
        List<WebElement> fourthPageUsers = driver.findElements(By.xpath("/html/body/div/div[1]/table/tbody/tr"));
        Assertions.assertTrue(fourthPageUsers.size() == 1);
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

        // Iniciar sesión con otro usuario
        PO_LoginView.logout(driver);

        // Pos: 1
        PO_OfferView.simulateAddNewOffer(driver, "user02@email.com", "user02", "Oferta de prueba 3", "Descripcion de la oferta de prueba 3", "2");

        // Borrar una oferta de otro usuario
        PO_OfferView.deleteOfferFromAllAvailableOfferList(driver, 1);

        // Comprobar que la oferta desaparece. Para ello, comprobar que no
        // aparece el título de la oferta en la lista de ofertas.
        driver.navigate().to(ALL_AVAILABLE_OFFERS_URL);
        PO_OfferView.checkOfferAppearOnAllAvailableOfferList(driver, 1, "Oferta de prueba 1");
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
    public void PR23() {
        // Iniciar sesión
        /*
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
         */

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda con el campo vacío
        // y comprobar que se muentra el listado de todas las ofertas existentes en el sistema
        /*
        String invalid = "";
        PO_AllOfferView.SearchInvalid(driver, invalid);
        List<WebElement> offerList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(21, offerList.size());
         */

        // Cerrar sesión
        // PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    /**
     * [Prueba24] Hacer una búsqueda escribiendo en el campo un texto que no exista
     * y comprobar que se muestra la página que corresponde, con la lista de ofertas vacía.
     */
    @Test
    @Order(24)
    public void PR24() {
        // Iniciar sesión
        /*
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
         */

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título que no corresponda con ninguna oferta
        // y comprobar que no se muentra ninguna oferta
        /*
        String invalid = "asdasd";
        PO_AllOfferView.SearchInvalid(driver, invalid);
        SeleniumUtils.textIsNotPresentOnPage(driver, "//tbody/tr");
         */

        // Cerrar sesión
        // PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    /**
     * [Prueba25] Hacer una búsqueda escribiendo en el campo un texto en minúscula o mayúscula
     * y comprobar que se muestra la página que corresponde, con la lista de ofertas que contengan
     * dicho texto, independientemente que el título esté almacenado en minúsculas o mayúscula
     */
    @Test
    @Order(25)
    public void PR25() {
        // Iniciar sesión
        /*
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
         */

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título existente, pero en mayúsculas
        // y comprobar que se muentra la oferta correspondiente aunque su título esté en minúsculas
        /*
        String invalid = "";
        PO_AllOfferView.SearchInvalid(driver, invalid);
        List<WebElement> offerList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(1, offerList.size());
         */

        // Cerrar sesión
        // PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    /**
     * [Prueba26] Sobre una búsqueda determinada,
     * comprar una oferta que deja un saldo positivo en el contador del comprador.
     * Y comprobar que el contador se actualiza correctamente en la vista del comprador.
     */
    @Test
    @Order(26)
    public void PR26() {
        // Iniciar sesión
        /*
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
         */

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título existente
        // y comprar dicha oferta dejando positivo el saldo del comprador
        // Comprobar que el contador se actualiza correctamente en la vista del comprador
        /*
        String buttonName = "buyOffer104";
        PO_AllOfferView.buyOffer(driver,buttonName);
        String value = PO_AllOfferView.seeWallet(driver);
        Assertions.assertEquals(value,"54.0");
         */

        // Cerrar sesión
        // PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    /**
     * [Prueba27] Sobre una búsqueda determinada,
     * comprar una oferta que deja un saldo 0 en el contador del comprobador.
     * Y comprobar que el contador se actualiza correctamente en la vista del comprador.
     */
    @Test
    @Order(27)
    public void PR27() {
        // Iniciar sesión
        /*
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
         */

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título existente
        // y comprar dicha oferta dejando nulo el saldo del comprador
        // Comprobar que el contador se actualiza correctamente en la vista del comprador
        /*
        String buttonName = "buyOffer104";
        PO_AllOfferView.buyOffer(driver,buttonName);
        String value = PO_AllOfferView.seeWallet(driver);
        Assertions.assertEquals(value,"0");
         */

        // Cerrar sesión
        // PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    /**
     * [Prueba28] Sobre una búsqueda determinada,
     * intentar comprar una oferta que esté por encima de saldo disponible del comprador.
     * Y comprobar que se muestra el mensaje de saldo no suficiente.
     */
    @Test
    @Order(28)
    public void PR28() {
        // Iniciar sesión
        /*
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
         */

        // Acceder a la vista del listado de todas las ofertas
        // Realizar una búsqueda de un título existente
        // e intentar comprar dicha oferta con precio mayor al saldo del comprador
        // Comprobar que se muestra el mensaje de saldo insuficiente
        /*
        String buttonName = "buyOffer34";
        PO_AllOfferView.buyOffer(driver,buttonName);
        boolean isDisplayed = driver.findElement(By.id("errorPrecio")).isDisplayed();
        Assertions.assertEquals(true,isDisplayed);
         */

        // Cerrar sesión
        // PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    /**
     * [Prueba29] Ir a la opción de ofertas compradas del usuario y mostrar la lista.
     * Comprobar que aparecen las ofertas que deben aparecer.
     */
    @Test
    @Order(29)
    public void PR29() {
        // Iniciar sesión
        /*
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "user04@email.com", "user04");
         */

        // Acceder a la vista del listado de ofertas compradas por el usuario
        // Comprobar que se muestra la lista de ofertas correspondientes
        /*
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listAllOfferMenu");
        PO_AllOfferView.buyOffer(driver, "buyOffer21");
        // Sacamos el valor del wallet
        String value = PO_AllOfferView.seeWallet(driver);
        // Lo comparamos con el precio restado
        Assertions.assertEquals(value, "30.0");
        // Accedemos a la vista de listado de ofertas compradas
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listBoughtOffers");
        // Obtenemos el número de filas de la tabla de la vista del listado de ofertas
        int rowCount = SeleniumUtils.countTableRows(driver, "//table[@class='table table-hover']/tbody/tr");
        // Verificamos que el número de registros mostrados es correcto
        Assertions.assertEquals(1, rowCount);
         */

        // Cerrar sesión
        // PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    /**
     * [Prueba30] Al crear una oferta marcar dicha oferta como destacada y a continuación comprobar:
     * i) que aparece en el listado de ofertas destacadas para los usuarios y que el saldo del usuario
     * se actualiza adecuadamente en la vista del ofertante (comprobar saldo antes y después,
     * que deberá diferir en 20€).
     */
    @Test
    @Order(30)
    public void PR30() {

    }

    /**
     * [Prueba31] Sobre el listado de ofertas de un usuario con más de 20 euros de saldo,
     * pinchar en el enlace Destacada y a continuación comprobar:
     * i) que aparece en el listado de ofertas destacadas para los usuarios y
     * que el saldo del usuario se actualiza adecuadamente en la vista del ofertante
     * (comprobar saldo antes y después, que deberá diferir en 20€ ).
     */
    @Test
    @Order(31)
    public void PR31() {

    }

    /**
     * [Prueba32] Sobre el listado de ofertas de un usuario con menos de 20 euros de saldo,
     * pinchar en el enlace Destacada y a continuación comprobar que se muestra el mensaje
     * de saldo no suficiente.
     */
    @Test
    @Order(32)
    public void PR32() {

    }

    /**
     * W13 Seguridad y auditoria de la aplicación.
     * <p>
     * [Prueba 33] Intentar acceder sin estar autenticado a la opción de listado de usuarios. Se deberá volver
     * al formulario de login.
     */
    @Test
    @Order(33)
    public void PR33() {
        // Acceso a la vista de listado de usuarios sin estar autenticado
        driver.navigate().to("http://localhost:8081/users/list");

        // Comprobamos que se muestra el formulario de login
        WebElement loginHeading = driver.findElement(By.xpath("/html/body/div/div/h2"));
        Assertions.assertEquals("Identificación de usuario", loginHeading.getText());
    }

    /**
     * W13 Seguridad y auditoria de la aplicación.
     * <p>
     * [Prueba 34] Intentar acceder sin estar autenticado a la opción de listado de conversaciones
     * [REQUISITO OBLIGATORIO S5]. Se deberá volver al formulario de login.
     */
    @Test
    @Order(34)
    public void PR34() {
        // TODO: Implementar
    }

    /**
     * W13 Seguridad y auditoria de la aplicación.
     * <p>
     * [Prueba 35] Estando autenticado como usuario estándar intentar acceder a una opción disponible solo
     * para usuarios administradores (Añadir menú de auditoria (visualizar logs)). Se deberá indicar un
     * mensaje de acción prohibida.
     */
    @Test
    @Order(35)
    public void PR35() {
        // Iniciar sesión como usuario estándar
        PO_LoginView.simulateLogin(driver, "user01@email.com", "user01");

        // Acceder a la vista de listado de logs
        driver.navigate().to("http://localhost:8081/admin");

        // Comprobar que se muestra el mensaje de accion prohibida
        WebElement loginHeading = driver.findElement(By.xpath("/html/body/div/div"));
        Assertions.assertEquals("Acción prohibida", loginHeading.getText());
    }

    /**
     * W13 Seguridad y auditoria de la aplicación.
     * <p>
     * [Prueba 36] Estando autenticado como usuario administrador visualizar todos los logs generados en
     * una serie de interacciones. Esta prueba deberá generar al menos dos interacciones de cada tipo y
     * comprobar que el listado incluye los logs correspondientes.
     */
    @Test
    @Order(36)
    public void PR36() {
        // Iniciar sesión como usuario administrador
        PO_LoginView.simulateLogin(driver, "admin@email.com", "admin");

        // Borrar los logs existentes
        driver.navigate().to("http://localhost:8081/logs/delete/all");

        //PO_LoginView.logout(driver);

        // Interaccion 1

        // LOG-ERR
        PO_LoginView.simulateLogin(driver, "user01@email.com", "USER0001");

        // LOG-EX
        PO_LoginView.simulateLogin(driver, "user01@email.com", "user01");

        // PET
        driver.navigate().to("http://localhost:8081/offers/all");

        // LOGOUT
        PO_LoginView.logout(driver);

        // Interaccion 2

        // LOG-ERR
        PO_LoginView.simulateLogin(driver, "user02@email.com", "USER0002");

        // LOG-EX
        PO_LoginView.simulateLogin(driver, "user02@email.com", "user02");

        // PET
        driver.navigate().to("http://localhost:8081/offers/all");

        // LOGOUT
        PO_LoginView.logout(driver);

        // Iniciar sesión como usuario administrador
        PO_LoginView.simulateLogin(driver, "admin@email.com", "admin");

        // Acceder a la vista de listado de logs
        driver.navigate().to("http://localhost:8081/admin");

        // Comprobar que se muestran los logs
        List<WebElement> logRegisters = driver.findElements(By.xpath("/html/body/div/div/div/div/table/tbody/tr"));

        // Se tienen que mostrar 21 logs
        Assertions.assertTrue(logRegisters.size() == 21);
    }

    /**
     * W13 Seguridad y auditoria de la aplicación.
     * <p>
     * [Prueba 37] Estando autenticado como usuario administrador, ir a visualización de logs, pulsar el
     * botón/enlace borrar logs y comprobar que se eliminan los logs de la base de datos.
     */
    @Test
    @Order(37)
    public void PR37() {
        // Iniciar sesión como usuario administrador
        PO_LoginView.simulateLogin(driver, "admin@email.com", "admin");

        // Pulsar el boton de borrar logs
        PO_NavView.selectDropdownById(driver, "gestionLogsMenu", "gestionLogsDropdown", "removeAllLogs");

        // Acceder a la vista de listado de logs
        driver.navigate().to("http://localhost:8081/admin");

        // Comprobar que no se muestran logs
        WebElement noLogsMessage = driver.findElement(By.xpath("/html/body/div/div/div/div/p[2]"));
        Assertions.assertEquals("No hay logs registrados", noLogsMessage.getText());
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
        driver.navigate().to("http://localhost:8081/apiclient/client.html?w=login");

        // Forzar redireccion al login pulsando el botón de login del navbar
        driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li/a")).click();

        PO_LoginView.fillLoginFormApi(driver, "user01@email.com", "user01");

        // Comprobar que se ha iniciado sesión y se redirecciona a la página
        // que contiene el listado de ofertas disponibles.
        Assertions.assertNotEquals("http://localhost:8081/apiclient//client.html?w=login", driver.getCurrentUrl());
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
        driver.navigate().to("http://localhost:8081/apiclient/client.html?w=login");

        // Forzar redireccion al login pulsando el botón de login del navbar
        driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li/a")).click();

        PO_LoginView.fillLoginFormApi(driver, "user01@email.com", "user0001");

        // Comprobar que se muestra un mensaje de error en la vista
        // indicando que las credenciales son incorrectas
        PO_View.checkErrorMessageIsShown(driver, "Credenciales incorrectas. Inténtenlo de nuevo");
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
        driver.navigate().to("http://localhost:8081/apiclient/client.html?w=login");

        // Forzar redireccion al login pulsando el botón de login del navbar
        driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li/a")).click();

        // Rellenar formulario de login con contraseña vacía
        PO_LoginView.fillLoginFormApi(driver, "user01@email.com", "");

        // Comprobar que se muestra un mensaje de error en la vista
        // indicando que faltan campos por completar
        PO_View.checkErrorMessageIsShown(driver, "Credenciales incorrectas. Inténtenlo de nuevo");
    }

    /**
     * Cliente ligero JQuery/AJAX
     * <p>
     * [Prueba 50] Mostrar el listado de ofertas disponibles y comprobar que se muestran todas las que existen,
     * menos las del usuario identificado.
     */
    @Test
    @Order(51)
    public void PR51() {
        DatabaseUtils.resetOffersCollection();

        // Añadir 3 ofertas con user01
        PO_OfferView.simulateAddNewOffer(driver, "user01@email.com", "user01", "Oferta de prueba 1", "Descripcion de la oferta de prueba 1", "1");
        PO_OfferView.simulateAddNewOffer(driver, "user01@email.com", "user01", "Oferta de prueba 2", "Descripcion de la oferta de prueba 2", "2");
        PO_OfferView.simulateAddNewOffer(driver, "user01@email.com", "user01", "Oferta de prueba 3", "Descripcion de la oferta de prueba 3", "3");

        PO_LoginView.logout(driver);

        // Añadir 1 oferta con user02
        PO_OfferView.simulateAddNewOffer(driver, "user02@email.com", "user02", "Oferta de prueba 4",
                "Descripcion de la oferta de prueba 4", "4");

        // Acceder con cliente ajax, al listado de ofertas y comprobar que se muestran 3 ofertas
        // (las de user01) y no se muestra la oferta de user02
        // Acceder a la página de login
        driver.navigate().to("http://localhost:8081/apiclient/client.html?w=login");

        // Forzar redireccion al login pulsando el botón de login del navbar
        driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li/a")).click();

        // Rellenar formulario de login con contraseña vacía
        PO_LoginView.fillLoginFormApi(driver, "user02@email.com", "user02");

        driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[1]/li[1]/a")).click();

        List<WebElement> offers = driver.findElements(By.xpath("/html/body/div/div/table/tbody/tr"));
        Assertions.assertEquals(3, offers.size());

        // Comprobar que se muestran las ofertas de user01
        offers.get(0).findElement(By.xpath("td[1]")).getText().equals("Oferta de prueba 1");
        offers.get(1).findElement(By.xpath("td[1]")).getText().equals("Oferta de prueba 2");
        offers.get(2).findElement(By.xpath("td[1]")).getText().equals("Oferta de prueba 3");
    }

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
