package models;

public class Question {
    private final long id;
    private final long idTest;
    private String text;
    private String variant1;
    private String variant2;
    private String variant3;
    private String variant4;
    private int correct;

    public Question(long id, long idTest, String text, String variant1, String variant2, String variant3, String variant4, int correct) {
        this.id = id;
        this.idTest = idTest;
        this.text = text;
        this.variant1 = variant1;
        this.variant2 = variant2;
        this.variant3 = variant3;
        this.variant4 = variant4;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public String getVariant1() {
        return variant1;
    }

    public String getVariant2() {
        return variant2;
    }

    public String getVariant3() {
        return variant3;
    }

    public String getVariant4() {
        return variant4;
    }

    public int getCorrect() {
        return correct;
    }

    public long getId() {
        return id;
    }

    public long getIdTest() {
        return idTest;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }

    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }

    public void setVariant3(String variant3) {
        this.variant3 = variant3;
    }

    public void setVariant4(String variant4) {
        this.variant4 = variant4;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }
}
