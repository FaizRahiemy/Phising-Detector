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
import Local.BulkItem;
import Local.Fuzzy;
import Local.URL;
import View.BulkMenu;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author rajar
 */
public class ControllerBulk extends MouseAdapter implements ActionListener, KeyListener{
    
    private BulkMenu bulkMenu;
    private API api;
    Config config;
    String isi;
    int index;
    DecimalFormat df;
    double point;
    String pointItems;
    String status;
    
    public ControllerBulk(){
        bulkMenu = new BulkMenu();
        api = RetrofitHelper.getClient().create(API.class);
        config = new Config();
        df = new DecimalFormat("#.##");
        
        bulkMenu.getTblList().setModel((DefaultTableModel) bulkMenu.getTblList().getModel());
        bulkMenu.getTblList().setPreferredSize(new Dimension(bulkMenu.getTblList().getWidth(),0));
        
        bulkMenu.getButton().addActionListener(this);
        bulkMenu.getBulk().addActionListener(this);
        bulkMenu.setVisible(true);
    }
    
    public void detect(){
        isi = "";
        index = 0;
        ArrayList<BulkItem> list = new ArrayList<>();
        DefaultTableModel tabel = (DefaultTableModel)bulkMenu.getTblList().getModel();
        bulkMenu.getTblList().setPreferredSize(new Dimension(bulkMenu.getTblList().getWidth(),0));
        
        for (String line : bulkMenu.getField().getText().split("\\n")){
            String domain = line;
            URL url = new URL(domain);
            Fuzzy fuzzy = new Fuzzy();
            point = 0;

            Call<Whois> callEvent = api.whois(config.getApiKey(), domain, config.getOutputFormat());
            callEvent.enqueue(new Callback<Whois>() {
                @Override
                public void onResponse(Call<Whois> callEvent, Response<Whois> response) {
                    try{
                        point = point + url.getPointRank(url.getRank());
                        point = point + url.getPointCertificate(url.getScrapCertificate());
                        point = point + url.getPointRequestUrl(url.getRequestUrl(url.getSite()));
                        point = point + url.getPointAnchor(url.getAnchor(url.getSite()));
                        point = point + url.getPointUrlLength();
                        point = point + url.getPointSuffix();
                        point = point + url.getPointDot();
                        point = point + url.validIP();
                        if (response.body().getWhoisRecord().getRegistryData().getRawText().charAt(1) != ' '){
                            point = point + response.body().getWhoisRecord().getPoint();
                        }
                        isi = isi + index + ". " + domain + " ";
                        if (response.body().getWhoisRecord().getRegistryData().getRawText().charAt(1) == ' '){
                            isi = isi + "Domain not found in the whois data \n";
                        }
                        isi = isi + " Point : " + df.format(point) + " ";
                        isi = isi + " Status : " + fuzzy.doFuzzy(point) + " \n";
                        pointItems = String.valueOf(df.format(point));
                        status = fuzzy.doFuzzy(point);
                    }catch(NullPointerException e){
                        isi = isi + index + ". " + domain + ": Website not found \n";
                        pointItems = "-";
                        status = "Website not found";
                    }
                    System.out.println(isi);
                    index++;
                    list.add(new BulkItem(domain,pointItems,status));
                    tabel.addRow(new Object[]{index, list.get(index-1).getUrl(), list.get(index-1).getPoint(), list.get(index-1).getStatus()});
                    bulkMenu.getTblList().setPreferredSize(new Dimension(bulkMenu.getTblList().getWidth(),(list.size())*bulkMenu.getTblList().getRowHeight()));
                }

                @Override
                public void onFailure(Call<Whois> callEvent, Throwable t) {
                }
            });
        };
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object x = e.getSource();
        if (x.equals(bulkMenu.getButton())){
            detect();
        }else if (x.equals(bulkMenu.getBulk())){
            ControllerMenu menu = new ControllerMenu();
            bulkMenu.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
