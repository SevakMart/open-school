package app.openschool.category;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import app.openschool.category.api.CategoryGenerator;
import app.openschool.category.api.dto.CreateCategoryRequest;
import app.openschool.category.api.dto.ModifyCategoryDataRequest;
import app.openschool.category.api.dto.ModifyCategoryImageRequest;
import app.openschool.common.services.aws.S3Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

  @Mock private CategoryRepository categoryRepository;

  @Mock private MessageSource messageSource;

  @Mock private S3Service s3Service;

  private CategoryService categoryService;

  @BeforeEach
  void setUp() {
    categoryService = new CategoryServiceImpl(categoryRepository, messageSource, s3Service);
  }

  @Test
  void findAllParentCategories_returnsPageOfCategoryDto() {
    List<Category> categoryList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      categoryList.add(CategoryGenerator.generateCategory());
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<Category> categoryPage = new PageImpl<>(categoryList, pageable, 5);
    when(categoryRepository.findByParentCategoryIsNull(pageable)).thenReturn(categoryPage);
    Assertions.assertEquals(3, categoryService.findAllParentCategories(pageable).getTotalPages());
    Assertions.assertEquals(
        5, categoryService.findAllParentCategories(pageable).getTotalElements());
    Mockito.verify(categoryRepository, Mockito.times(2)).findByParentCategoryIsNull(pageable);
  }

  @Test
  public void findById_withExistingCategoryId_returnsCategory() {

    Category expectingCategory = new Category("Software engineering", null);
    given(categoryRepository.findById(anyLong())).willReturn(Optional.of(expectingCategory));

    Category actualCategory = categoryService.findById(1L);

    assertEquals(expectingCategory.getId(), actualCategory.getId());
    assertEquals(expectingCategory.getTitle(), actualCategory.getTitle());
    verify(categoryRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  public void findById_withNonExistingCategoryId_throwsIllegalArgumentException() {

    given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());

    assertThatThrownBy(() -> categoryService.findById(1L))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void add_withCorrectArguments_returnsSavedCategory() {
    String logoPath = "Aws/S3/Java.png";
    MockMultipartFile multipartFile = new MockMultipartFile("Java.png", "Java".getBytes());
    given(s3Service.uploadFile(multipartFile)).willReturn(logoPath);
    Category expected = new Category("Java", logoPath, null);
    given(categoryRepository.save(any())).willReturn(expected);
    CreateCategoryRequest createCategoryRequest =
        new CreateCategoryRequest("Java", null, multipartFile);

    Category actual = categoryService.add(createCategoryRequest);

    assertEquals(actual.getTitle(), expected.getTitle());
    assertEquals(actual.getLogoPath(), expected.getLogoPath());
    assertNull(actual.getParentCategoryId());
    verify(s3Service, times(1)).uploadFile(any());
    verify(categoryRepository, times(1)).save(any());
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  public void add_withIncorrectParentCategoryId_throwsIllegalArgumentException() {
    String logoPath = "Aws/S3/Java.png";
    MockMultipartFile multipartFile = new MockMultipartFile("Java.png", "Java".getBytes());
    given(s3Service.uploadFile(multipartFile)).willReturn(logoPath);
    given(categoryRepository.findById(3L)).willReturn(Optional.empty());
    CreateCategoryRequest createCategoryRequest =
        new CreateCategoryRequest("Java", 3L, multipartFile);

    assertThatThrownBy(() -> categoryService.add(createCategoryRequest))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void updateData_withCorrectArguments_returnsUpdatedCategory() {
    Category updatingCategory = new Category("Java", "Aws/S3/Js.png", null);
    given(categoryRepository.findById(1L)).willReturn(Optional.of(updatingCategory));

    Category expected = new Category("Js", "Aws/S3/Js.png", null);
    given(categoryRepository.save(any())).willReturn(expected);
    ModifyCategoryDataRequest request = new ModifyCategoryDataRequest("Js", null);

    Category actual = categoryService.updateData(1L, request);

    assertEquals(actual.getTitle(), expected.getTitle());
    assertNull(actual.getParentCategoryId());
    verify(categoryRepository, times(1)).findById(1L);
    verify(categoryRepository, times(1)).save(any());
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  public void update_withIncorrectCategoryId_throwsIllegalArgumentException() {
    given(categoryRepository.findById(1L)).willReturn(Optional.empty());
    ModifyCategoryDataRequest request = new ModifyCategoryDataRequest("Js", null);

    assertThatThrownBy(() -> categoryService.updateData(1L, request))
        .isInstanceOf(IllegalArgumentException.class);
  }

  private static Stream<Arguments> updateData_withBlankTitle_throwsIllegalArgumentException() {
    return Stream.of(
        arguments(1L, new ModifyCategoryDataRequest("", null)),
        arguments(1L, new ModifyCategoryDataRequest(" ", null)));
  }

  @ParameterizedTest
  @MethodSource
  public void updateData_withBlankTitle_throwsIllegalArgumentException(
      Long categoryId, ModifyCategoryDataRequest request) {
    Category updatingCategory =
        new Category("Java", "Amazon/S3/open-school/eu-central-1/Java.png", null);
    given(categoryRepository.findById(1L)).willReturn(Optional.of(updatingCategory));

    assertThatThrownBy(() -> categoryService.updateData(categoryId, request))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void updateData_withIncorrectNewParenCategoryId_throwsIllegalArgumentException() {
    Category updatingCategory =
        new Category("Java", "Amazon/S3/open-school/eu-central-1/Java.png", null);
    given(categoryRepository.findById(1L)).willReturn(Optional.of(updatingCategory));
    given(categoryRepository.findById(2L)).willReturn(Optional.empty());
    ModifyCategoryDataRequest request = new ModifyCategoryDataRequest("Js", 2L);

    assertThatThrownBy(() -> categoryService.updateData(1L, request))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void updateImage_withNotNullImage_returnsUpdatedCategory() {
    Category updatingCategory =
        new Category(
            "Java",
            "Amazon/S3/open-school/eu-central-1/kldnfjnerjkvvernejkvdnfjnfjdbnk_Java.png",
            null);
    given(categoryRepository.findById(1L)).willReturn(Optional.of(updatingCategory));
    String logoPath = "Aws/S3/Java2.png";
    MockMultipartFile multipartFile = new MockMultipartFile("Java2.png", "Java2".getBytes());
    given(s3Service.uploadFile(multipartFile)).willReturn(logoPath);
    Category expected = new Category("Java", logoPath, null);
    given(categoryRepository.save(any())).willReturn(expected);
    ModifyCategoryImageRequest request = new ModifyCategoryImageRequest(multipartFile);

    Category actual = categoryService.updateImage(1L, request);

    assertEquals(actual.getLogoPath(), expected.getLogoPath());
    verify(categoryRepository, times(1)).findById(1L);
    verify(s3Service, times(1)).uploadFile(multipartFile);
    verify(s3Service, times(1)).deleteFile(anyString());
    verify(categoryRepository, times(1)).save(any());
    verifyNoMoreInteractions(s3Service);
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  public void delete_withCorrectCategoryId() {
    Category parentCategory =
        new Category(
            "Java",
            "Amazon/S3/open-school/eu-central-1/kldnfjnerjkvvernejkvdnfjnfjdbnk_Java.png",
            null);
    Category deletingCategory =
        new Category(
            "Collections",
            "Amazon/S3/open-school/eu-central-1/kldnfjnerjkvvernejkvdnfjnfjdbnk_Collections.png",
            parentCategory);
    deletingCategory.setParentCategoryId(1L);
    given(categoryRepository.findById(1L)).willReturn(Optional.of(deletingCategory));

    categoryService.delete(1L, Locale.ROOT);

    verify(s3Service, times(1)).deleteFile(anyString());
    verifyNoMoreInteractions(s3Service);
    verify(categoryRepository, times(1)).deleteById(1L);
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  public void delete_withIncorrectCategoryId_throwsIllegalArgumentException() {
    given(categoryRepository.findById(1L)).willReturn(Optional.empty());
    assertThatThrownBy(() -> categoryService.delete(1L, Locale.ROOT))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void delete_whenDeletingCategoryIsParent_throwsUnsupportedOperationException() {
    Category deletingCategory =
        new Category("Java", "Amazon/S3/open-school/eu-central-1/k_Java.png", null);
    given(categoryRepository.findById(1L)).willReturn(Optional.of(deletingCategory));
    given(messageSource.getMessage("category.delete.not.allowed", null, Locale.ROOT))
        .willReturn("Deleting parent category not allowed");

    assertThatThrownBy(() -> categoryService.delete(1L, Locale.ROOT))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
