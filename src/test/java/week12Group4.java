import jdk.jshell.execution.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class week12Group4 extends UtilityClass {
    public static void main(String[] args) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        driver.get("https://demo.openmrs.org/openmrs/login.htm");

        driver.findElement(By.id("username")).sendKeys("Admin");
        driver.findElement(By.id("password")).sendKeys("Admin123");
        WebElement loginButton = driver.findElement(By.id("loginButton"));
        loginButton.click();

        WebElement loginErrorMessage = driver.findElement(By.id("sessionLocationError"));
        String actLoginErrorMessage = loginErrorMessage.getText();
        String expLoginErrorMessage = "You must choose a location!";
        if (actLoginErrorMessage.equals(expLoginErrorMessage)){
            System.out.println("Login error message related to 'not selecting a location' was shown: Pass\n");
        }else {
            System.out.println("Login error message related to 'not selecting a location' was not shown: Fail\n");
        }

        List<WebElement> locationNames = driver.findElements(By.cssSelector("[id='sessionLocation']>li"));
        Actions actions = new Actions(driver);
        Action hoverOverLocationNames;

        for (int i = 0; i < locationNames.size(); i++) {
            String beforeHover = locationNames.get(i).getCssValue("background-color");
            System.out.println("Before case: " + beforeHover);

            hoverOverLocationNames = actions.moveToElement(locationNames.get(i)).build();
            hoverOverLocationNames.perform();

            wait.until(ExpectedConditions.visibilityOf(locationNames.get(i)));
            String afterHover = locationNames.get(i).getCssValue("background-color");
            System.out.println("After case: " + beforeHover);

            if (!(beforeHover.equals(afterHover))){
                System.out.println("The background color has changed\n");
            }
        }

        int randomIndex = (int)(Math.random() * locationNames.size()) ;
        locationNames.get(randomIndex).click();
        loginButton.click();

        List<WebElement> locationNamesNewPage = driver.findElements(By.cssSelector("[class='select']>li"));

        for (int i = 0; i < locationNamesNewPage.size(); i++) {

            WebElement locationButton;
            locationButton= driver.findElement(By.id("selected-location"));
            locationButton.click();

            String actNewLocationButton = locationNamesNewPage.get(i).getText();
            locationNamesNewPage.get(i).click();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            locationButton= driver.findElement(By.id("selected-location"));
            locationButton.click();
            String expLocationText = locationButton.getText();

            System.out.println("Actual: " + actNewLocationButton);
            System.out.println("Expected: " + expLocationText + "\n");

            locationNamesNewPage.get(i).click();
        }

        WebElement adminButton = driver.findElement(By.cssSelector("li[class='nav-item identifier']"));
        Action hoverOverAdminButton = actions.moveToElement(adminButton).build();
        hoverOverAdminButton.perform();
        System.out.println("Is admin button displayed? " + adminButton.isDisplayed());

        WebElement myAccountButton = driver.findElement(By.xpath("//*[@id=\"user-account-menu\"]/li/a"));
        System.out.println("Is my account button displayed? " + myAccountButton.isDisplayed());
        myAccountButton.click();

        String actMyAccountTitle = driver.getTitle();
        String expMyAccountTitle = "My Account";
        if (actMyAccountTitle.equals(expMyAccountTitle)){
            System.out.println("\nThe title of the 'My Account' page: " + expMyAccountTitle + " - Pass");
        }else {
            System.out.println("The expected title of the 'My Account' page is not the same as the actual one - Fail");
        }

        WebElement myLanguages = driver.findElement(By.cssSelector("i[class='icon-cog']"));
        myLanguages.click();
        String actMyLanguages = driver.getTitle();
        String expMyLanguages = "My Languages";
        if (actMyLanguages.equals(expMyLanguages)){
            System.out.println("The title of the 'My Languages' page: " + expMyLanguages + " - Pass");
        }else {
            System.out.println("The expected title of the 'My Languages' page is not the same as the actual one - Fail");
        }

        System.out.println();

        List<WebElement> dropdownLanguages = driver.findElements(By.cssSelector("[id='default-locale-field']>option+option"));
        WebElement languageDropdown = driver.findElement(By.xpath("//*[@id=\"default-locale-field\"]"));
        Select languageDropdownMenu = new Select(languageDropdown);
        int languageRandomInteger = (int)(Math.random() * dropdownLanguages.size());
        languageDropdownMenu.selectByIndex(languageRandomInteger);

        List<WebElement> checkBoxes = driver.findElements(By.xpath("//*[@id=\"content\"]/div/form/div[1]/input"));
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).click();
            System.out.println("Is the checkbox " + checkBoxes.get(i).getAttribute("value") + " selected? " + checkBoxes.get(i).isSelected());

            checkBoxes.get(i).click();
            System.out.println("Is the checkbox " + checkBoxes.get(i).getAttribute("value") + " unselected? " + !checkBoxes.get(i).isSelected());
        }

        System.out.println("\nSelecting all the checkboxes");

        for (int i = 0; i < checkBoxes.size(); i++) {
            if (!checkBoxes.get(i).isSelected()){
                checkBoxes.get(i).click();
            }
            if (checkBoxes.get(i).isSelected()){
                System.out.println("CheckBox " + checkBoxes.get(i).getAttribute("value") + " is selected: " + checkBoxes.get(i).isSelected());
            }
        }

        WebElement saveButton = driver.findElement(By.cssSelector("input[type='submit']"));
        saveButton.click();

        WebElement errorMessage = driver.findElement(By.xpath("//p[text()='User defaults could not be updated.']"));
        System.out.println("\nIs error message displayed? " + errorMessage.isDisplayed());

        quitDriver(4);

    }

}
