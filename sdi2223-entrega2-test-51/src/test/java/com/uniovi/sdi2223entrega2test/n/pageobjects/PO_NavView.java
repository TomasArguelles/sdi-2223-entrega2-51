package com.uniovi.sdi2223entrega2test.n.pageobjects;

import com.uniovi.sdi2223entrega2test.n.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_NavView extends PO_View {
    /**
     * Clic en una de las opciones principales (a href) y comprueba que se vaya a la vista con el elemento de
     * tipo type con el texto Destino
     *
     * @param driver:     apuntando al navegador abierto actualmente.
     * @param textOption: Texto de la opción principal.
     * @param criterio:   "id" or "class" or "text" or "@attribute" or "free". Si el valor de criterio es free es una
     *                    expresion xpath completa.
     * @param targetText: texto correspondiente a la búsqueda de la página destino.
     */
    public static void clickOption(WebDriver driver, String textOption, String criterio, String targetText) {
        //CLickamos en la opción de registro y esperamos a que se cargue el enlace de Registro.
        List<WebElement> elements = SeleniumUtils.waitLoadElementsBy(driver, "@href", textOption,
                getTimeout());
        //Tiene que haber un sólo elemento.
        Assertions.assertEquals(1, elements.size());
        //Ahora lo clickamos
        elements.get(0).click();
        //Esperamos a que sea visible un elemento concreto
        elements = SeleniumUtils.waitLoadElementsBy(driver, criterio, targetText, getTimeout());
        //Tiene que haber un sólo elemento.
        Assertions.assertEquals(1, elements.size());
    }

    public static void selectDropdownById(WebDriver driver, String downdownMenuArrowId,
                                          String downdownMenuId, String drowndownSubmenuId) {
        // Hacer click en el dropdown
        List<WebElement> offerManagementDropdown = SeleniumUtils.waitLoadElementsBy(driver, "id", downdownMenuArrowId,
                getTimeout());
        offerManagementDropdown.get(0).click();

        // Seleccionar el menu
        SeleniumUtils.waitLoadElementsBy(driver, "id", downdownMenuId,
                getTimeout());

        // Seleccionar el submenu
        if (drowndownSubmenuId != null) {
            List<WebElement> dropdownSubmenu = SeleniumUtils.waitLoadElementsBy(driver, "id", drowndownSubmenuId,
                    getTimeout());
            dropdownSubmenu.get(0).click();
        }
    }
}
