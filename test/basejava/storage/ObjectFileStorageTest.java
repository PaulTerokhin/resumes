package basejava.storage;

import basejava.storage.serializer.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }

}