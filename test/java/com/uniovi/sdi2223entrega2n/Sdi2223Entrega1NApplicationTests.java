package com.uniovi.sdi2223entrega2n;

import com.uniovi.sdi2223entrega2n.pageobjects.*;
import com.uniovi.sdi2223entrega2n.util.DatabaseUtils;
import com.uniovi.sdi2223entrega2n.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2223Entrega1NApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";

    // Kiko
    //static String Geckodriver = "C:\\Users\\kikoc\\Desktop\\SDI\\geckodriver-v0.30.0-win64.exe";

    static String Geckodriver = "C:\\Users\\Tomás\\Downloads\\OneDrive_1_7-3-2023\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String BASE_HTTP_URL = "http://localhost:8081";


    // Url por defecto para el cliente HTTP (REST)
    static String BASE_API_CLIENT_URL = BASE_HTTP_URL + "/apiclient";

    static String ALL_AVAILABLE_OFFERS_URL = "http://localhost:8081/offers/all";

    // Endpoint para mostrar el listado de usuarios (Ver UserController)
    static final String USER_LIST_ENDPOINT = BASE_HTTP_URL + "/user/list";

    // Endpoint para mostrar la vista de conversaciones de un usuario.
    static final String CONVERSATION_LIST_ENDPOINT = BASE_HTTP_URL + "/conversation/list";


    // Enpoint para mostrar la vista de login
    static final String LOGIN_ENDPOINT = BASE_HTTP_URL + "/login";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp() {
        driver.navigate().to(BASE_HTTP_URL);
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
        driver.navigate().to(BASE_HTTP_URL);
        DatabaseUtils.seedUsers();
    }

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        // Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

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
        PO_LoginView.fillForm(driver, "admin@email.com", "admin");
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
        PO_LoginView.fillForm(driver, "user01@email.com", "user01");
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
        PO_LoginView.fillForm(driver, "user01@email.com", "user01");
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


//
//    //[Prueba12] Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza
//    //y dicho usuario desaparece.
//    @Test
//    @Order(12)
//    void PR012() {
//        //Nos movemos al formulario de inicio de sesion
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_LoginView.fillForm(driver, "admin@email.com", "admin");
//        //Comprobamos que seguimos en la pantalla de registro
//        PO_HomeView.checkWelcomeToPage(driver);
//        //Accedemos a la lista de users
//        driver.get("http://localhost:8090/user/list");
//
//        //Sacamos la lista de usuarios q hay
//        List<WebElement> usersList = PO_UserListView.getUsersList(driver);
//        //guardamos tamaño para comporbar
//        int s1 = usersList.size();
//        //Primer usuario y marcaje de su checkbox
//        WebElement firstUser = usersList.get(0);
//
//        PO_UserListView.markCheckBoxUser(driver, firstUser);
//        //Borramos dandole al boton
//        PO_UserListView.clickDeleteButton(driver);
//
//        //Actualizamos la lista
//        usersList = PO_UserListView.getUsersList(driver);
//        //Guardamos segundo tamaño y vemos q no es el mismo, comprobamos que decrementó en 1
//        int s2 = usersList.size();
//        Assertions.assertNotEquals(s1, s2);
//        Assertions.assertEquals(s1, s2 + 1);
//    }
//
//    //[Prueba13] Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza
////y dicho usuario desaparece.
//    @Test
//    @Order(13)
//    void PR013() {
//        //Nos movemos al formulario de inicio de sesion
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_LoginView.fillForm(driver, "admin@email.com", "admin");
//        //Comprobamos que seguimos en la pantalla de registro
//        PO_HomeView.checkWelcomeToPage(driver);
//        //Accedemos a la lista de users
//        driver.get("http://localhost:8090/user/list");
//
//        //Sacamos la lista de usuarios q hay
//        List<WebElement> usersList = PO_UserListView.getUsersList(driver);
//        //guardamos tamaño para comporbar
//        int s1 = usersList.size();
//        //Ultimo usuario y marcaje de su checkbox
//        WebElement lastUser = usersList.get(usersList.size() - 1);
//        PO_UserListView.markCheckBoxUser(driver, lastUser);
//        //Borramos dandole al boton
//        PO_UserListView.clickDeleteButton(driver);
//        //Actualizamos la lista
//        usersList = PO_UserListView.getUsersList(driver);
//        //Guardamos segundo tamaño y vemos q no es el mismo, comprobamos que decrementó en 1
//        int s2 = usersList.size();
//        Assertions.assertNotEquals(s1, s2);
//        Assertions.assertEquals(s1, s2 + 1);
//    }
//
//    //[Prueba14] Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se actualiza y dichos
//    //usuarios desaparecen.
//    @Test
//    @Order(14)
//    void PR014() {
//        //Nos movemos al formulario de inicio de sesion
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_LoginView.fillForm(driver, "admin@email.com", "admin");
//        //Comprobamos que seguimos en la pantalla de registro
//        PO_HomeView.checkWelcomeToPage(driver);
//        //Accedemos a la lista de users
//        driver.get("http://localhost:8090/user/list");
//
//        //Sacamos la lista de usuarios q hay
//        List<WebElement> usersList = PO_UserListView.getUsersList(driver);
//        //guardamos tamaño para comporbar
//        int s1 = usersList.size();
//        //Sacamos los tres primeros usuarios y marcamos de sus checkboxes
//        WebElement u1 = usersList.get(0);
//        WebElement u2 = usersList.get(1);
//        WebElement u3 = usersList.get(2);
//        PO_UserListView.markCheckBoxUser(driver, u1);
//        PO_UserListView.markCheckBoxUser(driver, u2);
//        PO_UserListView.markCheckBoxUser(driver, u3);
//        //Borramos dandole al boton
//        PO_UserListView.clickDeleteButton(driver);
//        //Actualizamos la lista
//        usersList = PO_UserListView.getUsersList(driver);
//        //Guardamos segundo tamaño y vemos q no es el mismo, comprobamos que decrementó en 1
//        int s2 = usersList.size();
//        Assertions.assertNotEquals(s1, s2);
//        Assertions.assertEquals(s1, s2 + 3);
//    }
//
//    // [Prueba 15]. Añadir nueva oferta con datos válidos.
//    @Test
//    @Order(15)
//    public void PR015() {
//        // Crear nuevo usuario
//        SeleniumUtils.registerNewUser(driver, "miemail444@email.com", "123456");
//
//        String newOfferText = "Coche marca Renault 1";
//
//        // Acceder a la vista de añadir una nueva oferta
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");
//
//        // Rellenar campos del formulario con valores válidos.
//        PO_OfferView.fillForm(driver, newOfferText, "Coche de los años 90", 2000.50, false);
//
//        // Comprobar que se muestra en el listado de ofertas
//        List<WebElement> offers = PO_View.checkElementBy(driver, "text", newOfferText);
//        Assertions.assertEquals(1, offers.size());
//    }

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

    /**
     * Parte 1 - Aplicacion Web - W8
     * <p>
     * [Prueba 22] Ir a la lista de ofertas, borrar una oferta propia que ha sido vendida, comprobar que la
     * oferta no se borra.
     */
    @Test
    @Order(22)
    public void PR22() {
        // TODO: Implementar
    }


//
//    /**
//     * [Prueba 20] Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que
//     * corresponde con el listado de las ofertas existentes en el sistema
//     */
//    @Test
//    @Order(20)
//    public void PR020() {
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Rellenamos con datos validos del usuario estandar
//        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
//        String invalid = "";
//        PO_AllOfferView.SearchInvalid(driver, invalid);
//
//        //Comprobar que el numero es 0
//        List<WebElement> offerList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
//                PO_View.getTimeout());
//
//        Assertions.assertEquals(21, offerList.size());
//
//        //Cierro sesion
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//    }
//
//    /**
//     * [Prueba21] Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se
//     * muestra la página que corresponde, con la lista de ofertas vacía.
//     */
//    @Test
//    @Order(21)
//    public void PR021() {
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Rellenamos con datos validos del usuario estandar
//        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
//        String invalid = "asdasd";
//        PO_AllOfferView.SearchInvalid(driver, invalid);
//
//        //Comprobar que el numero es 0
//        SeleniumUtils.textIsNotPresentOnPage(driver, "//tbody/tr");
//
//        //Cierro sesion
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//    }
//
//    /**
//     * [Prueba22] Sobre una búsqueda determinada (a elección del desarrollador),
//     * comprar una oferta que deja un saldo positivo en el contador del comprador.
//     * Comprobar que el contador se actualiza correctamente en la vista del comprador.
//     */
//    @Test
//    @Order(22)
//    public void PR022() {
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Rellenamos con datos validos del usuario estandar
//        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
//        //Entramos a la vista de comprar y compramos la oferta 104 que su precio es valido
//        String buttonName = "buyOffer104";
//        PO_AllOfferView.buyOffer(driver, buttonName);
//        //Sacamos el valor del wallet
//        String value = PO_AllOfferView.seeWallet(driver);
//        //Lo comparamos con el precio restado
//        Assertions.assertEquals(value, "54.0");
//        //Cierro sesion
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//    }
//

//    /**
//     * [Prueba23] Sobre una búsqueda determinada (a elección del desarrollador),
//     * comprar una oferta que deja un saldo 0 en el contador del comprador.
//     * Comprobar que el contador se actualiza correctamente en la vista del comprador.
//     */
//    @Test
//    @Order(23)
//    public void PR023() {
//        // Registrar nuevo usuario
//        SeleniumUtils.registerNewUser(driver, "usuario113eddsf8@email.com", "123456");
//        // Accedemos al menu de añadir una oferta
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");
//        // Añadimos una oferta nueva
//        PO_OfferView.fillForm(driver, "Prueba 23", "Descripción prueba 23", 54.0, false);
//        //Cierro sesion
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//
//        //Accedo con el comprador
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Rellenamos con datos validos del usuario estandar
//        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
//        //Entramos a la vista de comprar y compramos la oferta 153 que su precio es igual al wallet
//        String buttonName = "buyOffer153";
//        PO_AllOfferView.buyOffer(driver, buttonName);
//        //Sacamos el valor del wallet
//        String value = PO_AllOfferView.seeWallet(driver);
//        //Lo comparamos con el precio restado
//        Assertions.assertEquals(value, "0.0");
//
//    }
//
//    /**
//     * [Prueba24] Sobre una búsqueda determinada (a elección del desarrollador),
//     * intentar comprar una oferta que esté por encima de saldo disponible del comprador.
//     * Y comprobar que se muestra el mensaje de saldo no suficiente.
//     */
//    @Test
//    @Order(24)
//    public void PR024() {
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Rellenamos con datos validos del usuario estandar
//        PO_LoginView.fillForm(driver, "user07@email.com", "user07");
//        //Entramos a la vista de comprar y compramos la oferta 34 que su precio es invalido
//        String buttonName = "buyOffer34";
//        PO_AllOfferView.buyOffer(driver, buttonName);
//        //Buscamos que aparezca en la pagina la label
//        boolean isDisplayed = driver.findElement(By.id("errorPrecio")).isDisplayed();
//        Assertions.assertEquals(true, isDisplayed);
//        //Cierro sesion
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//    }
//
//    // [Prueba 25]. Listado de ofertas compradas por un usuario.
//    // Comprobar que se muestran todas las ofertas compradas por dicho usuario.
//    @Test
//    @Order(25)
//    public void PR025() {
//        // Iniciamos sesión como usuario standard
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        PO_LoginView.fillForm(driver, "user04@email.com", "user04");
//
//        // Entramos a la vista de comprar y compramos la oferta 62
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listAllOfferMenu");
//
//        PO_AllOfferView.buyOffer(driver, "buyOffer21");
//
//        // Sacamos el valor del wallet
//        String value = PO_AllOfferView.seeWallet(driver);
//
//        // Lo comparamos con el precio restado
//        Assertions.assertEquals(value, "30.0");
//
//        // Accedemos a la vista de listado de ofertas compradas
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listBoughtOffers");
//
//        // Obtenemos el número de filas de la tabla de la vista del listado de ofertas
//        int rowCount = SeleniumUtils.countTableRows(driver, "//table[@class='table table-hover']/tbody/tr");
//
//        // Verificamos que el número de registros mostrados es correcto
//        Assertions.assertEquals(1, rowCount);
//
//        // Cerramos sesión
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//    }
//
//    //[Prueba26] Sobre una búsqueda determinada de ofertas (a elección de desarrollador), enviar un mensaje
////a una oferta concreta. Se abriría dicha conversación por primera vez. Comprobar que el mensaje aparece
////en la conversación.
//    @Test
//    @Order(26)
//    public void PR026() {
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_LoginView.fillForm(driver, "user06@email.com", "user06");
//
//
//        //vamos a la vista que queremos, directamente haciendo la búsqueda que queremos, en nuestro caso Carro
//        driver.get("http://localhost:8090/offer/allList?searchText=Carro");
//
//        //Click the link
//        PO_ConversationsView.clickConversationsLink(driver);
//
//        //we count the number of rows
//        WebElement table = driver.findElement(By.id("tableMessages"));
//        List<WebElement> rows = table.findElements(By.tagName("tr"));
//        int numRowsInit = rows.size();
//
//        // Write a message
//        PO_ConversationsView.writeMessage(driver, "MensajePrueba");
//        // Send the message
//        PO_ConversationsView.sendMessage(driver);
//
//        // Check if the message was sent by counting again the number of rows
//        table = driver.findElement(By.id("tableMessages"));
//        List<WebElement> rows2 = table.findElements(By.tagName("tr"));
//        int numRowsAfter = rows2.size();
//
//        Assertions.assertEquals(numRowsInit, numRowsAfter - 1);
//    }
//
//    //[Prueba27] Enviar un mensaje a una conversación ya existente accediendo desde el botón/enlace
//    //“Conversación”. Comprobar que el mensaje aparece en la conversación
//    @Test
//    @Order(27)
//    public void PR027() {
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_LoginView.fillForm(driver, "user01@email.com", "user01");
//        //vamos a la vista que queremos, directamente haciendo la búsqueda que queremos, en nuestro caso Carro
//        driver.get("http://localhost:8090/offer/allList?searchText=Carro");
//
//        //Click the link
//        PO_ConversationsView.clickConversationsLink(driver);
//
//        //we count the number of rows
//        WebElement table = driver.findElement(By.id("tableMessages"));
//        List<WebElement> rows = table.findElements(By.tagName("tr"));
//        int numRowsInit = rows.size();
//
//        // Write a message
//        PO_ConversationsView.writeMessage(driver, "MensajePrueba");
//        // Send the message
//        PO_ConversationsView.sendMessage(driver);
//
//        // Check if the message was sent by counting again the number of rows
//        table = driver.findElement(By.id("tableMessages"));
//        List<WebElement> rows2 = table.findElements(By.tagName("tr"));
//        int numRowsAfter = rows2.size();
//
//        Assertions.assertEquals(numRowsInit, numRowsAfter - 1);
//    }
//
//    //[Prueba28] Mostrar el listado de conversaciones ya abiertas. Comprobar que el listado contiene la
//    //cantidad correcta de conversaciones.
//    @Test
//    @Order(28)
//    public void PR028() {
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_LoginView.fillForm(driver, "user04@email.com", "user04");
//        //vamos a la vista que queremos, directamente haciendo la búsqueda que queremos, en nuestro caso Carro
//        driver.get("http://localhost:8090/conversation/mylist");
//        WebElement table = driver.findElement(By.id("tableMyOffer"));
//        List<WebElement> rows2 = table.findElements(By.tagName("tr"));
//        int numRows = rows2.size();
//
//        Assertions.assertEquals(numRows, 3);
//    }
//
//    /**
//     * [Prueba29] Visualizar al menos cuatro páginas en español/inglés/español (comprobando que algunas de las etiquetas cambian al idioma correspondiente).
//     * Ejemplo, Página principal/Opciones Principales de Usuario/Listado de Usuarios.
//     */
//    @Test
//    @Order(29)
//    public void PR29() {
//        //Me logueo como usuario normal
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        PO_LoginView.fillForm(driver, "user04@email.com", "user04");
//        //Compruebo el titulo con la pagina en español
//        String title = "Bienvenidos a la página principal";
//        String result = PO_HomeView.getTitleMessage(driver);
//        Assertions.assertEquals(title, result);
//        //Cambio a idioma ingles
//        PO_NavView.changeLanguage(driver, "English");
//        //Compruebo el titulo con la pagina en ingles
//        title = "Welcome to homepage";
//        result = PO_HomeView.getTitleMessage(driver);
//        Assertions.assertEquals(title, result);
//        //Cambio a español
//        PO_NavView.changeLanguage(driver, "Spanish");
//        //Compruebo el titulo en español
//        title = "Bienvenidos a la página principal";
//        result = PO_HomeView.getTitleMessage(driver);
//        Assertions.assertEquals(title, result);
//
//        //Cambio a pagina de ver ofertas para comprar
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdow", "listAllOfferMenu");
//
//        //Compruebo el titulo en español
//        title = "Todas las ofertas";
//        result = PO_AllOfferView.getTitleMessage(driver);
//        Assertions.assertEquals(title, result);
//        //Cambio a ingles
//        PO_NavView.changeLanguage(driver, "English");
//        //Compruebo en ingles
//        title = "All offers";
//        result = PO_AllOfferView.getTitleMessage(driver);
//        Assertions.assertEquals(title, result);
//        //Cambio a español
//        PO_NavView.changeLanguage(driver, "Spanish");
//        //Compruebo en español
//        title = "Todas las ofertas";
//        result = PO_HomeView.getTitleMessage(driver);
//        Assertions.assertEquals(title, result);
//
//        //Me deslogueo
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//        //Me logueo como admin
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        PO_LoginView.fillForm(driver, "admin@email.com", "admin");
//
//        //Voy a la ventana ver Usuario
//        driver.get("http://localhost:8090/user/list");
//        //Compruebo el titulo en español
//
//        title = "Usuarios";
//        result = PO_UserListView.getTile(driver);
//        Assertions.assertEquals(title, result);
//        //Me cambio a ingles
//        PO_NavView.changeLanguage(driver, "English");
//        //Compruebo el titulo en ingles
//        title = "Users";
//        result = PO_UserListView.getTile(driver);
//        Assertions.assertEquals(title, result);
//        //Me cambio a español
//        PO_NavView.changeLanguage(driver, "Spanish");
//        //Compruebo el titulo en español
//        title = "Usuarios";
//        result = PO_UserListView.getTile(driver);
//        Assertions.assertEquals(title, result);
//
//        //Cambiamos a la ventana log
//        driver.get("http://localhost:8090/admin/logs/all");
//        //Comprobamos en español
//        title = "Listado de logs";
//        result = PO_AdminView.getTitle(driver);
//        Assertions.assertEquals(title, result);
//        //Cambiamos a ingles
//        PO_NavView.changeLanguage(driver, "English");
//        //Comprobamos en ingles
//        title = "Log listing";
//        result = PO_AdminView.getTitle(driver);
//        Assertions.assertEquals(title, result);
//        //Cambiamos a español
//        PO_NavView.changeLanguage(driver, "Spanish");
//        //Comprobamos en español
//        title = "Listado de logs";
//        result = PO_AdminView.getTitle(driver);
//        Assertions.assertEquals(title, result);
//    }
//
//
//    // [Prueba 30]. Acceso sin autenticación a la opción de listado de usuarios.
//    @Test
//    @Order(30)
//    public void PR30() {
//        // Acceso por url sin estar autenticado.
//        driver.navigate().to(USER_LIST_ENDPOINT);
//
//        // Comprobar que se redirecciona a la vista de login
//        Assertions.assertEquals(LOGIN_ENDPOINT, driver.getCurrentUrl());
//    }
//
//    // [Prueba 31]. Acceso sin autenticación al listado de conversaciones de un
//    // usuario estandar. Se debe redireccionar al formulario de login.
//    @Test
//    @Order(31)
//    public void PR31() {
//        // Acceso por url sin estar autenticado.
//        driver.navigate().to(CONVERSATION_LIST_ENDPOINT);
//
//        // Comprobar que se redirecciona a la vista de login
//        Assertions.assertEquals(LOGIN_ENDPOINT, driver.getCurrentUrl());
//    }
//
//    // [Prueba 32]. Usuario estandar autenticado accede a una opción disponible solo
//    // para usuarios administradores.
//    @Test
//    @Order(32)
//    public void PR32() {
//
//        String email = "miemail888@email.com";
//
//        // Registrar nuevo usuario
//        SeleniumUtils.registerNewUser(driver, email, "123456");
//
//
//        // Acceder a una opción para usuarios administradores
//        driver.navigate().to(BASE_ENDPOINT + PO_AdminView.ADMIN_DASHBOARD_ENDPOINT);
//
//        // Comprobar que se redirecciona a la vista de login
//        Assertions.assertEquals(LOGIN_ENDPOINT, driver.getCurrentUrl());
//    }
//
//    // [Prueba 33]. Usuario administrador autenticado visualiza los logs generados
//    // en una serie de iteraciones. Generar al menos dos interacciones de cada
//    // tipo y comprobar que el listado incluye los correspondientes logs.
//    @Test
//    @Order(33)
//    public void PR33() {
//        // Acción previa: Borrar todos los logs
//        PO_AdminView.deleteAllLogsWithLogout(driver);
//
//        // -- TIPO LOGIN-EX
//        // Iniciar sesion como admin - LOG-EX
//        SeleniumUtils.signInIntoAccount(driver, "ADMIN", "admin@email.com");
//
//        // Cerrar la sesión del usuario admin
//        // TIPO_LOGOUT
//        PO_NavView.clickLogout(driver);
//        String userEmail1 = "user0000234@email.com";
//
//        // Registrar nuevo usuario
//        // -- TIPO ALTA
//        SeleniumUtils.registerNewUser(driver, userEmail1, "123456");
//
//        // -- TIPO LOGOUT
//        // Cerrar la sesión del usuario 1
//        PO_NavView.clickLogout(driver);
//
//        // Iniciar sesión con el usuario creado e introducir la contraseña mal - LOGIN-ERR
//        // -- TIPO LOGIN-ERR
//        SeleniumUtils.signInIntoAccount(driver, "STANDARD", userEmail1, "123");
//
//        // -- TIPO LOGIN-EX
//        SeleniumUtils.signInIntoAccount(driver, "STANDARD", userEmail1, "123456");
//
//        // Rellenar campos del formulario con valores válidos.
//        // -- TIPO PET
//        PO_OfferView.addSampleOfferWithDescriptionAndPrice(driver, "Oferta de prueba 1.1", "Coche de los años 90", 2000.50);
//
//        // -- TIPO LOGOUT
//        // Cerrar la sesión del usuario 1
//        PO_NavView.clickLogout(driver);
//
//        String userEmail2 = "user0000444@email.com";
//
//        // Registrar nuevo usuario
//        // -- TIPO ALTA
//        SeleniumUtils.registerNewUser(driver, userEmail2, "123456");
//
//        // -- TIPO LOGOUT
//        PO_NavView.clickLogout(driver);
//        // Iniciar sesión con el usuario creado e introducir la contraseña mal
//        // -- TIPO LOGIN-ERR
//        SeleniumUtils.signInIntoAccount(driver, "STANDARD", userEmail2, "123");
//
//        // -- TIPO LOGIN-EX
//        SeleniumUtils.signInIntoAccount(driver, "STANDARD", userEmail2, "123456");
//
//        // -- TIPO PET
//        // Acceder a la vista de añadir una nueva oferta
//        PO_OfferView.addSampleOfferWithDescriptionAndPrice(driver, "Oferta de prueba 1.2", "Piso céntrico en Oviedo centro", 2000.50);
//
//        // -- TIPO LOGOUT
//        // Cerrar la sesión del usuario 2
//        PO_NavView.clickLogout(driver);
//
//        // Total de logs esperado: +1 por la peticion de borrado de logs previamente
//        // realizada
//        int expectedLogs = 30;
//
//        // Ir a la vista de logs
//        // -- TIPO PET
//        PO_AdminView.goToListOfLogsView(driver);
//
//        // Obtener el número de logs de la tabla
//        int rowCount = SeleniumUtils.countTableRows(driver, "//table[@class='table table-striped px-3 my-3']/tbody/tr");
//        // Comprobar que el número de registros mostrados es correcto
//        Assertions.assertEquals(expectedLogs, rowCount);
//    }
//
//    // [Prueba 34]. Autenticado como usuario administrador, ir a visualización de
//    // logs, pulsar en el botón de borrar logs y comprobar que se eliminan los logs
//    // de la base de datos.
//    @Test
//    @Order(34)
//    public void PR34() {
//        // Iniciar sesion como administrador y eliminar todos los logs
//        PO_AdminView.deleteAllLogsWithLogout(driver);
//
//        // Total de logs esperado: 1
//        int expectedLogs = 1;
//
//        SeleniumUtils.signInIntoAccount(driver, "ADMIN", PO_AdminView.ADMIN_EMAIL);
//
//        // Ir a la vista de logs
//        PO_NavView.clickOption(driver, PO_AdminView.ADMIN_DASHBOARD_ENDPOINT, "id", "viewLogsMenuItem");
//
//        // Hacer click en el boton de eliminar logs
//        PO_NavView.clickOption(driver, PO_AdminView.ADMIN_DELETE_ALL_LOGS_ENDPOINT, "id", PO_AdminView.DELETE_ALL_LOGS_BUTTON);
//
//        // Obtener el número de logs de la tabla
//        int rowCount = SeleniumUtils.countTableRows(driver, "//table[@class='table table-striped px-3 my-3']/tbody/tr");
//
//        // Comprobar que el número de registros mostrados es correcto
//        Assertions.assertEquals(expectedLogs, rowCount);
//    }
//
//    //[Prueba35] Sobre el listado de conversaciones ya abiertas. Pinchar el enlace Eliminar de la primera y
//    //comprobar que el listado se actualiza correctamente
//    @Test
//    @Order(35)
//    public void PR035() {
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_LoginView.fillForm(driver, "user01@email.com", "user01");
//        //vamos a la vista que queremos, directamente haciendo la búsqueda que queremos, en nuestro caso Carro
//        driver.get("http://localhost:8090/conversation/list");
//
//        WebElement table = driver.findElement(By.id("tableOtherOffers"));
//        List<WebElement> rows = table.findElements(By.tagName("tr"));
//        int numRowsOriginal = rows.size();
//
//        PO_ConversationsView.clickEliminarOtherOffersFirst(driver);
//        WebElement table2 = driver.findElement(By.id("tableOtherOffers"));
//        List<WebElement> rows2 = table2.findElements(By.tagName("tr"));
//        int numRowsFinal = rows2.size();
//        Assertions.assertEquals(numRowsOriginal, numRowsFinal + 1);
//
//    }
//
//    //[Prueba36] Sobre el listado de conversaciones ya abiertas, pulsar el enlace Eliminar de la última y
//    //comprobar que el listado se actualiza correctamente
//    @Test
//    @Order(36)
//    public void PR036() {
//
//
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_LoginView.fillForm(driver, "user01@email.com", "user01");
//        //vamos a la vista que queremos, directamente haciendo la búsqueda que queremos, en nuestro caso Carro
//        driver.get("http://localhost:8090/conversation/list");
//
//        WebElement table = driver.findElement(By.id("tableOtherOffers"));
//        List<WebElement> rows = table.findElements(By.tagName("tr"));
//        int numRowsOriginal = rows.size();
//
//        PO_ConversationsView.clickEliminarOtherOffersLast(driver);
//        WebElement table2 = driver.findElement(By.id("tableOtherOffers"));
//        List<WebElement> rows2 = table2.findElements(By.tagName("tr"));
//        int numRowsFinal = rows2.size();
//        Assertions.assertEquals(numRowsOriginal, numRowsFinal + 1);
//
//    }
//
//    // [Prueba 37]. Al crear una oferta, marcar dicha oferta como destacada y a continuación comprobar:
//    // i) que aparece en el listado de ofertas destacadas para los usuarios
//    // y que el saldo del usuario se actualiza adecuadamente en la vista del ofertante (-20).
//    @Test
//    @Order(37)
//    public void PR37() {
//        // Iniciamos sesión con un usuario estándar
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        PO_LoginView.fillForm(driver, "user04@email.com", "user04");
//
//        // Sacamos el valor del wallet (inicial)
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listAllOfferMenu");
//        String initialValue = PO_AllOfferView.seeWallet(driver);
//        initialValue = String.valueOf(Double.parseDouble(initialValue) - 20.0);
//
//        // Acceder a la vista de añadir una nueva oferta
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");
//
//        // Rellenar campos del formulario
//        PO_OfferView.fillForm(driver, "coche", "Coche de los años 90", 2000.50, true);
//
//        // Comprobar que se muestra en el listado de ofertas
//        WebElement table = driver.findElement(By.id("tableFeaturedOffer"));
//        int numRows = table.findElements(By.tagName("tr")).size();
//        Assertions.assertEquals(2, numRows);
//
//        // Sacamos el valor del wallet
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listAllOfferMenu");
//        String value = PO_AllOfferView.seeWallet(driver);
//
//        // Lo comparamos con el precio restado:
//        Assertions.assertEquals(initialValue, value);
//
//        // Cerramos sesión
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//    }
//
//    // [Prueba 38]. Sobre el listado de ofertas de un usuario con 20 euros (o más) de saldo,
//    // pinchar en el enlace Destacada y a continuación comprobar:
//    // que aparece en el listado de ofertas destacadas para los usuarios y
//    // que el saldo del usuario se actualiza adecuadamente en la vista del ofertante (-20).
//    @Test
//    @Order(38)
//    public void PR38() {
//        // Iniciamos sesión como usuario standard
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        PO_LoginView.fillForm(driver, "user06@email.com", "user06");
//
//        // Acceder a la vista del listado de ofertas propias
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listOfferMenu");
//
//        WebElement initialTable = driver.findElement(By.id("tableFeaturedOffer"));
//        int initialNumRows = initialTable.findElements(By.tagName("tr")).size();
//
//        // Click en el enlace de destacar una oferta (la primera de la lista)
//        List<WebElement> offers = SeleniumUtils.waitLoadElementsBy(driver, "class", "linkFeaturedOffer",
//                PO_View.getTimeout());
//        offers.get(0).click();
//
//        WebElement table = driver.findElement(By.id("tableFeaturedOffer"));
//        int numRows = table.findElements(By.tagName("tr")).size();
//
//        // Comprobar que se muestra en el listado de ofertas destacadas
//        Assertions.assertEquals(initialNumRows + 1, numRows);
//
//        // Sacamos el valor del wallet
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listAllOfferMenu");
//        String value = PO_AllOfferView.seeWallet(driver);
//
//        // Lo comparamos con el precio restado: 154.0 - 20.0 = 134.0
//        Assertions.assertEquals(value, "134.0");
//
//        // Cerramos sesión
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//    }
//
//    // [Prueba 39]. Sobre el listado de ofertas de un usuario con menos de 20 euros de saldo,
//    // pinchar en el enlace Destacada y a continuación comprobar que se muestra el mensaje de
//    // saldo insuficiente.
//    @Test
//    @Order(39)
//    public void PR39() {
//        // Inicio de sesión con un usuario que tiene menos de 20€ de saldo
//        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//        PO_LoginView.fillForm(driver, "user04@email.com", "user04");
//
//        // Acceder a la vista del listado de ofertas propias
//        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listOfferMenu");
//
//        // Click en el enlace de destacar una oferta (la primera de la lista)
//        List<WebElement> offers = SeleniumUtils.waitLoadElementsBy(driver, "class", "linkFeaturedOffer",
//                PO_View.getTimeout());
//        offers.get(0).click();
//
//        // Comprobar que se muestra el mensaje
//        PO_OfferView.checkNoMoneyMessage(driver, PO_Properties.getSPANISH(), "offer.featured.nomoney");
//
//        // Cerramos sesión
//        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//    }
//
//    /**
//     * [Prueba40] Desde el formulario de dar de alta ofertas, crear una oferta con datos válidos y una imagen adjunta.
//     * Comprobar que en el listado de ofertas propias aparece la imagen adjunta junto al resto de datos de la oferta.
//     */
//    @Test
//    @Order(40)
//    public void PR040() {
//        //Crear un usuario
//        SeleniumUtils.registerNewUser(driver, "miemail12333@email.com", "123456");
//        //Creo una oferta sin imagen
//        String s = "coche";
//        PO_OfferView.addImageOffer(driver, s);
//
//        //Comprobar que se muestra
//        List<WebElement> offers = PO_View.checkElementBy(driver, "text", s);
//        Assertions.assertEquals(2, offers.size());
//
//    }
//
//    /**
//     * Crear una oferta con datos válidos y sin una imagen adjunta.
//     * Comprobar que la oferta se ha creado con éxito, ya que la imagen no es obligatoria.
//     */
//    @Test
//    @Order(41)
//    public void PR041() {
//        //Crear un usuario
//        SeleniumUtils.registerNewUser(driver, "miemail123@email.com", "123456");
//        //Creo una oferta sin imagen
//        String s = "coche";
//        PO_OfferView.addSampleOffer(driver, s);
//
//        //Comprobar que se muestra
//        List<WebElement> offers = PO_View.checkElementBy(driver, "text", s);
//        Assertions.assertEquals(2, offers.size());
//    }

    /**
     * W13 Seguridad y auditoria de la aplicación.
     * <p>
     * [Prueba 33] Intentar acceder sin estar autenticado a la opción de listado de usuarios. Se deberá volver
     * al formulario de login.
     */
    @Test
    @Order(33)
    public void PR33() {
        PO_LoginView.logout(driver);

        // Acceso a la vista de listado de usuarios sin estar autenticado
        driver.navigate().to("http://localhost:8081/users/list");

        // Comprobamos que se muestra el formulario de login
        WebElement loginHeading = driver.findElement(By.xpath("/html/body/div/h2"));
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

        PO_LoginView.logout(driver);

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
}
