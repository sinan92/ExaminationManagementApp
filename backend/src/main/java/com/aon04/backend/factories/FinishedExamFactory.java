package com.aon04.backend.factories;


import com.aon04.backend.models.Exam;
import com.aon04.backend.models.FinishedExam;
import com.aon04.backend.models.User;

public class FinishedExamFactory {
    public static FinishedExam createFromFinishedExamInput(Exam examInput, User userInput, String fileInput, String hash) {
        FinishedExam finishedExam = new FinishedExam();

        finishedExam.setExam(examInput);
        finishedExam.setUser(userInput);
        finishedExam.setFinishedExam(fileInput);
        finishedExam.setHash(hash);

        return finishedExam;
    }
}