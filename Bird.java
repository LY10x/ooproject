import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bird extends Animal implements Adoptable, Trainable {
    private boolean canTalk; // Може ли да бърбори
    private double wingSpan; // Колко широки са крилата (см)
    private List<String> trainedSkills;

    public Bird(String id, String name, int age, String breed, Size size,
                Temperament temperament, LocalDate arrivalDate,
                boolean canTalk, double wingSpan) {
        super(id, name, age, breed, size, temperament, arrivalDate);
        this.canTalk = canTalk;
        this.wingSpan = wingSpan;
        this.trainedSkills = new ArrayList<>();
    }

    @Override
    public String makeSound() {
        return canTalk ? "Здрасти! Как си?" : "Чуруликане!";
    }

    @Override
    public String getSpecialNeeds() {
        return String.format("Нужна е клетка мин. %.0f см. %s",
            wingSpan * 2,
            canTalk ? "Обича да общува." : "");
    }

    @Override
    public boolean isEligibleForAdoption() {
        return !isAdopted();
    }

    @Override
    public double getAdoptionFee() {
        double baseFee = 80.0;
        if (canTalk) baseFee += 100; // Говорящите струват повече
        return baseFee;
    }

    @Override
    public List<String> getAdoptionRequirements() {
        List<String> reqs = new ArrayList<>();
        reqs.add("Подходяща клетка");
        reqs.add("Време за внимание");
        if (canTalk) {
            reqs.add("Търпение за общуване");
        }
        return reqs;
    }

    @Override
    public void train(String skill) {
        if (!trainedSkills.contains(skill)) {
            trainedSkills.add(skill);
        }
    }

    @Override
    public List<String> getTrainedSkills() {
        return new ArrayList<>(trainedSkills);
    }

    @Override
    public int getTrainingLevel() {
        return Math.min(10, trainedSkills.size() + (canTalk ? 5 : 0));
    }

    public boolean canTalk() { return canTalk; }
    public void setCanTalk(boolean canTalk) { this.canTalk = canTalk; }
    
    public double getWingSpan() { return wingSpan; }
    public void setWingSpan(double wingSpan) { this.wingSpan = wingSpan; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Говори: %s | Размах: %.1f см",
            canTalk ? "Да" : "Не", wingSpan);
    }
}
