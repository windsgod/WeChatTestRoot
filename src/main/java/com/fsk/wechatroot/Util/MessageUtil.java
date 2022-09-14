package com.fsk.wechatroot.Util;

import com.fsk.wechatroot.dao.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: fsk
 * Date: 2022/8/31 13:49
 * FileName: MessageUtil
 * Description: xml转map工具类
 */

public class MessageUtil {

    public static Map<String, String> xmlToMap(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);
        Element root = doc.getRootElement();

        List<Element> list = root.elements();
        for (Element element : list) {
            map.put(element.getName(), element.getText());
        }
        ins.close();
        return map;
    }

    public static String textMessageToxml(TextMessage textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }







}
