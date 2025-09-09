package pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateBookingResponse {

    private Integer bookingid;
    private CreateBookingRequest booking;


}
