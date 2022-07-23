package com.hk.kb_graph;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class ACTreeTest {
    @Test
    public void test(){
        Trie trie = Trie.builder()
                .addKeyword("hers")
                .addKeyword("his")
                .addKeyword("she")
                .addKeyword("he")
                .addKeyword("你好")
                .build();
        Collection<Emit> emits = trie.parseText("你好你好");
        System.out.println(emits);
    }
}
