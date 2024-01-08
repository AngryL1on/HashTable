public class Main{
    public static void main(String[] args) {
        HashTable<String, String> hashTable = new HashTable<>();

        // Добавление элементов
        hashTable.add("ключ1", "значение1");
        hashTable.add("ключ2", "значение2");
        hashTable.add("ключ3", "значение3");

        // Демонстрация коллизии
        // Подберем ключи таким образом, чтобы они привели к одному и тому же слоту в хеш-таблице
        String key1 = "Aa";
        String key2 = "BB";
        // ключи 'Aa' и 'BB' имеют одинаковый хеш-код в Java, что приведет к коллизии
        hashTable.add(key1, "Коллизия1");
        hashTable.add(key2, "Коллизия2");

        // Поиск элементов
        System.out.println("Поиск 'ключ1': " + hashTable.get("ключ1"));
        System.out.println("Поиск '" + key1 + "': " + hashTable.get(key1));
        System.out.println("Поиск '" + key2 + "': " + hashTable.get(key2));

        // Удаление элемента
        hashTable.remove("ключ1");
        System.out.println("После удаления 'ключ1': " + hashTable.containsKey("ключ1"));

        // Итерация по ключам и значениям
        System.out.println("Ключи в хеш-таблице:");
        for (String key : hashTable.keys()) {
            System.out.println(key);
        }

        System.out.println("Значения в хеш-таблице:");
        for (String value : hashTable.values()) {
            System.out.println(value);
        }
    }
}