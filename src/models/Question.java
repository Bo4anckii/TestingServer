package models;

public class Question {
    private final int id;
    private final int idTest;
    private final String text;
    private final String variant1;
    private final String variant2;
    private final String variant3;
    private final String variant4;
    private final int correct;

    public Question(int id, int idTest, String text, String variant1, String variant2, String variant3, String variant4, int correct) {
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

    public int getId() {
        return id;
    }

    public int getIdTest() {
        return idTest;
    }

}
