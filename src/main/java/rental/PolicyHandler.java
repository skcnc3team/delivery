package rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
//
//    @StreamListener(KafkaProcessor.INPUT)
//    public void wheneverReserved_예약받기(@Payload Reserved reserved){
//
//        if(reserved.isMe()){
//            System.out.println("##### listener 예약받기 : " + reserved.toJson());
//        }
//    }
    @Autowired
    DeliveryProcessingRepository deliveryProcessingRepository;
    DeliveryProcessing deliveryProcessing = new DeliveryProcessing();

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_예약받기(@Payload Payed payed){

        if(payed.isMe()){
            System.out.println("##### listener 예약받기 : " + payed.toJson());
            //DeliveryProcessing deliveryProcessing = new DeliveryProcessing();

            deliveryProcessing.setStatus("Delivery Start");

            deliveryProcessing.setCarId(payed.getCarId());
            deliveryProcessing.setReservationId(payed.getReervationId());
            deliveryProcessing.setCustomerNm(payed.getCustomerNm());
            deliveryProcessingRepository.save(deliveryProcessing);

            deliveryProcessing.setStatus("Delivery End");

            System.out.println("##### listener 배달 완료 : " + payed.toJson());
            deliveryProcessingRepository.save(deliveryProcessing);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCanceled_예약취소처리(@Payload ReservationCanceled reservationCanceled){

        if(reservationCanceled.isMe()){
            System.out.println("##### listener 예약취소처리 : " + reservationCanceled.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationChanged_예약변경(@Payload ReservationChanged reservationChanged){

        if(reservationChanged.isMe()){
            System.out.println("##### listener 예약변경 : " + reservationChanged.toJson());
            deliveryProcessing.setStatus(" reservation changed");
            deliveryProcessing.setCarId(reservationChanged.getCarId());
            // deliveryProcessing.setId(reservationChanged.getId());
            deliveryProcessing.setCustomerNm(reservationChanged.getCustomerNm());
            deliveryProcessingRepository.save(deliveryProcessing);
        }
    }

}
