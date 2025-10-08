package seedu.noknock.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.noknock.commons.exceptions.DataLoadingException;
import seedu.noknock.model.ReadOnlyAddressBook;
import seedu.noknock.model.ReadOnlyUserPrefs;
import seedu.noknock.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
