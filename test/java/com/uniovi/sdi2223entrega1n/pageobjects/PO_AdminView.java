package com.uniovi.sdi2223entrega1n.pageobjects;

import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PO_AdminView extends PO_NavView {

    public static final String ADMIN_EMAIL = "admin@email.com";

    public static final String ADMIN_DASHBOARD_ENDPOINT = "/admin/logs/all";
    public static final String ADMIN_DELETE_ALL_LOGS_ENDPOINT = "/admin/logs/deleteAll";

    // Menu para acceder a la vista de logs
    public static final String VIEW_LOGS_MENU_ITEM = "viewLogsMenuItem";

    // Botón para eliminar todos los logs
    public static final String DELETE_ALL_LOGS_BUTTON = "deleteAllLogsButton";

    /**
     * Eliminación de todos los logs del sistema.
     *
     * @param driver WebDriver
     */
    public static void deleteAllLogsWithLogout(WebDriver driver) {
        // Iniciar sesión como administrador
        SeleniumUtils.signInIntoAccount(driver, "ADMIN", ADMIN_EMAIL);

        // Click en borrar todos los logs
        PO_NavView.clickOption(driver, ADMIN_DELETE_ALL_LOGS_ENDPOINT, "id", DELETE_ALL_LOGS_BUTTON);

        PO_NavView.clickLogout(driver);
    }

    public static void deleteAllLogsWithoutLogout(WebDriver driver) {
        // Iniciar sesión como administrador
        SeleniumUtils.signInIntoAccount(driver, "ADMIN", ADMIN_EMAIL);

        // Click en borrar todos los logs
        PO_NavView.clickOption(driver, ADMIN_DELETE_ALL_LOGS_ENDPOINT, "id", DELETE_ALL_LOGS_BUTTON);
    }

    /**
     * Navega a la vista de logs del administrador.
     *
     * @param driver
     */
    public static void goToListOfLogsView(WebDriver driver) {
        // Iniciar sesión como administrador
        SeleniumUtils.signInIntoAccount(driver, "ADMIN", ADMIN_EMAIL);

        // Click en borrar todos los logs
        PO_NavView.clickOption(driver, ADMIN_DASHBOARD_ENDPOINT, "id", "viewLogsMenuItem");
    }

    public static String getTitle(WebDriver driver) {
        return driver.findElement(By.name("title")).getText();
    }
}
