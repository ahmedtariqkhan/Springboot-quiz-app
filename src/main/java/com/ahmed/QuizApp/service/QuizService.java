package com.ahmed.QuizApp.service;

import com.ahmed.QuizApp.dao.QuestionDao;
import com.ahmed.QuizApp.dao.QuizDao;
import com.ahmed.QuizApp.model.Question;
import com.ahmed.QuizApp.model.QuestionWrapper;
import com.ahmed.QuizApp.model.Quiz;
import com.ahmed.QuizApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for (Question q : questionsFromDB){
            QuestionWrapper qw = QuestionWrapper.builder()
                    .id(q.getId())
                    .questionTitle(q.getQuestionTitle())
                    .option1(q.getOption1())
                    .option2(q.getOption2())
                    .option3(q.getOption3())
                    .option4(q.getOption4())
                    .build();
            questionForUser.add(qw);
        }

        return new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int index = 0;

        for (Response response : responses){
            for (int i = 0; i < questions.size(); i++) {
                if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
                    right++;
                }
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
