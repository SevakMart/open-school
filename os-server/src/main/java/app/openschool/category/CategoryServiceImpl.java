package app.openschool.category;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.CreateCategoryRequest;
import app.openschool.category.api.dto.ModifyCategoryDataRequest;
import app.openschool.category.api.dto.ModifyCategoryImageRequest;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.mapper.CategoryMapper;
import app.openschool.common.services.aws.FileStorageService;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final MessageSource messageSource;
  private final FileStorageService fileStorageService;

  public CategoryServiceImpl(
      CategoryRepository categoryRepository,
      MessageSource messageSource,
      FileStorageService fileStorageService) {
    this.categoryRepository = categoryRepository;
    this.messageSource = messageSource;
    this.fileStorageService = fileStorageService;
  }

  @Override
  public Page<CategoryDto> findAllParentCategories(Pageable pageable) {
    return CategoryMapper.toCategoryDtoPage(
        categoryRepository.findByParentCategoryIsNull(pageable));
  }

  @Override
  public Map<String, List<PreferredCategoryDto>> findCategoriesByTitle(String title) {
    if (StringUtils.isBlank(title)) {
      Map<String, List<PreferredCategoryDto>> result =
          findBySubCategories(categoryRepository.findByParentCategoryIsNotNull());
      result.putAll(findByParentCategories(categoryRepository.findByParentCategoryIsNull()));
      return result;
    }
    Map<String, List<PreferredCategoryDto>> result =
        findBySubCategories(
            categoryRepository.findByTitleContainingIgnoreCaseAndParentCategoryIsNotNull(
                title.trim()));
    result.putAll(
        findByParentCategories(
            categoryRepository.findByTitleContainingIgnoreCaseAndParentCategoryIsNull(
                title.trim())));
    return result;
  }

  private static Map<String, List<PreferredCategoryDto>> findBySubCategories(
      List<Category> subCategories) {
    return subCategories.stream()
        .collect(
            groupingBy(
                subCategory -> subCategory.getParentCategory().getTitle(),
                mapping(CategoryMapper::toPreferredCategoryDto, toList())));
  }

  private static Map<String, List<PreferredCategoryDto>> findByParentCategories(
      List<Category> parentCategories) {
    return parentCategories.stream()
        .collect(
            Collectors.toMap(
                Category::getTitle,
                category ->
                    CategoryMapper.toPreferredCategoryDtoList(category.getSubCategories())));
  }

  @Override
  public Category findById(Long categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public ParentAndSubCategoriesDto findAll() {
    return new ParentAndSubCategoriesDto(
        categoryRepository.findByParentCategoryIsNotNull().stream()
            .collect(
                groupingBy(
                    subCategory -> CategoryMapper.toCategoryDto(subCategory.getParentCategory()),
                    mapping(CategoryMapper::toCategoryDto, toList()))));
  }

  @Override
  public Category add(CreateCategoryRequest request) {
    String title = request.getTitle();
    String logoPath = fileStorageService.uploadFile(request.getImage());
    Long parentCategoryId = request.getParentCategoryId();
    if (parentCategoryId == null) {
      return categoryRepository.save(new Category(title, logoPath, null));
    }
    Category parent =
        categoryRepository.findById(parentCategoryId).orElseThrow(IllegalArgumentException::new);
    return categoryRepository.save(new Category(title, logoPath, parent));
  }

  @Override
  public Category updateData(Long categoryId, ModifyCategoryDataRequest request) {
    Category category =
        categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    String newTitle = request.getTitle();
    if (newTitle != null) {
      if (newTitle.isBlank()) {
        throw new IllegalArgumentException();
      }
      category.setTitle(newTitle);
    }
    Long newParentCategoryId = request.getParentCategoryId();
    if (newParentCategoryId != null) {
      Category newParent =
          categoryRepository
              .findById(newParentCategoryId)
              .orElseThrow(IllegalArgumentException::new);
      category.setParentCategory(newParent);
    }
    return categoryRepository.save(category);
  }

  @Override
  public Category updateImage(Long categoryId, ModifyCategoryImageRequest request) {
    Category category =
        categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    MultipartFile image = request.getImage();
    String oldImageName = category.getLogoPath().substring(57);
    category.setLogoPath(fileStorageService.uploadFile(image));
    fileStorageService.deleteFile(oldImageName);
    return categoryRepository.save(category);
  }

  @Override
  public void delete(Long categoryId, Locale locale) {
    Category category =
        categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    if (category.getParentCategoryId() == null) {
      throw new UnsupportedOperationException(
          messageSource.getMessage("category.delete.not.allowed", null, locale));
    }
    String oldFileName = category.getLogoPath().substring(57);
    fileStorageService.deleteFile(oldFileName);
    categoryRepository.deleteById(categoryId);
  }
}
