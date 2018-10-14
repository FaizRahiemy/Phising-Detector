/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Local;

/**
 *
 * @author Faiz Rahiemy
 */
public class Fuzzy {

    public String doFuzzy(Double total) {
        String result = "";
        
        if (total < 0.1){
            result = "TrustWorthy";
        }else if (total < 0.3){
            result = "Fairly Legitimate";
        }else if (total < 0.5){
            result = "Unsolved";
        }else if (total < 0.75){
            result = "Suspicious";
        }else{
            result = "Phisy";
        }
        
        return result;
    }
    
}
