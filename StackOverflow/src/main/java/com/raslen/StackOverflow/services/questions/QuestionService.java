package com.raslen.StackOverflow.services.questions;

import com.raslen.StackOverflow.dtos.QuestionDto;

public interface QuestionService {
    QuestionDto addQuestion(QuestionDto questionDTO);
}
