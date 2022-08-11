package app.openschool.category;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.CreateOrModifyCategoryRequest;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.exception.ImageNotExistsException;
import app.openschool.category.api.exception.IncorrectCategoryTitleException;
import app.openschool.category.api.mapper.CategoryMapper;
import app.openschool.common.services.aws.S3Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final MessageSource messageSource;
  private final S3Service s3Service;
  private final Logger logger = LoggerFactory.getLogger("CategoryServiceImpl");

  public CategoryServiceImpl(
      CategoryRepository categoryRepository, MessageSource messageSource, S3Service s3Service) {
    this.categoryRepository = categoryRepository;
    this.messageSource = messageSource;
    this.s3Service = s3Service;
  }

  @Override
  public Page<CategoryDto> findAllParentCategories(Pageable pageable) {
    return CategoryMapper.toCategoryDtoPage(
        categoryRepository.findByParentCategoryIsNull(pageable));
  }

  @Override
  public Map<String, List<PreferredCategoryDto>> findCategoriesByTitle(String title) {

    Map<String, List<PreferredCategoryDto>> result;

    if (StringUtils.isBlank(title)) {
      result = findBySubCategories(categoryRepository.findByParentCategoryIsNotNull());
      result.putAll(findByParentCategories(categoryRepository.findByParentCategoryIsNull()));
      return result;
    }
    result =
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
  public Category add(String createCategoryRequest, MultipartFile file) {
    CreateOrModifyCategoryRequest request = new CreateOrModifyCategoryRequest();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      request = objectMapper.readValue(createCategoryRequest, CreateOrModifyCategoryRequest.class);
    } catch (IOException e) {
      logger.error("Error converting request");
    }
    String title = request.getTitle();
    if (StringUtils.isBlank(title) || title.length() > 100) {
      throw new IncorrectCategoryTitleException();
    }
    if (file == null) {
      throw new ImageNotExistsException();
    }
    String logoPath = s3Service.uploadFile(file);
    Long parentCategoryId = request.getParentCategoryId();
    if (parentCategoryId == null) {
      return categoryRepository.save(new Category(title, logoPath, null));
    }
    Category parent =
        categoryRepository.findById(parentCategoryId).orElseThrow(IllegalArgumentException::new);
    return categoryRepository.save(new Category(title, logoPath, parent));
  }

  @Override
  public Category modify(Long categoryId, String modifyCategoryRequest, MultipartFile file) {
    Category category =
        categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    if (modifyCategoryRequest != null) {
      CreateOrModifyCategoryRequest request = new CreateOrModifyCategoryRequest();
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        request =
            objectMapper.readValue(modifyCategoryRequest, CreateOrModifyCategoryRequest.class);
      } catch (IOException e) {
        logger.error("Error converting request");
      }
      String newTitle = request.getTitle();
      if (newTitle != null) {
        if (newTitle.trim().length() == 0 || newTitle.trim().length() > 100) {
          throw new IncorrectCategoryTitleException();
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
    }
    if (file != null) {
      String olvFileName = category.getLogoPath().substring(55);
      category.setLogoPath(s3Service.uploadFile(file));
      s3Service.deleteFile(olvFileName);
    }
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
    String olvFileName = category.getLogoPath().substring(55);
    s3Service.deleteFile(olvFileName);
    categoryRepository.deleteById(categoryId);
  }
}
