package ua.edu.ukma.json.app;

public class Main {
    public static void main(String[] args) {
        UserDto myUserDto = new UserDto("Vadim", 20, "v.yashchenko@ukma.edu.ua");

        String json = JsonSerializer.serialize(myUserDto);

        System.out.println("Результат роботи JSON-Трансформера:");
        System.out.println(json);
    }
}