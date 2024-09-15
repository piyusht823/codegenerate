package com.example.codegen.repository;

import com.example.codegen.entity.GPTResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GPTResponseRepository extends JpaRepository<GPTResponse, Long> {
    // Custom query to get the maximum lines of code for each language
//    @Query("SELECT new com.example.codegen.repository.LanguageLineCount(g.language, MAX(g.totalLines)) FROM GPTResponse g GROUP BY g.language")
    @Query("SELECT new com.example.codegen.repository.LanguageLineCount(g.language, SUM(g.totalLines)) FROM GPTResponse g GROUP BY g.language")
    List<LanguageLineCount> findMaxLinesPerLanguage();

}
