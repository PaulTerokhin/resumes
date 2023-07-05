package basejava.storage;

import basejava.Config;

import static org.junit.jupiter.api.Assertions.*;

class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}