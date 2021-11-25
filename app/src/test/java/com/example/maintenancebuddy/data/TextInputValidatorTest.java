package com.example.maintenancebuddy.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TextInputValidatorTest {

    @Test
    void testValidateRequiredFieldOnValidStringSuccess() {
        assertTrue(TextInputValidator.validateRequiredField("valid"));
    }

    @Test
    void testValidateRequiredFieldOnNullStringFails() {
        assertFalse(TextInputValidator.validateRequiredField(null));
    }

    @Test
    void testValidateRequiredFieldOnEmptyStringFails() {
        assertFalse(TextInputValidator.validateRequiredField(""));
    }

    @Test
    void testValidateEmailFieldOnValidEmailSuccess() {
        assertTrue(TextInputValidator.validateEmailField("valid@email.com"));
    }

    @Test
    void testValidateEmailFieldOnEmailMissingDotComFails() {
        assertFalse(TextInputValidator.validateEmailField("invalid@email"));
    }

    @Test
    void testValidateEmailFieldOnEmailMissingDomainFails() {
        assertFalse(TextInputValidator.validateEmailField("invalid@.com"));
    }

    @Test
    void testValidateEmailFieldOnNullStringFails() {
        assertFalse(TextInputValidator.validateEmailField(null));
    }

    @Test
    void testValidateEmailFieldOnEmptyStringFails() {
        assertFalse(TextInputValidator.validateEmailField(""));
    }

    @Test
    void testValidateEmailFieldOnEmailNoAtFails() {
        assertFalse(TextInputValidator.validateEmailField("invalid email.com"));
    }

    @Test
    void testValidateEmailFieldOnDoubleEmailFails() {
        assertFalse(TextInputValidator.validateEmailField("one@email.com@email.com"));
        assertFalse(TextInputValidator.validateEmailField("one@email.com two@email.com"));
    }

    @Test
    void testValidatePasswordFieldValidShortPasswordNoLength() {
        assertTrue(TextInputValidator.validatePasswordField("short", false));
    }

    @Test
    void testValidatePasswordFieldValidShortPasswordWithLengthFails() {
        assertFalse(TextInputValidator.validatePasswordField("short", true));
    }

    @Test
    void testValidatePasswordFieldValidLongPasswordNoLengthSuccess() {
        assertTrue(TextInputValidator.validatePasswordField("longpassword", false));
    }

    @Test
    void testValidatePasswordFieldValidLongPasswordWithLengthSuccess() {
        assertTrue(TextInputValidator.validatePasswordField("longpassword", true));
    }

    @Test
    void testValidatePasswordFieldValidNullFails() {
        assertFalse(TextInputValidator.validatePasswordField(null, true));
    }

    @Test
    void testValidatePasswordFieldValidEmptyFails() {
        assertFalse(TextInputValidator.validatePasswordField("", true));
    }
}