package test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.DataGenerator.Registration.getRegisteredUser;
import static data.DataGenerator.Registration.getUser;

public class JSONTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    @DisplayName("should test with valid registered active user")
    void shouldTestWithValidRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("should test with valid registered blocked user")
    void shouldTestWithValidRegisteredBlockedUser() {
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("should test with valid unregistered user")
    void shouldTestWithValidUnregisteredUser() {
        var unregisteredUser = getUser("active");
        $("[data-test-id=login] input").setValue(unregisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(unregisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("should test with registered user wrong login")
    void shouldTestWithValidRegisteredUserWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var unregisteredUser = getUser("active");
        $("[data-test-id=login] input").setValue(unregisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("should test with registered user wrong pass")
    void shouldTestWithValidRegisteredUserWrongPass() {
        var registeredUser = getRegisteredUser("active");
        var unregisteredUser = getUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(unregisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(5));
    }
}
