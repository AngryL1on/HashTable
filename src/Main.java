public class Main{
    public static void main(String[] args) {
        HashTable<String, String> hashTable = new HashTable<>();

        // Adding elements
        hashTable.add("ключ1", "значение1");
        hashTable.add("ключ2", "значение2");
        hashTable.add("ключ3", "значение3");

        // Demonstration of collision
        // Select the keys so that they lead to the same slot in the hash table
        String key1 = "Aa";
        String key2 = "BB";
        // keys 'Aa' and 'BB' have the same hash code in Java which will result in a collision
        hashTable.add(key1, "Коллизия1");
        hashTable.add(key2, "Коллизия2");

        System.out.println("Поиск элементов:");
        System.out.println("Поиск 'ключ1': " + hashTable.get("ключ1"));
        System.out.println("Поиск '" + key1 + "': " + hashTable.get(key1));
        System.out.println("Поиск '" + key2 + "': " + hashTable.get(key2));

        System.out.println();

        System.out.println("Удаление элемента:");
        hashTable.remove("ключ1");
        System.out.println("После удаления 'ключ1': " + hashTable.containsKey("ключ1"));

        System.out.println();

        System.out.println("Итерация по ключам и значениям:");
        System.out.println("Ключи в хеш-таблице:");
        for (String key : hashTable.keys()) {
            System.out.println(key);
        }

        System.out.println();

        System.out.println("Значения в хеш-таблице:");
        for (String value : hashTable.values()) {
            System.out.println(value);
        }
    }
}
