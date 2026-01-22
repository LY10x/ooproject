import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.io.Serializable;

class WaitingListEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private Adopter adopter;
    private AnimalPreferences preferences;
    private LocalDate registrationDate;

    public WaitingListEntry(Adopter adopter, AnimalPreferences preferences, LocalDate registrationDate) {
        this.adopter = adopter;
        this.preferences = preferences;
        this.registrationDate = registrationDate;
    }

    public Adopter getAdopter() { return adopter; }
    public AnimalPreferences getPreferences() { return preferences; }
    public LocalDate getRegistrationDate() { return registrationDate; }

    @Override
    public String toString() {
        return String.format("%s чака от %s - %s", 
            adopter.getName(), registrationDate, preferences);
    }
}

// WaitingList.java - Опашка на чакащи
public class WaitingList<T extends Animal> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Queue<WaitingListEntry> queue;

    public WaitingList() {
        this.queue = new LinkedList<>();
    }

    // Слагаме човек на чакане
    public void addToWaitingList(Adopter adopter, AnimalPreferences preferences) {
        WaitingListEntry entry = new WaitingListEntry(adopter, preferences, LocalDate.now());
        queue.offer(entry);
        System.out.println("✓ " + adopter.getName() + " е добавен в листа на чакане");
    }

    // Взимаме следващия от опашката
    public WaitingListEntry getNext() {
        return queue.poll(); // Връща и премахва първия
    }

    // Уведомяваме чакащите ако има животно което ги интересува
    public void notifyWaiters(T animal) {
        System.out.println("\n🔔 Уведомяване на чакащи за: " + animal.getName());
        
        for (WaitingListEntry entry : queue) {
            if (entry.getPreferences().matches(animal)) {
                System.out.println("  → " + entry.getAdopter().getName() + 
                    " (" + entry.getAdopter().getEmail() + ") - MATCH!");
            }
        }
    }

    // На каква позиция е човека в опашката
    public int getPosition(String adopterId) {
        int position = 1;
        for (WaitingListEntry entry : queue) {
            if (entry.getAdopter().getId().equals(adopterId)) {
                return position;
            }
            position++;
        }
        return -1; // Не е в листа
    }

    // Преглед на целия лист
    public void displayWaitingList() {
        if (queue.isEmpty()) {
            System.out.println("Листът на чакане е празен");
            return;
        }
        
        System.out.println("\n📋 Лист на чакане (" + queue.size() + " души):");
        int pos = 1;
        for (WaitingListEntry entry : queue) {
            System.out.println(pos + ". " + entry);
            pos++;
        }
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}