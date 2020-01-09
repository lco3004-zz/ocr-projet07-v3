
/*
copier coller openclassrooms

 */
package fr.ocr.utility;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-configs")
public class ApplicationPropertiesConfiguration {

    private int dureePret;

    public int getDureePret() {
        return dureePret;
    }

    public void setDureePret(int dureePret) {
        this.dureePret = dureePret;
    }
}
