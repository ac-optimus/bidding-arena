package biddingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vendor implements User {
    private String id;
    private String name;
    private String email;
    private String pan;
    private String number;

}
