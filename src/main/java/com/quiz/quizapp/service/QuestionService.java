package com.quiz.quizapp.service;

import com.quiz.quizapp.dao.QuestionAnswerDAO;
import com.quiz.quizapp.dto.QuestionAnswerDTO;
import com.quiz.quizapp.model.QuestionAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private QuestionAnswerDAO questionAnswerDAO;

    public boolean saveQuestion(QuestionAnswerDTO questionAnswerDTO) {
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setCategoryId(questionAnswerDTO.getCategoryId());
        questionAnswer.setQuestion(questionAnswerDTO.getQuestionText());
        questionAnswer.setOptionA(questionAnswerDTO.getOptionA());
        questionAnswer.setOptionB(questionAnswerDTO.getOptionB());
        questionAnswer.setOptionC(questionAnswerDTO.getOptionC());
        questionAnswer.setOptionD(questionAnswerDTO.getOptionD());
        questionAnswer.setAnswer(questionAnswerDTO.getAnswerText());
        questionAnswer.setDifficulty(questionAnswerDTO.getDifficulty());
        return questionAnswerDAO.saveQuestion(questionAnswer);
    }
}
