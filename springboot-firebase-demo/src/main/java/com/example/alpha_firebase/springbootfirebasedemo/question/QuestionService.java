package com.example.alpha_firebase.springbootfirebasedemo.question;

import com.example.alpha_firebase.springbootfirebasedemo.utils.FireBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class QuestionService{
    private static final Firestore db = FireBase.getDb();
    static Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private String createQuestion(Question question, String userId) throws Exception{
        DocumentReference newQuestionDoc = db.collection("users/"+userId+"/questions").document();

        DocumentReference newQuestionMetaDataDoc = db.collection("users/"+userId+"/questionsPatchs")
                .document(question.getBelongTo());

        //Set id lấy từ firebase
        question.setId(newQuestionDoc.getId());
        question.setTimestamp(new Date());

        //Quản lí meta data trong bằng 1 document để giảm lượt đọc database
        HashMap<String, Date> questionMetaData = new HashMap<>();
        questionMetaData.put(question.getId(), question.getTimestamp());
        newQuestionMetaDataDoc.set(questionMetaData, SetOptions.merge());

        newQuestionDoc.set(question);

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseObject<String> response = new ResponseObject<>("success", "success");
        return objectMapper.writeValueAsString(response);
    }

    public String createMultiChoiceQuestion(
            MultiChoice question, String userId
    ) throws Exception{
        return createQuestion(question, userId);
    }

    public String createMatchingQuestion(
            Matching question, String userId
    ) throws Exception{
        return createQuestion(question, userId);
    }

    public String createTrueFalseQuestion(
            TrueFalse question, String userId
    ) throws Exception{
        return createQuestion(question, userId);
    }

    public String createHoleFillingQuestion(
            HoleFilling question, String userId
    ) throws Exception{
        return createQuestion(question, userId);
    }

    public String createMultiAnswerSelectQuestion(
            MultiAnswerSelect question, String userId
    ) throws Exception{
        return createQuestion(question, userId);
    }

    public String getAllQuestions(
            String userId
    ) throws Exception{
        ApiFuture<QuerySnapshot> newQuestionSnapShot = db.collection("users").document(userId).collection("questions").get();
        ArrayList<Question> result = new ArrayList<>();
        for (QueryDocumentSnapshot document: newQuestionSnapShot.get().getDocuments()) {
            Map<String, Object> data = document.getData();
            String type = (String) data.get("type");
            switch (type) {
                case "MultiChoice" -> result.add(document.toObject(MultiChoice.class));
                case "Matching" -> result.add(document.toObject(Matching.class));
                case "MultiAnswerSelect" -> result.add(document.toObject(MultiAnswerSelect.class));
                case "TrueFalse" -> result.add(document.toObject(TrueFalse.class));
                case "HoleFilling" -> result.add(document.toObject(HoleFilling.class));
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseObject<ArrayList<Question>> response = new ResponseObject<>("success", result);
        return objectMapper.writeValueAsString(response);
    }

    public String getQuestionsMetaByPatch(
            String userId, String patchId
    ) throws Exception{
        Map<String, Object> quesitonsMeta = db.collection("users").document(userId)
                .collection("questionsPatchs").document(patchId).get().get().getData();


        ObjectMapper objectMapper = new ObjectMapper();
        ResponseObject<Map<String, Object>> response = new ResponseObject<>("success", quesitonsMeta);
        return objectMapper.writeValueAsString(response);
    }

    public String deleteQuestion(
            String userId, Question question
    ) throws Exception{
        DocumentReference questionRef = db.collection("users").document(userId).collection("questions")
                .document(question.getId());

        DocumentReference questionMetaDataDoc = db.collection("users/"+userId+"/questionsPatchs")
                .document(question.getBelongTo());

        questionRef.delete();
        Map<String, Object> questionMetaData = questionMetaDataDoc.get().get().getData();
        assert questionMetaData != null;
        if (questionMetaData.containsKey(question.getId())){
            questionMetaData.remove(question.getId());
            questionMetaDataDoc.set(questionMetaData);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseObject<String> response = new ResponseObject<>("success", "success");
        return objectMapper.writeValueAsString(response);
    }
}
