package biddingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Customer implements User {
    private String id;
    private String name;
    private String email;
    private String number;
    private String address;

}
