package dtos.validation;

import dtos.validation.api.ValidationException;
import dtos.validation.api.Validator;
import dtos.validation.generic.ValidationError;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ec on 21/05/15.
 */
public class EnumRepresentationValidator implements Validator<String> {

    private String enumName;
    private String[] enumValueNames;
    public <E extends Enum<E>> EnumRepresentationValidator(Class<E> enumType){
        // Extract info from enumType here in constructor
        enumName = enumType.getName();
        enumValueNames = new String[enumType.getEnumConstants().length];
        int i = 0;
        for (E e: enumType.getEnumConstants() ) {
            enumValueNames[i++] = e.name();
        }
    }


    @Override public Collection<ValidationError> validate(String s) throws ValidationException {
        Collection<ValidationError> validationErrorsList = new LinkedList<>();
        for (String name: enumValueNames) {
            if (name.equals(s))
                return validationErrorsList;
        }
        validationErrorsList.add(ValidationError.of(String.format("%s does not match any %s value", s, enumName)));
        return validationErrorsList;
    }
}
