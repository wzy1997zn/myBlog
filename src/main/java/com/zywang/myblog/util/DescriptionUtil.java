package com.zywang.myblog.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class DescriptionUtil {

    public static String md2html(String md) {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(md);
        String html = renderer.render(document);
        return html;
    }

    public static String html2txt(String html)
    {
        if (StringUtils.isEmpty(html))
        {
            return "";
        }

        Document document = Jsoup.parse(html);
        Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        document.outputSettings(outputSettings);
        document.select("br").append(" ");
        document.select("p").prepend(" ");
        document.select("p").append(" ");
        String newHtml = document.html().replaceAll("\\\\n", " ");
        String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
        String result = StringEscapeUtils.unescapeHtml(plainText.trim()).replaceAll("\n", " ");
        return result;
    }

    public static String generateDescription(String md) {
        String text = html2txt(md2html(md));
        String result = text.substring(0,140) + "...";
        return result;
    }


}
