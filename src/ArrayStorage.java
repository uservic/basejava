import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        int indexForNewElement = size();
        storage[indexForNewElement] = r;
    }

    Resume get(String uuid) {
        Resume result = null;
        for (Resume resume: storage) {
            if ((resume != null) && (resume.uuid.equals(uuid))) {
                result = resume;
                break;
            }
        }
        return result;
    }

    void delete(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[size() - 1];
                storage[size() - 1] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size());
    }

    int size() {
        int size = 0;
        for (int i = 0; i < storage.length ; i++) {
            if (storage[i] != null) {
                size++;
            }
        }
        return size;
    }
}