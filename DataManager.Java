import java.io.*;

// DataManager.java - Запазване/зареждане на данни
public class DataManager {

    // Сериализация - запазваме обекта като файл
    public void saveToFile(String filename, Object data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(data);
            System.out.println("✓ Данните са запазени в " + filename);
        } catch (IOException e) {
            System.err.println("✗ Грешка при запазване: " + e.getMessage());
        }
    }

    // Десериализация - зареждаме обекта от файл
    public Object loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            System.out.println("✓ Данните са заредени от " + filename);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Грешка при зареждане: " + e.getMessage());
            return null;
        }
    }

    // Експорт в JSON - опростена версия БЕЗ библиотека
    public void exportToJSON(String filename, Object data) {
        System.out.println("JSON експорт не е наличен без Gson библиотека");
        System.out.println("Използвай saveToFile() вместо това");
    }

    // Импорт от JSON - опростена версия БЕЗ библиотека
    public <T> T importFromJSON(String filename, Class<T> type) {
        System.out.println("JSON импорт не е наличен без Gson библиотека");
        System.out.println("Използвай loadFromFile() вместо това");
        return null;
    }
}