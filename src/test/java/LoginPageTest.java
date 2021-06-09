import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPageTest {
    FakerUserGenerator fakerUserGenerator = new FakerUserGenerator();

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldValidAll() {
        UserData user = fakerUserGenerator.activeUser();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $(".App_appContainer__3jRx1").shouldBe(visible).shouldHave(text("Личный кабинет"));


    }

    @Test
    void shouldTestBlockedUser() {
        UserData user = fakerUserGenerator.blockedUser();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));

    }

    @Test
    void shouldTestInvalidLogin() {
        UserData user = fakerUserGenerator.activeUserInvalidLogin();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));

    }
    @Test
    void shouldTestInvalidPassword() {
        UserData user = fakerUserGenerator.activeUserInvalidPassword();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }
}
