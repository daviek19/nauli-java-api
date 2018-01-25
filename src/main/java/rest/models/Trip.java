package rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer tripId;
    private String tripReference;
    private String tripFrom;
    private String tripTo;
    private BigDecimal fareAmount;
    private int tripStatus;
    @Column(columnDefinition = "BINARY(16)")
    private UUID conversationId;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();

    @ManyToOne()
    @JoinColumn(name = "deviceId", nullable = false)
    private Device device;

    @OneToMany(mappedBy = "trip")
    private Set<Payment> payments = new HashSet<>();

    /**
     * @return the payments
     */
    public Set<Payment> getPayments() {
        return payments;
    }

    /**
     * @param payments the payments to set
     */
    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    /**
     * @return the device
     */
    public Device getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     * @return the tripId
     */
    public Integer getId() {
        return tripId;
    }

    /**
     * @param tripId the id to set
     */
    public void setId(Integer tripId) {
        this.tripId = tripId;
    }

    /**
     * @return the tripReference
     */
    public String getTripReference() {
        return tripReference;
    }

    /**
     * @param tripReference the tripReference to set
     */
    public void setTripReference(String tripReference) {
        this.tripReference = tripReference;
    }

    /**
     * @return the tripFrom
     */
    public String getTripFrom() {
        return tripFrom;
    }

    /**
     * @param tripFrom the tripFrom to set
     */
    public void setTripFrom(String tripFrom) {
        this.tripFrom = tripFrom;
    }

    /**
     * @return the tripTo
     */
    public String getTripTo() {
        return tripTo;
    }

    /**
     * @param tripTo the tripTo to set
     */
    public void setTripTo(String tripTo) {
        this.tripTo = tripTo;
    }

    /**
     * @return the fareAmount
     */
    public BigDecimal getFareAmount() {
        return fareAmount;
    }

    /**
     * @param fareAmount the fareAmount to set
     */
    public void setFareAmount(BigDecimal fareAmount) {
        this.fareAmount = fareAmount;
    }

    /**
     * @return the tripStatus
     */
    public int getTripStatus() {
        return tripStatus;
    }

    /**
     * @param tripStatus the tripStatus to set
     */
    public void setTripStatus(int tripStatus) {
        this.tripStatus = tripStatus;
    }

    /**
     * @return the conversationId
     */
    public UUID getConversationId() {
        return conversationId;
    }

    /**
     * @param conversationId the conversationId to set
     */
    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
