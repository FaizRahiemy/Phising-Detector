/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.Whois;

/**
 *
 * @author rajar
 */
public class WhoisRecord
{
    
    private RegistryData registryData;
    private String estimatedDomainAge;

    public RegistryData getRegistryData() {
        return registryData;
    }

    public void setRegistryData(RegistryData registryData) {
        this.registryData = registryData;
    }

    public String getEstimatedDomainAge ()
    {
        return estimatedDomainAge;
    }

    public void setEstimatedDomainAge (String estimatedDomainAge)
    {
        this.estimatedDomainAge = estimatedDomainAge;
    }
    
    public double getPoint(){
        if (estimatedDomainAge.isEmpty()){
            return 0.1;
        }else{
            if (Integer.parseInt(estimatedDomainAge) < 180){
                return 0.2;
            }else if (Integer.parseInt(estimatedDomainAge) < 360){
                return 0.1;
            }else{
                return 0;
            }
        }
    }
}
