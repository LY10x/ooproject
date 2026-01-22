import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Dog.java
public class Dog extends Animal implements Adoptable, Trainable {
    private boolean isHouseTrained; // Знае ли къде да си прави работата
    private int energyLevel; // 1-10, колко е луд
    private List<String> trainedSkills; // Какви номера знае

    public Dog(String id, String name, int age, String breed, Size size,
               Temperament temperament, LocalDate arrivalDate,
               boolean isHouseTrained, int energyLevel) {
        super(id, name, age, breed, size, temperament, arrivalDate);
        this.isHouseTrained = isHouseTrained;
        this.energyLevel = energyLevel;
        this.trainedSkills = new ArrayList<>();
    }

    @Override
    public String makeSound() {
        return "Бау бау!";
    }

    @Override
    public String getSpecialNeeds() {
        StringBuilder needs = new StringBuilder();
        if (energyLevel > 7) {
            needs.append("Много разходки! ");
        }
        if (!isHouseTrained) {
            needs.append("Трябва обучение за вкъщи. ");
        }
        return needs.length() > 0 ? needs.toString() : "Няма специални нужди";
    }

    // Adoptable методи
    @Override
    public boolean isEligibleForAdoption() {
        return !isAdopted() && getAge() >= 1; // Поне 1 година
    }

    @Override
    public double getAdoptionFee() {
        double baseFee = 150.0;
        if (getAge() > 7) baseFee *= 0.7; // Отстъпка за възрастни
        if (isHouseTrained) baseFee += 50; // Обучено струва повече
        return baseFee;
    }

    @Override
    public List<String> getAdoptionRequirements() {
        List<String> reqs = new ArrayList<>();
        reqs.add("Двор или редовни разходки");
        if (energyLevel > 7) {
            reqs.add("Активен начин на живот");
        }
        if (getSize() == Size.LARGE) {
            reqs.add("Къща с двор (не апартамент)");
        }
        return reqs;
    }

    // Trainable методи
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
        return Math.min(10, trainedSkills.size() + (isHouseTrained ? 3 : 0));
    }

    // Getters & Setters
    public boolean isHouseTrained() { return isHouseTrained; }
    public void setHouseTrained(boolean houseTrained) { isHouseTrained = houseTrained; }
    
    public int getEnergyLevel() { return energyLevel; }
    public void setEnergyLevel(int energyLevel) { this.energyLevel = energyLevel; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Енергия: %d/10 | Обучено: %s | Умения: %d",
            energyLevel, isHouseTrained ? "Да" : "Не", trainedSkills.size());
    }
}
