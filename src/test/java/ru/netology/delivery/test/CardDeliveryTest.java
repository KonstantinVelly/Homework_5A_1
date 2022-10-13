
package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

    class CardDeliveryTest {

        @BeforeAll
        static void setUpAll() {
            SelenideLogger.addListener("allure", new AllureSelenide());
        }
        @AfterAll
        static void tearDownAll() {
            SelenideLogger.removeListener("allure");
        }

        @BeforeEach
        void setup() {
            open("http://localhost:9999");
        }

        @Test
        @DisplayName("Plan and re-plan the meeting")
        void shouldPlanMeetingsRightWay() {
            var validUser = DataGenerator.Registration.getUser("ru");
            var daysToAddForFirstMeeting = 4;
            var firstMeetingDate = DataGenerator.getDate(daysToAddForFirstMeeting);
            var daysToAddForNextMeeting = 7;
            var secondMeetingDate = DataGenerator.getDate(daysToAddForNextMeeting);

            $("[data-test-id='city'] input").setValue(validUser.getCity());
            $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $x("//input[@placeholder='Дата встречи']").setValue(firstMeetingDate);
            $x("//input[@name='name']").setValue(validUser.getName());
            $x("//input[@name='phone']").setValue(validUser.getPhone());
            $("[data-test-id='agreement']").click();
            $x("//span[@class='button__text']").click();
            $x("//*[contains(text(),'Успешно!')]").shouldBe(Condition.visible, Duration.ofSeconds(15));
            $("[class='notification__content']").shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
            $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $x("//span[@class='button__text']").click();
            $x("//button[contains(@class,'button')]").click();
            $x("//input[@placeholder='Дата встречи']").setValue(secondMeetingDate);
            $x("//span[@class='button__text']").click();
            $x("//*[contains(text(),'Успешно!')]").shouldBe(Condition.visible, Duration.ofSeconds(15));
            $("[class='notification__content']").shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));

        }
}

