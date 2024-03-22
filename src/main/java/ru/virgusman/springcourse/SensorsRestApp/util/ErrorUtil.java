package ru.virgusman.springcourse.SensorsRestApp.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.virgusman.springcourse.SensorsRestApp.util.Errors.NotCreatedException;

import java.util.List;

public class ErrorUtil {

    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append(";");
        }
        throw new NotCreatedException(errorMsg.toString());
    }
}
