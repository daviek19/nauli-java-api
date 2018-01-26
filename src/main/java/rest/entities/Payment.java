package rest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer paymentId;
    private BigDecimal transAmount;
    private Long msisdn;
    @ManyToOne()
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    /**
     * @return the trip
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * @param trip the trip to set
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return paymentId;
    }

    /**
     * @param paymentId the id to set
     */
    public void setId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return the transAmount
     */
    public BigDecimal getTransAmount() {
        return transAmount;
    }

    /**
     * @param transAmount the transAmount to set
     */
    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * @return the msisdn
     */
    public Long getMsisdn() {
        return msisdn;
    }

    /**
     * @param msisdn the msisdn to set
     */
    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

}
