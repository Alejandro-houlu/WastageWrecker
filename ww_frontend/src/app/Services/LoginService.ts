import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NGXLogger } from 'ngx-logger';
import { firstValueFrom } from "rxjs";
import { LoginDTO } from "../Models/LoginDTO";
import { User } from "../Models/User";

@Injectable()
export class LoginService{
    constructor(private http: HttpClient, private logger: NGXLogger){}

    login(loginDTO:LoginDTO){

        return firstValueFrom(
            this.http.post<any>('/api/login', loginDTO)
        )

    }

    // createBasicAuthToken(email:string, password: string){

    //     return 'Basic ' + window.btoa(email+":"+password)
    // }


    logout(){
        return firstValueFrom(
            this.http.get('/api/logout')
            
        )
    }
}