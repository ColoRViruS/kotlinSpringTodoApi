package technical.assessment.todo.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import technical.assessment.todo.PostTodosRequest
import technical.assessment.todo.TodoListResponse
import technical.assessment.todo.TodoRequest
import technical.assessment.todo.TodoResponse
import technical.assessment.todo.entity.Todo
import technical.assessment.todo.entity.TodoRepository
import technical.assessment.todo.exception.BadRequestException
import technical.assessment.todo.exception.MissingDataException
import technical.assessment.todo.exception.NotFoundException
import technical.assessment.todo.model.TodosEntityStatus
import java.time.temporal.ChronoUnit

@Service
class TodosService @Autowired constructor(private val todoRepository: TodoRepository) {
    fun getTodos(): TodoListResponse {
        val todoList = todoRepository.findAll()
        return TodoListResponse().apply {
            todos = todoList.map { entityItem ->
                mapTodoResponse(entityItem)
            }
        }
    }

    fun getTodo(id: String?): TodoResponse {

        val idInt = validateId(id)
        val todo = todoRepository.findById(idInt)

        if (todo.isPresent) {
            return mapTodoResponse(todo.get())
        } else {
            throw NotFoundException()
        }
    }

    fun postTodos(postTodosRequest: PostTodosRequest?) {
        postTodosRequest?.todos?.forEach { item ->
            val newTodo = Todo(
                id = null,
                description = item.description,
                dueDate = item.dueDate,
                status = TodosEntityStatus.mapToEntityStatus(item.status)
            )
            todoRepository.save(newTodo)
        }
    }

    fun patchTodo(id: String?, todoRequest: TodoRequest?) {
        if (todoRequest == null) throw MissingDataException()

        val idInt = validateId(id)
        val todo = todoRepository.findById(idInt)

        if (todo.isPresent) {
            val updatedTodo = todo.get()

            updatedTodo.description = todoRequest.description
            updatedTodo.dueDate = todoRequest.dueDate
            updatedTodo.status = TodosEntityStatus.mapToEntityStatus(todoRequest.status)

            todoRepository.save(updatedTodo)
        } else {
            throw NotFoundException()
        }
    }

    fun deleteTodo(id: String?) {
        val idInt = validateId(id)

        if (todoRepository.existsById(idInt)) {
            todoRepository.deleteById(idInt)
        } else {
            throw NotFoundException()
        }
    }

    fun validateId(id: String?): Int {
        if (id.isNullOrEmpty()) throw MissingDataException()
        if (id.toIntOrNull() == null) throw BadRequestException()
        return Integer.parseInt(id)
    }

    fun mapTodoResponse(todo: Todo): TodoResponse {
        return TodoResponse().apply {
            id = todo.id
            description = todo.description
            dateAdded = todo.dateAdded
            dueDate = todo.dueDate
            daysLeft = ChronoUnit.DAYS.between(todo.dateAdded, todo.dueDate).toInt()
            status = todo.status.apiValue
        }
    }
}