
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rabbit extends Animal implements Adoptable {
    private FurLength furLength; // Дълга/къса козина
    private String dietType; // Какво яде

    public Rabbit(String id, String name, int age, String breed, Size size,
                  Temperament temperament, LocalDate arrivalDate,
                  FurLength furLength, String dietType) {
        super(id, name, age, breed, size, temperament, arrivalDate);
        this.furLength = furLength;
        this.dietType = dietType;
    }

    @Override
    public String makeSound() {
        return "Тих звук (зайците са тихи)";
    }

    @Override
    public String getSpecialNeeds() {
        return String.format("Диета: %s. %s",
            dietType,
            furLength == FurLength.LONG ? "Нужно редовно четкане." : "Лесно поддържане.");
    }

    @Override
    public boolean isEligibleForAdoption() {
        return !isAdopted();
    }

    @Override
    public double getAdoptionFee() {
        return 60.0; // Евтини са
    }

    @Override
    public List<String> getAdoptionRequirements() {
        List<String> reqs = new ArrayList<>();
        reqs.add("Просторна клетка или огражден двор");
        reqs.add("Правилна диета: " + dietType);
        if (furLength == FurLength.LONG) {
            reqs.add("Редовно четкане");
        }
        return reqs;
    }

    public FurLength getFurLength() { return furLength; }
    public void setFurLength(FurLength furLength) { this.furLength = furLength; }
    
    public String getDietType() { return dietType; }
    public void setDietType(String dietType) { this.dietType = dietType; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Козина: %s | Диета: %s",
            furLength, dietType);
    }
}