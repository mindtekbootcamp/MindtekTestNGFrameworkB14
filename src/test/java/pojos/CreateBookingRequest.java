package pojos;

import lombok.*;

@Getter
@Setter
@ToString
public class CreateBookingRequest {

    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    public void setDefaultValues(){
        firstname="John";
        lastname="Doe";
        totalprice=111;
        depositpaid=true;
        additionalneeds="Breakfast";
        BookingDates bookingDates=new BookingDates();
        bookingDates.setDefaultValues();
        this.bookingdates=bookingDates;
    }

}
