package com.example.quiz.Others;

public class ExChapter {

    private String chapterNumber;
    private String chapterName;
    private int correct;
    private int incorrect;
    private int remembered;

    public ExChapter(String chapterNumber, String chapterName, int correct, int incorrect, int remembered) {
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.correct = correct;
        this.incorrect = incorrect;
        this.remembered = remembered;
    }


    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getRemembered() {
        return remembered;
    }

    public void setRemembered(int remembered) {
        this.remembered = remembered;
    }
}
