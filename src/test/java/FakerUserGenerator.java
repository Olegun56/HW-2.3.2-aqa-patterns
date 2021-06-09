import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class FakerUserGenerator {
    private Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void setUpAll(UserData userData) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(userData) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public String login() {
        return faker.name().firstName().toLowerCase(Locale.ROOT);
    }

    public String password() {
        return faker.internet().password();
    }


    public UserData activeUser() {
        UserData userData = new UserData(login(), password(), "active");
        setUpAll(userData);
        return userData;
    }

    public UserData blockedUser() {
        UserData userData = new UserData(login(), password(), "blocked");
        setUpAll(userData);
        return userData;
    }

    public UserData activeUserInvalidLogin() {
        UserData userData = new UserData(login(), password(), "active");
        setUpAll(userData);
        return new UserData("Oleg", password(), "active");
    }

    public UserData activeUserInvalidPassword() {
        UserData userData = new UserData(faker.name().firstName(), faker.internet().password(), "active");
        setUpAll(userData);
        return new UserData(login(), "qwerty", "active");

    }
}
