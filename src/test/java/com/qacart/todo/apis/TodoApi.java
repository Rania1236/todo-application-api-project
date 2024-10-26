package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.route;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {
    public static Response AddTodo(Todo todo,String token){
        return given()
                .spec(Specs.gerRequestSpec())
                .body(todo)
                .auth().oauth2(token)
                .when().post(route.TODOS_ROUTE)
                .then()
                .log().all()
                .extract().response();

    }
    public static Response getTodo(String taskId,String token){
        return given()
                .spec(Specs.gerRequestSpec())
                .auth().oauth2(token)
                .when().get(route.TODOS_ROUTE+"/" + taskId)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response deleteTodo(String taskId,String token){
        return given()
                .spec(Specs.gerRequestSpec())
                .auth().oauth2(token)
                .when().delete(route.TODOS_ROUTE+"/" + taskId)
                .then()
                .log().all()
                .extract().response();

    }
}
