package pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchBookingRequest {

    private String firstname;
    private String lastname;

    public void setDefaultValues(){
        firstname="John";
        lastname="Doe";
    }

}
