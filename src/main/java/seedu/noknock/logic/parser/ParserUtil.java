package seedu.noknock.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.commons.util.StringUtil;
import seedu.noknock.logic.parser.exceptions.ParseException;
import seedu.noknock.model.person.Address;
import seedu.noknock.model.person.Email;
import seedu.noknock.model.person.IC;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Phone;
import seedu.noknock.model.person.Ward;
import seedu.noknock.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * @param ward
     * @return
     * @throws ParseException
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
     * Parses a {@code String dateStr} into a {@code LocalDate}.
     * Acceptable formats: YYYY-MM-DD or DD-MM-YYYY.
     * The date must not be in the past.
     *
     * @param dateStr the string representing the date
     * @return a LocalDate object representing the parsed date
     * @throws ParseException if the date is in an invalid format or in the past
     */
    public static LocalDate parseDate(String dateStr) throws ParseException {
        requireNonNull(dateStr);
        String trimmed = dateStr.trim();
        LocalDate date = null;
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                date = LocalDate.parse(trimmed, formatter);
                break;
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }

        if (date == null) {
            throw new ParseException("Date must be in YYYY-MM-DD or DD-MM-YYYY format and not in the past");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new ParseException("Cannot schedule sessions in the past");
        }

        return date;
    }

    /**
     * Parses a {@code String timeStr} into a {@code LocalTime}.
     * Acceptable formats: 24-hour HH:MM or 12-hour H:MM[am/pm].
     *
     * @param timeStr the string representing the time
     * @return a LocalTime object representing the parsed time
     * @throws ParseException if the time is in an invalid format
     */
    public static LocalTime parseTime(String timeStr) throws ParseException {
        requireNonNull(timeStr);
        String trimmed = timeStr.trim().toLowerCase(Locale.ROOT);

        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("H:mm"),
                DateTimeFormatter.ofPattern("h:mma"),
                DateTimeFormatter.ofPattern("h:mm a")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalTime.parse(trimmed, formatter);
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }

        throw new ParseException("Time must be in HH:MM format or 12-hour format with am/pm");
    }

    /**
     * Parses a {@code String typeStr} into a valid care type string.
     * Must be 1-50 characters long, letters/numbers/spaces/hyphens only, stored in lowercase.
     *
     * @param typeStr the string representing the care type
     * @return a normalized lowercase string representing the care type
     * @throws ParseException if the typeStr is invalid
     */
    public static String parseType(String typeStr) throws ParseException {
        requireNonNull(typeStr);
        String trimmed = typeStr.trim();

        if (trimmed.length() < 1 || trimmed.length() > 50) {
            throw new ParseException("Care type must be 1-50 characters");
        }

        if (!trimmed.matches("[\\w\\s\\-]+")) {
            throw new ParseException("Care type can only contain letters, numbers, spaces, and hyphens");
        }

        return trimmed.toLowerCase(Locale.ROOT);
    }

}
