package rental;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="DelivertProcessing_table")
public class DelivertProcessing {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @PostPersist
    public void onPostPersist(){
        DeliveryCompleted deliveryCompleted = new DeliveryCompleted();
        BeanUtils.copyProperties(this, deliveryCompleted);
        deliveryCompleted.publish();


    }

    @PostRemove
    public void onPostRemove(){
        DeliveryCanceled deliveryCanceled = new DeliveryCanceled();
        BeanUtils.copyProperties(this, deliveryCanceled);
        deliveryCanceled.publish();


    }

    @PrePersist
    public void onPrePersist(){
        DeliveryStarted deliveryStarted = new DeliveryStarted();
        BeanUtils.copyProperties(this, deliveryStarted);
        deliveryStarted.publish();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




}
