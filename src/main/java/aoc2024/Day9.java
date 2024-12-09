package aoc2024;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 implements Day {
    @Override
    public long executePart1(String input) {
        DiskMap diskMap = DiskMap.create(input);
        DiskMap compressDisk = diskMap.fragmentBlocks();
        return compressDisk.checksum();
    }

    @Override
    public long executePart2(String input) {
        DiskMap diskMap = DiskMap.create(input);
        DiskMap compressDisk = diskMap.fragmentFiles();
        return compressDisk.checksum();
    }

    record DiskMap(int diskSize, Map<Integer, DiskFile> files) {

        static DiskMap create(String input) {
            int blockIndex = 0;
            int fileId = 0;
            Map<Integer, DiskFile> files = new LinkedHashMap<>();
            int diskSize = 0;
            for (int i = 0; i < input.length(); i += 2) {
                Set<Integer> blockIndexes = new LinkedHashSet<>();
                int fileSize = Character.getNumericValue(input.charAt(i));
                diskSize += fileSize;
                for (int b = 0; b < fileSize; b++) {
                    blockIndexes.add(blockIndex++);
                }
                if (i + 1 < input.length()) {
                    int emptySpace = Character.getNumericValue(input.charAt(i + 1));
                    blockIndex += emptySpace;
                    diskSize += emptySpace;
                }
                DiskFile file = new DiskFile(fileId++, blockIndexes);
                files.put(file.id(), file);
            }
            return new DiskMap(diskSize, files);
        }

        DiskMap fragmentBlocks() {
            Map<Integer, Integer> blockFileIdMap = calculateBlockFileIdMap();
            Map<Integer, DiskFile> compressedFiles = new LinkedHashMap<>();
            int blockIndex = 0;
            int lastBlockIndex = Collections.max(files.values().stream()
                    .flatMap(f -> f.blocks().stream())
                    .toList());
            while (blockIndex <= lastBlockIndex) {
                if (blockFileIdMap.containsKey(blockIndex)) {
                    Integer fileId = blockFileIdMap.get(blockIndex);
                    compressedFiles
                            .computeIfAbsent(fileId, id -> new DiskFile(id, new LinkedHashSet<>()))
                            .addBlock(blockIndex);
                    blockIndex++;
                } else {
                    if (blockFileIdMap.containsKey(lastBlockIndex)) {
                        Integer fileId = blockFileIdMap.get(lastBlockIndex);
                        compressedFiles
                                .computeIfAbsent(fileId, id -> new DiskFile(id, new LinkedHashSet<>()))
                                .addBlock(blockIndex);
                        lastBlockIndex--;
                        blockIndex++;
                    } else {
                        lastBlockIndex--;
                    }
                }
            }
            return new DiskMap(diskSize, compressedFiles);
        }

        DiskMap fragmentFiles() {
            Map<Integer, DiskFile> reorderedFiles = new LinkedHashMap<>(files);
            SortedSet<Integer> fileIdsInReverseOrder = new TreeSet<>(files.keySet()).descendingSet();
            Set<EmptyBlock> emptyBlocks = calculateEmptyBlocks();
            for (EmptyBlock emptyBlock : emptyBlocks) {
                int startIndex = emptyBlock.index();
                for (Iterator<Integer> iterator = fileIdsInReverseOrder.iterator(); iterator.hasNext(); ) {
                    int fileId = iterator.next();
                    DiskFile file = files.get(fileId);
                    if (startIndex < file.firstBlock() && startIndex + file.length() <= emptyBlock.end()) {
                        Set<Integer> newBlocks = IntStream
                                .range(startIndex, startIndex + file.length())
                                .boxed()
                                .collect(Collectors.toCollection(LinkedHashSet::new));
                        reorderedFiles.put(fileId, new DiskFile(fileId, newBlocks));
                        iterator.remove();
                        startIndex += file.length();
                    }
                }
            }
            return new DiskMap(diskSize, reorderedFiles);
        }

        private Set<EmptyBlock> calculateEmptyBlocks() {
            Set<EmptyBlock> newEmptyBlocks = new LinkedHashSet<>();
            Set<Integer> fileBlocks = files.values().stream()
                    .flatMap(f -> f.blocks().stream())
                    .collect(Collectors.toSet());

            int blockIndex = 0;
            while (blockIndex < diskSize) {
                if (!fileBlocks.contains(blockIndex)) {
                    int start = blockIndex;
                    while (blockIndex < diskSize && !fileBlocks.contains(blockIndex)) {
                        blockIndex++;
                    }
                    newEmptyBlocks.add(new EmptyBlock(start, blockIndex - start));
                } else {
                    blockIndex++;
                }
            }
            return newEmptyBlocks;
        }

        Map<Integer, Integer> calculateBlockFileIdMap() {
            return files.values().stream()
                    .flatMap(file -> file.blocks().stream()
                            .map(blockIndex -> Map.entry(blockIndex, file.id())))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));
        }

        long checksum() {
            return files.values().stream().mapToLong(DiskFile::checksum).sum();
        }

        @Override
        public String toString() {
            StringBuilder diskMap = new StringBuilder();
            Map<Integer, Integer> blockFileIdMap = calculateBlockFileIdMap();
            for (int i = 0; i < diskSize; i++) {
                if (blockFileIdMap.containsKey(i)) diskMap.append(blockFileIdMap.get(i));
                else diskMap.append(".");
            }
            return diskMap.toString();
        }

    }

    record DiskFile(int id, Set<Integer> blocks) {

        void addBlock(int blockIndex) {
            blocks.add(blockIndex);
        }

        int firstBlock() {
            return Collections.min(blocks);
        }

        int length() {
            return blocks.size();
        }

        long checksum() {
            return blocks.stream().mapToLong(blockIndex -> (long) id * blockIndex).sum();
        }
    }

    record EmptyBlock(int index, int size) {
        int end() {
            return index + size;
        }
    }
}
