package technical.assessment.todo.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import technical.assessment.todo.ClientFriendlyException
import technical.assessment.todo.TodoApplication

@ControllerAdvice(basePackageClasses = [TodoApplication::class])
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun handleUnhandledErrors(throwable: Throwable): ClientFriendlyException {
        logger.error("Unhandled Exception", throwable)
        return ClientFriendlyException().reason("Something went wrong. Please contact support.")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingDataException::class)
    @ResponseBody
    fun handleMissingData(throwable: MissingDataException): ClientFriendlyException {
        logger.error("Request is missing Data", throwable)
        return ClientFriendlyException().reason("Request is missing data")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    @ResponseBody
    fun handleBadReqeust(throwable: BadRequestException): ClientFriendlyException {
        logger.error("Bad Request", throwable)
        return ClientFriendlyException().reason("Bad Request")
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    @ResponseBody
    fun notFoundException(throwable: NotFoundException): ClientFriendlyException {
        logger.error("File not Found", throwable)
        return ClientFriendlyException().reason("File not Found")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseBody
    fun illegalArgumentException(throwable: IllegalArgumentException): ClientFriendlyException {
        logger.error("IllegalArgument", throwable)
        return ClientFriendlyException().reason(throwable.message)
    }


}