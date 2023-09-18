package technical.assessment.todo.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import technical.assessment.todo.*
import technical.assessment.todo.service.TodosService

@Controller
class TodosController(
    private val todosService: TodosService
) : TodosApi {
    override fun getTodos(): ResponseEntity<TodoListResponse> {
        val todos = todosService.getTodos()
        return ResponseEntity.ok(todos)
    }

    override fun getTodo(id: String?): ResponseEntity<TodoResponse> {
        val todos = todosService.getTodo(id)
        return ResponseEntity.ok(todos)
    }

    override fun postTodos(postTodosRequest: PostTodosRequest?): ResponseEntity<Void> {
        todosService.postTodos(postTodosRequest)
        return ResponseEntity(HttpStatus.CREATED)
    }

    override fun patchTodo(id: String?, todoRequest: TodoRequest?): ResponseEntity<Void> {
        todosService.patchTodo(id, todoRequest)
        return ResponseEntity(HttpStatus.ACCEPTED)
    }

    override fun deteteTodo(id: String?): ResponseEntity<Void> {
        todosService.deleteTodo(id)
        return ResponseEntity(HttpStatus.OK)
    }
}