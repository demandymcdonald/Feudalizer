package com.base.worldforge.lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

class LexerTest {
    @Test
    void smokeTest() {
        String input = """
            sword {
                name: Excalibur
                attack: 10
                // this is a comment
            }
            """;
        List<Token> tokens = Lexer.decode(input);
        tokens.forEach(t -> System.out.println(t.getType().name() + " -> " + t.value()));
    }
}