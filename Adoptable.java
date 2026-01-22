import java.util.List;

public interface Adoptable {
    boolean isEligibleForAdoption(); // Готово ли е за осиновяване
    double getAdoptionFee(); // Колко струва
    List<String> getAdoptionRequirements(); // Какво трябва да имаш
}

