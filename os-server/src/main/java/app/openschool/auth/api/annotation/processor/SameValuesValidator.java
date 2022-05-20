package app.openschool.auth.api.annotation.processor;

import app.openschool.auth.api.annotation.SameValues;
import app.openschool.auth.api.exception.NotPresentFieldException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SameValuesValidator implements ConstraintValidator<SameValues, Object> {

  private List<String> fieldNames;

  @Override
  public void initialize(SameValues sameValues) {
    fieldNames = Arrays.asList(sameValues.sameFields());
  }

  @Override
  public boolean isValid(Object targetObject, ConstraintValidatorContext ctx) {
    if (getUniqueFieldValues(targetObject).size() > 1) {
      ctx.disableDefaultConstraintViolation();
      ctx.buildConstraintViolationWithTemplate("{passwords.mismatch}")
          .addPropertyNode("confirmedPassword")
          .addConstraintViolation();
      return false;
    }
    return true;
  }

  private Set<Object> getUniqueFieldValues(Object targetObject) {
    return fieldNames.stream()
        .map(
            fieldName -> {
              try {
                Field declaredField = targetObject.getClass().getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                return declaredField.get(targetObject);
              } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new NotPresentFieldException("Specified fields are not present");
              }
            })
        .collect(Collectors.toSet());
  }
}
