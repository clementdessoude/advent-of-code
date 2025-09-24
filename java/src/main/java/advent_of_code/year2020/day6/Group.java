package advent_of_code.year2020.day6;

import advent_of_code.utils.CollectionUtils;
import advent_of_code.utils.StringUtils;

import java.util.*;

class Group {

    private final List<String> answers;

    Group() {
        answers = new ArrayList<>();
    }

    void add(String answer) {
        answers.add(answer);
    }

    public long getDistinctAnswers() {
        return answers.stream().flatMapToInt(CharSequence::chars).distinct().count();
    }

    public long getCommonAnswers() {
        return answers
            .stream()
            .map(s -> (Collection<Character>) StringUtils.chars(s))
            .reduce(CollectionUtils::intersection)
            .orElse(Set.of())
            .size();
    }
}
