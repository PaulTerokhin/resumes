package basejava.storage;

import basejava.exception.StorageException;
import basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void saveOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            Assertions.fail("Unexpected StorageException during saving");
        }

        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume("Linner")));
    }
}