package seedu.noknock.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.noknock.testutil.Assert.assertThrows;
import static seedu.noknock.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

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

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseWard_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWard(null));
    }

    @Test
    public void parseWard_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWard("")); // assuming empty is invalid
    }

    @Test
    public void parseWard_validValueWithoutWhitespace_returnsWard() throws Exception {
        Ward expectedWard = new Ward("1A");
        assertEquals(expectedWard, ParserUtil.parseWard("1A"));
    }

    @Test
    public void parseWard_validValueWithWhitespace_returnsTrimmedWard() throws Exception {
        String wardWithWhitespace = WHITESPACE + "3B" + WHITESPACE;
        Ward expectedWard = new Ward("3B");
        assertEquals(expectedWard, ParserUtil.parseWard(wardWithWhitespace));
    }

    @Test
    public void parseIC_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIC(null));
    }

    @Test
    public void parseIC_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIC("INVALID"));
    }

    @Test
    public void parseIC_validValueWithoutWhitespace_returnsIC() throws Exception {
        IC expectedIC = new IC("S1234567A");
        assertEquals(expectedIC, ParserUtil.parseIC("S1234567A"));
    }

    @Test
    public void parseIC_validValueWithWhitespace_returnsTrimmedIC() throws Exception {
        String icWithWhitespace = WHITESPACE + "S1234567A" + WHITESPACE;
        IC expectedIC = new IC("S1234567A");
        assertEquals(expectedIC, ParserUtil.parseIC(icWithWhitespace));
    }

    @Test
    public void parseIc_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIc(null));
    }

    @Test
    public void parseIc_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIc("INVALID"));
    }

    @Test
    public void parseIc_validValueWithoutWhitespace_returnsIC() throws Exception {
        IC expectedIC = new IC("S1234567A");
        assertEquals(expectedIC, ParserUtil.parseIc("S1234567A"));
    }

    @Test
    public void parseIc_validValueWithWhitespace_returnsTrimmedIC() throws Exception {
        String icWithWhitespace = WHITESPACE + "S1234567A" + WHITESPACE;
        IC expectedIC = new IC("S1234567A");
        assertEquals(expectedIC, ParserUtil.parseIc(icWithWhitespace));
    }

    @Test
    public void parseRelationship_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRelationship(null));
    }

    @Test
    public void parseRelationship_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRelationship("InvalidRelation"));
    }

    @Test
    public void parseRelationship_validValueWithoutWhitespace_returnsRelationship() throws Exception {
        Relationship expectedRelationship = Relationship.of("son");
        assertEquals(expectedRelationship, ParserUtil.parseRelationship("son"));
    }

    @Test
    public void parseRelationship_validValueWithWhitespace_returnsTrimmedRelationship() throws Exception {
        String relationshipWithWhitespace = WHITESPACE + "grandson" + WHITESPACE;
        Relationship expectedRelationship = Relationship.of("grandson");
        assertEquals(expectedRelationship, ParserUtil.parseRelationship(relationshipWithWhitespace));
    }

    @Test
    public void parseNote_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNote(null));
    }

    @Test
    public void parseNote_validValueWithoutWhitespace_returnsNote() throws Exception {
        Note expectedNote = new Note("Patient needs assistance");
        assertEquals(expectedNote, ParserUtil.parseNote("Patient needs assistance"));
    }

    @Test
    public void parseNote_validValueWithWhitespace_returnsTrimmedNote() throws Exception {
        String noteWithWhitespace = WHITESPACE + "Patient needs assistance" + WHITESPACE;
        Note expectedNote = new Note("Patient needs assistance");
        assertEquals(expectedNote, ParserUtil.parseNote(noteWithWhitespace));
    }

    @Test
    public void parseCareType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCareType(null));
    }

    @Test
    public void parseCareType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCareType(""));
    }

    @Test
    public void parseCareType_validValueWithoutWhitespace_returnsCareType() throws Exception {
        CareType expectedCareType = new CareType("medication");
        assertEquals(expectedCareType, ParserUtil.parseCareType("medication"));
    }

    @Test
    public void parseCareType_validValueWithWhitespace_returnsTrimmedCareType() throws Exception {
        String careTypeWithWhitespace = WHITESPACE + "medication" + WHITESPACE;
        CareType expectedCareType = new CareType("medication");
        assertEquals(expectedCareType, ParserUtil.parseCareType(careTypeWithWhitespace));
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime(null));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime("25:00"));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        Time expectedTime = new Time("14:30");
        assertEquals(expectedTime, ParserUtil.parseTime("14:30"));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + "14:30" + WHITESPACE;
        Time expectedTime = new Time("14:30");
        assertEquals(expectedTime, ParserUtil.parseTime(timeWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("2025-13-01"));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        seedu.noknock.model.date.Date expectedDate = new seedu.noknock.model.date.Date("2025-12-25");
        assertEquals(expectedDate, ParserUtil.parseDate("2025-12-25"));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + "2025-12-25" + WHITESPACE;
        seedu.noknock.model.date.Date expectedDate = new Date("2025-12-25");
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseSessionStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSessionStatus(null));
    }

    @Test
    public void parseSessionStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSessionStatus("invalid"));
    }

    @Test
    public void parseSessionStatus_validValueWithoutWhitespace_returnsSessionStatus() throws Exception {
        SessionStatus expectedStatus = SessionStatus.of("completed");
        assertEquals(expectedStatus, ParserUtil.parseSessionStatus("completed"));
    }

    @Test
    public void parseSessionStatus_validValueWithWhitespace_returnsTrimmedSessionStatus() throws Exception {
        String statusWithWhitespace = WHITESPACE + "completed" + WHITESPACE;
        SessionStatus expectedStatus = SessionStatus.of("completed");
        assertEquals(expectedStatus, ParserUtil.parseSessionStatus(statusWithWhitespace));
    }
}
