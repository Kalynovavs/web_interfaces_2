package ru.netology.interfaces;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CallbackTest {
    private String dateAllowable;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    String generateDate(int days) {
        return LocalDateTime.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {

        open("http://localhost:9999");
        dateAllowable = generateDate(4);
    }


    @Test
    void shouldSubmitRequestSuccess() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Нижний Новгород");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(dateAllowable);
        form.$("[data-test-id=name] input").setValue("Василий Маршал-Передовой");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldFormValidateWithoutAgreement() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Нижний Новгород");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(dateAllowable);
        form.$("[data-test-id=name] input").setValue("Василий Маршал-Передовой");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$(".button").click();
        form.$("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }

    @Test
    void shouldFormValidateEmptyName() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Нижний Новгород");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(dateAllowable);
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFormValidateEmptyNumber() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Нижний Новгород");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(dateAllowable);
        form.$("[data-test-id=name] input").setValue("Василий Маршал-Передовой");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFormValidateWrongName() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Нижний Новгород");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(dateAllowable);
        form.$("[data-test-id=name] input").setValue("kalynova valentina");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldFormValidateWrongNumber() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Нижний Новгород");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(dateAllowable);
        form.$("[data-test-id=name] input").setValue("Василий Маршал-Передовой");
        form.$("[data-test-id=phone] input").setValue("77779270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldFormValidateWrongDate() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Нижний Новгород");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue("32");
        form.$("[data-test-id=name] input").setValue("Василий Маршал-Передовой");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$("[data-test-id=date] .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldFormValidateNotAllowedDate() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Нижний Новгород");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(generateDate(1));
        form.$("[data-test-id=name] input").setValue("Василий Маршал-Передовой");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$("[data-test-id=date] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldFormValidateWrongCiti() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Несуществующий город");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(dateAllowable);
        form.$("[data-test-id=name] input").setValue("Василий Маршал-Передовой");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldSubmitRequestSuccessChecDate() {
        SelenideElement form =$(".form");
        form.$("[data-test-id=city] input").setValue("Нижний Новгород");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(dateAllowable);
        form.$("[data-test-id=name] input").setValue("Василий Маршал-Передовой");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=notification] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + dateAllowable), Duration.ofSeconds(15));
    }


}
