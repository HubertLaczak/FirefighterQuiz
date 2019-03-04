package com.example.quiz.Others;

import android.os.Parcel;
import android.os.Parcelable;

public class ExQuestion implements Parcelable {
    private String number;
    private String question;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;
    private String wrongAnswer4;
    private String correctAnswer;


//    public ExQuestion(String number, String question, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3, String wrongAnswer4, String correctAnswer) {
//        this.number = number;
//        this.question = question;
//        this.wrongAnswer1 = wrongAnswer1;
//        this.wrongAnswer2 = wrongAnswer2;
//        this.wrongAnswer3 = wrongAnswer3;
//        this.wrongAnswer4 = wrongAnswer4;
//        this.correctAnswer = correctAnswer;
//    }

    protected ExQuestion(Parcel in) {
        number = in.readString();
        question = in.readString();
        wrongAnswer1 = in.readString();
        wrongAnswer2 = in.readString();
        wrongAnswer3 = in.readString();
        wrongAnswer4 = in.readString();
        correctAnswer = in.readString();
    }

    public static final Creator<ExQuestion> CREATOR = new Creator<ExQuestion>() {
        @Override
        public ExQuestion createFromParcel(Parcel in) {
            return new ExQuestion(in);
        }

        @Override
        public ExQuestion[] newArray(int size) {
            return new ExQuestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(number);
        dest.writeString(question);
        dest.writeString(wrongAnswer1);
        dest.writeString(wrongAnswer2);
        dest.writeString(wrongAnswer3);
        dest.writeString(wrongAnswer4);
        dest.writeString(correctAnswer);
    }

//    public String getNumber() {
//        return number;
//    }
//
//    public String getQuestion() {
//        return question;
//    }
//
//    public String getWrongAnswer1() {
//        return wrongAnswer1;
//    }
//
//    public String getWrongAnswer2() {
//        return wrongAnswer2;
//    }
//
//    public String getWrongAnswer3() {
//        return wrongAnswer3;
//    }
//
//    public String getWrongAnswer4() {
//        return wrongAnswer4;
//    }
//
//    public String getCorrectAnswer() {
//        return correctAnswer;
//    }
}
