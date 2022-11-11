import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NGXLogger } from 'ngx-logger';
import { firstValueFrom } from "rxjs";
import { User } from "../Models/User";

@Injectable()
export class SignUpService{
    constructor(private http:HttpClient, private logger: NGXLogger){}

    saveCustomer(user:User):Promise<User>{

        const formData = new FormData()

        formData.set('username', user.username)
        formData.set('password',user.password)
        formData.set('email',user.email)
        formData.set('phoneNumber',user.phoneNumber)
        formData.set('role',user.role)
        formData.set('profilePic',user.profilePic)

        return firstValueFrom(this.http.post<any>('/api/signup/saveCustomer',formData))
    }
}