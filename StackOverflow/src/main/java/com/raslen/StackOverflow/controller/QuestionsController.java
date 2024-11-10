package com.raslen.StackOverflow.controller;


import com.raslen.StackOverflow.dtos.QuestionDto;
import com.raslen.StackOverflow.services.questions.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class QuestionsController {


    private final QuestionService questionService;

    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question")
    public ResponseEntity<?> postQuestion(@RequestBody QuestionDto questionDTO) {
     QuestionDto createdQuestionDto = questionService.addQuestion(questionDTO);
     if(createdQuestionDto ==null){
         return new ResponseEntity<>("something went wrong" , HttpStatus.BAD_REQUEST);
     }
     return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDto);
    }
}





