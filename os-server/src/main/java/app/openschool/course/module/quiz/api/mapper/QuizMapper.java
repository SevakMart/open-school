package app.openschool.course.module.quiz.api.mapper;

import app.openschool.course.module.Module;
import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.answer.api.dto.AnswerOptionDto;
import app.openschool.course.module.quiz.question.answer.api.dto.CreateAnswerOptionDto;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import app.openschool.course.module.quiz.question.type.QuestionType;
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
    quizDto.setStudentGrade(quiz.getStudentGrade());
    quizDto.setPassingScore(quiz.getPassingScore());
    quizDto.setQuestions(questionsToQuestionDto(quiz.getQuestions()));
    return quizDto;
  }

  private static Set<Question> createQuestionDtoToQuestions(
      Set<CreateQuestionDto> questions, Quiz quiz) {
    return questions.stream()
        .map(
            createQuestionDto -> {
              Question question = Question.getInstance();
              question.setQuestion(createQuestionDto.getQuestion());
              question.setAnswerOptions(
                  createAnswerOptionDtoToAnswerOptions(
                      createQuestionDto.getAnswerOptions(), question));
              question.setQuestionType(createQuestionType(createQuestionDto.getQuestionType()));
              question.setQuiz(quiz);
              return question;
            })
        .collect(Collectors.toSet());
  }

  private static Set<AnswerOption> createAnswerOptionDtoToAnswerOptions(
      Set<CreateAnswerOptionDto> answerOptions, Question question) {
    return answerOptions.stream()
        .map(
            createAnswerOptionDto -> {
              AnswerOption answerOption = AnswerOption.getInstance();
              answerOption.setAnswerOption(createAnswerOptionDto.getAnswerOption());
              answerOption.setRightAnswer(createAnswerOptionDto.getRightAnswer());
              answerOption.setQuestion(question);
              return answerOption;
            })
        .collect(Collectors.toSet());
  }

  private static QuestionType createQuestionType(String questionType) {
    return questionType.equals(MULTIPLE_CHOICE)
        ? QuestionType.isMultipleChoice()
        : QuestionType.isMatching();
  }

  private static Set<QuestionDto> questionsToQuestionDto(Set<Question> questions) {
    return questions.stream()
        .map(
            question -> {
              QuestionDto questionDto = QuestionDto.getInstance();
              questionDto.setId(question.getId());
              questionDto.setQuestion(question.getQuestion());
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
}
