package technical.assessment.todo.model

import technical.assessment.todo.TodoStatus

enum class TodosEntityStatus(val apiValue: TodoStatus) {
    CANCELLED(TodoStatus.CANCELLED),
    OPEN(TodoStatus.OPEN),
    DONE(TodoStatus.DONE),

    DEFAULT(TodoStatus.DONE);

    companion object {
        fun mapToEntityStatus(todoStatus: TodoStatus): TodosEntityStatus {
            return values().firstOrNull {
                it.apiValue == todoStatus
            } ?: DEFAULT
        }
    }

}