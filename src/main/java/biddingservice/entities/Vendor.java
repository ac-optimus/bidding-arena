package biddingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Vendor implements User {
    private String id;
    private String name;
    private String email;
    private String sellerId;

}
