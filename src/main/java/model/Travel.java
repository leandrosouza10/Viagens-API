package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Travel {

    private Long id;
    private String orderNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal amount;
    private TravelTypeEnum type;
    private List<Link> links;


    public Travel(TravelTypeEnum type){
        this.type = type;
    }

    public void addLink(Link link) {
        if(links == null) links = new ArrayList<>();
        links.add(link);
    }
}











