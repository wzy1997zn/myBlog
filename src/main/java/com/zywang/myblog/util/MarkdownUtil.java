package com.zywang.myblog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

import java.util.*;

public class MarkdownUtil {

    public static String md2html(String md) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(md);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String html = renderer.render(document);
        return html;
    }

    public static String md2htmlExtension(String md) {
        //fit catalog generator
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());

        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());

        Parser parser = Parser.builder().extensions(tableExtension).build();
        Node document = parser.parse(md);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(attributeProviderContext -> new CustomAttributeProvider()).build();
        String html = renderer.render(document);
        return html;
    }

    static class CustomAttributeProvider implements AttributeProvider {

        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            if (node instanceof Link) {
                //make <a> go to new page
                map.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                //fit semantic ui
                map.put("class", "ui celled table");
            }
        }
    }

//    public static void main(String[] args) {
//        System.out.println(MarkdownUtil.md2txt("t"));
//    }

    public static String md2txt(String md)
    {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(md);
        TextContentRenderer renderer = TextContentRenderer.builder().build();
        String text = renderer.render(document);
        return text;
    }

    public static String generateDescription(String md) {
        String text = md2txt(md);
        String result = text.length()>252?(text.substring(0,252) + "..."):text;
        return result;
    }


}
