package assertions;

import assertions.conditions.MessageConditions;
import assertions.conditions.StatusCodeConditions;

public class Conditions {
    public static MessageConditions hasMessage(String expectedMessage) {
        return new MessageConditions(expectedMessage);
    }

    public static StatusCodeConditions hasStatusCode(Integer expectedStatusCode) {
        return new StatusCodeConditions(expectedStatusCode);
    }
}
