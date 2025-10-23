package seedu.noknock.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.commons.util.StringUtil;
import seedu.noknock.logic.parser.exceptions.ParseException;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.person.Address;
import seedu.noknock.model.person.Email;
import seedu.noknock.model.person.IC;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Phone;
import seedu.noknock.model.person.Relationship;
import seedu.noknock.model.person.Ward;
import seedu.noknock.model.session.CareType;
import seedu.noknock.model.session.Note;
import seedu.noknock.model.session.SessionStatus;
import seedu.noknock.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses a {@code String ward} into an {@code Ward}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ward} is invalid.
     */
    public static Ward parseWard(String ward) throws ParseException {
        requireNonNull(ward);
        String trimmedWard = ward.trim();
        if (!Ward.isValidWard(trimmedWard)) {
            throw new ParseException(Ward.MESSAGE_CONSTRAINTS);
        }
        return new Ward(trimmedWard);
    }

    /**
     * Parses a {@code String ic} into an {@code IC}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ic} is invalid.
     */
    public static IC parseIC(String ic) throws ParseException {
        requireNonNull(ic);
        String trimmedIC = ic.trim();
        if (!IC.isValidIC(trimmedIC)) {
            throw new ParseException(IC.MESSAGE_CONSTRAINTS);
        }
        return new IC(trimmedIC);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String ic} into an {@code IC} without validation.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws NullPointerException if {@code ic} is null.
     */
    public static IC parseIc(String ic) throws ParseException {
        requireNonNull(ic);
        String trimmedIc = ic.trim();
        if (!IC.isValidIC(trimmedIc)) {
            throw new ParseException(IC.MESSAGE_CONSTRAINTS);
        }
        return new IC(trimmedIc);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String relationship} into a {@code Relationship}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code relationship} is invalid.
     */
    public static Relationship parseRelationship(String relationship) throws ParseException {
        requireNonNull(relationship);
        String trimmedRelationship = relationship.trim();
        if (!Relationship.isValidRelationship(trimmedRelationship)) {
            throw new ParseException(Relationship.MESSAGE_CONSTRAINTS);
        }
        return Relationship.of(trimmedRelationship);
    }

    /**
     * Parses a {@code String note} into a {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String trimmedNote = note.trim();
        if (!Note.isValidNote(trimmedNote)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(trimmedNote);
    }

    /**
     * Parses a {@code String careType} into an {@code CareType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code careType} is invalid.
     */
    public static CareType parseCareType(String careType) throws ParseException {
        requireNonNull(careType);
        String trimmedType = careType.trim();
        if (!CareType.isValidCareType(trimmedType)) {
            throw new ParseException(CareType.MESSAGE_CONSTRAINTS);
        }
        return new CareType(trimmedType);
    }

    /**
     * Parses a {@code String time} into an {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String sessionStatus} into an {@code SessionStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sessionStatus} is invalid.
     */
    public static SessionStatus parseSessionStatus(String sessionStatus) throws ParseException {
        requireNonNull(sessionStatus);
        String trimmedStatus = sessionStatus.trim();
        if (!SessionStatus.isValidSessionStatus(trimmedStatus)) {
            throw new ParseException(SessionStatus.MESSAGE_CONSTRAINTS);
        }
        return SessionStatus.of(trimmedStatus);
    }
}
