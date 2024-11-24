package advent_of_code.year2023.day22;

import java.util.*;
import java.util.stream.Collectors;

final class Block {
    private final int id;
    private final Range xs;
    private final Range ys;
    private final Range zs;
    private final Set<Block> supportedBy;
    private final Set<Block> supports;

    public Block(
        int id,
        Range xs,
        Range ys,
        Range zs,
        Set<Block> supportedBy,
        Set<Block> supports
    ) {
        this.id = id;
        this.xs = xs;
        this.ys = ys;
        this.zs = zs;
        this.supportedBy = supportedBy;
        this.supports = supports;
    }

    private Block(
            int id,
            Range xs,
            Range ys,
            Range zs
    ) {
        this.id = id;
        this.xs = xs;
        this.ys = ys;
        this.zs = zs;
        this.supportedBy = new HashSet<>();
        this.supports = new HashSet<>();
    }

    public static Block from(int idx, String row) {
        var split = row.split("~");
        var start = Arrays
                .stream(split[0].split(","))
                .map(Integer::parseInt)
                .toList();
        var end = Arrays
                .stream(split[1].split(","))
                .map(Integer::parseInt)
                .toList();

        return new Block(
                idx,
                new Range(start.get(0), end.get(0)),
                new Range(start.get(1), end.get(1)),
                new Range(start.get(2), end.get(2))
        );
    }

    public boolean isBlocking(Block block) {
        return block.xs.intersects(xs) && block.ys.intersects(ys);
    }

    public void addSupportedBy(Block block) {
        supportedBy.add(block);
    }

    public Range zs() {
        return zs;
    }

    public int height() {
        return zs.length();
    }

    public Set<Block> notDisintegrables() {
        if (supportedBy.size() == 1) {
            return new HashSet<>(supportedBy);
        }

        return Set.of();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Block block)) {
            return false;
        }

        return id == block.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void addSupports(Block block) {
        supports.add(block);
    }

    public long disintegrates(List<Block> pile) {
        Set<Block> fall = fall(pile, pile.get(pile.indexOf(this)));

        return fall.size();
    }

    public static Set<Block> fall(List<Block> pile, Block block) {
        block.supports.forEach(b -> b.removeSupportedBy(block));

        Set<Block> fallingBlocks = block.supports
            .stream()
            .filter(b -> b.supportedBy.isEmpty())
            .collect(Collectors.toSet());


        return fallingBlocks
            .stream()
            .map(b -> fall(pile, b))
            .reduce(fallingBlocks, Utils::union);
    }

    private void removeSupportedBy(Block block) {
        supportedBy.remove(block);
    }

    private List<Block> copy(List<Block> pile) {
        return pile.stream().map(block -> new Block(
            id,
            xs,
            ys,
            zs,
            new HashSet<>(supportedBy),
            new HashSet<>(supports)
        )).toList();
    }
}
