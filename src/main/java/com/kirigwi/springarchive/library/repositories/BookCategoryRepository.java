package com.kirigwi.springarchive.library.repositories;

import com.kirigwi.springarchive.library.entities.BookCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookCategoryRepository {

    @Select("SELECT COUNT(*) FROM BookCategories WHERE name = #{name}")
    int countByName(@Param("name") String name);

    default void safeInsertCategory(BookCategory category) {
        if (countByName(category.getName()) == 0) {
            insertCategory(category);
        } else {
            System.out.println("Category '" + category.getName() + "' already exists. Skipping insert.");
        }
    }


    @Insert("INSERT INTO BookCategories (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertCategory(BookCategory category);


    @Select("SELECT * FROM BookCategories WHERE id = #{id}")
    BookCategory getCategoryById(@Param("id") Long id);

    @Select("SELECT * FROM BookCategories ORDER BY id OFFSET #{offset} ROWS FETCH NEXT #{size} ROWS ONLY")
    List<BookCategory> getCategoriesWithPagination(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM BookCategories")
    int getTotalCategories();

    // Retrieve Category by Name
    @Select("SELECT * FROM BookCategories WHERE name = #{name}")
    BookCategory findCategoryByName(String name);
}
