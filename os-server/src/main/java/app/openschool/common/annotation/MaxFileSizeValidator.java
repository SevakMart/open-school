package app.openschool.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSize, MultipartFile> {

  private static final Integer MB = 1024 * 1024;

  private long maxSizeInMb;

  @Override
  public void initialize(MaxFileSize maxfileSize) {
    this.maxSizeInMb = maxfileSize.maxSizeInMb();
  }

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    if (file == null) {
      return true;
    }
    return file.getSize() < maxSizeInMb * MB;
  }
}
