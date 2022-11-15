package mihai;

public class Util {
    public static String generateTransformations(String initialState, String finalState, char start, char end) {
        StringBuilder builder = new StringBuilder();
        for (char c = start; c <= end; c += 1) {
            builder.append(initialState)
                    .append(' ')
                    .append(c)
                    .append(' ')
                    .append(finalState)
                    .append('\n');
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateTransformations("q1", "q8", '0', '9'));
//        System.out.println(generateTransformations("q4", "q5", 'A', 'Z'));
//        System.out.println(generateTransformations("q4", "q5", '0', '9'));
    }
}
