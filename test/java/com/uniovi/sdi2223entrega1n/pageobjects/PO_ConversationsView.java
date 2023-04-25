package com.uniovi.sdi2223entrega1n.pageobjects;

import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_ConversationsView extends PO_NavView {


    public static void compareOneByOneTwoUsersLists(WebDriver driver, List<WebElement> usersList, List<User> usersSystem) {
        for(int i=0;i< usersList.size(); i++){
            WebElement userWeb=usersList.get(i);
            User user=usersSystem.get(i);
            Assertions.assertEquals(user.getEmail(), userWeb.findElement(By.xpath("./td[1]")).getText());
            Assertions.assertEquals(user.getName(), userWeb.findElement(By.xpath("./td[2]")).getText());
            Assertions.assertEquals(user.getLastName(), userWeb.findElement(By.xpath("./td[3]")).getText());
        }
    }

    public static void clickConversationsLink(WebDriver driver) {
        WebElement conversationLink = driver.findElement(By.linkText("Conversation"));
        conversationLink.click();
    }
    public static void writeMessage(WebDriver driver, String text) {
        WebElement messageInput = driver.findElement(By.name("text"));
        messageInput.sendKeys(text);
    }

    public static void sendMessage(WebDriver driver) {
        WebElement sendButton = driver.findElement(By.cssSelector("button[type=submit]"));
        sendButton.click();
    }


    public static void clickEliminarOtherOffersFirst(WebDriver driver) {
        WebElement table = driver.findElement(By.id("tableOtherOffers"));
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        // choose the first row to click the eliminar link
        if (rows.size() > 0) {
            WebElement eliminarLink = rows.get(0).findElement(By.linkText("Eliminar"));
            eliminarLink.click();
        }


    }
    public static void clickEliminarOtherOffersLast(WebDriver driver) {
        WebElement table = driver.findElement(By.id("tableOtherOffers"));
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        // choose the first row to click the eliminar link
        if (rows.size() > 0) {
            WebElement eliminarLink = rows.get(rows.size()-1).findElement(By.linkText("Eliminar"));
            eliminarLink.click();
        }


    }
}
