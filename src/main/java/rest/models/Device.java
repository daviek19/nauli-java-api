package rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;
    private Long deviceId;
    private String pin;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();
    private Boolean isActive;

    @ManyToOne()    
    @JoinColumn(name = "merchantId", nullable = false)
    private Merchant merchant;

    @Column(columnDefinition = "BINARY(16)")
    private UUID conversationId;

    @OneToMany(mappedBy = "device")
    private Set<Trip> trips = new HashSet<>();

    /**
     * @return the trips
     */
    public Set<Trip> getTrips() {
        return trips;
    }

    /**
     * @param trips the trips to set
     */
    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
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
     * @return the merchant
     */
    public Merchant getMerchant() {
        return merchant;
    }

    /**
     * @param merchant the merchant to set
     */
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the deviceId
     */
    public Long getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     */
    public void setPin(String pin) {
        this.pin = pin;
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

    /**
     * @return the isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
