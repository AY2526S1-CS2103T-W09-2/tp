package seedu.address.model.validation.field;


import seedu.address.model.validation.AbstractValidator;
import seedu.address.model.validation.ValidationResult;

/**
 * Validates wards to ensure they meet specific criteria.
 * A valid ward must start with a letter and can contain only letters and digits.
 */
public final class WardValidator extends AbstractValidator<String> {
    private static final String WARD_REGEX = "^[A-Za-z][0-9]+$";

    /**
     * Validates the given ward.
     *
     * @param ward The ward to validate.
     * @return A ValidationResult indicating whether the ward is valid or not.
     */
    @Override
    public ValidationResult validate(String ward) {
        if (ward == null || ward.trim().isEmpty() || !ward.matches(WARD_REGEX)) {
            return fail("ward", "Ward must start with a letter and contain only letters and digits");
        }
        return ok();
    }
}
