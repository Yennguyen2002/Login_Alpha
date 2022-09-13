package com.example.alpha_firebase.springbootfirebasedemo.question;

import com.example.alpha_firebase.springbootfirebasedemo.utils.FireBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService{
    private static class PatchsList{
        private ArrayList<String> list = new ArrayList<>();

        public void setList(ArrayList<String> list) {
            this.list = list;
        }

        public ArrayList<String> getList() {
            return list;
        }
    }

    static Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private final FireBase fireBase;
    private final Firestore db;
    @Autowired
    public QuestionService(FireBase fireBase){
        this.fireBase = fireBase;
        this.db = fireBase.getDb();
    }

    /**
     * @Description - Lưu dữ liệu đầy đủ và metadata của câu hỏi vào database
     * @param question - đối tượng câu hỏi
     * @param userId - id người ra đề
     * @return
     * @throws Exception - một số Firebase exception
     */
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

    /**
     * @description - Biến đổi các hash map trong documentsnapshot thành các đối tượng
     * @param result - ArrayList chứa các đối tượng được trích xuất từ documentSnapshot
     * @param document - DocumentSnapshot cần được trích xuất
     * @param type - kiểu của câu hỏi
     */
    public void convertQuestion(ArrayList<Question> result,
                                    DocumentSnapshot document, String type){
        switch (type) {
            case "MultiChoice" -> result.add(document.toObject(MultiChoice.class));
            case "Matching" -> result.add(document.toObject(Matching.class));
            case "MultiAnswersSelect" -> result.add(document.toObject(MultiAnswerSelect.class));
            case "TrueFalse" -> result.add(document.toObject(TrueFalse.class));
            case "HoleFilling" -> result.add(document.toObject(HoleFilling.class));
        }
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
            convertQuestion(result, document, type);
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

    /**
     * @Description - lấy dữ liệu câu hỏi theo id câu hỏi và id người ra đề
     * @param questionId
     * @param userId
     * @return
     * @throws Exception - một số lỗi firebase
     */
    public String getQuestionById(String questionId, String userId) throws Exception{
        DocumentSnapshot document = db.collection("users").document(userId)
                .collection("questions").document(questionId).get().get();

        String type = (String) document.getData().get("type");
        ArrayList<Question> result = new ArrayList<>();
        convertQuestion(result, document, type);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(result.get(0));
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

    /**
     * @Description - Tạo 1 bộ câu hỏi
     * @param patchName - Tên bộ câu hỏi
     * @param userId - id người tạo
     */
    public String createQuestionsPatch(String patchName, String userId) throws Exception{
        DocumentReference document = db.collection("users").document(userId)
                .collection("questionsPatchs").document("patchsList");
        if (!document.get().get().exists()){
            HashMap<String, Object> data = new HashMap<>();
            ArrayList<String> newPatchList = new ArrayList<>();
            newPatchList.add(patchName);
            data.put("list",newPatchList);
            document.set(data);
        }

        document.update("list", FieldValue.arrayUnion(patchName));

        return "success";
    }

    /**
     * @Description - Lấy id của tất cả bộ câu hỏi của người dùng
     * @param userId
     * @return
     * @throws - một số lỗi firebase
     */
    public String getAllQuestionsPatchs(String userId) throws Exception{
        DocumentReference document = db.collection("users").document(userId)
                .collection("questionsPatchs").document("patchsList");

        PatchsList patchsList = document.get().get().toObject(PatchsList.class);
        ObjectMapper objectMapper = new ObjectMapper();
        if (patchsList == null){
            return "null";
        }

        String result = objectMapper.writeValueAsString(patchsList);
        return result;
    }
}
