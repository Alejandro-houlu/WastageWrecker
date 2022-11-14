import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { OutletAndPromoDTO } from "../Models/OutletAndPromoDTO";

@Injectable()
export class SearchService{

    constructor(private http:HttpClient){}

    searchAddress(searchParam:string){
        
         return firstValueFrom(this.http.post<any>('/api/search/', searchParam))
        
    }


}