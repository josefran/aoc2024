package aoc2024;

import java.util.*;
import java.util.stream.Collectors;

public class Day9 implements Day {
    @Override
    public long executePart1(String input) {
        DiskMap diskMap = DiskMap.create(input);
        DiskMap compressDisk = diskMap.compress();
        return compressDisk.checksum();
    }

    @Override
    public long executePart2(String input) {
        return -1;
    }

    record DiskMap(List<DiskFile> files, Set<Integer> emptyBlocks) {

        public static DiskMap create(String input) {
            int blockIndex = 0;
            int fileId = 0;
            List<DiskFile> files = new ArrayList<>();
            Set<Integer> emptyBlocks = new LinkedHashSet<>();
            for (int i = 0; i < input.length(); i += 2) {
                Set<Integer> blockIndexes = new LinkedHashSet<>();
                int fileSize = Integer.parseInt(String.valueOf(input.charAt(i)));
                for (int b = 0; b < fileSize; b++)
                     blockIndexes.add(blockIndex++);
                if (i + 1 < input.length()) {
                    int emptySpace = Integer.parseInt(String.valueOf(input.charAt(i+1)));
                    for (int b = 0; b < emptySpace; b++)
                        emptyBlocks.add(blockIndex++);
                }
                DiskFile file = new DiskFile(fileId++, blockIndexes);
                files.add(file);
            }

            return new DiskMap(files, emptyBlocks);
        }

        DiskMap compress() {
            Map<Integer, Integer> blockFileIdMap = files().stream()
                    .flatMap(file -> file.blocks().stream()
                            .map(blockIndex -> Map.entry(blockIndex, file.id())))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));

            Map<Integer, DiskFile> compressFiles = new LinkedHashMap<>();
            int blockIndex = 0;
            int lastBlockIndex = Collections.max(files.stream().flatMap(f -> f.blocks().stream()).toList());
            while (blockIndex <= lastBlockIndex) {
                if (!emptyBlocks.contains(blockIndex)) {
                    if (!blockFileIdMap.containsKey(blockIndex))
                        throw new RuntimeException("Block index not found: " + blockIndex);

                    Integer fileId = blockFileIdMap.get(blockIndex);
                    compressFiles.putIfAbsent(fileId, new DiskFile(fileId, new LinkedHashSet<>()));
                    compressFiles.get(fileId).addBlock(blockIndex);
                    blockIndex++;
                } else {
                    if (emptyBlocks.contains(lastBlockIndex)) {
                        lastBlockIndex--;
                    } else {
                        if (!blockFileIdMap.containsKey(lastBlockIndex))
                            throw new RuntimeException("Block index not found: " + lastBlockIndex);
                        Integer fileId = blockFileIdMap.get(lastBlockIndex);
                        compressFiles.putIfAbsent(fileId, new DiskFile(fileId, new LinkedHashSet<>()));
                        compressFiles.get(fileId).addBlock(blockIndex);
                        lastBlockIndex--;
                        blockIndex++;
                    }
                }
            }

            return new DiskMap(compressFiles.values().stream().toList(), Collections.emptySet());
        }

        int checksum() {
            return files.stream().mapToInt(DiskFile::checksum).sum();
        }

    }

    record DiskFile(int id, Set<Integer> blocks) {

        void addBlock(int blockIndex) {
            blocks.add(blockIndex);
        }

        int checksum() {
            return blocks.stream().mapToInt(blockIndex -> id * blockIndex).sum();
        }

    }


}
