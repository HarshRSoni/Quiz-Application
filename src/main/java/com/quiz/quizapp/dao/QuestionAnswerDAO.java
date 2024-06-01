package com.quiz.quizapp.dao;
import org.springframework.stereotype.Repository;
import com.quiz.quizapp.model.QuestionAnswer;
import com.quiz.quizapp.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository

public class QuestionAnswerDAO {
    public List<QuestionAnswer> getQuestionsByCategoryAndDifficulty(int categoryId, String difficulty) {
        List<QuestionAnswer> questions = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM question WHERE category_id = ? AND difficulty = ?")) {
            stmt.setInt(1, categoryId);
            stmt.setString(2, difficulty);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                QuestionAnswer qa = new QuestionAnswer();
                qa.setId(rs.getInt("id"));
                qa.setCategoryId(rs.getInt("category_id"));
                qa.setQuestion(rs.getString("question_text"));
                qa.setOptionA(rs.getString("option_a"));
                qa.setOptionB(rs.getString("option_b"));
                qa.setOptionC(rs.getString("option_c"));
                qa.setOptionD(rs.getString("option_d"));
                qa.setAnswer(rs.getString("answer_text"));
                qa.setDifficulty(rs.getString("difficulty"));
                questions.add(qa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
    public boolean saveQuestion(QuestionAnswer questionAnswer) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO question (category_id, question_text, option_a, option_b, option_c, option_d, answer_text, difficulty) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setInt(1, questionAnswer.getCategoryId());
            stmt.setString(2, questionAnswer.getQuestion());
            stmt.setString(3, questionAnswer.getOptionA());
            stmt.setString(4, questionAnswer.getOptionB());
            stmt.setString(5, questionAnswer.getOptionC());
            stmt.setString(6, questionAnswer.getOptionD());
            stmt.setString(7, questionAnswer.getAnswer());
            stmt.setString(8, questionAnswer.getDifficulty());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}