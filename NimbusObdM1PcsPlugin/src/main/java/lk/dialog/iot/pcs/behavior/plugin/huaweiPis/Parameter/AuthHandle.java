package lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Parameter;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "singleton")
@Component
public class AuthHandle {
    private String accessToken = "";
    private String refreshToken = "";

    public AuthHandle() {

    }

    public String getaccessToken() {
        return this.accessToken;
    }

    public void setaccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setrefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getrefreshToken() {
        return this.refreshToken;
    }
}