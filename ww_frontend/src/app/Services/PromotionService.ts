import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NGXLogger } from "ngx-logger";
import { firstValueFrom } from "rxjs";
import { OutletAddressDTO } from "../Models/OutletAddressDTO";
import { OutletAndPromoDTO } from "../Models/OutletAndPromoDTO";
import { PromotionDTO } from "../Models/PromotionDTO";


@Injectable()
export class PromotionService{
    constructor(private logger: NGXLogger, private http:HttpClient){}

    getAllPromotionsAndAddressByUser():Promise<OutletAndPromoDTO[]>{

        return firstValueFrom(
            this.http.get<OutletAndPromoDTO[]>('/api/promotion/owner/all')
        )
    }

    getAddressByPromoId(promotionId:string):Promise<OutletAddressDTO>{
        return firstValueFrom(
            this.http.get<OutletAddressDTO>(`api/promotion/getAddressById/${promotionId}`)
        )
    }
}