package com.example.sawxu.au_hm_svg.tool;


import com.example.sawxu.au_hm_svg.CityItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


public class SaxParseXml extends DefaultHandler{  

    private List<CityItem> list;
    private CityItem cityItem;
    private String tagName;  
      
      
    public List<CityItem> getList() {  
        return list;  
    }  
  
  
    public void setList(List<CityItem> list) {  
        this.list = list;  
    }  
  
  
    public CityItem getCityItem() {  
        return cityItem;
    }  
  
  
    public void setCityItem(CityItem student) {  
        this.cityItem = student;
    }  
  
  
    public String getTagName() {  
        return tagName;  
    }  
  
  
    public void setTagName(String tagName) {  
        this.tagName = tagName;  
    }  
  
  
    //只调用一次  初始化list集合    
    @Override  
    public void startDocument() throws SAXException {  
        list=new ArrayList<CityItem>();
    }  
      
      
    //调用多次    开始解析  
    @Override  
    public void startElement(String uri, String localName, String qName,  
            Attributes attributes) throws SAXException {  
        if(qName.equals("path")){
            cityItem =new CityItem();
//            cityItem.setFillColor(Integer.parseInt(attributes.getValue(0).substring(1)));
//            cityItem.setStrokeColor(Integer.parseInt(attributes.getValue(1).substring(1)));
            cityItem.setStrokeWidth(Float.parseFloat(attributes.getValue(2)));
            cityItem.setPath(PathParser.createPathFromPathData(attributes.getValue(3)));
        }  
        this.tagName=qName;  
    }  
      
      
    //调用多次    
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
        if(qName.equals("path")){
            this.list.add(this.cityItem);
        }  
        this.tagName=null;  
    }  
      
      
    //只调用一次  
    @Override  
    public void endDocument() throws SAXException {  
    }  
      
    //调用多次  
    @Override  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {  

    }  
}  