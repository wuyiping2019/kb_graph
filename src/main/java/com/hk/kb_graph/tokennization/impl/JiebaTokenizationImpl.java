package com.hk.kb_graph.tokennization.impl;

import com.hk.kb_graph.tokennization.Tokenization;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
public class JiebaTokenizationImpl implements Tokenization {

    private static final String regexp = "[`~!@#$%^&*()+=|{}':;'》《\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
    private static List<String> stopWords;

    //    @Value("${tokenization.jieba.stopword}")
//    private static String stopWords;
    static {
        try {
            stopWords = getStopWords("tokenization/stop_words.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> tokenize(String inputStr) {
        String replaceAll = inputStr.replaceAll(regexp, "");
//        String content = "《开端》《镜双城》《淘金》三部热播剧均有她，你发现了吗？什么";
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> result = new ArrayList<>();
        segmenter.sentenceProcess(replaceAll).stream().forEach((word)->{
            if(!stopWords.contains(word)){
                result.add(word);
            }
        });
        return result;
    }

    public static List<String> getStopWords(String stopWordFile) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + stopWordFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<String> stopWords = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            stopWords.add(line);
        }
        return stopWords;
    }

    public static void main(String[] args) throws IOException {
        JiebaTokenizationImpl jiebaTokenization = new JiebaTokenizationImpl();
        List<String> stopWords = jiebaTokenization.tokenize("《开端》《镜双城》《淘金》三部热播剧均有她，你发现了吗？什么");
        System.out.println(stopWords);
    }
}
