package com.aon04.backend.factories;


import com.aon04.backend.models.Exam;

public class ExamFactory {
    public static Exam create(String naam, String skelet) {
        Exam exam = new Exam();

        exam.setNaam(naam);
        exam.setSkelet(skelet);

        return exam;
    }
}
