package app.openschool.course.module.quiz;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentRequestDto;
import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentResponseDto;
import app.openschool.course.module.quiz.api.dto.ModifyQuizDataRequest;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.api.mapper.EnrolledQuizAssessmentResponseMapper;
import app.openschool.course.module.quiz.api.mapper.QuizMapper;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.status.QuizStatus;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class QuizServiceImpl implements QuizService {

  private final ModuleRepository moduleRepository;
  private final QuizRepository quizRepository;
  private final EnrolledQuizRepository enrolledQuizRepository;
  private final MessageSource messageSource;

  public QuizServiceImpl(
      ModuleRepository moduleRepository,
      QuizRepository quizRepository,
      EnrolledQuizRepository enrolledQuizRepository,
      MessageSource messageSource) {
    this.moduleRepository = moduleRepository;
    this.quizRepository = quizRepository;
    this.enrolledQuizRepository = enrolledQuizRepository;
    this.messageSource = messageSource;
  }

  @Override
  public Optional<Quiz> createQuiz(Long moduleId, CreateQuizDto createQuizDto) {
    return moduleRepository
        .findById(moduleId)
        .map(
            module -> {
              checkIfTheModuleBelongsToCurrentMentor(module);
              Quiz quiz = QuizMapper.createQuizDtoToQuiz(createQuizDto, module);
              return quizRepository.save(quiz);
            });
  }

  @Override
  public Boolean deleteQuiz(Long quizId) {
    return quizRepository
        .findById(quizId)
        .map(
            quiz -> {
              checkIfTheQuizBelongsToCurrentMentor(quiz);
              quizRepository.delete(quiz);
              return true;
            })
        .orElse(false);
  }

  @Override
  public QuizDto findById(Long id) {
    return QuizMapper.quizToQuizDto(
        quizRepository.findById(id).orElseThrow(IllegalArgumentException::new));
  }

  @Override
  public Page<QuizDto> findAllByModuleId(Long id, Pageable pageable) {
    return QuizMapper.toQuizDtoPage(quizRepository.findAllByModuleId(id, pageable));
  }

  @Override
  @Transactional
  public Optional<EnrolledQuizAssessmentResponseDto> completeEnrolledQuiz(
      Long enrolledQuizId,
      EnrolledQuizAssessmentRequestDto enrolledQuizAssessmentRequestDto,
      Locale locale) {
    return enrolledQuizRepository
        .findById(enrolledQuizId)
        .map(enrolledQuiz -> completeQuiz(enrolledQuiz, enrolledQuizAssessmentRequestDto, locale));
  }

  @Override
  public Quiz updateQuiz(Long id, ModifyQuizDataRequest request) {
    Quiz quiz = quizRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    if (request.getTitle() != null) {
      quiz.setTitle(request.getTitle());
    }
    if (request.getTitle() != null) {
      quiz.setDescription(request.getDescription());
    }
    if (request.getPassingScore() != -1) {
      quiz.setPassingScore(request.getPassingScore());
    }
    if (request.getMaxGrade() != -1) {
      quiz.setMaxGrade(request.getMaxGrade());
    }

    return quizRepository.save(quiz);
  }

  private EnrolledQuizAssessmentResponseDto completeQuiz(
      EnrolledQuiz enrolledQuiz,
      EnrolledQuizAssessmentRequestDto enrolledQuizAssessmentRequestDto,
      Locale locale) {
    AtomicInteger rightAnswers =
        checkQuestionsAndReturnRightAnswersCount(
            enrolledQuiz.getQuiz(), enrolledQuizAssessmentRequestDto);
    String assessmentResult =
        getAssessmentResult(rightAnswers.get(), enrolledQuiz.getQuiz(), locale);
    updateEnrolledQuiz(rightAnswers.get(), enrolledQuiz);
    return EnrolledQuizAssessmentResponseMapper.toEnrolledQuizAssessmentResponseDto(
        rightAnswers.get(),
        assessmentResult,
        enrolledQuiz.getQuiz().getPassingScore(),
        enrolledQuiz.getQuiz().getMaxGrade());
  }

  private void updateEnrolledQuiz(int studentGrade, EnrolledQuiz enrolledQuiz) {
    enrolledQuiz.setStudentGrade(studentGrade);
    if (studentGrade >= enrolledQuiz.getQuiz().getPassingScore()) {
      enrolledQuiz.setQuizStatus(QuizStatus.isCompleted());
    } else {
      enrolledQuiz.setQuizStatus(QuizStatus.isFailed());
    }
    enrolledQuizRepository.save(enrolledQuiz);
  }

  private String getAssessmentResult(int rightAnswers, Quiz quiz, Locale locale) {
    return quiz.getPassingScore() <= rightAnswers
        ? messageSource.getMessage("quiz.completed", null, locale)
        : messageSource.getMessage("quiz.failed", null, locale);
  }

  private AtomicInteger checkQuestionsAndReturnRightAnswersCount(
      Quiz quiz, EnrolledQuizAssessmentRequestDto enrolledQuizAssessmentRequestDto) {
    AtomicInteger rightAnswers = new AtomicInteger(0);
    quiz.getQuestions()
        .forEach(
            question -> {
              if (checkQuestion(question, enrolledQuizAssessmentRequestDto)) {
                rightAnswers.getAndIncrement();
              }
            });
    return rightAnswers;
  }

  private boolean checkQuestion(
      Question question, EnrolledQuizAssessmentRequestDto enrolledQuizAssessmentRequestDto) {
    AtomicBoolean isRightAnswer = new AtomicBoolean(false);
    enrolledQuizAssessmentRequestDto
        .getQuestionWithChosenAnswerDtoSet()
        .forEach(
            questionWithChosenAnswerDto -> {
              if (Objects.equals(questionWithChosenAnswerDto.getQuestionId(), question.getId())) {
                isRightAnswer.set(
                    checkQuestionAnswers(
                        question, questionWithChosenAnswerDto.getChosenAnswersIds()));
              }
            });
    return isRightAnswer.get();
  }

  private boolean checkQuestionAnswers(Question question, List<Long> chosenAnswerIds) {
    AtomicInteger rightAnswers = new AtomicInteger();
    question
        .getAnswerOptions()
        .forEach(
            answerOption -> {
              if (chosenAnswerIds.contains(answerOption.getId()) && answerOption.isRightAnswer()) {
                rightAnswers.getAndIncrement();
              }
            });
    return rightAnswers.get() == question.getRightAnswersCount();
  }

  private void checkIfTheQuizBelongsToCurrentMentor(Quiz quiz) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!quiz.getModule().getCourse().getMentor().getEmail().equals(username)) {
      throw new IllegalArgumentException();
    }
  }

  private void checkIfTheModuleBelongsToCurrentMentor(Module module) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!module.getCourse().getMentor().getEmail().equals(username)) {
      throw new PermissionDeniedException("permission.denied");
    }
  }
}
