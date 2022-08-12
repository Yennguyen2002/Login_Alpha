package com.example.alpha_firebase.springbootfirebasedemo.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

enum questionTypes{
    MultiChoice,
    MultiAnswerSelect,
    Matching,
    HoleFilling,
    TrueFalse
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    protected String id;
    protected String question;
    protected String belongTo;
    protected Date timestamp;
    protected questionTypes type;
    static Logger logger = LoggerFactory.getLogger(Question.class);
}

@NoArgsConstructor
class MultiChoice extends Question{

    private final ArrayList<String> answers = new ArrayList<>();
    private int correctAnswer;

    public int getCorrectAnswer(){
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) throws Exception{
        if (correctAnswer < 1 || correctAnswer > answers.size()){
            throw new Exception("invalid correct answer");
        }
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getAnswers(){
        return answers;
    }

    public MultiChoice(String id, String question, String belongTo, Date timestamp, ArrayList<String> answers,
                       int correctAnswer, questionTypes type){
        super(id, question, belongTo, timestamp, type);
        try{
            for (String answer : answers) {
                addAnswer(answer);
            }
            setCorrectAnswer(correctAnswer);
        }catch (Exception error){
            logger.error(error.getMessage());
        }
    }

    public void addAnswer(String answer){
        answers.add(answer);
    }

    public void deleteAnswer(String answer) throws Exception{
        if (!answers.contains(answer)){
            throw new Exception("answer does not exist");
        }
        answers.remove(answer);
    }
}

@NoArgsConstructor
class MultiAnswerSelect extends Question{
    private final ArrayList<String> answers = new ArrayList<>();
    private final ArrayList<Integer> correctAnswers = new ArrayList<>();

    public ArrayList<Integer> getCorrectAnswers(){
        return correctAnswers;
    }

    public void setCorrectAnswer(ArrayList<Integer> correctAnswers) throws Exception{
        for (int correctAnswer : correctAnswers) {
            if (correctAnswer < 1 || correctAnswer > answers.size()){
                throw new Exception("invalid correct answer");
            }
        }
        this.correctAnswers.addAll(correctAnswers);
    }

    public ArrayList<String> getAnswers(){
        return answers;
    }

    public MultiAnswerSelect(String id, String question, String belongTo, Date timestamp, ArrayList<String> answers,
                       ArrayList<Integer> correctAnswers, questionTypes type){
        super(id, question, belongTo, timestamp, type);
        try{
            for (String answer : answers) {
                addAnswer(answer);
            }
            setCorrectAnswer(correctAnswers);
        }catch (Exception error){
            logger.error(error.getMessage());
        }
    }

    public void addAnswer(String answer){
        answers.add(answer);
    }

    public void deleteAnswer(String answer) throws Exception{
        if (!answers.contains(answer)){
            throw new Exception("answer does not exist");
        }
        answers.remove(answer);
    }
}

@NoArgsConstructor
class Matching extends Question{
    private final HashMap<String, String> matchList = new HashMap<>();

    public void setMatchList(HashMap<String, String> matchList){
        for (Map.Entry<String, String> match : matchList.entrySet()) {
            addMatch(match);
        }
    }

    public HashMap<String, String> getMatchList(){
        return matchList;
    }

    public Matching(String id, String question, String belongTo, Date timestamp, Map<String, String> matchList,
                             questionTypes type){
        super(id, question, belongTo, timestamp, type);
        try{
            for (Map.Entry<String, String> match : matchList.entrySet()) {
                addMatch(match);
            }
        }catch (Exception error){
            logger.error(error.getMessage());
        }
    }

    public void addMatch(Map.Entry<String, String> match){
        matchList.put(match.getKey(), match.getValue());
    }

    public void deleteMatch(Map.Entry<String, String> match){
        matchList.remove(match.getKey());
    }
}

@NoArgsConstructor
class HoleFilling extends Question{
    private HashMap<String, String> answersList = new HashMap<>();

    public HashMap<String, String> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(HashMap<String, String> answersList){
        this.answersList = answersList;
    }

    public HoleFilling(String id, String question, String belongTo, Date timestamp, HashMap<String, String> answersList,
                       questionTypes type){
        super(id, question, belongTo, timestamp, type);
        setAnswersList(answersList);
    }
}

@NoArgsConstructor
class TrueFalse extends Question{
    private boolean answer;

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public TrueFalse(String id, String question, String belongTo, Date timestamp, Boolean answer,
                       questionTypes type){
        super(id, question, belongTo, timestamp, type);
        setAnswer(answer);
    }
}


