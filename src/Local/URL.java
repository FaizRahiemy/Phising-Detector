/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Local;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rajar
 */
public class URL {
    private String url;

    public URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    
    public double getPointUrlLength(){
        double point;
        if (url.length() < 54){
            point = 0;
        }else if (url.length() >= 54 && url.length() < 75){
            point = 0.025;
        }else{
            point = 0.05;
        }
        return point;
    }
    
    public double getPointSuffix(){
        double point;
        if (url.contains("-")){
            point = 0.05;
        }else{
            point = 0;
        }
        return point;
    }
    
    public int countDot(){
        int jum = 0;
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '.'){
                jum++;
            }
        }
        return jum;
    }
    
    public double getPointDot(){
        double point;
        if (countDot() < 3){
            point = 0;
        }else if (countDot() == 3){
            point = 0.025;
        }else{
            point = 0.05;
        }
        return point;
    }
    
    public double validIP () {
        
        String chkDomain = url;
        if (chkDomain.contains("https")){
            chkDomain = chkDomain.replace("https://", "");
        }else if (chkDomain.contains("http")){
            chkDomain = chkDomain.replace("http://", "");
        }
        
        chkDomain = chkDomain.split("/")[0];
        try {
            if ( chkDomain == null || chkDomain.isEmpty() ) {
                return 0;
            }

            String[] parts = chkDomain.split( "\\." );
            if ( parts.length != 4 ) {
                return 0;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return 0;
                }
            }
            if ( chkDomain.endsWith(".") ) {
                return 0;
            }

            return 0.025;
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }
    
    public boolean isCharInt(char s)
    {
        try
        {
            String ss = "";
            ss = ss + s;
            Integer.parseInt(ss);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
    
    public double getPointAt(){
        double point;
        if (url.contains("@")){
            point = 0.1;
        }else{
            point = 0;
        }
        return point;
    }

    public int getRank() { 
        int rank = -1;
        try{
            UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
            userAgent.visit("https://www.alexa.com/siteinfo/" + url);                        //visit a url  
            String title = userAgent.doc.findFirst("<strong class=\"metrics-data align-vmiddle\">").getChildText();
            title = title.replace(" ", "");
            title = title.replace("\n", "");
            title = title.replace(",", "");
            rank = Integer.valueOf(title);
        }catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e);
        }catch(NumberFormatException e){
            rank = -1;
        }
        
        return rank;
    }
    
    public double getPointRank(int rank){
        double point;
        if (rank == -1){
            point = 0.2;
        }else if (rank > 150000){
            point = 0.1;
        }else{
            point = 0;
        }
        return point;
    }
    
    public Element getScrapCertificate(){
        
        String chkDomain = url;
        if (chkDomain.contains("https")){
            chkDomain = chkDomain.replace("https://", "");
        }else if (chkDomain.contains("http")){
            chkDomain = chkDomain.replace("http://", "");
        }
        
        chkDomain = chkDomain.split("/")[0];
        
        Element scrap = null;
        try{
            UserAgent userAgent = new UserAgent(); 
            userAgent.visit("https://www.thesslstore.com/ssltools/ssl-checker.php?hostname=" + chkDomain + "#results");

            Element div = userAgent.doc;
            scrap = div;
          }
          catch(JauntException e){                   
            System.err.println(e);
          }
        return scrap;
    }

    public String getCertificate(Element div) { 
        String issuer = "";
        
        Elements lis = div.findEach("<div class=\"chkserver\">").findEach("li");
        Element li = null;
        try {
            if (lis.getElement(2).getTextContent().contains("Organization")){
                li = lis.getElement(5);
            }else{
                li = lis.getElement(4);
            }
            issuer = li.getTextContent().replace("Issuer: ","");
            if (issuer.contains("GeoTrust") || issuer.contains("DigiCert") || issuer.contains("GlobalSign")){
            }else{
                Elements lisc = div.findEach("<div class=\"chkchain\">").findEach("li");
                Element lic = null;
                if (lisc.getElement(1).getTextContent().contains("Organization")){
                    lic = lisc.getElement(5);
                }else{
                    lic = lisc.getElement(4);
                }
                issuer = issuer + "\nChain Certificate: " + lic.getTextContent().replace("Issuer: ","");
            }
        } catch (NotFound ex) {
            issuer = "No Certificate\n";
        } catch(Exception e){                   
            issuer = "No Certificate\n";
        }
        
        return issuer;
    }
    
    public double getPointCertificate(Element div){
        double point = 0.25;
        String cert;
        
        Elements lis = div.findEach("<div class=\"chkserver\">").findEach("li");
        Element li = null;
        try {
            if (lis.getElement(2).getTextContent().contains("Organization")){
                li = lis.getElement(5);
            }else{
                li = lis.getElement(4);
            }
            cert = li.getTextContent().replace("Issuer: ","");
            if (cert.contains("GeoTrust") || cert.contains("DigiCert") || cert.contains("GlobalSign")){
                point = 0;
            }else{
                Elements lisc = div.findEach("<div class=\"chkchain\">").findEach("li");
                Element lic = null;
                if (lisc.getElement(1).getTextContent().contains("Organization")){
                    lic = lisc.getElement(5);
                }else{
                    lic = lisc.getElement(4);
                }
                cert = lic.getTextContent().replace("Issuer: ","");
                if (cert.contains("GeoTrust") || cert.contains("DigiCert") || cert.contains("GlobalSign")){
                    point = 0;
                }else{
                    point = 0.2;
                }
            }
        } catch (NotFound ex) {
            point = 0.3;
        }
        
        return point;
    }
    
    public Element getSite(){
        Element scrap = null;
        
        String chkDomain = url;
        if (!chkDomain.split("/")[0].contains("http")){
            chkDomain = "http://" + chkDomain;
        }
        
        try{
            UserAgent userAgent = new UserAgent(); 
            userAgent.visit(chkDomain);

            Element div = userAgent.doc;
            scrap = div;
          }
          catch(JauntException e){                   
            System.err.println(e);
          }
        return scrap;
    }
    
    public double getRequestUrl(Element div){
        double percent = 0;
        
        String chkDomain = url;
        if (chkDomain.contains("https")){
            chkDomain = chkDomain.replace("https://", "");
        }else if (chkDomain.contains("http")){
            chkDomain = chkDomain.replace("http://", "");
        }
        chkDomain = chkDomain.split("/")[0];
        
        List<String> lis = div.findAttributeValues("<img src='.*'>");
        double asli = 0;
        for (int i = 0; i < lis.size(); i++) {
            if (lis.get(i).contains(chkDomain)){
                asli++;
            }
        }
        
        if (lis.size() != 0){
            percent = (((double)lis.size()-asli)/lis.size())*100;
        }
        
        return percent;
    }
    
    public double getPointRequestUrl(double percent){
        double point = 0;
        
        if (percent < 22){
            point = 0;
        }else if (percent >= 22 && percent < 61){
            point = 0.05;
        }else{
            point = 0.1;
        }
        
        return point;
    }
    
    public double getAnchor(Element div){
        double percent = 0;
        
        String chkDomain = url;
        if (chkDomain.contains("https")){
            chkDomain = chkDomain.replace("https://", "");
        }else if (chkDomain.contains("http")){
            chkDomain = chkDomain.replace("http://", "");
        }
        chkDomain = chkDomain.split("/")[0];
        
        List<String> lis = div.findAttributeValues("<a href>");
        double asli = 0;
        for (int i = 0; i < lis.size(); i++) {
            if (lis.get(i).contains(chkDomain)){
                asli++;
            }
        }
        
        if (lis.size() != 0){
            percent = (((double)lis.size()-asli)/lis.size())*100;
        }
        
        return percent;
    }
    
    public double getPointAnchor(double percent){
        double point = 0;
        
        if (percent < 22){
            point = 0;
        }else if (percent >= 22 && percent < 61){
            point = 0.0125;
        }else{
            point = 0.025;
        }
        
        return point;
    }
}
