package app.openschool.course;

import app.openschool.category.CategoryRepository;
import app.openschool.course.api.dto.CreateCourseRequest;
import app.openschool.course.api.dto.UpdateCourseRequest;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.keyword.KeywordRepository;
import app.openschool.course.language.LanguageRepository;
import app.openschool.course.module.Module;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import app.openschool.course.module.item.type.ModuleItemTypeRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final CategoryRepository categoryRepository;
  private final DifficultyRepository difficultyRepository;
  private final LanguageRepository languageRepository;
  private final KeywordRepository keywordRepository;
  private final UserRepository userRepository;
  private final ModuleItemTypeRepository moduleItemTypeRepository;

  public CourseServiceImpl(
      CourseRepository courseRepository,
      CategoryRepository categoryRepository,
      DifficultyRepository difficultyRepository,
      LanguageRepository languageRepository,
      KeywordRepository keywordRepository,
      UserRepository userRepository,
      ModuleItemTypeRepository moduleItemTypeRepository) {
    this.courseRepository = courseRepository;
    this.categoryRepository = categoryRepository;
    this.difficultyRepository = difficultyRepository;
    this.languageRepository = languageRepository;
    this.keywordRepository = keywordRepository;
    this.userRepository = userRepository;
    this.moduleItemTypeRepository = moduleItemTypeRepository;
  }

  @Override
  public Optional<Course> findCourseById(Long id) {
    return courseRepository.findById(id);
  }

  @Override
  public Page<Course> findAll(
      String courseTitle,
      List<Long> subCategoryIds,
      List<Long> languageIds,
      List<Long> difficultyIds,
      Pageable pageable) {
    return courseRepository.findAll(
        courseTitle, subCategoryIds, languageIds, difficultyIds, pageable);
  }

  @Override
  public Course add(CreateCourseRequest request) {
    Course course = new Course();
    course.setTitle(request.getTitle());
    course.setDescription(request.getDescription());
    course.setGoal(request.getGoal());
    course.setCategory(
        categoryRepository
            .findById(request.getCategoryId())
            .orElseThrow(IllegalArgumentException::new));
    course.setDifficulty(
        difficultyRepository
            .findById(request.getDifficultyId())
            .orElseThrow(IllegalArgumentException::new));
    course.setLanguage(
        languageRepository
            .findById(request.getLanguageId())
            .orElseThrow(IllegalArgumentException::new));
    course.setMentor(
        userRepository.findUserByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName()));
    Set<Keyword> keywords =
        request.getKeywordIds().stream()
            .map(
                keywordId ->
                    keywordRepository
                        .findById(keywordId)
                        .orElseThrow(IllegalArgumentException::new))
            .collect(Collectors.toSet());
    course.getKeywords().addAll(keywords);
    course.setModules(createModules(request.getCreateModuleRequests(), course));
    return courseRepository.save(course);
  }

  private Set<Module> createModules(Set<CreateModuleRequest> requests, Course course) {
    Set<Module> createdModules =
        requests.stream()
            .map(
                createModuleRequest -> {
                  Module module = new Module();
                  module.setTitle(createModuleRequest.getTitle());
                  module.setDescription(createModuleRequest.getDescription());
                  module.setCourse(course);
                  module.setModuleItems(
                      createModuleItems(createModuleRequest.getCreateModuleItemRequests(), module));
                  return module;
                })
            .collect(Collectors.toSet());
    Set<Module> modules = course.getModules();
    modules.addAll(createdModules);
    return modules;
  }

  private Set<ModuleItem> createModuleItems(Set<CreateModuleItemRequest> requests, Module module) {
    Set<ModuleItem> createdModuleItems =
        requests.stream()
            .map(
                createModuleItemRequest -> {
                  ModuleItem moduleItem = new ModuleItem();
                  moduleItem.setTitle(createModuleItemRequest.getTitle());
                  moduleItem.setEstimatedTime(createModuleItemRequest.getEstimatedTime());
                  moduleItem.setLink(createModuleItemRequest.getLink());
                  moduleItem.setModuleItemType(
                      moduleItemTypeRepository
                          .findById(createModuleItemRequest.getModuleItemTypeId())
                          .orElseThrow(IllegalArgumentException::new));
                  moduleItem.setModule(module);
                  return moduleItem;
                })
            .collect(Collectors.toSet());
    Set<ModuleItem> moduleItems = module.getModuleItems();
    moduleItems.addAll(createdModuleItems);
    return moduleItems;
  }

  @Override
  public Course update(Long courseId, UpdateCourseRequest request) {
    Course course = courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);
    User authenticatedUser =
        userRepository.findUserByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName());
    if (authenticatedUser.getRole().getType().equals("MENTOR")
        && !course.getMentor().getEmail().equals(authenticatedUser.getEmail())) {
      throw new IllegalArgumentException();
    }
    course.setTitle(request.getTitle());
    course.setDescription(request.getDescription());
    course.setGoal(request.getGoal());
    course.setCategory(
        categoryRepository
            .findById(request.getCategoryId())
            .orElseThrow(IllegalArgumentException::new));
    course.setDifficulty(
        difficultyRepository
            .findById(request.getDifficultyId())
            .orElseThrow(IllegalArgumentException::new));
    course.setLanguage(
        languageRepository
            .findById(request.getLanguageId())
            .orElseThrow(IllegalArgumentException::new));
    Set<Keyword> keywords =
        request.getKeywordIds().stream()
            .map(
                keywordId ->
                    keywordRepository
                        .findById(keywordId)
                        .orElseThrow(IllegalArgumentException::new))
            .collect(Collectors.toSet());
    course.setKeywords(keywords);
    return courseRepository.save(course);
  }

  @Override
  public void delete(Long courseId) {
    Course course = courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);
    User authenticatedUser =
        userRepository.findUserByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName());
    if (authenticatedUser.getRole().getType().equals("MENTOR")
        && !course.getMentor().getEmail().equals(authenticatedUser.getEmail())) {
      throw new IllegalArgumentException();
    }
    courseRepository.delete(course);
  }
}
