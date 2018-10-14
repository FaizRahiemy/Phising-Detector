/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import API.API;
import API.Config;
import API.RetrofitHelper;
import API.Whois.Whois;
import Local.Fuzzy;
import Local.URL;
import View.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author rajar
 */
public class ControllerMenu extends MouseAdapter implements ActionListener, KeyListener{
    
    private Menu menu;
    private API api;
    Config config;
    
    public ControllerMenu(){
        menu = new Menu();
        api = RetrofitHelper.getClient().create(API.class);
        config = new Config();
        
        menu.getTextField().addKeyListener(this);
        menu.getButton().addActionListener(this);
        menu.getBulk().addActionListener(this);
        menu.setVisible(true);
    }
    
    public void detect(){
        menu.getButton().setEnabled(false);
        menu.getBulk().setEnabled(false);
        
        String domain = menu.getTextField().getText();
        URL url = new URL(domain);
        
        Call<Whois> callEvent = api.whois(config.getApiKey(), domain, config.getOutputFormat());
        callEvent.enqueue(new Callback<Whois>() {
            @Override
            public void onResponse(Call<Whois> callEvent, Response<Whois> response) {
                String isi = "";
                double point = 0;
                try{
                    if (response.body().getWhoisRecord().getRegistryData().getRawText().charAt(1) == ' '){
                        isi = isi + "Domain not found in the whois data \n";
                    }else{
                        isi = isi + "Estimated domain age: " + response.body().getWhoisRecord().getEstimatedDomainAge() + " days \n";
                    }
                    if (url.getRank() == -1){
                        isi = isi + "Website Rank: not available \n";
                    }else{
                        isi = isi + "Website Rank: " + url.getRank() + " \n";
                    }
                    isi = isi + "Certificate Issuer: " + url.getCertificate(url.getScrapCertificate());
                    if (url.getPointCertificate(url.getScrapCertificate()) == 0){
                        isi = isi + " (Trusted) \n";
                    }else if (url.getPointCertificate(url.getScrapCertificate()) == 0.2){
                        isi = isi + " (Not Trusted) \n";
                    }
                    isi = isi + "URL Length: " + url.getUrl().length() + " \n";
                    isi = isi + "Dot Count: " + url.countDot() + " \n";
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    isi = isi + "Request Url Percentage: " + numberFormat.format(url.getRequestUrl(url.getSite())) + "% \n";
                    isi = isi + "Url Anchor Percentage: " + numberFormat.format(url.getAnchor(url.getSite())) + "% \n";
                    isi = isi + "Point Website Rank: " + url.getPointRank(url.getRank()) + " \n";
                    point = point + url.getPointRank(url.getRank());
                    isi = isi + "Point Certificate Issuer: " + url.getPointCertificate(url.getScrapCertificate()) + " \n";
                    point = point + url.getPointCertificate(url.getScrapCertificate());
                    isi = isi + "Point Long URL: " + url.getPointUrlLength() + " \n";
                    point = point + url.getPointUrlLength();
                    isi = isi + "Point Suffix: " + url.getPointSuffix() + " \n";
                    point = point + url.getPointSuffix();
                    isi = isi + "Point Dot Count: " + url.getPointDot() + " \n";
                    point = point + url.getPointDot();
                    isi = isi + "Point Request Url: " + url.getPointRequestUrl(url.getRequestUrl(url.getSite()))+ " \n";
                    point = point + url.getPointRequestUrl(url.getRequestUrl(url.getSite()));
                    isi = isi + "Point Url Anchor: " + url.getPointAnchor(url.getAnchor(url.getSite()))+ " \n";
                    point = point + url.getPointAnchor(url.getAnchor(url.getSite()));
                    isi = isi + "Point IP : " + url.validIP()+ " \n";
                    point = point + url.validIP();
                    if (response.body().getWhoisRecord().getRegistryData().getRawText().charAt(1) != ' '){
                        isi = isi + "Point Domain Age : " + response.body().getWhoisRecord().getPoint() + " \n";
                        point = point + response.body().getWhoisRecord().getPoint();
                    }
                    DecimalFormat df = new DecimalFormat("#.##");
                    isi = isi + " \nTotal Point : " + df.format(point) + "";
                    Fuzzy fuzzy = new Fuzzy();
                    isi = isi + " \nStatus : " + fuzzy.doFuzzy(point) + " \n";
                    
                }catch(NullPointerException e){
                    isi = "Website not found";
                }
                menu.getTextArea().setText(isi);
                menu.getButton().setEnabled(true);
                menu.getBulk().setEnabled(true);
            }

            @Override
            public void onFailure(Call<Whois> callEvent, Throwable t) {
            }
        });
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object x = e.getSource();
        if (x.equals(menu.getButton())){
            menu.getTextArea().setText("Loading Data");
            detect();
        }else if (x.equals(menu.getBulk())){
            ControllerBulk bulk = new ControllerBulk();
            menu.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            menu.getTextArea().setText("Loading Data");
            detect();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
