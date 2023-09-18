package technical.assessment.todo.entity

import org.hibernate.annotations.CreationTimestamp
import technical.assessment.todo.model.TodosEntityStatus
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    var description: String,
    @CreationTimestamp
    val dateAdded: LocalDate? = null,
    var dueDate: LocalDate,
    var status: TodosEntityStatus
) {
    constructor() : this(null, "", null, LocalDate.now(), TodosEntityStatus.OPEN)

}

