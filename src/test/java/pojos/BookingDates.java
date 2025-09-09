package pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookingDates {

    private String checkin;
    private String checkout;

    public void setDefaultValues(){
        checkin="2025-09-09";
        checkout="2025-10-10";
    }

}
