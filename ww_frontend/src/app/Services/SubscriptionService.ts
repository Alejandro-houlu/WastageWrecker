import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { OutletAndPromoDTO } from "../Models/OutletAndPromoDTO";
import { SubscriptionDTO } from "../Models/SubscriptionDTO";

@Injectable()
export class SubscriptionService{
    constructor(private http: HttpClient){}

    subscribe(addressId:string){

        return firstValueFrom(
            this.http.post<any>('/api/subscribe', addressId)
        )

    }

    isSubscribed(addressId:string):Promise<SubscriptionDTO>{
        return firstValueFrom(
            this.http.get<SubscriptionDTO>(`api/isSubscribed/${addressId}`)
        )
    }

    getSubscriptionsByUser():Promise<OutletAndPromoDTO[]>{
        return firstValueFrom(
            this.http.get<OutletAndPromoDTO[]>('/api/getAllUserSubs')
        )
    }

    unsubscribe(addressId:string){
        return firstValueFrom(
            this.http.delete<any>(`/api/unsub/${addressId}`)
        )
    }

    getAllOwnerSubs():Promise<SubscriptionDTO>{
        return firstValueFrom(
            this.http.get<SubscriptionDTO>('/api/getAllOwnerSubs')
        )
    }

}