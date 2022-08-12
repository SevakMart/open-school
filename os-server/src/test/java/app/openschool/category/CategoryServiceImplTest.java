package app.openschool.category;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import app.openschool.category.api.CategoryGenerator;
import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import app.openschool.category.api.exception.ImageNotExistsException;
import app.openschool.category.api.exception.IncorrectCategoryTitleException;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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
  void findAllCategories() {
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
  public void findAll_returnsDtoContainingParentAndSubCategoriesMap() {
    Category parentCategoryJava = new Category(1L, "Java", null);
    Category parentCategoryJs = new Category(2L, "JS", null);
    Category subCategoryJava1 = new Category(3L, "Thread", parentCategoryJava);
    Category subCategoryJava2 = new Category(4L, "Collections", parentCategoryJava);
    Category subCategoryJava3 = new Category(5L, "Collections List", parentCategoryJava);
    Category subCategoryJs1 = new Category(6L, "React", parentCategoryJs);
    Category subCategoryJs2 = new Category(7L, "Collections", parentCategoryJs);
    CategoryDto categoryDtoJava = new CategoryDto(1L, "Java", null);
    CategoryDto categoryDtoJs = new CategoryDto(2L, "JS", null);
    given(categoryRepository.findByParentCategoryIsNotNull())
        .willReturn(
            List.of(
                subCategoryJava1,
                subCategoryJava2,
                subCategoryJava3,
                subCategoryJs1,
                subCategoryJs2));

    ParentAndSubCategoriesDto actualDto = categoryService.findAll();

    assertTrue(actualDto.getParentAndSubCategoriesMap().containsKey(categoryDtoJava));
    assertTrue(actualDto.getParentAndSubCategoriesMap().containsKey(categoryDtoJs));
    assertEquals(actualDto.getParentAndSubCategoriesMap().size(), 2);
    assertEquals(actualDto.getParentAndSubCategoriesMap().get(categoryDtoJava).size(), 3);
    assertEquals(actualDto.getParentAndSubCategoriesMap().get(categoryDtoJs).size(), 2);
    verify(categoryRepository, times(1)).findByParentCategoryIsNotNull();
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  public void add_withCorrectArguments_returnsSavedCategory() {
    String createCategoryRequest = "{\"title\": \"Java\"}";
    String logoPath = "AWS/S3/Java.txt";
    MockMultipartFile multipartFile =
        new MockMultipartFile("Java", "Java.txt", MediaType.TEXT_PLAIN_VALUE, "Java".getBytes());
    Category expected = new Category("Java", logoPath, null);
    given(s3Service.uploadFile(multipartFile)).willReturn(logoPath);
    given(categoryRepository.save(any())).willReturn(expected);

    Category actual = categoryService.add(createCategoryRequest, multipartFile);

    assertEquals(actual.getTitle(), expected.getTitle());
    assertEquals(actual.getLogoPath(), expected.getLogoPath());
    assertNull(actual.getParentCategoryId());
    verify(s3Service, times(1)).uploadFile(any());
    verify(categoryRepository, times(1)).save(any());
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  public void add_withNullCreateCategoryRequest_throwsIllegalArgumentException() {
    MockMultipartFile multipartFile =
        new MockMultipartFile("Java", "Java.txt", MediaType.TEXT_PLAIN_VALUE, "Java".getBytes());
    assertThatThrownBy(() -> categoryService.add(null, multipartFile))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void add_withNullMultiPartFile_throwsImageNotExistsException() {
    String createCategoryRequest = "{\"title\": \"Java\"}";
    assertThatThrownBy(() -> categoryService.add(createCategoryRequest, null))
        .isInstanceOf(ImageNotExistsException.class);
  }

  @ParameterizedTest
  @MethodSource
  public void add_withBlankTitle_IncorrectCategoryTitleException(
      String createCategoryRequest, MultipartFile multipartFile) {
    assertThatThrownBy(() -> categoryService.add(createCategoryRequest, multipartFile))
        .isInstanceOf(IncorrectCategoryTitleException.class);
  }

  private static Stream<Arguments> add_withBlankTitle_IncorrectCategoryTitleException() {
    MockMultipartFile multipartFile =
        new MockMultipartFile("Java", "Java.txt", MediaType.TEXT_PLAIN_VALUE, "Java".getBytes());
    return Stream.of(
        arguments("", multipartFile),
        arguments(" ", multipartFile),
        arguments("{ }", multipartFile),
        arguments("{\"title\": }", multipartFile),
        arguments("{\"title\": \"\"}", multipartFile),
        arguments("{\"title\": \" \"}", multipartFile));
  }

  @Test
  public void update_withCorrectArguments_returnsSavedCategory() {
    Category updatingCategory =
        new Category(
            "Java",
            "Amazon/S3/open-school/eu-central-1/kldnfjnerjkvvernejkvdnfjnfjdbnk_JS.txt",
            null);
    given(categoryRepository.findById(1L)).willReturn(Optional.of(updatingCategory));
    String modifyCategoryRequest = "{\"title\": \"JS\"}";
    MockMultipartFile multipartFile =
        new MockMultipartFile("JS", "JS.txt", MediaType.TEXT_PLAIN_VALUE, "JS".getBytes());
    String logoPath = "AWS/S3/JS.txt";
    given(s3Service.uploadFile(multipartFile)).willReturn(logoPath);
    doNothing().when(s3Service).deleteFile(any());
    Category expected = new Category("JS", logoPath, null);
    given(categoryRepository.save(any())).willReturn(expected);

    Category actual = categoryService.update(1L, modifyCategoryRequest, multipartFile);

    assertEquals(actual.getTitle(), expected.getTitle());
    assertEquals(actual.getLogoPath(), expected.getLogoPath());
    assertNull(actual.getParentCategoryId());
    verify(s3Service, times(1)).uploadFile(any());
    verify(s3Service, times(1)).deleteFile(any());
    verify(categoryRepository, times(1)).save(any());
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  public void update_withIncorrectCategoryId_throwsIllegalArgumentException() {
    String modifyCategoryRequest = "{\"title\": \"Java\"}";
    MockMultipartFile multipartFile =
        new MockMultipartFile("Java", "Java.txt", MediaType.TEXT_PLAIN_VALUE, "Java".getBytes());
    given(categoryRepository.findById(1L)).willReturn(Optional.empty());
    assertThatThrownBy(() -> categoryService.update(1L, modifyCategoryRequest, multipartFile))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @MethodSource
  public void update_withBlank_throwsIncorrectCategoryTitleException(
      Long categoryId, String createCategoryRequest, MultipartFile multipartFile) {
    Category updatingCategory =
        new Category(
            "Java",
            "Amazon/S3/open-school/eu-central-1/kldnfjnerjkvvernejkvdnfjnfjdbnk_JS.txt",
            null);
    given(categoryRepository.findById(1L)).willReturn(Optional.of(updatingCategory));

    assertThatThrownBy(
            () -> categoryService.update(categoryId, createCategoryRequest, multipartFile))
        .isInstanceOf(IncorrectCategoryTitleException.class);
  }

  private static Stream<Arguments> update_withBlank_throwsIncorrectCategoryTitleException() {
    MockMultipartFile multipartFile =
        new MockMultipartFile("Java", "Java.txt", MediaType.TEXT_PLAIN_VALUE, "Java".getBytes());
    return Stream.of(
        arguments(1L, "", multipartFile),
        arguments(1L, " ", multipartFile),
        arguments(1L, "{ }", multipartFile),
        arguments(1L, "{\"title\": }", multipartFile),
        arguments(1L, "{\"title\": \"\"}", multipartFile),
        arguments(1L, "{\"title\": \" \"}", multipartFile));
  }

  @Test
  public void update_withIncorrectNewParenCategoryId_throwsIllegalArgumentException() {
    Category updatingCategory =
        new Category(
            "Java",
            "Amazon/S3/open-school/eu-central-1/kldnfjnerjkvvernejkvdnfjnfjdbnk_JS.txt",
            null);
    given(categoryRepository.findById(1L)).willReturn(Optional.of(updatingCategory));
    given(categoryRepository.findById(2L)).willReturn(Optional.empty());
    String modifyCategoryRequest = "{ \"title\": \"JS\", \"parentCategoryId\": \"2\" }";
    MockMultipartFile multipartFile =
        new MockMultipartFile("Java", "Java.txt", MediaType.TEXT_PLAIN_VALUE, "Java".getBytes());

    assertThatThrownBy(() -> categoryService.update(1L, modifyCategoryRequest, multipartFile))
        .isInstanceOf(IllegalArgumentException.class);
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
