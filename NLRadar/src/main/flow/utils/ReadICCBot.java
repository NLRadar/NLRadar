package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadICCBot {
    public List<Element> readIntentSummaryModel(String dir, String file) {
        List<Element> elements = new ArrayList<>();
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File xmlFile = new File(dir + "/" + file);
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element root = document.getDocumentElement();
            elements.add(root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return elements;
    }

    public static String findMethodValueInComponent(Element componentElement) {
        NodeList children = componentElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node childNode = children.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                System.out.println(childElement.getTagName());
                if (childElement.getTagName().equals("intentSummary")) {
                    NodeList intentSummaryChildren = childElement.getChildNodes();
                    for (int j = 0; j < intentSummaryChildren.getLength(); j++) {
                        Node intentSummaryChildNode = intentSummaryChildren.item(j);
                        if (intentSummaryChildNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element intentSummaryChildElement = (Element) intentSummaryChildNode;
                            if (intentSummaryChildElement.getTagName().equals("method")) {
                                return intentSummaryChildElement.getAttribute("value");
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String finddstmethod(Element element, String component){
        NodeList nodeList = element.getChildNodes();
        String result = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) node;
                if (childElement.getTagName().equals("Component")) {
                    String source = childElement.getAttribute("source");
                    if (source.equals(component)) {
                        result = findMethodValueInComponent(childElement);
                    }
                }
            }
        }
        return result;
    }

    public static List<String> findNodes(Element element, String Component){
        List<String> result = new ArrayList<>();
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) node;
                if (childElement.getTagName().equals("Component")) {
                    String source = childElement.getAttribute("source");
                    if (source.equals(Component)) {
                        NodeList children = childElement.getChildNodes();
                        for (int j = 0; j < children.getLength(); j++) {
                            Node childNode = children.item(j);
                            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element child = (Element) childNode;
                                if (child.getTagName().equals("intentSummary")) {
                                    NodeList intentSummaryChildren = child.getChildNodes();
                                    for (int k = 0; k < intentSummaryChildren.getLength(); k++) {
                                        Node intentSummaryChildNode = intentSummaryChildren.item(k);
                                        if (intentSummaryChildNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element intentSummaryChildElement = (Element) intentSummaryChildNode;
                                            System.out.println(intentSummaryChildElement.getTagName());
                                            if (intentSummaryChildElement.getTagName().equals("nodes")) {
                                                NodeList nodeElements = intentSummaryChildElement.getChildNodes();
                                                for (int l = 0; l < nodeElements.getLength(); l++) {
                                                    Node nodeElement = nodeElements.item(l);
                                                    if (nodeElement.getNodeType() == Node.ELEMENT_NODE) {
                                                        Element nodeEl = (Element) nodeElement;
                                                        if (nodeEl.hasAttribute("unit")) {
                                                            result.add(nodeEl.getAttribute("unit"));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static String findICCdst(Element element, String srcunit) {
        NodeList nodeList = element.getChildNodes();
        String result = null;
        String destination = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) node;
                if (childElement.getTagName().equals("destination")) {
                    destination = childElement.getAttribute("name");
                }
                if (childElement.getTagName().equals("sendICCInfo")) {
                    NodeList info = childElement.getChildNodes();
                    for (int j = 0; j < info.getLength(); j++) {
                        Node childNode = info.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals("info")) {
                            Element infoElement = (Element) childNode;
                            String unitValue = infoElement.getAttribute("unit");
                            System.out.println(unitValue);
                            if (unitValue.equals(srcunit)) {
                                if(destination!=null){
                                    return destination;
                                }
                            }
                        }
                    }
                }
                else{
                    result = findICCdst(childElement, srcunit);
                    if(result != null){
                        return result;
                    }
                }
            }
        }
        return result;
    }

    private static void printElement(Element element, int indent) {
        printIndent(indent);
        System.out.print("<" + element.getTagName());

        // Print attributes
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
        }

        System.out.print(">");
        
        // Print text content if exists
        String textContent = element.getTextContent().trim();
        if (!textContent.isEmpty() && !hasElementChildren(element)) {
            System.out.print(textContent);
        }
        
        System.out.println();

        // Recursively print child elements
        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
            Node childNode = element.getChildNodes().item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                printElement((Element) childNode, indent + 2);
            }
        }

        // Closing tag
        if (hasElementChildren(element)) {
            printIndent(indent);
            System.out.println("</" + element.getTagName() + ">");
        }
    }

    private static boolean hasElementChildren(Element element) {
        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
            if (element.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                return true;
            }
        }
        return false;
    }

    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
    }
}
