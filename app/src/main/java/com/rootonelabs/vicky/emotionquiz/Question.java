package com.rootonelabs.vicky.emotionquiz;

public class Question {

    private String image;
    private String answer;

    public Question(String image, String answer) {
        this.image = image;
        this.answer = answer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
