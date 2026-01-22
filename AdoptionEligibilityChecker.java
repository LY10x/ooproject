import java.util.ArrayList;
import java.util.List;

// AdoptionEligibilityChecker.java - Проверява дали може да осинови
public class AdoptionEligibilityChecker {

    // Главна проверка - може ли да го вземе
    public static boolean checkEligibility(Adopter adopter, Animal animal) {
        return getIneligibilityReasons(adopter, animal).isEmpty();
    }

    // Всички причини защо НЕ може да го осинови
    public static List<String> getIneligibilityReasons(Adopter adopter, Animal animal) {
        List<String> reasons = new ArrayList<>();

        // Вече е осиновено
        if (animal.isAdopted()) {
            reasons.add("Животното вече е осиновено");
            return reasons; // Стига дотук
        }

        // Проверка за кучета
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal;
            
            // Голямо куче в апартамент? НЕ!
            if (dog.getSize() == Size.LARGE && 
                adopter.getHousingType() == HousingType.APARTMENT) {
                reasons.add("Големи кучета не могат в апартамент");
            }
            
            // Много енергично куче без двор? НЕ!
            if (dog.getEnergyLevel() > 7 && !adopter.hasYard() &&
                adopter.getHousingType() != HousingType.FARM) {
                reasons.add("Енергични кучета се нуждаят от двор");
            }
        }

        // Проверка за котки
        if (animal instanceof Cat) {
            Cat cat = (Cat) animal;
            
            // Ако котката е само за вътре и има други животни навън
            if (cat.isIndoorOnly() && adopter.hasOtherPets() && adopter.hasYard()) {
                reasons.add("Котката е само за вътре - може да има конфликт");
            }
        }

        // Проверка за птици
        if (animal instanceof Bird) {
            Bird bird = (Bird) animal;
            
            // Големи птици трябват пространство
            if (bird.getWingSpan() > 50 && 
                adopter.getHousingType() == HousingType.APARTMENT) {
                reasons.add("Голяма птица нужна просторна къща");
            }
        }

        // Проверка за зайци
        if (animal instanceof Rabbit) {
            // Зайците са ок за всички
            if (adopter.getHousingType() == HousingType.APARTMENT && 
                !adopter.hasYard()) {
                reasons.add("Препоръчваме място за движение на зайчето");
            }
        }

        // Проверка за Adoptable
        if (animal instanceof Adoptable) {
            Adoptable adoptable = (Adoptable) animal;
            if (!adoptable.isEligibleForAdoption()) {
                reasons.add("Животното все още не е готово за осиновяване");
            }
        }

        return reasons;
    }
}