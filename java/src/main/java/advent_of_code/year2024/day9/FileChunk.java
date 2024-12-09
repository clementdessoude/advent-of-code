package advent_of_code.year2024.day9;

class FileChunk implements Space {

    private final int fileId;

    FileChunk(int fileId) {
        this.fileId = fileId;
    }

    public int fileId() {
        return fileId;
    }
}
