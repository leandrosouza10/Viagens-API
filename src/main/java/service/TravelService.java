package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import model.Travel;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TravelService {

    private TravelFactory factory;
    private List<Travel> travels;

    public void createFactory() {
        if (factory == null) {
            factory = new TravelFactoryImpl();
        }
    }

    public void createTravelList() {
        if (travels == null) {
            travels = new ArrayList<>();
        }
    }

    public boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }

    }

    private Long parseId(JSONPObject travel) {
        return Long.valueOf((int) travel.get("id"));
    }

    private BigDecimal parseAmount(JSONPObject travel) {
        return new BigDecimal((String) travel.get("amount"));
    }

    private LocalDateTime parseStarDate(JSONPObject travel) {
        String startDate = travel.get("startDate");
        return ZonedDateTime.parse(startDate).toLocalDateTime();
    }

    private LocalDateTime parseEndData(JSONPObject travel) {
        String endData = travel.get("endDate");
        return ZonedDateTime.parse(endData).toLocalDateTime();
    }

    public boolean isStartDateGreaterThanEndDate(Travel travel) {
        if (travel.getEndDate() == null) return false;
        return travel.getStartDate().isAfter(travel.getEndDate());
    }

    private void setTravelsValues(JSONPObject jsonTravel, Travel travel) {
        String orderNumber = JSONPObject.get("orderNumber");
        String type = JSONPObject.get("type");

        travel.setOrderNumber(orderNumber != null ? orderNumber : travel.getOrderNumber());
        travel.setAmount(jsonTravel.get("amount") != null ? parseAmount(jsonTravel) : travel.getAmount());
        travel.setStartDate(jsonTravel.get("startDate") != null ? parseStarDate(jsonTravel) : travel.getStartDate());
        travel.setEndDate(jsonTravel.get("endDate") != null ? parseEndData(jsonTravel) : travel.getEndDate());
        travel.setType(type != null ? TravelTypeEnum.getEnum(type) : travel.getType());

    }

    public Travel create(JSONPObject jsonTravel) {

        createFactory();

        Travel travel = factory.createTravel((String) jsonTravel.get("type"));
        travel.setId(parseId(Jsontravel));
        setTravelValues(jsonTravel, travel);

        return travel;
    }

    public Travel upDate(Travel travel, JSONPObject jsonTravel) {
        setTravelValue(jsonTravel, travel);
        return travel;
    }

    public void add(Travel travel) {
        createTraveList();
        travels.add(travel);
    }

    public List<Travel> find() {
        createTravelList();
        return travels;
    }

    public Travel findById(long id) {
        return travels.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
    }

    public void delete() {
        travels.clear();
    }

    public void clearObjects() {
        travels = null;
        factory = null;
    }


}


