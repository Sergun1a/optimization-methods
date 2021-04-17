package sample;

public class Validator {
    public static boolean validate(String type, Object value) {
        if (type == null || value == null) {
            return false;
        }
        return switch (type) {
            case ("String"), ("string") -> StringValidator(value);
            case ("Integer"), ("integer"), ("int"), ("Int") -> IntegerValidator(value);
            case ("Double"), ("double") -> DoubleValidator(value);
            default -> false;
        };
    }

    private static boolean StringValidator(Object value) {
        return value instanceof String;
    }

    private static boolean IntegerValidator(Object value) {
        try {
            Integer.parseInt(value.toString());
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private static boolean DoubleValidator(Object value) {
        try {
            Double.parseDouble(value.toString());
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
