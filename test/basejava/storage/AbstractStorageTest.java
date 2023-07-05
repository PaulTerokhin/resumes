package basejava.storage;

import basejava.Config;
import basejava.TestData;
import basejava.exception.ExistStorageException;
import basejava.exception.NotExistStorageException;
import basejava.model.ContactType;
import basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(TestData.R1);
        storage.save(TestData.R2);
        storage.save(TestData.R3);
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void update() {
        Resume newResume = new Resume(TestData.UUID_1, "New Name");
        newResume.setContact(ContactType.MAIL, "mail1@google.com");
        newResume.setContact(ContactType.SKYPE, "NewSkype");
        newResume.setContact(ContactType.MOBILE, "+7 921 222-22-22");
        storage.update(newResume);
        assertEquals(newResume, storage.get(TestData.UUID_1));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        List<Resume> sortedResumes = Arrays.asList(TestData.R1, TestData.R2, TestData.R3);
        Collections.sort(sortedResumes);
        assertEquals(sortedResumes, list);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(TestData.R2));
    }

    @Test
    void save() {
        storage.save(TestData.R4);
        assertGet(TestData.R4);
        assertSize(4);
    }

    @Test
    void delete() {
        try {
            storage.delete(TestData.UUID_2);
            assertSize(2);
        } catch (NotExistStorageException e) {
            Assertions.fail("Unexpected NotExistStorageException during deleting existing resume");
        }

        assertThrows(NotExistStorageException.class, () -> storage.get(TestData.UUID_2));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void get() {
        assertGet(TestData.R1);
        assertGet(TestData.R2);
        assertGet(TestData.R3);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

}