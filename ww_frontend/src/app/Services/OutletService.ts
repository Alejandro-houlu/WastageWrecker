import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { OutletAddressDTO } from "../Models/OutletAddressDTO";
import { PromotionDTO } from "../Models/PromotionDTO";


@Injectable()
export class OutletService{

    constructor(private http: HttpClient){}

    saveOutlet(outletAddressDTO: OutletAddressDTO){

        const formData = new FormData()

        formData.set('storeName', outletAddressDTO.storeName)
        formData.set('unitNumber', outletAddressDTO.unitNumber)
        formData.set('phoneNumber', outletAddressDTO.phoneNumber)
        formData.set('outletPic', outletAddressDTO.outletPic)

        return firstValueFrom(
                this.http.post<any>('/api/address/saveAddress',formData)
            )
    }

    getOutlets():Promise<OutletAddressDTO[]>{

        return firstValueFrom(
            this.http.get<OutletAddressDTO[]>('/api/address/getAddresses')
        )

    }

    getOutlet(addressId:string):Promise<OutletAddressDTO>{

        return firstValueFrom( 
            this.http.get<OutletAddressDTO>(`/api/address/getAddress/${addressId}`)
        )
    }

    savePromotion(promotionDTO:PromotionDTO, addressId:string){

        const formData = new FormData()
        if(promotionDTO.promotionId!==null){formData.set('promotionId',promotionDTO.promotionId)}
        formData.set('itemName', promotionDTO.itemName)
        formData.set('description', promotionDTO.description)
        formData.set('startDate', promotionDTO.startDate.getTime().toString())
        formData.set('endDate',promotionDTO.endDate.getTime().toString())
        formData.set('status', promotionDTO.status)
        formData.set('price', promotionDTO.price)
        formData.set('promoPic',promotionDTO.promoPic)
        formData.set('addressId', addressId)

        return firstValueFrom(
            this.http.post<any>('api/outlet/savePromo', formData)
        )
    }

    getAllPromotions(addressId: string):Promise<PromotionDTO[]>{
        return firstValueFrom(
            this.http.get<PromotionDTO[]>(`api/outlet/getAllPromos/${addressId}`)
        )
    }

}