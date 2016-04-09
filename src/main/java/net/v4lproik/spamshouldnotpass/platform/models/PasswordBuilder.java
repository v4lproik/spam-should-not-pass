package net.v4lproik.spamshouldnotpass.platform.models;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordBuilder {

    private final static List<Character> LOWER_CAPS, UPPER_CAPS, DIGITS, SPECIALS;
    private final List<Template> templateList = new ArrayList<>();
    private boolean doShuffle;

    static {
        LOWER_CAPS = new ArrayList<>(26);
        UPPER_CAPS = new ArrayList<>(26);
        for (int i = 0; i < 26; i++) {
            LOWER_CAPS.add((char) (i + 'a'));
            UPPER_CAPS.add((char) (i + 'A'));
        }

        DIGITS = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            DIGITS.add((char) (i + '0'));
        }

        SPECIALS = new ArrayList<Character>() {{
            add('!');
            add('@');
            add('$');
            add('%');
            add('^');
            add('(');
            add(')');
            add('*');
            add('+');
        }};
    }

    public static PasswordBuilder builder() {
        return new PasswordBuilder();
    }


    public PasswordBuilder lowercase(int count) {
        templateList.add(new Template(LOWER_CAPS, count));
        return this;
    }

    public PasswordBuilder uppercase(int count) {
        templateList.add(new Template(UPPER_CAPS, count));
        return this;
    }

    public PasswordBuilder digits(int count) {
        templateList.add(new Template(DIGITS, count));
        return this;
    }

    public PasswordBuilder specials(int count) {
        templateList.add(new Template(SPECIALS, count));
        return this;
    }

    public PasswordBuilder shuffle() {
        doShuffle = true;
        return this;
    }

    public String build() {
        StringBuilder passwordBuilder = new StringBuilder();
        List<Character> characters = new ArrayList<Character>();

        for (Template template : templateList) {
            characters.addAll(template.take());
        }

        if (doShuffle)
            Collections.shuffle(characters);

        for (char chr : characters) {
            passwordBuilder.append(chr);
        }

        return passwordBuilder.toString();
    }

    private static class Template {
        private final List<Character> source;
        private final int count;

        private static final SecureRandom random = new SecureRandom();

        public Template(List<Character> source, int count) {
            this.source = source;
            this.count = count;
        }

        public List<Character> take() {
            List<Character> taken = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                taken.add(source.get(random.nextInt(source.size())));
            }

            return taken;
        }
    }
}