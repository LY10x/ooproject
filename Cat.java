import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cat extends Animal implements Adoptable {
    private boolean isIndoorOnly; // Само за вътре или може навън
    private boolean isLitterTrained; // Знае ли да си ходи на тоалетна

    public Cat(String id, String name, int age, String breed, Size size,
               Temperament temperament, LocalDate arrivalDate,
               boolean isIndoorOnly, boolean isLitterTrained) {
        super(id, name, age, breed, size, temperament, arrivalDate);
        this.isIndoorOnly = isIndoorOnly;
        this.isLitterTrained = isLitterTrained;
    }

    @Override
    public String makeSound() {
        return "Мяу мяу!";
    }

    @Override
    public String getSpecialNeeds() {
        StringBuilder needs = new StringBuilder();
        if (isIndoorOnly) {
            needs.append("Само за вътре в дома! ");
        }
        if (!isLitterTrained) {
            needs.append("Нужно обучение за тоалетна. ");
        }
        return needs.length() > 0 ? needs.toString() : "Няма специални нужди";
    }

    // Adoptable методи
    @Override
    public boolean isEligibleForAdoption() {
        return !isAdopted() && isLitterTrained; // Трябва да знае тоалетна
    }

    @Override
    public double getAdoptionFee() {
        double baseFee = 100.0;
        if (getAge() > 7) baseFee *= 0.7; // По-евтино за по-стари
        if (isLitterTrained) baseFee += 20;
        return baseFee;
    }

    @Override
    public List<String> getAdoptionRequirements() {
        List<String> reqs = new ArrayList<>();
        reqs.add("Котешка тоалетна");
        reqs.add("Драскало");
        if (isIndoorOnly) {
            reqs.add("Затворен дом без излизане навън");
        }
        return reqs;
    }

    // Getters & Setters
    public boolean isIndoorOnly() { return isIndoorOnly; }
    public void setIndoorOnly(boolean indoorOnly) { isIndoorOnly = indoorOnly; }
    
    public boolean isLitterTrained() { return isLitterTrained; }
    public void setLitterTrained(boolean litterTrained) { isLitterTrained = litterTrained; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | %s | Тоалетна: %s",
            isIndoorOnly ? "Само вътре" : "Може навън",
            isLitterTrained ? "Да" : "Не");
    }
}