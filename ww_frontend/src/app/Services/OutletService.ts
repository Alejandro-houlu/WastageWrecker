import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { OutletAddressDTO } from "../Models/OutletAddressDTO";


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

}