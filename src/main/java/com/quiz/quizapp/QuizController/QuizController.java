package com.quiz.quizapp.QuizController;

import com.quiz.quizapp.dao.CategoryDAO;
import com.quiz.quizapp.dao.QuestionAnswerDAO;
import com.quiz.quizapp.dto.QuestionAnswerDTO;
import com.quiz.quizapp.model.Category;
import com.quiz.quizapp.model.QuestionAnswer;
import com.quiz.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizController {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private QuestionAnswerDAO questionAnswerDAO;

    @Autowired
    private QuestionService questionService;

    @CrossOrigin(origins = "*")
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryDAO.getAllCategories();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/questions")
    public List<QuestionAnswer> getQuestionsByCategoryAndDifficulty(
            @RequestParam int categoryIds,
            @RequestParam String difficulty) {
        return questionAnswerDAO.getQuestionsByCategoryAndDifficulty(categoryIds, difficulty);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/questions")
    public ResponseEntity<String> saveQuestion(@RequestBody QuestionAnswerDTO questionAnswerDTO) {
        try {
            boolean success = questionService.saveQuestion(questionAnswerDTO);
            if (success) {
                return ResponseEntity.ok("Question saved successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save question");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
