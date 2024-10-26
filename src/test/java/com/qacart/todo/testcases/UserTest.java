package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {

    @Test
    public void shouldBeAbleToRegister()
    {
        /*
        String body= "{\n" +
                "    \"firstName\": \"youssif\",\n" +
                "    \"lastName\":\"magdy\",\n" +
                "    \"email\":\"youssifmagdy0161@gmail.com\",\n" +
                "    \"password\":\"youssif1234\"\n" +
                "}";
         */
        User user = UserSteps.generateUser();
        Response response=UserApi.register(user);
        User returnedUser= response.body().as(User.class);
        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName()));

    }
    @Test
    public void shouldNotBeAbleToRegisterWithTheSameEmail()
    {

        User user = UserSteps.getRegisteredUser();
        Response response=UserApi.register(user);
        Error returnedUser= response.body().as(Error.class);
        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedUser.getMessage(),equalTo(ErrorMessages.EMAIL_IS_ALREADY_REGISTERED));

    }
    @Test
    public void shouldBeAbleTOLogin()
    {
        User user = UserSteps.getRegisteredUser();
        User loginData =new User(user.getEmail(), user.getPassword());

        Response response= UserApi.login(loginData);
        User returnedUser= response.body().as(User.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName()));
        assertThat(returnedUser.getAccessToken(),not(equalTo(null)));
    }
    @Test
    public void shouldNotBeAbleTOLoginIfThePasswordIsNotCorrect()
    {
        User user = UserSteps.getRegisteredUser();

        User loginData =new User(user.getEmail(),"wrongPassword");
        Response response= UserApi.login(loginData);


        Error returnedUser= response.body().as(Error.class);
        assertThat(response.statusCode(),equalTo(401));
        assertThat(returnedUser.getMessage(),equalTo(ErrorMessages.EMAIL_OR_PASSWORD_IS_WRONG));
    }


}
