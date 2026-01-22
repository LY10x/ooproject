import java.util.List;

public interface Trainable {
    void train(String skill); // Учи го на нещо
    List<String> getTrainedSkills(); // Какво знае вече
    int getTrainingLevel(); // Колко е готино (1-10)
}
