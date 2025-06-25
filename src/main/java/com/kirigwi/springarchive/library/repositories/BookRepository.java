package com.kirigwi.springarchive.library.repositories;

import com.kirigwi.springarchive.library.entities.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookRepository {

    @Insert("""
    MERGE INTO Books AS target
    USING (SELECT #{isbn} AS isbn) AS source
    ON target.isbn = source.isbn
    WHEN NOT MATCHED THEN
        INSERT (title, author, isbn, publishedDate, thumbnailUrl, language, categoryId, createdAt)
        VALUES (#{title}, #{author}, #{isbn}, #{publishedDate}, #{thumbnailUrl}, #{language}, #{category.id}, #{createdAt});
""")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertBook(Book book);


    @Select("SELECT * FROM Books WHERE id = #{id}")
    @Results({
            @Result(property = "category", column = "category_id",
                    one = @One(select = "com.kirigwi.springarchive.library.repositories.BookCategoryRepository.getCategoryById"))
    })
    Book getBookById(@Param("id") Long id);

    @Select("SELECT * FROM Books WHERE isbn = #{isbn}")
    @Results({
            @Result(property = "category", column = "category_id",
                    one = @One(select = "com.kirigwi.springarchive.library.repositories.BookCategoryRepository.getCategoryById"))
    })
    Book getBookByIsbn(@Param("isbn") String isbn);


    List<Book> getAllBooks(@Param("limit") int limit, @Param("offset") int offset);

}
