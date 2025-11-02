package seedu.noknock.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.noknock.commons.exceptions.IllegalValueException;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Phone;
import seedu.noknock.model.person.Relationship;

/**
 * Jackson-friendly version of {@link NextOfKin}.
 */
public class JsonAdaptedNextOfKin {
    private final String name;
    private final String phone;
    private final String relationship;

    /**
     * Constructs a {@code JsonAdaptedNextOfKin} with the given next-of-kin details.
     */
    @JsonCreator
    public JsonAdaptedNextOfKin(@JsonProperty("name") String name,
                                @JsonProperty("phone") String phone,
                                @JsonProperty("relationship") String relationship) {
        this.name = name;
        this.phone = phone;
        this.relationship = relationship;
    }

    /**
     * Converts a given {@code NextOfKin} into this class for Jackson use.
     */
    public JsonAdaptedNextOfKin(NextOfKin source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        relationship = source.getRelationship().toString();
    }

    /**
     * Converts this Jackson-friendly adapted next-of-kin object into the model's {@code NextOfKin} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted next-of-kin.
     */
    public NextOfKin toModelType() throws IllegalValueException {
        if (name == null || !Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null || !Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (!Relationship.isValidRelationship(relationship)) {
            throw new IllegalValueException(Relationship.MESSAGE_CONSTRAINTS);
        }
        final Relationship modelRelationship = Relationship.of(relationship);

        return new NextOfKin(modelName, modelPhone, modelRelationship);
    }
}
