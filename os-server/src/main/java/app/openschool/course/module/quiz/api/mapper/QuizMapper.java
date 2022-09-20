package app.openschool.course.module.quiz.api.mapper;

import app.openschool.course.module.Module;
import app.openschool.course.module.quiz.EnrolledQuiz;
import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import app.openschool.course.module.quiz.api.dto.EnrolledQuizDto;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.answer.api.dto.AnswerOptionDto;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import app.openschool.course.module.quiz.question.api.mapper.QuestionMapper;
import java.util.Set;
import java.util.stream.Collectors;

public final class QuizMapper {

  private static final String MULTIPLE_CHOICE = "MULTIPLE_CHOICE";

  private QuizMapper() {}

  public static Quiz createQuizDtoToQuiz(CreateQuizDto createQuizDto, Module module) {
    Quiz quiz = Quiz.getInstance();
    quiz.setMaxGrade(createQuizDto.getMaxGrade());
    quiz.setPassingScore(createQuizDto.getPassingScore());
    quiz.setQuestions(createQuestionDtoToQuestions(createQuizDto.getQuestions(), quiz));
    quiz.setModule(module);
    return quiz;
  }

  public static QuizDto quizToQuizDto(Quiz quiz) {
    QuizDto quizDto = QuizDto.getInstance();
    quizDto.setId(quiz.getId());
    quizDto.setModuleId(quiz.getModule().getId());
    quizDto.setMaxGrade(quiz.getMaxGrade());
    quizDto.setPassingScore(quiz.getPassingScore());
    quizDto.setQuestions(questionsToQuestionDto(quiz.getQuestions()));
    return quizDto;
  }

  public static Set<EnrolledQuizDto> enrolledQuizzesToEnrolledQuizDtoSet(
      Set<EnrolledQuiz> enrolledQuiz) {
    return enrolledQuiz.stream().map(QuizMapper::toEnrolledQuizDtoSet).collect(Collectors.toSet());
  }

  private static Set<Question> createQuestionDtoToQuestions(
      Set<CreateQuestionDto> questions, Quiz quiz) {
    return questions.stream()
        .map(
            createQuestionDto ->
                QuestionMapper.createQuestionDtoToQuestion(createQuestionDto, quiz))
        .collect(Collectors.toSet());
  }

  private static Set<QuestionDto> questionsToQuestionDto(Set<Question> questions) {
    return questions.stream()
        .map(
            question -> {
              QuestionDto questionDto = QuestionDto.getInstance();
              questionDto.setId(question.getId());
              questionDto.setQuestion(question.getQuestion());
              questionDto.setRightAnswersCount(question.getRightAnswersCount());
              questionDto.setQuestionType(question.getQuestionType().getType());
              questionDto.setAnswerOptions(
                  answerOptionToAnswerOptionDto(question.getAnswerOptions()));
              return questionDto;
            })
        .collect(Collectors.toSet());
  }

  private static Set<AnswerOptionDto> answerOptionToAnswerOptionDto(
      Set<AnswerOption> answerOptions) {
    return answerOptions.stream()
        .map(
            answerOption -> {
              AnswerOptionDto answerOptionDto = AnswerOptionDto.getInstance();
              answerOptionDto.setId(answerOption.getId());
              answerOptionDto.setAnswerOption(answerOption.getAnswerOption());
              answerOptionDto.setRightAnswer(answerOption.isRightAnswer());
              return answerOptionDto;
            })
        .collect(Collectors.toSet());
  }

  private static EnrolledQuizDto toEnrolledQuizDtoSet(EnrolledQuiz enrolledQuiz) {
    EnrolledQuizDto enrolledQuizDto = EnrolledQuizDto.getInstance();
    enrolledQuizDto.setId(enrolledQuiz.getId());
    enrolledQuizDto.setEnrolledModuleId(enrolledQuiz.getEnrolledModule().getId());
    enrolledQuizDto.setMaxGrade(enrolledQuiz.getQuiz().getMaxGrade());
    enrolledQuizDto.setStudentGrade(enrolledQuiz.getStudentGrade());
    enrolledQuizDto.setPassingScore(enrolledQuiz.getQuiz().getPassingScore());
    enrolledQuizDto.setQuizStatus(enrolledQuiz.getQuizStatus().getType());
    enrolledQuizDto.setQuestions(questionsToQuestionDto(enrolledQuiz.getQuiz().getQuestions()));
    return enrolledQuizDto;
  }
}
