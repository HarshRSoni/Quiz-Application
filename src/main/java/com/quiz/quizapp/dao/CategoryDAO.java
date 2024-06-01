package com.quiz.quizapp.dao;

import com.quiz.quizapp.model.Category;
import com.quiz.quizapp.util.DatabaseUtil;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDAO {

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT id, name FROM Category");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                categories.add(new Category(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
}
