
package adventOfCode.day22;

import java.util.*;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

public class Day22 {

    public int part1(List<String> lines) {
        List<Block> blocks = getBlocks(lines);

        var notDisintegrables = blocks
            .stream()
            .map(Block::notDisintegrables)
            .reduce(Set.of(), Utils::union);

        return blocks.size() - notDisintegrables.size();
    }

    private static List<List<Block>> getPile(List<Block> blocks) {
        List<List<Block>> pile = new ArrayList<>();

        for (var block: blocks) {
            fall(block, pile);
        }

        return pile;
    }

    private static void fall(Block block, List<List<Block>> pile) {
        for (int i = pile.size() - 1; i >= 0; i--) {
            List<Block> blockingBlocks = pile.get(i)
                .stream()
                .filter(b -> b.isBlocking(block))
                .toList();

            blockingBlocks.forEach(block::addSupportedBy);
            blockingBlocks.forEach(b -> b.addSupports(block));

            if (blockingBlocks.isEmpty()) {
                continue;
            }

            for (int j = i + 1; j < i + 1 + block.height(); j++) {
                if (j >= pile.size()) {
                    List<Block> row = new ArrayList<>();
                    row.add(block);
                    pile.add(row);
                } else {
                    var row = pile.get(j);
                    row.add(block);
                }
            }
            return;
        }

        for (int j = 0; j < block.height(); j++) {
            if (j >= pile.size()) {
                List<Block> row = new ArrayList<>();
                row.add(block);
                pile.add(row);
            } else {
                var row = pile.get(j);
                row.add(block);
            }
        }
    }

    public long part2(List<String> lines) {
        List<Block> blocks = getBlocks(lines);

        var notDisintegrables = blocks
            .stream()
            .map(Block::notDisintegrables)
            .reduce(Set.of(), Utils::union);

        return notDisintegrables
            .stream()
            .mapToLong(block -> block.disintegrates(getBlocks(lines)))
            .sum();
    }

    private static List<Block> getBlocks(List<String> lines) {
        List<Block> blocks = IntStream.range(0, lines.size())
                                      .mapToObj(i -> Block.from(
                                          i + 1,
                                          lines.get(i)
                                      ))
                                      .sorted(Comparator.comparing(block -> block
                                          .zs()
                                          .start()))
                                      .toList();
        var pile = getPile(blocks);
        return blocks;
    }
}

