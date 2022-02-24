package y.w.datastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Data;

/**
 * File System
 */
public class FS {

    private final Node root = new Node("/", FSType.DIR);
    private Node current = root;

    enum FSType {DIR, FILE}

    @Data
    class Node {
        private String name;
        private FSType type;
        private StringBuilder storage;
        private Map<String, Node> nodes;

        public Node(String name, FSType type) {
            this.name = name;
            this.type = type;
            this.storage = null;
            this.nodes = null;

            switch (type) {
                case DIR:
                    nodes = new HashMap<>();
                    break;
                case FILE:
                    storage = new StringBuilder();
                    break;
                default:
                    throw new InvalidOperationException("INVALID TYPE");
            }
        }

        // Create a new file under current DIR node
        public Node newFile(String fileName) {
            Objects.requireNonNull(nodes);
            Objects.requireNonNull(fileName);

            if (fileName.length() == 0 || !fileName.matches("^[a-zA-Z0-9_\\.]+"))
                throw new InvalidOperationException("INVALID FILE NAME");

            if (nodes.containsKey(fileName))
                throw new InvalidOperationException("FILE EXISTS");

            nodes.put(fileName, new Node(fileName, FSType.FILE));
            return nodes.get(fileName);
        }

        public int writeFile(String content) {
            Objects.requireNonNull(storage);
            Objects.requireNonNull(content);

            storage.append(content);

            return content.length();
        }

        public String readFile() {
            Objects.requireNonNull(storage);

            return storage.toString();
        }
    }

    class InvalidOperationException extends RuntimeException {

        public InvalidOperationException(String message) {
            super(message);
        }
    }
}
