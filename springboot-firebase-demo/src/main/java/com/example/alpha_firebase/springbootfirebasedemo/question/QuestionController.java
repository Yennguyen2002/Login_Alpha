package com.example.alpha_firebase.springbootfirebasedemo.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class CreateRequest<Q>{
    private Q data;
    private String userId;
}

@AllArgsConstructor
@Getter
@Setter
class ResponseObject<T>{
    private String msg;
    private T data;
}

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/v1/question")
public class QuestionController {
    static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @PostMapping(path = "/create-multi-choice")
    public ResponseEntity<String> createMultiChoiceQuestion(
            @RequestBody CreateRequest<MultiChoice> request
    ){
        try {
            String jsonResponse = questionService.createMultiChoiceQuestion(request.getData(), request.getUserId());

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        }catch (Exception error){
            logger.warn(error.getMessage());
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/create-multi-answer-select")
    public ResponseEntity<String> createMultiAnswerSelectQuestion(
            @RequestBody CreateRequest<MultiAnswerSelect> request
    ){
        try {
            String jsonResponse = questionService.createMultiAnswerSelectQuestion(request.getData(), request.getUserId());

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        }catch (Exception error){
            logger.warn(error.getMessage());
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/create-matching")
    public ResponseEntity<String> createMatchingQuestion(
            @RequestBody CreateRequest<Matching> request
    ){
        try {
            String jsonResponse = questionService.createMatchingQuestion(request.getData(), request.getUserId());

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        }catch (Exception error){
            logger.warn(error.getMessage());
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/create-true-false")
    public ResponseEntity<String> createTrueFalseQuestion(
            @RequestBody CreateRequest<TrueFalse> request
    ){
        try {
            String jsonResponse = questionService.createTrueFalseQuestion(request.getData(), request.getUserId());

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        }catch (Exception error){
            logger.warn(error.getMessage());
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/create-hole-filling")
    public ResponseEntity<String> createHoleFillingQuestion(
            @RequestBody CreateRequest<HoleFilling> request
    ){
        try {
            String jsonResponse = questionService.createHoleFillingQuestion(request.getData(), request.getUserId());

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        }catch (Exception error){
            logger.warn(error.getMessage());
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<String> getAllQuestions(
            @RequestBody CreateRequest request
    ){
        try{
            String jsonResponse = questionService.getAllQuestions(request.getUserId());

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }catch (Exception error){
            logger.error(error.getMessage());
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteQuestion(
            @RequestBody CreateRequest<Question> request
    ){
        try{
            String jsonResponse = questionService.deleteQuestion(request.getUserId(), request.getData());

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }catch (Exception error){
            logger.error(error.getMessage());
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
