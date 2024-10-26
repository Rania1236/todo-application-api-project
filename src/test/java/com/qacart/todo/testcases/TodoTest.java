package com.qacart.todo.testcases;
import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static com.qacart.todo.steps.TodoSteps.generateTodo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TodoTest {
    @Test
    public void shouldBeAbleTOAddToDo() {
        String token = UserSteps.getUserToken();
        Todo todo = generateTodo();
        Response response = TodoApi.AddTodo(todo,token);
        Todo returnedToDo= response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(201));
       assertThat(returnedToDo.getItem(),equalTo(todo.getItem()));
       assertThat(returnedToDo.getIsCompleted(),equalTo(todo.getIsCompleted()));
    }

    @Test
    public void shouldNotBeAbleTOAddToDoIfISCompletedIsMissing() {

        Todo todo = new Todo("Learn Appuim");
        String token = UserSteps.getUserToken();
        Response response = TodoApi.AddTodo(todo,token);
        Error returnedToDo= response.body().as(Error.class);
        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedToDo.getMessage(),equalTo(ErrorMessages.IS_COMPLETED_IS_REQUIRED));
    }

    @Test
    public void shouldBeAbleTOGetAddToDoById() {
        String token = UserSteps.getUserToken();
        Todo todo=TodoSteps.generateTodo();
        String TodoId = TodoSteps.getTodoID(todo,token);
        Response response = TodoApi.getTodo(TodoId, token);
        Todo returnedToDo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedToDo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedToDo.getIsCompleted(), equalTo(false));
    }

    @Test
    public void shouldBeAbleTODeleteAddToDO() {

        String token = UserSteps.getUserToken();
        Todo todo=TodoSteps.generateTodo();
        String TodoId = TodoSteps.getTodoID(todo,token);
        Response response = TodoApi.deleteTodo(TodoId,token);
        Todo returnedToDo= response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedToDo.getItem(),equalTo(todo.getItem()));
        assertThat(returnedToDo.getIsCompleted(),equalTo(false));
    }

}