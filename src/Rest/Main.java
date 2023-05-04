package Rest;

public class Main {
    public static void main(String[] args) {
        String path = args[0];
        GameFlow gameFlow = new GameFlow(path, s -> System.out.println(s));
        gameFlow.run();
    }

}
