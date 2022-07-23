package com.hk.kb_graph.actree;

import org.ahocorasick.trie.Trie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ACTreeConfiguration {
    @Bean
    public Trie getTrie(){
        Trie trie = Trie.builder().build();
        return trie;
    }
}
