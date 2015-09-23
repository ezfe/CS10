public class Manager {
    public static void main(String[] args) {
        DigitalClock clock = new DigitalClock(23,57,55);
        System.out.println(clock);

        System.out.println("---");

        clock.set(23, 59, 5);
        System.out.println(clock);

        System.out.println("---");

        clock.run(23, 59, 55);

        System.out.println("---");

        clock.run();
    }
}
