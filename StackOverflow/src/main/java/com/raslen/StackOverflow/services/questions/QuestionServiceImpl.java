package com.raslen.StackOverflow.services.questions;

import com.raslen.StackOverflow.dtos.QuestionDto;
import com.raslen.StackOverflow.entities.Questions;
import com.raslen.StackOverflow.entities.User;
import com.raslen.StackOverflow.repositories.QuestionRepository;
import com.raslen.StackOverflow.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }



    @Override
    public QuestionDto addQuestion(QuestionDto questionDTO) {
        Optional<User> optional = userRepository.findById(questionDTO.getUserId());
        if (optional.isPresent()){
            Questions question = new Questions();
            question.setTitle(questionDTO.getTitle());
            question.setBody(questionDTO.getBody());
            question.setTags(questionDTO.getTags());
            question.setCreateDate(new Date());

            question.setUser(optional.get());

            Questions createdQuestion = questionRepository.save(question);

            QuestionDto createdQuestionDto = new QuestionDto();
            createdQuestionDto.setId(createdQuestionDto.getId());
            createdQuestionDto.setTitle(createdQuestionDto.getTitle());
            return createdQuestionDto;
        }
        return null;
    }
}
