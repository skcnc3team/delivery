package rental;

public class DeliveryCompleted extends AbstractEvent {

    private Long id;

    public DeliveryCompleted(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
