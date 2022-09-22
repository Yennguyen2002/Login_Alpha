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
    MultiAnswersSelect,
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
        if (correctAnswer < 0 || correctAnswer > answers.size()){
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
    protected ArrayList<String> answersA = new ArrayList<>();
    protected ArrayList<String> answersB = new ArrayList<>();
    public void setAnswersA(ArrayList<String> answersA){
        this.answersA = answersA;
    }
    public void setAnswersB(ArrayList<String> answersB){
        this.answersB = answersB;
    }
    public void setAnswers(ArrayList<String> answersA, ArrayList<String> answersB){
        setAnswersA(answersA);
        setAnswersB(answersB);
    }
    public ArrayList<String> getAnswersA(){
        return answersA;
    }
    public ArrayList<String> getAnswersB(){
        return answersB;
    }

    public Matching(String id, String question, String belongTo, Date timestamp,ArrayList<String> answersA,
                             ArrayList<String> answersB,questionTypes type){
        super(id, question, belongTo, timestamp, type);
        try{
            setAnswers(answersA, answersB);
        }catch (Exception error){
            logger.error(error.getMessage());
        }
    }

    public void addMatch(String answerA, String answerB){
        answersA.add(answerA);
        answersB.add(answerB);
    }

    public void deleteMatch(int index){
       answersA.remove(index);
       answersB.remove(index);
    }
}

@NoArgsConstructor
class HoleFilling extends Matching{
    private String fillingParagraph;

    public String getFillingParagraph() {
        return fillingParagraph;
    }

    public void setFillingParagraph(String fillingParagraph) {
        this.fillingParagraph = fillingParagraph;
    }

    public HoleFilling(String id, String question, String belongTo, Date timestamp, ArrayList<String> answersA,
                       ArrayList<String> answersB, questionTypes type, String fillingParagraph){
        super(id, question, belongTo, timestamp, answersA, answersB, type);
        setFillingParagraph(fillingParagraph);
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

@NoArgsConstructor
class CreateTopic extends Question {
    Scanner sc = new Scanner(System.in);
    ArrayList <Question> qs = new ArrayList<>();
    String ans;
    String newId;
    public void initailize (Question id){
        do{
            qs.add(id);
            if (id.getId().equals("0")){
                System.out.print("Do you stop? [y/n]: ");
                ans = sc.nextLine();
                if (ans.equals("y")){
                    return;
                }else{
                    newId = sc.nextLine();
                    id.setId(newId);
                }
            }
        }while(true);
    }
    public ArrayList random (int amount){
        ArrayList<Question> topic = new ArrayList<>();
        boolean check = true;
        for (int i = 0; i < amount; i++ ){;
            int index = (int)(Math.random()*qs.size());
            topic.add(qs.get(index));
        }
        return topic;
    }
}



